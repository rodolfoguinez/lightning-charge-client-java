package com.rodolfoguinez.lightningchargeclient;

import com.google.gson.*;
import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.MessageEvent;

import java.lang.reflect.Type;

class SseEventHandler implements EventHandler {

    private Gson gson;
    private PaymentEventHandler paymentEventHandler;

	public SseEventHandler(PaymentEventHandler paymentEventHandler){
		this.paymentEventHandler = paymentEventHandler;
		this.gson = new GsonBuilder().registerTypeAdapter(Long.class, new JsonDeserializer() {
			@Override
			public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
				return jsonElement.getAsLong() * 1000; // because light charge response works with seconds, and java with millis
			}
		}).create();
	}

	@Override
	public void onOpen() throws Exception {
		LogFacade.getInstance().debug("LightningChargeClient SSE Open");
	}

	@Override
	public void onClosed() throws Exception {
		LogFacade.getInstance().debug("LightningChargeClient SSE Closed");
	}

	@Override
	public void onMessage(String event, MessageEvent messageEvent) throws Exception {
		String data = messageEvent.getData();
		LogFacade.getInstance().debug("LightningChargeClient SSE Message");
		LogFacade.getInstance().debug(data);
		Invoice invoice = this.gson.fromJson(data, Invoice.class);
		paymentEventHandler.onPaymentReceived(invoice);
	}

	@Override
	public void onComment(String comment) throws Exception {
		LogFacade.getInstance().debug("LightningChargeClient SSE Comment: " + comment);
	}

	@Override
	public void onError(Throwable t) {
		LogFacade.getInstance().error("LightningChargeClient SSE Error: ", t);
	}

}
