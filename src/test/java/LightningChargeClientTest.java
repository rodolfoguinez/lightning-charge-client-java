import com.google.gson.Gson;
import com.rodolfoguinez.lightningchargeclient.Info;
import com.rodolfoguinez.lightningchargeclient.Invoice;
import com.rodolfoguinez.lightningchargeclient.LightningChargeClient;
import com.rodolfoguinez.lightningchargeclient.PaymentEventHandler;
import org.junit.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LightningChargeClientTest {
    @Test
    public void restTest() throws InterruptedException {
        Logger logger = LogManager.getLogger(LightningChargeClientTest.class);
        Gson gson = new Gson();
        PaymentEventHandler paymentEventHandler = new PaymentEventHandler() {
            @Override
            public void onPaymentReceived(Invoice invoice) {
                System.out.println("New payment received: " + invoice);
            }
        };

        LightningChargeClient client = new LightningChargeClient(
                "http://localhost",
                "9112",
                "api-token",
                "asd123",
                paymentEventHandler);
        client.start();
        Info info = client.getInfo();
        System.out.println(gson.toJson(info));
        client.postEmptyInvoice();

        Thread.sleep(10000);
    }
}
