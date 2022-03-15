package com.boa.aerd.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Compte.
 */
@Entity
@Table(name = "compte")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Compte implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "compte_seq_gen")
    @SequenceGenerator(name = "compte_seq_gen", sequenceName = "compte_id_seq", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "intitule_compte")
    private String intituleCompte;

    @Column(name = "card_type")
    private String cardType;

    @Column(name = "producct_id")
    private String producctId;

    @Column(name = "agence")
    private String agence;

    @Column(name = "ncg")
    private String ncg;

    @Column(name = "devise")
    private String devise;

    @Column(name = "code_exploitant")
    private String codeExploitant;

    @Column(name = "opt_validation")
    private String optValidation;

    @Column(name = "dat_cre")
    private Instant datCre;

    @ManyToOne
    private Client client;

    @Transient
    private String customerId;

    public String getCustomerId() {
        return this.customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }


    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIntituleCompte() {
        return intituleCompte;
    }

    public Compte intituleCompte(String intituleCompte) {
        this.intituleCompte = intituleCompte;
        return this;
    }

    public void setIntituleCompte(String intituleCompte) {
        this.intituleCompte = intituleCompte;
    }

    public String getCardType() {
        return cardType;
    }

    public Compte cardType(String cardType) {
        this.cardType = cardType;
        return this;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getProducctId() {
        return producctId;
    }

    public Compte producctId(String producctId) {
        this.producctId = producctId;
        return this;
    }

    public void setProducctId(String producctId) {
        this.producctId = producctId;
    }

    public String getAgence() {
        return agence;
    }

    public Compte agence(String agence) {
        this.agence = agence;
        return this;
    }

    public void setAgence(String agence) {
        this.agence = agence;
    }

    public String getNcg() {
        return ncg;
    }

    public Compte ncg(String ncg) {
        this.ncg = ncg;
        return this;
    }

    public void setNcg(String ncg) {
        this.ncg = ncg;
    }

    public String getDevise() {
        return devise;
    }

    public Compte devise(String devise) {
        this.devise = devise;
        return this;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }

    public String getCodeExploitant() {
        return codeExploitant;
    }

    public Compte codeExploitant(String codeExploitant) {
        this.codeExploitant = codeExploitant;
        return this;
    }

    public void setCodeExploitant(String codeExploitant) {
        this.codeExploitant = codeExploitant;
    }

    public String getOptValidation() {
        return optValidation;
    }

    public Compte optValidation(String optValidation) {
        this.optValidation = optValidation;
        return this;
    }

    public void setOptValidation(String optValidation) {
        this.optValidation = optValidation;
    }

    public Instant getDatCre() {
        return datCre;
    }

    public Compte datCre(Instant datCre) {
        this.datCre = datCre;
        return this;
    }

    public void setDatCre(Instant datCre) {
        this.datCre = datCre;
    }

    public Client getClient() {
        return client;
    }

    public Compte client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Compte)) {
            return false;
        }
        return id != null && id.equals(((Compte) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Compte{" +
            "id=" + getId() +
            ", intituleCompte='" + getIntituleCompte() + "'" +
            ", cardType='" + getCardType() + "'" +
            ", producctId='" + getProducctId() + "'" +
            ", agence='" + getAgence() + "'" +
            ", ncg='" + getNcg() + "'" +
            ", devise='" + getDevise() + "'" +
            ", codeExploitant='" + getCodeExploitant() + "'" +
            ", optValidation='" + getOptValidation() + "'" +
            ", datCre='" + getDatCre() + "'" +
            "}";
    }
}
