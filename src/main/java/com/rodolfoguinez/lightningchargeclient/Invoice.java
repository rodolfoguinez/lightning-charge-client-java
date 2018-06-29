package com.rodolfoguinez.lightningchargeclient;

public class Invoice {

    private String id;
    private String msatoshi;
    private String currency;
    private String amount;
    private String quoted_currency;
    private String quoted_amount;
    private String status;
    private String rhash;
    private String payreq;
    private Long created_at;
    private Long expires_at;
    private Long paid_at;
    private String metadata;
    private Integer user_id;
    private Integer credits;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsatoshi() {
        return msatoshi;
    }

    public void setMsatoshi(String msatoshi) {
        this.msatoshi = msatoshi;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getQuoted_currency() {
        return quoted_currency;
    }

    public void setQuoted_currency(String quoted_currency) {
        this.quoted_currency = quoted_currency;
    }

    public String getQuoted_amount() {
        return quoted_amount;
    }

    public void setQuoted_amount(String quoted_amount) {
        this.quoted_amount = quoted_amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRhash() {
        return rhash;
    }

    public void setRhash(String rhash) {
        this.rhash = rhash;
    }

    public String getPayreq() {
        return payreq;
    }

    public void setPayreq(String payreq) {
        this.payreq = payreq;
    }

    public Long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Long created_at) {
        this.created_at = created_at;
    }

    public Long getExpires_at() {
        return expires_at;
    }

    public void setExpires_at(Long expires_at) {
        this.expires_at = expires_at;
    }

    public Long getPaid_at() {
        return paid_at;
    }

    public void setPaid_at(Long paid_at) {
        this.paid_at = paid_at;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    Long getDuration() {
        return expires_at.longValue() - created_at.longValue();
    }

}
