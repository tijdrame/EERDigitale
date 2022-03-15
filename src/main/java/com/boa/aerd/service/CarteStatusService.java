package com.boa.aerd.service;

import com.boa.aerd.domain.CarteStatus;
import com.boa.aerd.repository.CarteStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link CarteStatus}.
 */
@Service
@Transactional
public class CarteStatusService {

    private final Logger log = LoggerFactory.getLogger(CarteStatusService.class);

    private final CarteStatusRepository carteStatusRepository;

    public CarteStatusService(CarteStatusRepository carteStatusRepository) {
        this.carteStatusRepository = carteStatusRepository;
    }

    /**
     * Save a carteStatus.
     *
     * @param carteStatus the entity to save.
     * @return the persisted entity.
     */
    public CarteStatus save(CarteStatus carteStatus) {
        log.debug("Request to save CarteStatus : {}", carteStatus);
        return carteStatusRepository.save(carteStatus);
    }

    public CarteStatus findByClientAndStatus(String client, String status) {
        log.info("********** card & status [{}, {}]",client, status);
		return carteStatusRepository.findByClientAndStatus(client, status);
	}

    /**
     * Get all the carteStatuses.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CarteStatus> findAll() {
        log.debug("Request to get all CarteStatuses");
        return carteStatusRepository.findAll();
    }


    /**
     * Get one carteStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CarteStatus> findOne(Long id) {
        log.debug("Request to get CarteStatus : {}", id);
        return carteStatusRepository.findById(id);
    }

    /**
     * Delete the carteStatus by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CarteStatus : {}", id);
        carteStatusRepository.deleteById(id);
    }
}
