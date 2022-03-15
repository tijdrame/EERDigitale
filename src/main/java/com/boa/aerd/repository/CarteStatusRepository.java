package com.boa.aerd.repository;

import com.boa.aerd.domain.CarteStatus;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CarteStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarteStatusRepository extends JpaRepository<CarteStatus, Long> {

	CarteStatus findByClientAndStatus(String client, String status);
}
