package com.rodolfoguinez.lightningchargeclient;

import com.launchdarkly.eventsource.EventSource;
import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.Logger;

import java.net.URI;
import java.util.List;

public class LightningChargeClient{
    private RestClient restClient;
    private EventSource sseClient;
    private String baseUrl;
    private String user;
    private String password;
    private PaymentEventHandler paymentEventHandler;

    public LightningChargeClient (String host, String port, String user, String password, PaymentEventHandler paymentEventHandler){
        this.baseUrl = host + ":" +  port;
        this.user = user;
        this.password = password;
        this.paymentEventHandler = paymentEventHandler;
    }

    public LightningChargeClient (String host, String port, String user, String password, PaymentEventHandler paymentEventHandler, Logger logger){
        this.baseUrl = host + ":" +  port;
        this.user = user;
        this.password = password;
        this.paymentEventHandler = paymentEventHandler;
        LogFacade.getInstance().setLogger(logger);
    }

    public void start(){
        LogFacade.getInstance().debug("Starting LightningChargeClient...");
        restClient = Feign.builder()
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .logger(new feign.Logger.NoOpLogger())
                .logLevel(feign.Logger.Level.FULL)
                .requestInterceptor(new BasicAuthRequestInterceptor(user, password))
                .target(RestClient.class, baseUrl);

        SseEventHandler eventHandler = new SseEventHandler(paymentEventHandler);
        EventSource.Builder builder = new EventSource.Builder(eventHandler, URI.create(baseUrl+"/payment-stream"));
        String authString = user + ":" + password;
        byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
        String authStringEnc = new String(authEncBytes);
        builder.headers(okhttp3.Headers.of("Authorization", "Basic " + authStringEnc));
        sseClient = builder.build();
        sseClient.start();
        LogFacade.getInstance().debug("LightningChargeClient started");
    }

    public void stop(){
        sseClient.close();
    }

    public Info getInfo(){
        return restClient.getInfo();
    }

    public Invoice postInvoice(Invoice invoice){
        return restClient.postInvoice(invoice);
    }

    public Invoice postEmptyInvoice(){
        return restClient.postEmptyInvoice();
    }

    public List<Invoice> getInvoiceList(){
        return restClient.getInvoiceList();
    }

    public Invoice getInvoice(String id){
        return restClient.getInvoice(id);
    }

    public void setLogger(Logger logger) {
        LogFacade.getInstance().setLogger(logger);
    }
}
