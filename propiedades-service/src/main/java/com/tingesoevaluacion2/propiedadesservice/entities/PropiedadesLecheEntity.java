package com.tingesoevaluacion2.propiedadesservice.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
Clase que representa la entidad Propiedades.
Su funcion principal es almacenar los datos de los archivos CSV de propiedades
que se suban desde la aplicacion.
*/
@Entity
@Table(name = "PropiedadesLeche")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropiedadesLecheEntity {

    /*
    Variable que cumple la funcion de identificador unico para la entidad Propiedades.
    Variable de tipo Integer.
    Variable no nula.
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;

    /*
    Variable que representa el identificador unico del proveedor.
    Variable de tipo String.
     */
    private String codigo_proveedor;

    /*
    Variable que representa el porcentaje de solidos totales de la leche.
    Variable de tipo Integer.
     */
    private Integer porcentaje_solidos;

    /*
    Variable que representa el porcentaje de grasa que posee la leche.
    Variable de tipo Integer.
     */
    private Integer porcentaje_grasa;
}