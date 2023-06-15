package com.tingesoevaluacion2.pagoservice.services;

import com.tingesoevaluacion2.pagoservice.entities.PagoEntity;
import com.tingesoevaluacion2.pagoservice.models.AcopioLecheModel;
import com.tingesoevaluacion2.pagoservice.models.PropiedadesLecheModel;
import com.tingesoevaluacion2.pagoservice.models.ProveedorModel;
import com.tingesoevaluacion2.pagoservice.repositories.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class PagoService {

    @Autowired
    PagoRepository pagoRepository;

    @Autowired
    RestTemplate restTemplate;

    public PagoEntity guardarPago(PagoEntity pago) {
        return pagoRepository.save(pago);
    }

    public void guardarPagos() {
        //List<ProveedorModel> proveedores = restTemplate.getForObject("http://proveedor-service/proveedores/", List.class);
        ResponseEntity<List<ProveedorModel>> response = restTemplate.exchange(
                "http://proveedor-service/proveedores/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProveedorModel>>() {}
        );

        List<ProveedorModel> proveedores = response.getBody();
        PagoEntity pago;
        for (int i = 0; i < proveedores.size(); ++i) {
            pago = crearPago(proveedores.get(i).getCodigo());
            if (pago != null) {
                guardarPago(pago);
            }
        }
        restTemplate.delete("http://acopio-service/acopios/");
        restTemplate.delete("http://propiedades-service/propiedades/");
    }

    public ArrayList<PagoEntity> obtenerPagos() {
        return (ArrayList<PagoEntity>) pagoRepository.findAll();
    }

    public ArrayList<PagoEntity> obtenerPagosProveedor(String codigo_proveedor) {
        return pagoRepository.findByCodigoProveedor(codigo_proveedor);
    }

    public PagoEntity crearPago(String codigo_proveedor) {

        PagoEntity nuevo_pago = new PagoEntity();

        ProveedorModel proveedor_actual = restTemplate.getForObject("http://proveedor-service/proveedores/" + codigo_proveedor, ProveedorModel.class);

        //Se obtienen los acopios de la quincena del proveedor.
        ParameterizedTypeReference<List<AcopioLecheModel>> responseType = new ParameterizedTypeReference<List<AcopioLecheModel>>() {};
        ResponseEntity<List<AcopioLecheModel>> response = restTemplate.exchange(
                "http://acopio-service/acopios/" + codigo_proveedor,
                HttpMethod.GET,
                null,
                responseType
        );
        List<AcopioLecheModel> acopios = response.getBody();

        if (acopios == null) {
            return null;
        }

        nuevo_pago.setCodigo_proveedor(codigo_proveedor);

        nuevo_pago.setNombre_proveedor(proveedor_actual.getNombre());

        //Se obtienen las propiedades de la leche de la quincena del proveedor.
        PropiedadesLecheModel propiedades_proveedor = restTemplate.getForObject("http://propiedades-service/propiedades/" + codigo_proveedor, PropiedadesLecheModel.class);

        //Se crea un arreglo de fechas de tipo LocalDate y se transforman las de acopios.
        ArrayList<LocalDate> fechas = transformarfechas(acopios);

        nuevo_pago.setQuincena(establecerQuincena(fechas));

        PagoEntity quincenaAnterior = obtenerPagoQuincenaAnterior(codigo_proveedor, nuevo_pago.getQuincena());

        nuevo_pago.setTotal_klsleche(totalKlsLeche(acopios));

        nuevo_pago.setNumero_dias_envio(cantidadDiasEnvio(fechas));

        nuevo_pago.setPromedio_diario_klsleche(promedioDiarioLeche(nuevo_pago.getTotal_klsleche(), nuevo_pago.getQuincena()));

        nuevo_pago.setPorcentaje_variacion_leche(porcentajeVariacionLeche(nuevo_pago.getTotal_klsleche(), quincenaAnterior));

        nuevo_pago.setPorcentaje_grasa(propiedades_proveedor.getPorcentaje_grasa());

        nuevo_pago.setPorcentaje_variacion_grasa(porcentajeVariacionGrasa(nuevo_pago.getPorcentaje_grasa(), quincenaAnterior));

        nuevo_pago.setPorcentaje_solidos(propiedades_proveedor.getPorcentaje_solidos());

        nuevo_pago.setPorcentaje_variacion_st(porcentajeVariacionSolidos(propiedades_proveedor.getPorcentaje_solidos(), quincenaAnterior));

        nuevo_pago.setPago_leche(pagoPorLeche(proveedor_actual.getCategoria(), nuevo_pago.getTotal_klsleche()));

        nuevo_pago.setPago_grasa(pagoPorGrasa(nuevo_pago.getPorcentaje_grasa(), nuevo_pago.getTotal_klsleche()));

        nuevo_pago.setPago_solidos(pagoPorSolidos(nuevo_pago.getPorcentaje_solidos(), nuevo_pago.getTotal_klsleche()));

        nuevo_pago.setBonificacion_frecuencia(calculoBonificaionFrecuencia(obtenerTurnosQuincena(acopios, fechas, nuevo_pago.getQuincena())));

        nuevo_pago.setDescuento_variacion_klsleche(porcentajeDescuentoVariacionLeche(nuevo_pago.getPorcentaje_variacion_leche()));

        nuevo_pago.setDescuento_variacion_grasa(porcentajeDescuentoVariacionGrasa(nuevo_pago.getPorcentaje_variacion_grasa()));

        nuevo_pago.setDescuento_variacion_st(porcentajeDescuentoVariacionSolidos(nuevo_pago.getPorcentaje_variacion_st()));

        float pago_acopio_leche = pagoAcopioLeche(nuevo_pago.getPago_leche(), nuevo_pago.getPago_grasa(), nuevo_pago.getPago_solidos(), nuevo_pago.getBonificacion_frecuencia());
        float descuentos = descuentos(pago_acopio_leche, nuevo_pago.getDescuento_variacion_klsleche(), nuevo_pago.getDescuento_variacion_grasa(), nuevo_pago.getDescuento_variacion_st());
        int pago_total = (int) pagoTotal(pago_acopio_leche, descuentos);
        nuevo_pago.setPago_total(pago_total);

        if (Objects.equals(proveedor_actual.getRetencion(), "SI") && nuevo_pago.getPago_total() > 950000) {
            nuevo_pago.setMonto_retencion((pago_total * 13) / 100);
        } else {
            nuevo_pago.setMonto_retencion(0);
        }

        int pago_final = (int) pagoFinal(nuevo_pago.getPago_total(), nuevo_pago.getMonto_retencion());
        nuevo_pago.setMonto_final(pago_final);

        return nuevo_pago;
    }

    public LocalDate establecerQuincena(ArrayList<LocalDate> fechas) {
        int dia;
        int mes;
        int anio;
        if (!fechas.isEmpty()) {
            LocalDate primeraFecha = fechas.get(0);
            if (fechas.get(0).getDayOfMonth() > 15) {
                dia = primeraFecha.lengthOfMonth();
            } else {
                dia = 15;
            }
            mes = primeraFecha.getMonthValue();
            anio = primeraFecha.getYear();
        }
        //Caso en el que el arreglo fechas esta vacio.
        else {
            return null;
        }
        return LocalDate.of(anio, mes, dia);
    }

    //Descripcion metodo: Metodo que obtiene el total de kls de leche de la quincena de un proveedor
    public int totalKlsLeche(List<AcopioLecheModel> acopios_proveedor) {
        int totalKls = 0;
        for (AcopioLecheModel acopioLecheEntity : acopios_proveedor) {
            totalKls = totalKls + acopioLecheEntity.getKilos_leche();
        }
        return totalKls;
    }

    public int cantidadDiasEnvio(ArrayList<LocalDate> fechas) {
        int diaActual;
        ArrayList<Integer> dias = new ArrayList<>();
        //Se recorren las fechas en las que se realizaron envios y se agregan a la lista
        //dias, a menos que el dia se repita.
        for (LocalDate fecha : fechas) {
            diaActual = fecha.getDayOfMonth();
            if (!dias.contains(diaActual)) {
                dias.add(diaActual);
            }
        }
        return dias.size();
    }

    public float promedioDiarioLeche(float total_Klsleche, LocalDate quincena) {
        int total_dias;
        float promedio = 0;
        if (quincena != null) {
            if (quincena.getDayOfMonth() > 15) {
                total_dias = quincena.lengthOfMonth() - 15;
            } else {
                total_dias = 15;
            }
        } else {
            return promedio;
        }
        promedio = total_Klsleche / total_dias;
        return promedio;
    }

    //Descripcion metodo: Metodo que transforma cada fecha String de los acopios a tipo LocalDate.
    public ArrayList<LocalDate> transformarfechas(List<AcopioLecheModel> acopios_proveedor) {
        ArrayList<LocalDate> fechas = new ArrayList<>();
        // Crear un objeto DateTimeFormatter para analizar la cadena de fecha
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        for (AcopioLecheModel acopioLecheEntity : acopios_proveedor) {
            fechas.add(LocalDate.parse(acopioLecheEntity.getFecha(), formato));
        }
        return fechas;
    }

    public float calculoVariacion(float valor_actual, float valor_anterior) {
        return ((valor_actual - valor_anterior) / valor_anterior) * 100;
    }

    public float porcentajeVariacionLeche(float total_klsleche, PagoEntity pago_anterior) {
        float porcentaje_variacion = 0;
        if (pago_anterior != null) {
            porcentaje_variacion = calculoVariacion(total_klsleche, pago_anterior.getTotal_klsleche());
        }
        return porcentaje_variacion;
    }

    public float porcentajeVariacionGrasa(float porcentaje_grasa, PagoEntity pago_anterior) {
        float porcentaje_variacion_grasa = 0;
        if (pago_anterior != null) {
            porcentaje_variacion_grasa = calculoVariacion(porcentaje_grasa, pago_anterior.getPorcentaje_grasa());
        }
        return porcentaje_variacion_grasa;
    }

    public float porcentajeVariacionSolidos(float porcentaje_solidos, PagoEntity pago_anterior) {
        float porcentaje_variacion_solidos = 0;
        if (pago_anterior != null) {
            porcentaje_variacion_solidos = calculoVariacion(porcentaje_solidos, pago_anterior.getPorcentaje_solidos());
        }
        return porcentaje_variacion_solidos;
    }

    public PagoEntity obtenerPagoQuincenaAnterior(String codigo_proveedor, LocalDate quincena_actual) {
        ArrayList<PagoEntity> pagos;
        pagos = (ArrayList<PagoEntity>) pagoRepository.findAll();
        PagoEntity pago_anterior = null;
        LocalDate quincena_anterior;
        //Si es que la quincena corresponde a la segunda del mes la fecha es el mismo anio, mes y el dia es igual a 15.
        if (quincena_actual.getDayOfMonth() > 15) {
            quincena_anterior = LocalDate.of(quincena_actual.getYear(), quincena_actual.getMonth(), 15);
        }
        //En caso contrario se debe cambiar el mes e incluso el anio.
        else {
            //Si el mes actual es enero se debe cambiar el anio.
            if (quincena_actual.getMonthValue() == 1) {
                quincena_anterior = LocalDate.of(quincena_actual.getYear() - 1, 12, 31);
            }
            //Si es otro mes se debe calcular la cantidad de dias del mes anterior.
            else {
                //Fecha para calcular la cantidad de dias del mes anterior.
                LocalDate nuevaFecha = LocalDate.of(quincena_actual.getYear(), quincena_actual.getMonthValue() - 1, 1);
                int cantidadDiasMes = nuevaFecha.lengthOfMonth();
                quincena_anterior = LocalDate.of(quincena_actual.getYear(), quincena_actual.getMonthValue() - 1, cantidadDiasMes);
            }
        }
        LocalDate quincena_pago;
        if (!pagos.isEmpty()) {
            for (PagoEntity pago : pagos) {
                quincena_pago = pago.getQuincena();
                if (Objects.equals(pago.getCodigo_proveedor(), codigo_proveedor) && quincena_pago.getDayOfMonth() == quincena_anterior.getDayOfMonth()
                        && quincena_pago.getMonthValue() == quincena_anterior.getMonthValue() && quincena_pago.getYear() == quincena_anterior.getYear()) {
                    pago_anterior = pago;
                    break;
                }
            }
        } else {
            return null;
        }
        return pago_anterior;
    }

    public int porcentajeDescuentoVariacionLeche(float porcentaje_variacion_leche) {
        int porcentaje_descuento = 0;
        if (porcentaje_variacion_leche <= -8) {
            if (porcentaje_variacion_leche <= -9 && porcentaje_variacion_leche >= -25) {
                porcentaje_descuento = 7;
            }
            if (porcentaje_variacion_leche <= -26 && porcentaje_variacion_leche >= -45) {
                porcentaje_descuento = 15;
            }
            if (porcentaje_variacion_leche <= -46) {
                porcentaje_descuento = 30;
            }
        }
        return porcentaje_descuento;
    }

    public int porcentajeDescuentoVariacionGrasa(float porcentaje_variacion_grasa) {
        int porcentaje_descuento = 0;
        if (porcentaje_variacion_grasa <= -15) {
            if (porcentaje_variacion_grasa <= -16 && porcentaje_variacion_grasa >= -25) {
                porcentaje_descuento = 12;
            }
            if (porcentaje_variacion_grasa <= -26 && porcentaje_variacion_grasa >= -40) {
                porcentaje_descuento = 20;
            }
            if (porcentaje_variacion_grasa <= -41) {
                porcentaje_descuento = 30;
            }
        }
        return porcentaje_descuento;
    }

    public int porcentajeDescuentoVariacionSolidos(float porcentaje_variacion_solidos) {
        int porcentaje_descuento = 0;
        if (porcentaje_variacion_solidos <= -6) {
            if (porcentaje_variacion_solidos <= -7 && porcentaje_variacion_solidos >= -12) {
                porcentaje_descuento = 18;
            }
            if (porcentaje_variacion_solidos <= -13 && porcentaje_variacion_solidos >= -35) {
                porcentaje_descuento = 27;
            }
            if (porcentaje_variacion_solidos <= -36) {
                porcentaje_descuento = 45;
            }
        }
        return porcentaje_descuento;
    }

    public int pagoPorLeche(String categoria_proveedor, int total_klsleche) {
        int total = 0;
        if (Objects.equals(categoria_proveedor, "A")) {
            total = 700 * total_klsleche;
        }
        if (Objects.equals(categoria_proveedor, "B")) {
            total = 550 * total_klsleche;
        }
        if (Objects.equals(categoria_proveedor, "C")) {
            total = 400 * total_klsleche;
        }
        if (Objects.equals(categoria_proveedor, "D")) {
            total = 250 * total_klsleche;
        }
        return total;
    }

    public int pagoPorGrasa(float porcentaje_grasa, int total_klsleche) {
        int total = 0;
        if (porcentaje_grasa >= 0 && porcentaje_grasa <= 20) {
            total = 30 * total_klsleche;
        }
        if (porcentaje_grasa >= 21 && porcentaje_grasa <= 45) {
            total = 80 * total_klsleche;
        }
        if (porcentaje_grasa >= 46) {
            total = 120 * total_klsleche;
        }
        return total;
    }

    public int pagoPorSolidos(float porcentaje_solidos, int total_klsleche) {
        int total = 0;
        if (porcentaje_solidos >= 0 && porcentaje_solidos <= 7) {
            total = -130 * total_klsleche;
        }
        if (porcentaje_solidos >= 8 && porcentaje_solidos <= 18) {
            total = -90 * total_klsleche;
        }
        if (porcentaje_solidos >= 19 && porcentaje_solidos <= 35) {
            total = 95 * total_klsleche;
        }
        if (porcentaje_solidos >= 36) {
            total = 150 * total_klsleche;
        }
        return total;
    }

    public ArrayList<String> obtenerTurnosQuincena(List<AcopioLecheModel> acopios, ArrayList<LocalDate> fechas, LocalDate quincena) {
        ArrayList<String> turnos = new ArrayList<>();
        int inicio_mes;
        int final_quincena;
        boolean maniana;
        boolean tarde;
        if (quincena.getDayOfMonth() > 15) {
            inicio_mes = 16;
            final_quincena = quincena.lengthOfMonth();
        } else {
            inicio_mes = 1;
            final_quincena = 15;
        }
        for (int i = inicio_mes; i <= final_quincena; ++i) {
            int dia = i;
            List<AcopioLecheModel> acopiosFiltrados = IntStream.range(0, fechas.size())
                    .filter(j -> fechas.get(j).getDayOfMonth() == dia)
                    .mapToObj(acopios::get)
                    .collect(Collectors.toList());
            maniana = acopiosFiltrados.stream()
                    .anyMatch(acopio -> Objects.equals(acopio.getTurno(), "M"));
            tarde = acopiosFiltrados.stream()
                    .anyMatch(acopio -> Objects.equals(acopio.getTurno(), "T"));
            if (maniana && tarde) {
                turnos.add("MyT");
            }
            if (maniana && !tarde) {
                turnos.add("M");
            }
            if (!maniana && tarde) {
                turnos.add("T");
            }
        }
        return turnos;
    }

    public float calculoBonificaionFrecuencia(ArrayList<String> turnos) {
        float bonificacion = 0;
        int cantidad_maniana_tarde = Collections.frequency(turnos, "MyT");
        int cantidad_maniana = Collections.frequency(turnos, "MyT") + Collections.frequency(turnos, "M");
        int cantidad_tarde = Collections.frequency(turnos, "MyT") + Collections.frequency(turnos, "T");
        if (cantidad_maniana_tarde >= 10) {
            bonificacion = 20;
        } else {
            if (cantidad_maniana >= 10) {
                bonificacion = 12;
            } else {
                if (cantidad_tarde >= 10) {
                    bonificacion = 8;
                }
            }
        }
        return bonificacion;
    }

    public float pagoAcopioLeche(int pago_leche, int pago_grasa, int pago_solidos, float bonificacion_frecuencia) {
        return pago_leche + pago_grasa + pago_solidos + pago_leche * (bonificacion_frecuencia / 100);
    }

    public float descuentos(float pago_acopio_leche, float descuento_variacion_leche, float descuento_variacion_grasa, float descuento_variacion_solidos) {
        return -pago_acopio_leche * (descuento_variacion_leche / 100) -
                pago_acopio_leche * (descuento_variacion_grasa / 100) -
                pago_acopio_leche * (descuento_variacion_solidos / 100);
    }

    public float pagoTotal(float pago_acopio_leche, float descuentos) {
        return pago_acopio_leche + descuentos;
    }

    public float pagoFinal(float pago_total, int retencion) {
        return pago_total - retencion;
    }
}