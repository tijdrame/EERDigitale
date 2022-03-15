package com.boa.aerd.response;

import java.time.Instant;
import java.time.LocalDate;

public class GenericResponse {
    private int code;
    private String description;
    private Instant dateResponse;
    private String idClient;

public String getIdClient() {
	return this.idClient;
}
public void setIdClient(String idClient) {
	this.idClient = idClient;
}


public int getCode() {
	return this.code;
}
public void setCode(int code) {
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
}