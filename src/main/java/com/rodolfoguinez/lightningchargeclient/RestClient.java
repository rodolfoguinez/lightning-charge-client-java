package com.rodolfoguinez.lightningchargeclient;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.List;

@Headers("Content-Type: application/json")
interface RestClient {
    @RequestLine("GET /info")
    Info getInfo();

    @RequestLine("POST /invoice")
    Invoice postInvoice(Invoice invoice);

    @RequestLine("POST /invoice")
    Invoice postEmptyInvoice();

    @RequestLine("GET /invoices")
    List<Invoice> getInvoiceList();

    @RequestLine("GET /invoice/{id}")
    Invoice getInvoice(@Param("id") String id);
}
