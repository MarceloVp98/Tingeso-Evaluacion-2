package com.tingesoevaluacion2.proveedorservice.services;

import com.tingesoevaluacion2.proveedorservice.entities.ProveedorEntity;
import com.tingesoevaluacion2.proveedorservice.repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProveedorService {

    @Autowired
    ProveedorRepository proveedorRepository;

    public ProveedorEntity guardarProveedor(String codigo, String nombre, String categoria, String retencion) {
        ProveedorEntity proveedor= new ProveedorEntity();
        proveedor.setCodigo(codigo);
        proveedor.setNombre(nombre);
        proveedor.setCategoria(categoria);
        proveedor.setRetencion(retencion);
        return proveedorRepository.save(proveedor);
    }

    public ArrayList<ProveedorEntity> obtenerProveedores() {
        return (ArrayList<ProveedorEntity>) proveedorRepository.findAll();
    }

    public ProveedorEntity obtenerPorCodigo(String codigo) {
        return proveedorRepository.findByCodigo(codigo);
    }

    public void eliminarProveedorPorCodigo(String codigo){
        proveedorRepository.deleteById(codigo);
    }
}