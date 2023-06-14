package com.tingesoevaluacion2.propiedadesservice.services;

import com.tingesoevaluacion2.propiedadesservice.entities.PropiedadesLecheEntity;
import com.tingesoevaluacion2.propiedadesservice.repositories.PropiedadesLecheRepository;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

@Service
public class PropiedadesLecheService {

    @Autowired
    PropiedadesLecheRepository propiedadesLecheRepository;

    private final Logger logg = LoggerFactory.getLogger(PropiedadesLecheService.class);

    /*
    Descripcion metodo: Metodo que guarda una propiedad de leche en la base de datos.
    Parametros de entrada: Propiedades(PropiedadesLecheEntity).
    Retorno: Propiedades(PropiedadesLecheEntity).
    */
    public PropiedadesLecheEntity guardarPropiedades(PropiedadesLecheEntity propiedades) {
        return propiedadesLecheRepository.save(propiedades);
    }

    /*
    Descripcion metodo: Metodo que crea una propiedad de leche y la guarda en la base de datos.
    Parametros de entrada: Codigo del proveedor(String), porcentaje de solidos(Integer) y porcentaje de grasa(Integer).
    Retorno: Propiedades(PropiedadesLecheEntity).
    */
    public PropiedadesLecheEntity guardarPropiedadesLecheBD(String codigo_proveedor, Integer porcentaje_solidos, Integer porcentaje_grasa) {
        PropiedadesLecheEntity nuevasPropiedades = new PropiedadesLecheEntity();
        nuevasPropiedades.setCodigo_proveedor(codigo_proveedor);
        nuevasPropiedades.setPorcentaje_solidos(porcentaje_solidos);
        nuevasPropiedades.setPorcentaje_grasa(porcentaje_grasa);
        return guardarPropiedades(nuevasPropiedades);
    }

    /*
    Descripcion metodo: Metodo que obtiene todas las propiedades de la base de datos.
    Parametros de entrada: Vacio.
    Retorno: Lista con las propiedades encontradas(ArrayList<PropiedadesLecheEntity>)
    */
    public ArrayList<PropiedadesLecheEntity> obtenerPropiedadesLeche() {
        return (ArrayList<PropiedadesLecheEntity>) propiedadesLecheRepository.findAll();
    }

    /*
    Descripcion metodo: Metodo que obtiene las propiedades de la leche de la quincena de un Proveedor.
    Parametros de entrada: codigo del proveedor(String).
    Retorno: Las propiedades de la leche de ese Proveedor(PropiedadesLecheEntity).
    */
    public PropiedadesLecheEntity obtenerPropiedadesProveedor(String codigo_proveedor) {
        PropiedadesLecheEntity propiedades_proveedor = new PropiedadesLecheEntity();
        ArrayList<PropiedadesLecheEntity> total_propiedades;
        total_propiedades = (ArrayList<PropiedadesLecheEntity>) propiedadesLecheRepository.findAll();
        for (PropiedadesLecheEntity totalPropiedade : total_propiedades) {
            if (Objects.equals(totalPropiedade.getCodigo_proveedor(), codigo_proveedor)) {
                propiedades_proveedor = totalPropiedade;
                break;
            }
        }
        return propiedades_proveedor;
    }

    /*
    Descripcion metodo: Metodo que elimina todos las propiedades almacenados en la base de datos.
    Parametros de entrada: Vacio.
    Retorno: Vacio.
    */
    public void eliminarPropiedades(){
        propiedadesLecheRepository.deleteAll();
    }

    /*
    Descripcion metodo: Metodo que guarda un archivo.
    Parametros de entrada: Archivo(MultipartFile).
    Retorno: Mensaje de comprobacion(String).
    */
    @Generated
    public String guardarArchivoPropiedades(MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (filename != null) {
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    Path path = Paths.get(file.getOriginalFilename());
                    Files.write(path, bytes);
                    logg.info("Archivo de Propiedades Guardado");
                } catch (IOException e) {
                    logg.error("ERROR", e);
                }
            } else {
                return "Archivo de Propiedades Guardado con Exito";
            }
            return "Archivo de Propiedades Guardado con Exito";
        } else {
            return "No Se Pudo Guardar el Archivo de Propiedades";
        }
    }

    /*
    Descripcion metodo: Metodo que lee archivo CSV, guardando las propiedades en la base de datos.
    Parametros de entrada: Direccion(String).
    Retorno: Vacio.
    */
    @Generated
    public void leerCSVPropiedades(String direccion) {
        String texto = "";
        BufferedReader bf = null;
        try {
            bf = new BufferedReader(new FileReader(direccion));
            String temp = "";
            String bfRead;
            int count = 1;
            while ((bfRead = bf.readLine()) != null) {
                if (count == 1) {
                    count = 0;
                } else {
                    guardarPropiedadesLecheBD(bfRead.split(";")[0], Integer.valueOf(bfRead.split(";")[1]), Integer.valueOf(bfRead.split(";")[2]));
                    temp = temp + "\n" + bfRead;
                }
            }
            texto = temp;
            logg.info("Archivo de Propiedades Leido Exitosamente.");
        } catch (Exception e) {
            logg.info("No Se Encontro el Archivo de Propiedades");
        } finally {
            if (bf != null) {
                try {
                    bf.close();
                } catch (IOException e) {
                    logg.error("ERROR", e);
                }
            }
        }
    }
}
