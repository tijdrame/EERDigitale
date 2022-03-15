package com.boa.aerd.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Data {
    private List<ItRecord> records = new ArrayList<>();

    public Data() {
    }

    public Data(List<ItRecord> records) {
        this.records = records;
    }

    public List<ItRecord> getRecords() {
        return this.records;
    }

    public void setRecords(List<ItRecord> records) {
        this.records = records;
    }

    public Data records(List<ItRecord> records) {
        this.records = records;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " records='" + getRecords() + "'" +
            "}";
    }

}