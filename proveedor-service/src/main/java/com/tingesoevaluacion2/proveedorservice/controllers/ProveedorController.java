package com.tingesoevaluacion2.proveedorservice.controllers;

import com.tingesoevaluacion2.proveedorservice.entities.ProveedorEntity;
import com.tingesoevaluacion2.proveedorservice.services.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/proveedores")
public class ProveedorController {

    @Autowired
    ProveedorService proveedorService;

    @GetMapping
    public ResponseEntity<ArrayList<ProveedorEntity>> listarProveedores() {
        ArrayList<ProveedorEntity> proveedores = proveedorService.obtenerProveedores();
        if (proveedores.isEmpty()) {
            return ResponseEntity.ok(new ArrayList<>());
        }
        return ResponseEntity.ok(proveedores);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<ProveedorEntity> obtenerPorCodigo(@PathVariable("codigo") String codigo){
        ProveedorEntity proveedor = proveedorService.obtenerPorCodigo(codigo);
        if(proveedor == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(proveedor);
    }

    @PostMapping
    public void nuevoProveedor(@RequestBody ProveedorEntity proveedor) {
        proveedorService.guardarProveedor(proveedor);
    }
}
