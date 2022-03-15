package com.boa.aerd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A CarteStatus.
 */
@Entity
@Table(name = "carte_status")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CarteStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "uid_carte")
    private Long id;

    @Column(name = "client")
    private String client;

    @Column(name = "carte_no")
    private String carteNo;

    @Column(name = "status_code")
    private String statusCode;

    @Column(name = "status_lib")
    private String statusLib;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "kafka_topic")
    private String kafkaTopic;

    @Column(name = "id_carte_status")
    private String idCarteStatus;

    @Column(name = "env")
    private String env;

    @Column(name = "kafka_message_key")
    private String kafkaMessageKey;

    @Column(name = "kafka_key")
    private String kafkaKey;

    @Column(name = "kafka_timestamp")
    private String kafkaTimestamp;

    @Column(name = "kafka_carte_key")
    private String kafka_carte_key;

    @Column(name = "status")
    private String status;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public CarteStatus client(String client) {
        this.client = client;
        return this;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getCarteNo() {
        return carteNo;
    }

    public CarteStatus carteNo(String carteNo) {
        this.carteNo = carteNo;
        return this;
    }

    public void setCarteNo(String carteNo) {
        this.carteNo = carteNo;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public CarteStatus statusCode(String statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusLib() {
        return statusLib;
    }

    public CarteStatus statusLib(String statusLib) {
        this.statusLib = statusLib;
        return this;
    }

    public void setStatusLib(String statusLib) {
        this.statusLib = statusLib;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public CarteStatus creationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public CarteStatus countryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getKafkaTopic() {
        return kafkaTopic;
    }

    public CarteStatus kafkaTopic(String kafkaTopic) {
        this.kafkaTopic = kafkaTopic;
        return this;
    }

    public void setKafkaTopic(String kafkaTopic) {
        this.kafkaTopic = kafkaTopic;
    }

    public String getIdCarteStatus() {
        return idCarteStatus;
    }

    public CarteStatus idCarteStatus(String idCarteStatus) {
        this.idCarteStatus = idCarteStatus;
        return this;
    }

    public void setIdCarteStatus(String idCarteStatus) {
        this.idCarteStatus = idCarteStatus;
    }

    public String getEnv() {
        return env;
    }

    public CarteStatus env(String env) {
        this.env = env;
        return this;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getKafkaMessageKey() {
        return kafkaMessageKey;
    }

    public CarteStatus kafkaMessageKey(String kafkaMessageKey) {
        this.kafkaMessageKey = kafkaMessageKey;
        return this;
    }

    public void setKafkaMessageKey(String kafkaMessageKey) {
        this.kafkaMessageKey = kafkaMessageKey;
    }

    public String getKafkaKey() {
        return kafkaKey;
    }

    public CarteStatus kafkaKey(String kafkaKey) {
        this.kafkaKey = kafkaKey;
        return this;
    }

    public void setKafkaKey(String kafkaKey) {
        this.kafkaKey = kafkaKey;
    }

    public String getKafkaTimestamp() {
        return kafkaTimestamp;
    }

    public CarteStatus kafkaTimestamp(String kafkaTimestamp) {
        this.kafkaTimestamp = kafkaTimestamp;
        return this;
    }

    public void setKafkaTimestamp(String kafkaTimestamp) {
        this.kafkaTimestamp = kafkaTimestamp;
    }

    public String getKafka_carte_key() {
        return kafka_carte_key;
    }

    public CarteStatus kafka_carte_key(String kafka_carte_key) {
        this.kafka_carte_key = kafka_carte_key;
        return this;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setKafka_carte_key(String kafka_carte_key) {
        this.kafka_carte_key = kafka_carte_key;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CarteStatus)) {
            return false;
        }
        return id != null && id.equals(((CarteStatus) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CarteStatus{" +
            "id=" + getId() +
            ", client='" + getClient() + "'" +
            ", carteNo='" + getCarteNo() + "'" +
            ", statusCode='" + getStatusCode() + "'" +
            ", statusLib='" + getStatusLib() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", countryCode='" + getCountryCode() + "'" +
            ", kafkaTopic='" + getKafkaTopic() + "'" +
            ", idCarteStatus='" + getIdCarteStatus() + "'" +
            ", env='" + getEnv() + "'" +
            ", kafkaMessageKey='" + getKafkaMessageKey() + "'" +
            ", kafkaKey='" + getKafkaKey() + "'" +
            ", kafkaTimestamp='" + getKafkaTimestamp() + "'" +
            ", kafka_carte_key='" + getKafka_carte_key() + "'" +
            ", status='" + status + "'" +
            "}";
    }
}
