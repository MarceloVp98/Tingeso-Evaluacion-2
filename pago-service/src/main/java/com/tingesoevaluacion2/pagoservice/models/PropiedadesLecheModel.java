package com.tingesoevaluacion2.pagoservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropiedadesLecheModel {

    private String codigo_proveedor;
    private Integer porcentaje_solidos;
    private Integer porcentaje_grasa;
}
