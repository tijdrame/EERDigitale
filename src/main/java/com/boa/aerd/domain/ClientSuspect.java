package com.boa.aerd.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "person_susp")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ClientSuspect implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "uid_person")
    private Long id;

    @Column(name = "client")
    private String client;

    @Column(name = "idp")
    private String idp;

    @Column(name = "status_code")
    private String statusCode;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "kafka_topic")
    private String kafkaTopic;

    @Column(name = "id_client_suspect")
    private String idClientSuspect;

    @Column(name = "env")
    private String env;

    @Column(name = "kafka_message_key")
    private String kafkaMessageKey;

    @Column(name = "kafka_key")
    private String kafkaKey;

    @Column(name = "kafka_timestamp")
    private String kafkaTimestamp;

    @Column(name = "kafka_client_key")
    private String kafkaClientKey;

    @Column(name = "status")
    private String status;

    public ClientSuspect() {
    }

    public ClientSuspect(Long id, String client, String idp, String statusCode, Date creationDate, String countryCode, String kafkaTopic, String idClientSuspect, String env, String kafkaMessageKey, String kafkaKey, String kafkaTimestamp, String kafkaClientKey, String status) {
        this.id = id;
        this.client = client;
        this.idp = idp;
        this.statusCode = statusCode;
        this.creationDate = creationDate;
        this.countryCode = countryCode;
        this.kafkaTopic = kafkaTopic;
        this.idClientSuspect = idClientSuspect;
        this.env = env;
        this.kafkaMessageKey = kafkaMessageKey;
        this.kafkaKey = kafkaKey;
        this.kafkaTimestamp = kafkaTimestamp;
        this.kafkaClientKey = kafkaClientKey;
        this.status = status;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClient() {
        return this.client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getIdp() {
        return this.idp;
    }

    public void setIdp(String idp) {
        this.idp = idp;
    }

    public String getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getKafkaTopic() {
        return this.kafkaTopic;
    }

    public void setKafkaTopic(String kafkaTopic) {
        this.kafkaTopic = kafkaTopic;
    }

    public String getIdClientSuspect() {
        return this.idClientSuspect;
    }

    public void setIdClientSuspect(String idClientSuspect) {
        this.idClientSuspect = idClientSuspect;
    }

    public String getEnv() {
        return this.env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getKafkaMessageKey() {
        return this.kafkaMessageKey;
    }

    public void setKafkaMessageKey(String kafkaMessageKey) {
        this.kafkaMessageKey = kafkaMessageKey;
    }

    public String getKafkaKey() {
        return this.kafkaKey;
    }

    public void setKafkaKey(String kafkaKey) {
        this.kafkaKey = kafkaKey;
    }

    public String getKafkaTimestamp() {
        return this.kafkaTimestamp;
    }

    public void setKafkaTimestamp(String kafkaTimestamp) {
        this.kafkaTimestamp = kafkaTimestamp;
    }

    public String getKafkaClientKey() {
        return this.kafkaClientKey;
    }

    public void setKafkaClientKey(String kafkaClientKey) {
        this.kafkaClientKey = kafkaClientKey;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ClientSuspect id(Long id) {
        this.id = id;
        return this;
    }

    public ClientSuspect client(String client) {
        this.client = client;
        return this;
    }

    public ClientSuspect idp(String idp) {
        this.idp = idp;
        return this;
    }

    public ClientSuspect statusCode(String statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public ClientSuspect creationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public ClientSuspect countryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public ClientSuspect kafkaTopic(String kafkaTopic) {
        this.kafkaTopic = kafkaTopic;
        return this;
    }

    public ClientSuspect idClientSuspect(String idClientSuspect) {
        this.idClientSuspect = idClientSuspect;
        return this;
    }

    public ClientSuspect env(String env) {
        this.env = env;
        return this;
    }

    public ClientSuspect kafkaMessageKey(String kafkaMessageKey) {
        this.kafkaMessageKey = kafkaMessageKey;
        return this;
    }

    public ClientSuspect kafkaKey(String kafkaKey) {
        this.kafkaKey = kafkaKey;
        return this;
    }

    public ClientSuspect kafkaTimestamp(String kafkaTimestamp) {
        this.kafkaTimestamp = kafkaTimestamp;
        return this;
    }

    public ClientSuspect kafkaClientKey(String kafkaClientKey) {
        this.kafkaClientKey = kafkaClientKey;
        return this;
    }

    public ClientSuspect status(String status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", client='" + getClient() + "'" +
            ", idp='" + getIdp() + "'" +
            ", statusCode='" + getStatusCode() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", countryCode='" + getCountryCode() + "'" +
            ", kafkaTopic='" + getKafkaTopic() + "'" +
            ", idClientSuspect='" + getIdClientSuspect() + "'" +
            ", env='" + getEnv() + "'" +
            ", kafkaMessageKey='" + getKafkaMessageKey() + "'" +
            ", kafkaKey='" + getKafkaKey() + "'" +
            ", kafkaTimestamp='" + getKafkaTimestamp() + "'" +
            ", kafkaClientKey='" + getKafkaClientKey() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }

    
}