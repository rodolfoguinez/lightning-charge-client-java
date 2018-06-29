# Lightning Charge Client Java
Java client for the [Lightning Charge REST API](https://github.com/ElementsProject/lightning-charge)
## Install
### By maven dependency
I'm working on it...

### By yourself
1. Clone and build, skipping tests.
    ```bash
        git clone https://github.com/rodolfoguinez/lightning-charge-client-java.git
        cd lightning-charge-client-java
        mvn clean package -Dmaven.test.skip=true
    ```
2. Then add `target/lightning-charge-client-1.0.jar` to your project.

## Use
### Creating the client object
Create a handler for the payments received. Then create the client library object, providing the `host`, `port`, `user` and `password` of your [Lightning Charge](https://github.com/ElementsProject/lightning-charge), and the payment handler.

```java
PaymentEventHandler paymentEventHandler = (Invoice invoice) -> {
    // Here you can handle your received payment 
    System.out.println("New payment received: " + invoice.getId());
};
String host = "http://localhost";
String port = "9112";
String user = "api-token";
String password = "xxxxx";

LightningChargeClient lightningClient = new LightningChargeClient(host, port, user, password, paymentEventHandler);
```

If you want, you can set a [Log4j](https://logging.apache.org/log4j/2.0/) Logger to the client
```java
Logger logger = LogManager.getLogger("LighningChargeClient");
lightningClient.setLogger(logger);
```

### Make requests to Lightning Charge
* Getting the server info
    ```java
    Info info = lightningClient.getInfo();
    System.out.println(info.getId() + ", " + info.getPort() + ", " + info.getVersion());
    ```
* Send a empty invoice
    ```java
    Invoice invoice = lightningClient.postEmptyInvoice();
    System.out.println("Empty invoice created with id: " + invoice.getId());
    ```
* Send a $10 USD invoice
    ```java
    Invoice invoice = new Invoice();
    invoice.setCurrency("USD");
    invoice.setAmount("10");
    invoice = lightningClient.postInvoice(invoice);
    System.out.println("Invoice created with id: " + invoice.getId());
    ```

* Send a 10 Satoshi invoice
    ```java
    Invoice invoice = new Invoice();
    invoice.setMsatoshi("10000"); // 10.000 msatoshi = 10 satoshi
    invoice = lightningClient.postInvoice(invoice);
    System.out.println("Invoice created with id: " + invoice.getId());
  
* Get a specific invoice
  ```java
  Invoice invoice = lightningClient.getInvoice(id);
  System.out.println("Status of invoice id " + id + ": " + invoice.getStatus());
  ```
* Get all the invoices
    ```java
    List<Invoice> invoiceList = lightningClient.getInvoiceList();
    //...
    ```
### Receive payments
As I said before, you have to implement the `PaymentEventHandler` interface, and pass it to `LightningChargeClient` constructor.
To do this, you can create a new class that implements `PaymentEventHandler`, or you can use lambda and make it easier.
```java
PaymentEventHandler paymentEventHandler = (Invoice invoice) -> {
    // Here you can handle your received payment 
    System.out.println("New payment received: " + invoice.getId());
};
LightningChargeClient lightningClient = new LightningChargeClient(host, port, user, password, paymentEventHandler);
```

## License
The MIT License (MIT)

Copyright (c) 2018 Rodolfo Guíñez Espinoza

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.