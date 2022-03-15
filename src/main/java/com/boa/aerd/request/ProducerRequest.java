package com.boa.aerd.request;

public class ProducerRequest {
    private Payload payload;
    private String  key;


    public ProducerRequest() {
    }

    public ProducerRequest(Payload payload, String key) {
        this.payload = payload;
        this.key = key;
    }

    public Payload getPayload() {
        return this.payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ProducerRequest payload(Payload payload) {
        this.payload = payload;
        return this;
    }

    public ProducerRequest key(String key) {
        this.key = key;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " payload:'" + getPayload() + "'" +
            ", key:'" + getKey() + "'" +
            "}";
    }
    
}