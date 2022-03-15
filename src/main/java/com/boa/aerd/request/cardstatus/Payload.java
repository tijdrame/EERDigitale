package com.boa.aerd.request.cardstatus;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "action", "client", "headers" })
public class Payload {

    @JsonProperty("action")
    private String action;
    @JsonProperty("client")
    private List<Client> client = new ArrayList<>();
    @JsonProperty("headers")
    private Headers headers;

    @JsonProperty("action")
    public String getAction() {
        return action;
    }

    @JsonProperty("action")
    public void setAction(String action) {
        this.action = action;
    }


    public List<Client> getClient() {
        return this.client;
    }

    public void setClient(List<Client> client) {
        this.client = client;
    }
    

    @JsonProperty("headers")
    public Headers getHeaders() {
        return headers;
    }

    @JsonProperty("headers")
    public void setHeaders(Headers headers) {
        this.headers = headers;
    }

    @Override
    public String toString() {
        return "{" +
            " action='" + action + "'" +
            ", client='" + client + "'" +
            ", headers='" + headers + "'" +
            "}";
    }

}