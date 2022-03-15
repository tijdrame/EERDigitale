package com.boa.aerd.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.boa.aerd.domain.ClientSuspect;
import com.boa.aerd.domain.ParamFiliale;
import com.boa.aerd.domain.Tracking;
import com.boa.aerd.repository.ClientRepository;
import com.boa.aerd.repository.ClientSuspRepository;
import com.boa.aerd.response.GenericResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Client}.
 */
@Service
@Transactional
public class ClientSuspService {

    private final Logger log = LoggerFactory.getLogger(ClientService.class);

    private final ClientSuspRepository clientSuspRepository;

    private final UserService userService;

    private final TrackingService trackingService;

    private final ParamFilialeService filialeService;

    public ClientSuspService(ClientSuspRepository clientSuspRepository, UserService userService, TrackingService trackingService, 
    ParamFilialeService filialeService) {
        this.clientSuspRepository = clientSuspRepository;
        this.userService = userService;
        this.trackingService = trackingService;
        this.filialeService = filialeService;
    }

    public Boolean save(ClientSuspect suspects){
        suspects.setStatus("1");
        clientSuspRepository.save(suspects);
        return true;
    }
    

    /**
     * Get all the clients.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ClientSuspect> findAll(String status, String topic) {
        log.debug("Request to get all ClientSuspect");
        return clientSuspRepository.findByStatusAndKafkaTopic(status, topic);
    }

    
    /**
     * Get one client by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClientSuspect> findOne(Long id) {
        log.debug("Request to get ClientSuspect : {}", id);
        return clientSuspRepository.findById(id);
    }

	public ClientSuspect findByClient(String client, String status) {
        log.info("********** clie & status [{}, {}]",client, status);
		return clientSuspRepository.findByClientAndStatus(client, status);
	}
}
