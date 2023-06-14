package com.tingesoevaluacion2.acopioservice.services;

import com.tingesoevaluacion2.acopioservice.entities.AcopioLecheEntity;
import com.tingesoevaluacion2.acopioservice.repositories.AcopioLecheRepository;
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
public class AcopioLecheService {

    @Autowired
    AcopioLecheRepository acopioLecheRepository;

    private final Logger logg = LoggerFactory.getLogger(AcopioLecheService.class);

    public AcopioLecheEntity guardarAcopio(AcopioLecheEntity acopio) {
        return acopioLecheRepository.save(acopio);
    }

    public AcopioLecheEntity guardarAcopioBD(String fecha, String turno, String codigo_proveedor, Integer kilos_leche) {
        AcopioLecheEntity nuevoAcopio = new AcopioLecheEntity();
        nuevoAcopio.setFecha(fecha);
        nuevoAcopio.setTurno(turno);
        nuevoAcopio.setCodigo_proveedor(codigo_proveedor);
        nuevoAcopio.setKilos_leche(kilos_leche);
        return guardarAcopio(nuevoAcopio);
    }

    public ArrayList<AcopioLecheEntity> obtenerAcopiosLeche() {
        return (ArrayList<AcopioLecheEntity>) acopioLecheRepository.findAll();
    }

    public ArrayList<AcopioLecheEntity> obtenerAcopiosProveedor(String codigo_proveedor) {
        ArrayList<AcopioLecheEntity> acopios;
        acopios = (ArrayList<AcopioLecheEntity>) acopioLecheRepository.findAll();
        ArrayList<AcopioLecheEntity> acopiosProveedor = new ArrayList<>();
        for (int i = 0; i < acopios.size(); ++i) {
            if (Objects.equals(acopios.get(i).getCodigo_proveedor(), codigo_proveedor)) {
                acopiosProveedor.add(acopios.get(i));
            }
        }
        return acopiosProveedor;
    }

    public void eliminarAcopios() {
        acopioLecheRepository.deleteAll();
    }

    //Descripcion metodo: Metodo que guarda un archivo.
    @Generated
    public String guardarArchivoAcopio(MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (filename != null) {
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    Path path = Paths.get(file.getOriginalFilename());
                    Files.write(path, bytes);
                    logg.info("Archivo de Acopio Guardado");
                } catch (IOException e) {
                    logg.error("ERROR", e);
                }
            } else {
                return "Archivo guardado con exito";
            }
            return "Archivo de Acopio guardado con exito";
        } else {
            return "No se pudo guardar el archivo de Acopio";
        }
    }

    //Descripcion metodo: Metodo que lee archivo CSV, guardando los acopios en la base de datos.
    @Generated
    public void leerCSV(String direccion) {
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
                    guardarAcopioBD(bfRead.split(";")[0], bfRead.split(";")[1], bfRead.split(";")[2], Integer.valueOf(bfRead.split(";")[3]));
                    temp = temp + "\n" + bfRead;
                }
            }
            texto = temp;
            logg.info("Archivo de Acopio Leido Exitosamente.");
        } catch (Exception e) {
            logg.info("No Se Encontro el Archivo de Acopio");
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
