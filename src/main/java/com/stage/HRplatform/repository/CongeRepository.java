package com.stage.HRplatform.repository;

import com.stage.HRplatform.entity.Conge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CongeRepository extends JpaRepository<Conge, Long> {

}
