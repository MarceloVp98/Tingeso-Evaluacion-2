package com.tingesoevaluacion2.propiedadesservice.controllers;

import com.tingesoevaluacion2.propiedadesservice.entities.PropiedadesLecheEntity;
import com.tingesoevaluacion2.propiedadesservice.services.PropiedadesLecheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@RestController
@RequestMapping("/propiedades")
public class PropiedadesLecheController {

    @Autowired
    PropiedadesLecheService propiedadesLecheService;

    @PostMapping
    public void subirPropiedades(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        propiedadesLecheService.guardarArchivoPropiedades(file);
        propiedadesLecheService.leerCSVPropiedades(file.getOriginalFilename());
    }

    @GetMapping
    public ResponseEntity<ArrayList<PropiedadesLecheEntity>> listarPropiedades() {
        ArrayList<PropiedadesLecheEntity> propiedades = propiedadesLecheService.obtenerPropiedadesLeche();
        if (propiedades.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(propiedades);
    }

    @GetMapping("/{codigo_proveedor}")
    public ResponseEntity<PropiedadesLecheEntity> propiedadesProveedor(@PathVariable("codigo_proveedor") String codigo_proveedor) {
        PropiedadesLecheEntity propiedades_proveedor = propiedadesLecheService.obtenerPropiedadesProveedor(codigo_proveedor);
        if (propiedades_proveedor == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(propiedades_proveedor);
    }

    @DeleteMapping
    public void eliminarPropiedades() {
        propiedadesLecheService.eliminarPropiedades();
    }
}
