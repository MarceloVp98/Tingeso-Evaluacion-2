package com.tingesoevaluacion2.acopioservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
Clase que representa la entidad AcopioLeche.
Su funcion principal es almacenar los datos de los archivos CSV de Acopios de leche
que se suban desde la aplicacion.
*/
@Entity
@Table(name = "AcopioLeche")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcopioLecheEntity {

    /*
    Variable que cumple la funcion de identificador unico para la entidad AcopioLeche.
    Variable de tipo Integer.
    Variable no nula.
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;

    /*
    Variable que representa la fecha en la que se realizo el acopio.
    Variable de tipo String.
     */
    private String fecha;

    /*
    Variable que representa el turno en que se realizo el acopio.
    Variable de tipo String.
    Valores validos: "M"(mañana) y "T"(tarde).
     */
    private String turno;

    /*
    Variable que representa el identificador unico del proveedor.
    Variable de tipo String.
     */
    private String codigo_proveedor;

    /*
    Variable que representa la cantidad de leche del acopio medida en kilos.
    Variable de tipo Integer.
     */
    private Integer kilos_leche;
}
