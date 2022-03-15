package com.boa.aerd.request.cardstatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "kafka_topic", "id", "env", "kafka_messageKey", "key", "timestamp" })
public class Headers {

    @JsonProperty("kafka_topic")
    private String kafkaTopic;
    @JsonProperty("id")
    private String id;
    @JsonProperty("env")
    private String env;
    @JsonProperty("kafka_messageKey")
    private String kafkaMessageKey;
    @JsonProperty("key")
    private String key;
    @JsonProperty("timestamp")
    private Long timestamp;

    @JsonProperty("kafka_topic")
    public String getKafkaTopic() {
        return kafkaTopic;
    }

    @JsonProperty("kafka_topic")
    public void setKafkaTopic(String kafkaTopic) {
        this.kafkaTopic = kafkaTopic;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("env")
    public String getEnv() {
        return env;
    }

    @JsonProperty("env")
    public void setEnv(String env) {
        this.env = env;
    }

    @JsonProperty("kafka_messageKey")
    public String getKafkaMessageKey() {
        return kafkaMessageKey;
    }

    @JsonProperty("kafka_messageKey")
    public void setKafkaMessageKey(String kafkaMessageKey) {
        this.kafkaMessageKey = kafkaMessageKey;
    }

    @JsonProperty("key")
    public String getKey() {
        return key;
    }

    @JsonProperty("key")
    public void setKey(String key) {
        this.key = key;
    }

    @JsonProperty("timestamp")
    public Long getTimestamp() {
        return timestamp;
    }

    @JsonProperty("timestamp")
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "{" +
            " kafkaTopic='" + kafkaTopic + "'" +
            ", id='" + id + "'" +
            ", env='" + env + "'" +
            ", kafkaMessageKey='" + kafkaMessageKey + "'" +
            ", key='" + key + "'" +
            ", timestamp='" + timestamp + "'" +
            "}";
    }

}