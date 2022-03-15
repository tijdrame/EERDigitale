package com.boa.aerd.request;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "idclient", "idp", "statusCode", "createdAt", "countryCode" })
public class Client {

    @JsonProperty("idclient")
    private String idclient;
    @JsonProperty("idp")
    private String idp;
    @JsonProperty("statusCode")
    private String statusCode;
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

    @JsonProperty("idp")
    public String getIdp() {
        return idp;
    }

    @JsonProperty("idp")
    public void setIdp(String idp) {
        this.idp = idp;
    }

    @JsonProperty("statusCode")
    public String getStatusCode() {
        return statusCode;
    }

    @JsonProperty("statusCode")
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
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
            " idclient:'" + idclient + "'" +
            ", idp:'" + idp + "'" +
            ", statusCode:'" + statusCode + "'" +
            ", createdAt:'" + createdAt + "'" +
            ", countryCode:'" + countryCode + "'" +
            "}";
    }

}