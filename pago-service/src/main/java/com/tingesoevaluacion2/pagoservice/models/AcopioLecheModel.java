package com.tingesoevaluacion2.pagoservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcopioLecheModel {

    private String fecha;
    private String turno;
    private String codigo_proveedor;
    private Integer kilos_leche;
}
