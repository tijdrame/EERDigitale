package com.boa.aerd.repository;
import java.util.List;

import com.boa.aerd.domain.ClientSuspect;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Client entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientSuspRepository extends JpaRepository<ClientSuspect, Long> {

	List<ClientSuspect> findByStatusAndKafkaTopic(String status, String topic);

	ClientSuspect findByClientAndStatus(String client, String status);

}
