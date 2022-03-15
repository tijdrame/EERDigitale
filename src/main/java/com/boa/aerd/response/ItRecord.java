package com.boa.aerd.response;

public class ItRecord {
    private String key, value;

    public ItRecord() {
    }

    public ItRecord(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ItRecord key(String key) {
        this.key = key;
        return this;
    }

    public ItRecord value(String value) {
        this.value = value;
        return this;
    }
    
    @Override
    public String toString() {
        return "{" +
            " key='" + getKey() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }

}
