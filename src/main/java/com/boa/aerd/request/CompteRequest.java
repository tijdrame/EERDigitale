package com.boa.aerd.request;

public class CompteRequest {
    private String customerId;
    private String rubComptable;
    private String devise;
    private String agence;
    private String typeCarte;
    private String idProduit;
    private String cExploitant;
    private String intCompte;

public String getDevise() {
	return this.devise;
}
public void setDevise(String devise) {
	this.devise = devise;
}


    public String getAgence() {
    	return this.agence;
    }
    public void setAgence(String agence) {
    	this.agence = agence;
    }


    public String getTypeCarte() {
    	return this.typeCarte;
    }
    public void setTypeCarte(String typeCarte) {
    	this.typeCarte = typeCarte;
    }


    public String getIdProduit() {
    	return this.idProduit;
    }
    public void setIdProduit(String idProduit) {
    	this.idProduit = idProduit;
    }


    public String getCExploitant() {
    	return this.cExploitant;
    }
    public void setCExploitant(String cExploitant) {
    	this.cExploitant = cExploitant;
    }


    public String getIntCompte() {
    	return this.intCompte;
    }
    public void setIntCompte(String intCompte) {
    	this.intCompte = intCompte;
    }


public String getCustomerId() {
	return this.customerId;
}
public void setCustomerId(String customerId) {
	this.customerId = customerId;
}


    public String getRubComptable() {
    	return this.rubComptable;
    }
    public void setRubComptable(String rubComptable) {
    	this.rubComptable = rubComptable;
    }


}