package com.boa.aerd.web.rest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.boa.aerd.domain.Client;
import com.boa.aerd.response.GenericResponse;
import com.boa.aerd.service.ClientService;
import com.boa.aerd.web.rest.errors.BadRequestAlertException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.Api;
import springfox.documentation.annotations.ApiIgnore;

/**
 * REST controller for managing {@link com.boa.aerd.domain.Client}.
 */
@RestController
@RequestMapping("/eerd/api")
@Api(description = "Entrée en Relation Digitale", tags = "EERD.")
public class ClientResource {

    private final Logger log = LoggerFactory.getLogger(ClientResource.class);

    private static final String ENTITY_NAME = "client";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClientService clientService;

    public ClientResource(ClientService clientService) {
        this.clientService = clientService;
    }

    /**
     * {@code POST  /clients} : Create a new client.
     *
     * @param client the client to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new client, or with status {@code 400 (Bad Request)} if the
     *         client has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     * @throws IOException
     */
    @PostMapping("/clients")
    public ResponseEntity<GenericResponse> createClient(@RequestBody Client client, HttpServletRequest request) throws URISyntaxException {
        log.debug("##############################REST request to save Client : {}", client);
        GenericResponse genericResponse = new GenericResponse();
        if(client.getPays()==null || client.getPays().trim().equals("")){
            genericResponse.setCode(400);
            genericResponse.setDateResponse(Instant.now());
            genericResponse.setDescription("Paramétres incorrectes!!");
            return ResponseEntity.status(400)
            .header("Authorization", request.getHeader("Authorization"))
            .body(genericResponse);
        }
        
        if (client.getId() != null) {
            throw new BadRequestAlertException("A new client cannot already have an ID", ENTITY_NAME, "idexists");
        }
        genericResponse = clientService.save(client, request);
        
        return ResponseEntity.status(genericResponse.getCode())//(new URI("/api/clients/" /*+ result.getId()*/))
            //.headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, resp))
            .header("Authorization", request.getHeader("Authorization"))
            .body(genericResponse);
    }

    /**
     * {@code PUT  /clients} : Updates an existing client.
     *
     * @param client the client to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated client,
     * or with status {@code 400 (Bad Request)} if the client is not valid,
     * or with status {@code 500 (Internal Server Error)} if the client couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    /*@PutMapping("/clients")
    public ResponseEntity<Client> updateClient(@RequestBody Client client) throws URISyntaxException {
        log.debug("REST request to update Client : {}", client);
        if (client.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Client result = clientService.save(client);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, client.getId().toString()))
            .body(result);
    }*/

    /**
     * {@code GET  /clients} : get all the clients.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clients in body.
     */
    @GetMapping(value="/clients")
    //@GetMapping(value = "/clients", produces = MediaType.APPLICATION_XML_VALUE)
    /*@RequestMapping(value="/clients", 
    method = RequestMethod.GET,
    produces={MediaType.APPLICATION_XML_VALUE})*/ 
    @ApiIgnore
    public ResponseEntity<List<Client>> getAllClients(Pageable pageable) {
    //public List<Client> getAllClients(Pageable pageable) {
        log.debug("REST request to get a page of Clients**************************");
        log.debug("REST request to get a page of Clients**************************");
        Page<Client> page = clientService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        
        //MediaType accept = MediaType(MediaType.APPLICATION_XML_VALUE) ;
        //headers.setContentType(MediaType.APPLICATION_XML);
        //return clientService.findAll();
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /clients/:id} : get the "id" client.
     *
     * @param id the id of the client to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the client, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/clients/{id}")
    @ApiIgnore
    public ResponseEntity<Client> getClient(@PathVariable Long id) {
        log.debug("REST request to get Client : {}", id);
        Optional<Client> client = clientService.findOne(id);
        return ResponseUtil.wrapOrNotFound(client);
    }

    /**
     * {@code DELETE  /clients/:id} : delete the "id" client.
     *
     * @param id the id of the client to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/clients/{id}")
    @ApiIgnore
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        log.debug("REST request to delete Client : {}", id);
        clientService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
