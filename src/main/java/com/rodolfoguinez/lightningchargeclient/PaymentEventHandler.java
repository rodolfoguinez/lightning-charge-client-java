package com.rodolfoguinez.lightningchargeclient;

public interface PaymentEventHandler {
    void onPaymentReceived(Invoice invoice);
}
