package com.tingesoevaluacion2.acopioservice.controllers;

import com.tingesoevaluacion2.acopioservice.entities.AcopioLecheEntity;
import com.tingesoevaluacion2.acopioservice.services.AcopioLecheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@RestController
@RequestMapping("/acopios")
public class AcopioLecheController {

    @Autowired
    AcopioLecheService acopioLecheService;

    @PostMapping
    public void subirAcopio(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        acopioLecheService.guardarArchivoAcopio(file);
        acopioLecheService.leerCSV(file.getOriginalFilename());
    }

    @GetMapping
    public ResponseEntity<ArrayList<AcopioLecheEntity>> listasAcopios() {
        ArrayList<AcopioLecheEntity> acopios = acopioLecheService.obtenerAcopiosLeche();
        if (acopios.isEmpty()) {
            return ResponseEntity.ok(new ArrayList<>());
        }
        return ResponseEntity.ok(acopios);
    }

    @GetMapping("{codigo_proveedor}")
    public ResponseEntity<ArrayList<AcopioLecheEntity>> acopiosProveedor(@PathVariable("codigo_proveedor") String codigo_proveedor) {
        ArrayList<AcopioLecheEntity> acopios_proveedor = acopioLecheService.obtenerAcopiosProveedor(codigo_proveedor);
        if (acopios_proveedor.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(acopios_proveedor);
    }

    @DeleteMapping
    public void eliminarAcopios() {
        acopioLecheService.eliminarAcopios();
    }
}
