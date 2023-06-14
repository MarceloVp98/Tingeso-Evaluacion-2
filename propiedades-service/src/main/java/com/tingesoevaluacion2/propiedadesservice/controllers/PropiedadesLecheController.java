package com.tingesoevaluacion2.propiedadesservice.controllers;

import com.tingesoevaluacion2.propiedadesservice.entities.PropiedadesLecheEntity;
import com.tingesoevaluacion2.propiedadesservice.services.PropiedadesLecheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
public class PropiedadesLecheController {

    @Autowired
    PropiedadesLecheService propiedadesLecheService;

    @GetMapping("/subirArchivoPropiedades")
    public String subirArchivoPropiedades(){
        return "subirArchivoPropiedades";
    }

    @PostMapping("/subirArchivoPropiedades")
    public String subirPropiedades(@RequestParam("file")MultipartFile file, RedirectAttributes redirectAttributes){
        propiedadesLecheService.guardarArchivoPropiedades(file);
        redirectAttributes.addFlashAttribute("mensaje", "Archivo cargado!");
        propiedadesLecheService.leerCSVPropiedades(file.getOriginalFilename());
        return "redirect:/subirArchivoPropiedades";
    }

    @GetMapping("/informacionPropiedades")
    public String listarPropiedades(Model model){
        ArrayList<PropiedadesLecheEntity> propiedades=propiedadesLecheService.obtenerPropiedadesLeche();
        model.addAttribute("propiedades", propiedades);
        return "informacionPropiedades";
    }
}
