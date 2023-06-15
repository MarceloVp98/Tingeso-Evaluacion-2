package com.tingesoevaluacion2.pagoservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProveedorModel {

    private String codigo;
    private String nombre;
    private String categoria;
    private String retencion;
}
