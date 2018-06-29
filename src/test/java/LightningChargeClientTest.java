import com.google.gson.Gson;
import com.rodolfoguinez.lightningchargeclient.Info;
import com.rodolfoguinez.lightningchargeclient.Invoice;
import com.rodolfoguinez.lightningchargeclient.LightningChargeClient;
import com.rodolfoguinez.lightningchargeclient.PaymentEventHandler;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class LightningChargeClientTest {
    private LightningChargeClient lightningClient;

    @Before
    public void before(){
        Logger logger = LogManager.getLogger("LighningChargeClient");
        PaymentEventHandler paymentEventHandler = (Invoice invoice) -> {
            // Here you can handle your received payment
            System.out.println("New payment received: " + invoice.getId());
        };
        String host = "http://localhost";
        String port = "9112";
        String user = "api-token";
        String password = "xxxxx";

        lightningClient = new LightningChargeClient(host, port, user, password, paymentEventHandler, logger);
    }

    @Test
    public void getInfo(){
        Info info = lightningClient.getInfo();
        System.out.println(info.getId() + ", " + info.getPort() + ", " + info.getVersion());
    }

    @Test
    public void sendEmptyInvoice(){
        Invoice invoice = lightningClient.postEmptyInvoice();
        System.out.println("Empty invoice created with id: " + invoice.getId());
    }

    @Test
    public void sendCurrencyInvoice(){
        Invoice invoice = new Invoice();
        invoice.setCurrency("USD");
        invoice.setAmount("10");
        invoice = lightningClient.postInvoice(invoice);
        System.out.println("Invoice created with id: " + invoice.getId());
    }

    @Test
    public void sendSatoshiInvoice(){
        Invoice invoice = new Invoice();
        invoice.setMsatoshi("10000"); // 10.000 msatoshi = 10 satoshi
        invoice = lightningClient.postInvoice(invoice);
        System.out.println("Invoice created with id: " + invoice.getId());
    }

    @Test
    public void getSpecificInvoice(){
        String id = "OYwwaOQAPMFvg039gj_Rb";
        Invoice invoice = lightningClient.getInvoice(id);
        System.out.println("Status of invoice id " + id + ": " + invoice.getStatus());
    }

    @Test
    public void getAllInvoices(){
        List<Invoice> invoiceList = lightningClient.getInvoiceList();
        Assertions.assertThat(invoiceList).isNotNull();
    }
}
