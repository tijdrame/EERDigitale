package com.boa.aerd.request.cardstatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "idclient", "cardno", "statusCode", "statusLib", "createdAt", "countryCode" })
public class Client {

    @JsonProperty("idclient")
    private String idclient;
    @JsonProperty("cardno")
    private String cardno;
    @JsonProperty("statusCode")
    private String statusCode;
    @JsonProperty("statusLib")
    private String statusLib;
    @JsonProperty("createdAt")
    private String createdAt;
    @JsonProperty("countryCode")
    private String countryCode;

    @JsonProperty("idclient")
    public String getIdclient() {
        return idclient;
    }

    @JsonProperty("idclient")
    public void setIdclient(String idclient) {
        this.idclient = idclient;
    }

    @JsonProperty("cardno")
    public String getCardno() {
        return cardno;
    }

    @JsonProperty("cardno")
    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    @JsonProperty("statusCode")
    public String getStatusCode() {
        return statusCode;
    }

    @JsonProperty("statusCode")
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    @JsonProperty("statusLib")
    public String getStatusLib() {
        return statusLib;
    }

    @JsonProperty("statusLib")
    public void setStatusLib(String statusLib) {
        this.statusLib = statusLib;
    }

    @JsonProperty("createdAt")
    public String getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("createdAt")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("countryCode")
    public String getCountryCode() {
        return countryCode;
    }

    @JsonProperty("countryCode")
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public String toString() {
        return "{" +
            " idclient='" + idclient + "'" +
            ", cardno='" + cardno + "'" +
            ", statusCode='" + statusCode + "'" +
            ", statusLib='" + statusLib + "'" +
            ", createdAt='" + createdAt + "'" +
            ", countryCode='" + countryCode + "'" +
            "}";
    }

}