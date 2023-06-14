package com.tingesoevaluacion2.propiedadesservice.repositories;

import com.tingesoevaluacion2.propiedadesservice.entities.PropiedadesLecheEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
Repositorio de la entidad PropiedadesLeche.
*/
@Repository
public interface PropiedadesLecheRepository extends JpaRepository <PropiedadesLecheEntity, Integer> {
}
