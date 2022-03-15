package com.boa.aerd.web.rest;

import com.boa.aerd.config.KafkaProperties;
import com.boa.aerd.domain.CarteStatus;
import com.boa.aerd.domain.ClientSuspect;
import com.boa.aerd.request.ProducerRequest;
import com.boa.aerd.request.cardstatus.CardStatusRequest;
import com.boa.aerd.response.ItRecord;
import com.boa.aerd.response.KafkaResponse;
import com.boa.aerd.service.CarteStatusService;
import com.boa.aerd.service.ClientSuspService;

import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.requests.ProduceRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/eerd/api/kafka")
public class EerdKafkaKafkaResource {

    private final Logger log = LoggerFactory.getLogger(EerdKafkaKafkaResource.class);

    private final KafkaProperties kafkaProperties;
    private final ClientSuspService clientSuspService;
    private final CarteStatusService carteStatusService;
    private KafkaProducer<String, String> producer;
    private String TOPIC_CLIENT_SUSPECT = "BOA_UNBLCK_CLT_SUSPECT";
    private String TOPIC_CARD_STATUS = "BOA_CARD_REQUEST_STATUS";
    private String TOPIC_ACK_CLIENT = "BOA_UNBLCK_CLT_SUSPECT_ACK";
    private String TOPIC_ACK_STATUS = "BOA_CARD_REQUEST_STATUS_ACK";
    // private ExecutorService sseExecutorService = Executors.newCachedThreadPool();

    public EerdKafkaKafkaResource(KafkaProperties kafkaProperties, ClientSuspService clientSuspService,
            CarteStatusService carteStatusService) {
        this.kafkaProperties = kafkaProperties;
        this.carteStatusService = carteStatusService;
        this.clientSuspService = clientSuspService;
        this.producer = new KafkaProducer<>(kafkaProperties.getProducerProps());
    }

    /*
     * @PostMapping("/publishClientSuspect") public PublishResult publishClient()
     * throws ExecutionException, InterruptedException {
     * log.info("REST request to send to Kafka topic {}", TOPIC_CLIENT_SUSPECT);
     * List<ClientSuspect> suspects = clientSuspService.findAll("0",
     * TOPIC_CLIENT_SUSPECT); if(suspects==null && suspects.isEmpty()){ return new
     * PublishResult(301, "", 0, 0l, Instant.now()); } for (ClientSuspect it :
     * suspects) { ProducerRequest request = new ProducerRequest();
     * 
     * RecordMetadata metadata = producer .send(new
     * ProducerRecord<>(TOPIC_CLIENT_SUSPECT, request.getKey(),
     * request.getPayload().toString())).get(); }
     * 
     * return new PublishResult(200, metadata.topic(), metadata.partition(),
     * metadata.offset(), Instant.ofEpochMilli(metadata.timestamp())); }
     */

    @PostMapping("/publishClientSuspect")
    public PublishResult publishClientBis(@RequestBody ProducerRequest request)
            throws ExecutionException, InterruptedException {
        log.info("REST request to send to Kafka topic {} with key {} the message : {}", TOPIC_CLIENT_SUSPECT,
                request.getKey(), request.toString());
        RecordMetadata metadata = producer
                .send(new ProducerRecord<>(TOPIC_CLIENT_SUSPECT, request.getKey(), request.getPayload().toString()))
                .get();
        return new PublishResult(200, metadata.topic(), metadata.partition(), metadata.offset(),
                Instant.ofEpochMilli(metadata.timestamp()));
    }

    @PostMapping("/publishCardStatus")
    public PublishResult publishCard(@RequestBody CardStatusRequest request)
            throws ExecutionException, InterruptedException {
        log.info("REST request to send to Kafka topic {} with key {} the message : {}", TOPIC_CARD_STATUS,
                request.getKey(), request.toString());
        RecordMetadata metadata = producer
                .send(new ProducerRecord<>(TOPIC_CARD_STATUS, request.getKey(), request.getPayload().toString())).get();
        return new PublishResult(200, metadata.topic(), metadata.partition(), metadata.offset(),
                Instant.ofEpochMilli(metadata.timestamp()));
    }

    @PostMapping("/publishAckSuspect")
    public PublishResult publishAckClient(@RequestBody ProducerRequest request)
            throws ExecutionException, InterruptedException {
        log.info("REST request to send to Kafka topic {} with key {} the message : {}", TOPIC_ACK_CLIENT,
                request.getKey(), request.toString());
        RecordMetadata metadata = producer
                .send(new ProducerRecord<>(TOPIC_CLIENT_SUSPECT, request.getKey(), request.getPayload().toString()))
                .get();
        return new PublishResult(200, metadata.topic(), metadata.partition(), metadata.offset(),
                Instant.ofEpochMilli(metadata.timestamp()));
    }

    @GetMapping("/consume")
    public ResponseEntity<KafkaResponse> consume(@RequestParam Map<String, String> consumerParams) {
        log.info("REST request to consume records from Kafka topics [{}]", TOPIC_CLIENT_SUSPECT);
        log.info("Topic= [{}]", consumerParams.get("topic"));
        KafkaResponse response = new KafkaResponse();
        if (consumerParams == null || consumerParams.get("topic") == null) {
            response.setCode(301);
            response.setDateResponse(Instant.now());
            response.setDescription("Operation echouee, topic absent.");
            return ResponseEntity.ok().body(response);
        }
        if (!consumerParams.get("topic").equals(TOPIC_CLIENT_SUSPECT)
                && !consumerParams.get("topic").equals(TOPIC_CARD_STATUS)) {
            response.setCode(301);
            response.setDateResponse(Instant.now());
            response.setDescription("Nom de topic non pris en compte");
            return ResponseEntity.ok().body(response);
        }
        if (consumerParams.get("topic").equals(TOPIC_CLIENT_SUSPECT)) {
            log.info("dans TOPIC_CLIENT_SUSPECT ********************");
            Map<String, Object> consumerProps = kafkaProperties.getConsumerProps();

            consumerProps.putAll(consumerParams);
            consumerProps.remove("topic");

            // consumerProps.put("group.id", UUID.randomUUID().toString());
            // create consumer
            KafkaConsumer<String, String> consumerBis = new KafkaConsumer<String, String>(consumerProps);
            consumerBis.subscribe(Collections.singleton(consumerParams.get("topic")));
            // SseEmitter emitter = new SseEmitter(0L);
            // List<String> result = new ArrayList<>();
            // while (true) {
            ConsumerRecords<String, String> records = consumerBis.poll(Duration.ofMillis(3000));

            for (ConsumerRecord<String, String> it : records) {
                log.info("Key = [{}]", it.key());
                log.info("Value = [{}]", it.value());
                log.info("Partition = [{}]", it.partition());
                log.info("Offset = [{}]", it.offset());
                try {
                    log.info("oh=[{}]", it.value());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ItRecord itRecord = new ItRecord();
                itRecord.key((it != null && it.key() != null) ? it.key() : "")
                        .value((it != null && it.value() != null) ? it.value() : "");
                response.getData().getRecords().add(itRecord);

            }
            if (response.getData().getRecords() != null && !response.getData().getRecords().isEmpty()) {
                for (ItRecord it : response.getData().getRecords()) {
                    try {
                        String str = StringUtils.substringBetween(it.getValue(), "[", "]");
                        log.info("strr [{}]", str);
                        if (str != null) {
                            String[] leTab = StringUtils.substringsBetween(str, "{", "}");
                            log.info("le tab [{}]", leTab.toString());

                            if (leTab != null && leTab.length > 0) {
                                for (int i = 0; i < leTab.length; i++) {
                                    String idCli = StringUtils.substringBetween(leTab[i], "idclient:", "',");
                                    if (idCli != null) {
                                        idCli = idCli.replace("'", "");
                                        ClientSuspect clientSuspect = clientSuspService.findByClient(idCli, "0");
                                        if (clientSuspect != null) {
                                            log.info("client susp trouve [{}]", clientSuspect.getClient());
                                            clientSuspService.save(clientSuspect);
                                        }
                                        log.info("clientSuspect [{}]", clientSuspect);
                                        log.info("idCli [{}]", idCli);
                                        log.info("it [{}]", it.getValue());
                                    }
                                }
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            // end adding
            consumerBis.close();
            response.setCode(200);
            response.setDateResponse(Instant.now());
            response.setDescription("Operation effectuee avec succes.");
            return ResponseEntity.ok().body(response);
        } else {
            log.info("dans TOPIC_ARD_SUSPECT ******************** [{}]", Collections.singleton(TOPIC_CARD_STATUS));
            Map<String, Object> consumerProps = kafkaProperties.getConsumerProps();
            consumerProps.putAll(consumerParams);
            consumerProps.remove("topic");

            // consumerProps.put("group.id", UUID.randomUUID().toString());
            // create consumer
            KafkaConsumer<String, String> consumerBis = new KafkaConsumer<String, String>(consumerProps);
            consumerBis.subscribe(Collections.singleton(TOPIC_CARD_STATUS));
            // while (true) {
            ConsumerRecords<String, String> records = consumerBis.poll(Duration.ofMillis(3000));

            for (ConsumerRecord<String, String> it : records) {
                log.info("Key = [{}]", it.key());
                log.info("Value = [{}]", it.value());
                log.info("Partition = [{}]", it.partition());
                log.info("Offset = [{}]", it.offset());
                // result.add(it.value());
                ItRecord itRecord = new ItRecord();
                itRecord.key((it != null && it.key() != null) ? it.key() : "")
                        .value((it != null && it.value() != null) ? it.value() : "");
                response.getData().getRecords().add(itRecord);

            }
            // adding
            if (response.getData().getRecords() != null && !response.getData().getRecords().isEmpty()) {
                for (ItRecord it : response.getData().getRecords()) {
                    try {
                        String str = StringUtils.substringBetween(it.getValue(), "[", "]");
                        log.info("strr [{}]", str);
                        if (str != null) {
                            String[] leTab = StringUtils.substringsBetween(str, "{", "}");
                            log.info("le tab [{}]", leTab.toString());

                            if (leTab != null && leTab.length > 0) {
                                for (int i = 0; i < leTab.length; i++) {
                                    String idCli = StringUtils.substringBetween(leTab[i], "idclient:", "',");
                                    if (idCli != null) {
                                        idCli = idCli.replace("'", "");
                                        // TODO call table and up status
                                        CarteStatus carteStatus = carteStatusService.findByClientAndStatus(idCli, "0");
                                        if (carteStatus != null) {
                                            log.info("client susp trouve [{}]", carteStatus.getClient());
                                            carteStatus.setStatus("1");
                                            carteStatusService.save(carteStatus);
                                        }
                                        log.info("carteStatus [{}]", carteStatus);
                                        log.info("idCli [{}]", idCli);
                                        log.info("it [{}]", it.getValue());
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            // end adding
            consumerBis.close();
            response.setCode(200);
            response.setDateResponse(Instant.now());
            response.setDescription("Operation effectuee avec succés.");
            return ResponseEntity.ok().body(response);
        }

    }

    @GetMapping("/consumeCardStatus")
    public ResponseEntity<KafkaResponse> consumeCardStatus(@RequestParam Map<String, String> consumerParams) {
        log.info("REST request to consume records from Kafka topics {}", TOPIC_CARD_STATUS);
        KafkaResponse response = new KafkaResponse();
        Map<String, Object> consumerProps = kafkaProperties.getConsumerProps();

        consumerProps.putAll(consumerParams);
        consumerProps.remove("topic");

        // consumerProps.put("group.id", UUID.randomUUID().toString());
        // create consumer
        KafkaConsumer<String, String> consumerBis = new KafkaConsumer<String, String>(consumerProps);
        consumerBis.subscribe(Collections.singleton(TOPIC_CARD_STATUS));
        // while (true) {
        ConsumerRecords<String, String> records = consumerBis.poll(Duration.ofMillis(3000));

        for (ConsumerRecord<String, String> it : records) {
            log.info("Key = [{}]", it.key());
            log.info("Value = [{}]", it.value());
            log.info("Partition = [{}]", it.partition());
            log.info("Offset = [{}]", it.offset());
            // result.add(it.value());
            ItRecord itRecord = new ItRecord();
            itRecord.key((it != null && it.key() != null) ? it.key() : "")
                    .value((it != null && it.value() != null) ? it.value() : "");
            response.getData().getRecords().add(itRecord);

        }
        // adding
        if (response.getData().getRecords() != null && !response.getData().getRecords().isEmpty()) {
            for (ItRecord it : response.getData().getRecords()) {
                try {
                    String str = StringUtils.substringBetween(it.getValue(), "[", "]");
                    log.info("strr [{}]", str);
                    if (str != null) {
                        String[] leTab = StringUtils.substringsBetween(str, "{", "}");
                        log.info("le tab [{}]", leTab.toString());

                        if (leTab != null && leTab.length > 0) {
                            for (int i = 0; i < leTab.length; i++) {
                                String idCli = StringUtils.substringBetween(leTab[i], "idclient:", "',");
                                if (idCli != null) {
                                    idCli = idCli.replace("'", "");
                                    // TODO call table and up status
                                    CarteStatus carteStatus = carteStatusService.findByClientAndStatus(idCli, "0");
                                    if (carteStatus != null) {
                                        log.info("client susp trouve [{}]", carteStatus.getClient());
                                        carteStatus.setStatus("1");
                                        carteStatusService.save(carteStatus);
                                    }
                                    log.info("carteStatus [{}]", carteStatus);
                                    log.info("idCli [{}]", idCli);
                                    log.info("it [{}]", it.getValue());
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        // end adding
        consumerBis.close();
        response.setCode(200);
        response.setDateResponse(Instant.now());
        response.setDescription("Operation effectuee avec succés.");
        return ResponseEntity.ok().body(response);
        // }
    }

    private static class PublishResult {
        public final int code;
        public final String topic;
        public final int partition;
        public final long offset;
        public final Instant timestamp;

        private PublishResult(int code, String topic, int partition, long offset, Instant timestamp) {
            this.code = code;
            this.topic = topic;
            this.partition = partition;
            this.offset = offset;
            this.timestamp = timestamp;
        }
    }
}
