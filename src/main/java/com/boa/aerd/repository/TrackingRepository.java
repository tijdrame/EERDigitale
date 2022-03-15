package com.boa.aerd.repository;
import com.boa.aerd.domain.Tracking;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Tracking entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrackingRepository extends JpaRepository<Tracking, Long> {

}
