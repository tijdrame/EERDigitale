package com.boa.aerd.web.rest;

import com.boa.aerd.domain.CarteStatus;
import com.boa.aerd.service.CarteStatusService;
import com.boa.aerd.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.boa.aerd.domain.CarteStatus}.
 */
@RestController
@RequestMapping("/api")
public class CarteStatusResource {

    private final Logger log = LoggerFactory.getLogger(CarteStatusResource.class);

    private static final String ENTITY_NAME = "carteStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CarteStatusService carteStatusService;

    public CarteStatusResource(CarteStatusService carteStatusService) {
        this.carteStatusService = carteStatusService;
    }

    /**
     * {@code POST  /carte-statuses} : Create a new carteStatus.
     *
     * @param carteStatus the carteStatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new carteStatus, or with status {@code 400 (Bad Request)} if the carteStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/carte-statuses")
    public ResponseEntity<CarteStatus> createCarteStatus(@RequestBody CarteStatus carteStatus) throws URISyntaxException {
        log.debug("REST request to save CarteStatus : {}", carteStatus);
        if (carteStatus.getId() != null) {
            throw new BadRequestAlertException("A new carteStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CarteStatus result = carteStatusService.save(carteStatus);
        return ResponseEntity.created(new URI("/api/carte-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /carte-statuses} : Updates an existing carteStatus.
     *
     * @param carteStatus the carteStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carteStatus,
     * or with status {@code 400 (Bad Request)} if the carteStatus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the carteStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/carte-statuses")
    public ResponseEntity<CarteStatus> updateCarteStatus(@RequestBody CarteStatus carteStatus) throws URISyntaxException {
        log.debug("REST request to update CarteStatus : {}", carteStatus);
        if (carteStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CarteStatus result = carteStatusService.save(carteStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carteStatus.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /carte-statuses} : get all the carteStatuses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of carteStatuses in body.
     */
    @GetMapping("/carte-statuses")
    public List<CarteStatus> getAllCarteStatuses() {
        log.debug("REST request to get all CarteStatuses");
        return carteStatusService.findAll();
    }

    /**
     * {@code GET  /carte-statuses/:id} : get the "id" carteStatus.
     *
     * @param id the id of the carteStatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the carteStatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/carte-statuses/{id}")
    public ResponseEntity<CarteStatus> getCarteStatus(@PathVariable Long id) {
        log.debug("REST request to get CarteStatus : {}", id);
        Optional<CarteStatus> carteStatus = carteStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(carteStatus);
    }

    /**
     * {@code DELETE  /carte-statuses/:id} : delete the "id" carteStatus.
     *
     * @param id the id of the carteStatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/carte-statuses/{id}")
    public ResponseEntity<Void> deleteCarteStatus(@PathVariable Long id) {
        log.debug("REST request to delete CarteStatus : {}", id);
        carteStatusService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
