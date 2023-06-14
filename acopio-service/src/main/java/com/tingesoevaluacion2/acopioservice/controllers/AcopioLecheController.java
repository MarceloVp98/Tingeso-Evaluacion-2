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

    @GetMapping
    public ResponseEntity<ArrayList<AcopioLecheEntity>> listasAcopios(){
        ArrayList<AcopioLecheEntity> acopios= acopioLecheService.obtenerAcopiosLeche();
        if(acopios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(acopios);
    }

    @PostMapping("/subirArchivoAcopio")
    public String subirAcopio(@RequestParam("file")MultipartFile file, RedirectAttributes redirectAttributes){
        acopioLecheService.guardarArchivoAcopio(file);
        redirectAttributes.addFlashAttribute("mensaje", "Archivo cargado!");
        acopioLecheService.leerCSV(file.getOriginalFilename());
        return "redirect:/subirArchivoAcopio";
    }
}
