package com.tingesoevaluacion2.pagoservice.repositories;

import com.tingesoevaluacion2.pagoservice.entities.PagoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/*
Repositorio de la entidad Pago.
*/
@Repository
public interface PagoRepository extends JpaRepository<PagoEntity, Integer> {

    @Query("SELECT e FROM PagoEntity e WHERE e.codigo_proveedor =:codigo_proveedor")
    ArrayList<PagoEntity> findByCodigoProveedor(@Param("codigo_proveedor") String codigo_proveedor);
}
