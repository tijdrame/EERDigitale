package com.boa.aerd.web.rest;

import com.boa.aerd.AerdApp;
import com.boa.aerd.domain.CarteStatus;
import com.boa.aerd.repository.CarteStatusRepository;
import com.boa.aerd.service.CarteStatusService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CarteStatusResource} REST controller.
 */
@SpringBootTest(classes = AerdApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CarteStatusResourceIT {

    private static final String DEFAULT_CLIENT = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT = "BBBBBBBBBB";

    private static final String DEFAULT_CARTE_NO = "AAAAAAAAAA";
    private static final String UPDATED_CARTE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS_CODE = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS_LIB = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_LIB = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_COUNTRY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_KAFKA_TOPIC = "AAAAAAAAAA";
    private static final String UPDATED_KAFKA_TOPIC = "BBBBBBBBBB";

    private static final String DEFAULT_ID_CARTE_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_ID_CARTE_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_ENV = "AAAAAAAAAA";
    private static final String UPDATED_ENV = "BBBBBBBBBB";

    private static final String DEFAULT_KAFKA_MESSAGE_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KAFKA_MESSAGE_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_KAFKA_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KAFKA_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_KAFKA_TIMESTAMP = "AAAAAAAAAA";
    private static final String UPDATED_KAFKA_TIMESTAMP = "BBBBBBBBBB";

    private static final String DEFAULT_KAFKA_CARTE_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KAFKA_CARTE_KEY = "BBBBBBBBBB";

    @Autowired
    private CarteStatusRepository carteStatusRepository;

    @Autowired
    private CarteStatusService carteStatusService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCarteStatusMockMvc;

    private CarteStatus carteStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CarteStatus createEntity(EntityManager em) {
        CarteStatus carteStatus = new CarteStatus()
            .client(DEFAULT_CLIENT)
            .carteNo(DEFAULT_CARTE_NO)
            .statusCode(DEFAULT_STATUS_CODE)
            .statusLib(DEFAULT_STATUS_LIB)
            .creationDate(DEFAULT_CREATION_DATE)
            .countryCode(DEFAULT_COUNTRY_CODE)
            .kafkaTopic(DEFAULT_KAFKA_TOPIC)
            .idCarteStatus(DEFAULT_ID_CARTE_STATUS)
            .env(DEFAULT_ENV)
            .kafkaMessageKey(DEFAULT_KAFKA_MESSAGE_KEY)
            .kafkaKey(DEFAULT_KAFKA_KEY)
            .kafkaTimestamp(DEFAULT_KAFKA_TIMESTAMP)
            .kafka_carte_key(DEFAULT_KAFKA_CARTE_KEY);
        return carteStatus;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CarteStatus createUpdatedEntity(EntityManager em) {
        CarteStatus carteStatus = new CarteStatus()
            .client(UPDATED_CLIENT)
            .carteNo(UPDATED_CARTE_NO)
            .statusCode(UPDATED_STATUS_CODE)
            .statusLib(UPDATED_STATUS_LIB)
            .creationDate(UPDATED_CREATION_DATE)
            .countryCode(UPDATED_COUNTRY_CODE)
            .kafkaTopic(UPDATED_KAFKA_TOPIC)
            .idCarteStatus(UPDATED_ID_CARTE_STATUS)
            .env(UPDATED_ENV)
            .kafkaMessageKey(UPDATED_KAFKA_MESSAGE_KEY)
            .kafkaKey(UPDATED_KAFKA_KEY)
            .kafkaTimestamp(UPDATED_KAFKA_TIMESTAMP)
            .kafka_carte_key(UPDATED_KAFKA_CARTE_KEY);
        return carteStatus;
    }

    @BeforeEach
    public void initTest() {
        carteStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createCarteStatus() throws Exception {
        int databaseSizeBeforeCreate = carteStatusRepository.findAll().size();
        // Create the CarteStatus
        restCarteStatusMockMvc.perform(post("/api/carte-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(carteStatus)))
            .andExpect(status().isCreated());

        // Validate the CarteStatus in the database
        List<CarteStatus> carteStatusList = carteStatusRepository.findAll();
        assertThat(carteStatusList).hasSize(databaseSizeBeforeCreate + 1);
        CarteStatus testCarteStatus = carteStatusList.get(carteStatusList.size() - 1);
        assertThat(testCarteStatus.getClient()).isEqualTo(DEFAULT_CLIENT);
        assertThat(testCarteStatus.getCarteNo()).isEqualTo(DEFAULT_CARTE_NO);
        assertThat(testCarteStatus.getStatusCode()).isEqualTo(DEFAULT_STATUS_CODE);
        assertThat(testCarteStatus.getStatusLib()).isEqualTo(DEFAULT_STATUS_LIB);
        assertThat(testCarteStatus.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testCarteStatus.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
        assertThat(testCarteStatus.getKafkaTopic()).isEqualTo(DEFAULT_KAFKA_TOPIC);
        assertThat(testCarteStatus.getIdCarteStatus()).isEqualTo(DEFAULT_ID_CARTE_STATUS);
        assertThat(testCarteStatus.getEnv()).isEqualTo(DEFAULT_ENV);
        assertThat(testCarteStatus.getKafkaMessageKey()).isEqualTo(DEFAULT_KAFKA_MESSAGE_KEY);
        assertThat(testCarteStatus.getKafkaKey()).isEqualTo(DEFAULT_KAFKA_KEY);
        assertThat(testCarteStatus.getKafkaTimestamp()).isEqualTo(DEFAULT_KAFKA_TIMESTAMP);
        assertThat(testCarteStatus.getKafka_carte_key()).isEqualTo(DEFAULT_KAFKA_CARTE_KEY);
    }

    @Test
    @Transactional
    public void createCarteStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = carteStatusRepository.findAll().size();

        // Create the CarteStatus with an existing ID
        carteStatus.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarteStatusMockMvc.perform(post("/api/carte-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(carteStatus)))
            .andExpect(status().isBadRequest());

        // Validate the CarteStatus in the database
        List<CarteStatus> carteStatusList = carteStatusRepository.findAll();
        assertThat(carteStatusList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCarteStatuses() throws Exception {
        // Initialize the database
        carteStatusRepository.saveAndFlush(carteStatus);

        // Get all the carteStatusList
        restCarteStatusMockMvc.perform(get("/api/carte-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carteStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].client").value(hasItem(DEFAULT_CLIENT)))
            .andExpect(jsonPath("$.[*].carteNo").value(hasItem(DEFAULT_CARTE_NO)))
            .andExpect(jsonPath("$.[*].statusCode").value(hasItem(DEFAULT_STATUS_CODE)))
            .andExpect(jsonPath("$.[*].statusLib").value(hasItem(DEFAULT_STATUS_LIB)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE)))
            .andExpect(jsonPath("$.[*].kafkaTopic").value(hasItem(DEFAULT_KAFKA_TOPIC)))
            .andExpect(jsonPath("$.[*].idCarteStatus").value(hasItem(DEFAULT_ID_CARTE_STATUS)))
            .andExpect(jsonPath("$.[*].env").value(hasItem(DEFAULT_ENV)))
            .andExpect(jsonPath("$.[*].kafkaMessageKey").value(hasItem(DEFAULT_KAFKA_MESSAGE_KEY)))
            .andExpect(jsonPath("$.[*].kafkaKey").value(hasItem(DEFAULT_KAFKA_KEY)))
            .andExpect(jsonPath("$.[*].kafkaTimestamp").value(hasItem(DEFAULT_KAFKA_TIMESTAMP)))
            .andExpect(jsonPath("$.[*].kafka_carte_key").value(hasItem(DEFAULT_KAFKA_CARTE_KEY)));
    }
    
    @Test
    @Transactional
    public void getCarteStatus() throws Exception {
        // Initialize the database
        carteStatusRepository.saveAndFlush(carteStatus);

        // Get the carteStatus
        restCarteStatusMockMvc.perform(get("/api/carte-statuses/{id}", carteStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(carteStatus.getId().intValue()))
            .andExpect(jsonPath("$.client").value(DEFAULT_CLIENT))
            .andExpect(jsonPath("$.carteNo").value(DEFAULT_CARTE_NO))
            .andExpect(jsonPath("$.statusCode").value(DEFAULT_STATUS_CODE))
            .andExpect(jsonPath("$.statusLib").value(DEFAULT_STATUS_LIB))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.countryCode").value(DEFAULT_COUNTRY_CODE))
            .andExpect(jsonPath("$.kafkaTopic").value(DEFAULT_KAFKA_TOPIC))
            .andExpect(jsonPath("$.idCarteStatus").value(DEFAULT_ID_CARTE_STATUS))
            .andExpect(jsonPath("$.env").value(DEFAULT_ENV))
            .andExpect(jsonPath("$.kafkaMessageKey").value(DEFAULT_KAFKA_MESSAGE_KEY))
            .andExpect(jsonPath("$.kafkaKey").value(DEFAULT_KAFKA_KEY))
            .andExpect(jsonPath("$.kafkaTimestamp").value(DEFAULT_KAFKA_TIMESTAMP))
            .andExpect(jsonPath("$.kafka_carte_key").value(DEFAULT_KAFKA_CARTE_KEY));
    }
    @Test
    @Transactional
    public void getNonExistingCarteStatus() throws Exception {
        // Get the carteStatus
        restCarteStatusMockMvc.perform(get("/api/carte-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCarteStatus() throws Exception {
        // Initialize the database
        carteStatusService.save(carteStatus);

        int databaseSizeBeforeUpdate = carteStatusRepository.findAll().size();

        // Update the carteStatus
        CarteStatus updatedCarteStatus = carteStatusRepository.findById(carteStatus.getId()).get();
        // Disconnect from session so that the updates on updatedCarteStatus are not directly saved in db
        em.detach(updatedCarteStatus);
        updatedCarteStatus
            .client(UPDATED_CLIENT)
            .carteNo(UPDATED_CARTE_NO)
            .statusCode(UPDATED_STATUS_CODE)
            .statusLib(UPDATED_STATUS_LIB)
            .creationDate(UPDATED_CREATION_DATE)
            .countryCode(UPDATED_COUNTRY_CODE)
            .kafkaTopic(UPDATED_KAFKA_TOPIC)
            .idCarteStatus(UPDATED_ID_CARTE_STATUS)
            .env(UPDATED_ENV)
            .kafkaMessageKey(UPDATED_KAFKA_MESSAGE_KEY)
            .kafkaKey(UPDATED_KAFKA_KEY)
            .kafkaTimestamp(UPDATED_KAFKA_TIMESTAMP)
            .kafka_carte_key(UPDATED_KAFKA_CARTE_KEY);

        restCarteStatusMockMvc.perform(put("/api/carte-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCarteStatus)))
            .andExpect(status().isOk());

        // Validate the CarteStatus in the database
        List<CarteStatus> carteStatusList = carteStatusRepository.findAll();
        assertThat(carteStatusList).hasSize(databaseSizeBeforeUpdate);
        CarteStatus testCarteStatus = carteStatusList.get(carteStatusList.size() - 1);
        assertThat(testCarteStatus.getClient()).isEqualTo(UPDATED_CLIENT);
        assertThat(testCarteStatus.getCarteNo()).isEqualTo(UPDATED_CARTE_NO);
        assertThat(testCarteStatus.getStatusCode()).isEqualTo(UPDATED_STATUS_CODE);
        assertThat(testCarteStatus.getStatusLib()).isEqualTo(UPDATED_STATUS_LIB);
        assertThat(testCarteStatus.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testCarteStatus.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testCarteStatus.getKafkaTopic()).isEqualTo(UPDATED_KAFKA_TOPIC);
        assertThat(testCarteStatus.getIdCarteStatus()).isEqualTo(UPDATED_ID_CARTE_STATUS);
        assertThat(testCarteStatus.getEnv()).isEqualTo(UPDATED_ENV);
        assertThat(testCarteStatus.getKafkaMessageKey()).isEqualTo(UPDATED_KAFKA_MESSAGE_KEY);
        assertThat(testCarteStatus.getKafkaKey()).isEqualTo(UPDATED_KAFKA_KEY);
        assertThat(testCarteStatus.getKafkaTimestamp()).isEqualTo(UPDATED_KAFKA_TIMESTAMP);
        assertThat(testCarteStatus.getKafka_carte_key()).isEqualTo(UPDATED_KAFKA_CARTE_KEY);
    }

    @Test
    @Transactional
    public void updateNonExistingCarteStatus() throws Exception {
        int databaseSizeBeforeUpdate = carteStatusRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarteStatusMockMvc.perform(put("/api/carte-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(carteStatus)))
            .andExpect(status().isBadRequest());

        // Validate the CarteStatus in the database
        List<CarteStatus> carteStatusList = carteStatusRepository.findAll();
        assertThat(carteStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCarteStatus() throws Exception {
        // Initialize the database
        carteStatusService.save(carteStatus);

        int databaseSizeBeforeDelete = carteStatusRepository.findAll().size();

        // Delete the carteStatus
        restCarteStatusMockMvc.perform(delete("/api/carte-statuses/{id}", carteStatus.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CarteStatus> carteStatusList = carteStatusRepository.findAll();
        assertThat(carteStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
