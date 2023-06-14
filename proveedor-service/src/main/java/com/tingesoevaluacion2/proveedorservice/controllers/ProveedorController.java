package com.tingesoevaluacion2.proveedorservice.controllers;

import com.tingesoevaluacion2.proveedorservice.entities.ProveedorEntity;
import com.tingesoevaluacion2.proveedorservice.services.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
@RequestMapping
public class ProveedorController {

    @Autowired
    ProveedorService proveedorService;

    @GetMapping("/listar-proveedores")
    public String listar(Model model) {
        ArrayList<ProveedorEntity> proveedores = proveedorService.obtenerProveedores();
        model.addAttribute("proveedores", proveedores);
        return "informacionProveedores";
    }

    @GetMapping("/nuevo-proveedor")
    public String proveedor(){
        return "nuevoProveedor";
    }

    @PostMapping("/nuevo-proveedor")
    public String nuevoProveedor(@RequestParam("codigo") String codigo,
                                 @RequestParam("nombre") String nombre,
                                 @RequestParam("categoria") String categoria,
                                 @RequestParam("retencion") String retencion) {
        proveedorService.guardarProveedor(codigo, nombre, categoria, retencion);
        return "redirect:/nuevo-proveedor";
    }
}
