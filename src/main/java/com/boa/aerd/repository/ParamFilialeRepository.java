package com.boa.aerd.repository;
import java.util.List;

import com.boa.aerd.domain.ParamFiliale;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ParamFiliale entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParamFilialeRepository extends JpaRepository<ParamFiliale, Long> {

    List<ParamFiliale> findAll();
    
    ParamFiliale findByCodeFiliale(String code);

}
