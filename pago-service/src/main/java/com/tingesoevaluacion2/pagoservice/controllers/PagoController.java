package com.tingesoevaluacion2.pagoservice.controllers;

import com.tingesoevaluacion2.pagoservice.entities.PagoEntity;
import com.tingesoevaluacion2.pagoservice.services.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/pagos")
public class PagoController {

    @Autowired
    PagoService pagoService;

    @GetMapping
    public ResponseEntity<ArrayList<PagoEntity>> listarPagos(){
        pagoService.guardarPagos();
        ArrayList<PagoEntity> pagos=pagoService.obtenerPagos();
        if(pagos.isEmpty()){
            return ResponseEntity.ok(new ArrayList<>());
        }
        return ResponseEntity.ok(pagos);
    }
}
