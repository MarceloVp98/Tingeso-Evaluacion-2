package com.tingesoevaluacion2.acopioservice.repositories;

import com.tingesoevaluacion2.acopioservice.entities.AcopioLecheEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
Repositorio de la entidad AcopioLeche.
*/
@Repository
public interface AcopioLecheRepository extends JpaRepository <AcopioLecheEntity, Integer> {
}

