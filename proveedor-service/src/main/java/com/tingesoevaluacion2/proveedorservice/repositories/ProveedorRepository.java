package com.tingesoevaluacion2.proveedorservice.repositories;


import com.tingesoevaluacion2.proveedorservice.entities.ProveedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
Repositorio de la entidad Proveedor.
*/
@Repository
public interface ProveedorRepository extends JpaRepository<ProveedorEntity, String> {

    public ProveedorEntity findByCodigo(String codigo);
}
