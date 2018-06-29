import com.google.gson.Gson;
import com.rodolfoguinez.lightningchargeclient.Info;
import com.rodolfoguinez.lightningchargeclient.Invoice;
import com.rodolfoguinez.lightningchargeclient.LightningChargeClient;
import com.rodolfoguinez.lightningchargeclient.PaymentEventHandler;
import org.junit.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class LightningChargeClientTest {
    @Test
    public void restTest() throws InterruptedException {
        Logger logger = LogManager.getLogger("dsfa");
        Gson gson = new Gson();
        PaymentEventHandler paymentEventHandler = (Invoice invoice) -> {
            System.out.println("New payment received: " + invoice.getId());
        };

        LightningChargeClient client = new LightningChargeClient(
                "http://localhost",
                "9112",
                "api-token",
                "asd123",
                paymentEventHandler);
        client.start();
        client.setLogger(logger);
        Info info = client.getInfo();
        System.out.println(gson.toJson(info));
        client.postEmptyInvoice();

        Invoice invoice = new Invoice();
        invoice.setCurrency("USD");
        invoice.setMsatoshi("10");
        List<Invoice> invoiceList = client.getInvoiceList();

        Thread.sleep(10000);
    }
}
