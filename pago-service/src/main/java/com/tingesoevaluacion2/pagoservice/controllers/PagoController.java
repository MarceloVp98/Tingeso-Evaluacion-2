package com.tingesoevaluacion2.pagoservice.controllers;

import com.tingesoevaluacion2.pagoservice.entities.PagoEntity;
import com.tingesoevaluacion2.pagoservice.services.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class PagoController {

    @Autowired
    PagoService pagoService;

    @GetMapping("/informacionPagos")
    public String listarPagos(Model model){
        pagoService.guardarPagos();
        ArrayList<PagoEntity> pagos=pagoService.obtenerPagos();
        model.addAttribute("pagos",pagos);
        return "informacionPagos";
    }
}
