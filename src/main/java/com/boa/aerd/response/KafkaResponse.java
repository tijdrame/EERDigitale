package com.boa.aerd.response;

import java.time.Instant;

public class KafkaResponse{

    private Integer code;
    private String description;
    private Instant dateResponse;
    private Data data = new Data();

    public KafkaResponse() {
    }

    public KafkaResponse(Data data, Integer code, String description, Instant dateResponse) {
        this.data = data;
        this.code = code;
        this.description = description;
        this.dateResponse = dateResponse;
    }

    public Data getData() {
        return this.data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getDateResponse() {
        return this.dateResponse;
    }

    public void setDateResponse(Instant dateResponse) {
        this.dateResponse = dateResponse;
    }

    public KafkaResponse data(Data data) {
        this.data = data;
        return this;
    }

    public KafkaResponse code(Integer code) {
        this.code = code;
        return this;
    }

    public KafkaResponse description(String description) {
        this.description = description;
        return this;
    }

    public KafkaResponse dateResponse(Instant dateResponse) {
        this.dateResponse = dateResponse;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " data='" + getData() + "'" +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", dateResponse='" + getDateResponse() + "'" +
            "}";
    }


}