package com.tingesoevaluacion2.proveedorservice.entities;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*
Clase que representa la entidad Proveedor.
*/
@Entity
@Table(name = "Proveedores")
@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ProveedorEntity {

    /*
    Variable que cumple la funcion de identificador unico para la entidad Proveedor.
    Variable de tipo String.
    Variable no nula.
    */
    @Id
    @NotNull
    public String codigo;

    /*
    Variable que representa el nombre del Proveedor.
    Variable de tipo String.
     */
    public String nombre;

    /*
    Variable que representa la categoria del Proveedor.
    Variable de tipo String.
    Valores validos: "A", "B", "C" y "D".
     */
    public String categoria;

    /*
    Variable que represnta si el Proveedor posee o no retencion.
    Variable de tipo String.
    Valores validos: "SI" y "NO"
     */
    public String retencion;
}
