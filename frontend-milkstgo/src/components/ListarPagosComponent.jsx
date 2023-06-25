import React, { Component } from "react";
import PagosService from "../services/PagosService";

class ListarPagosComponent extends Component {
  constructor(props) {
    super(props);

    this.state = {
      pagos: [],
    };
  }

  componentDidMount() {
    PagosService.getPagos()
      .then((res) => {
        this.setState({ pagos: res.data });
      })
      .catch((error) => {
        console.error("Error en la solicitud de pagos:", error);
        this.setState({ pagos: [] });
      });
  }

  render() {
    return (
      <div>
        <h2 className="text-center">Lista de Pagos</h2>
        <div className="row"></div>
        <br></br>
        <div className="row">
          <table className="table table-striped table-bordered tabla-pagos">
            <thead>
              <tr>
                <th>Quincena</th>
                <th>Código Proveedor</th>
                <th>Nombre Proveedor</th>
                <th>Total Kls Leche</th>
                <th>Número Días de Envío</th>
                <th>Promedio Diario Kls Leche</th>
                <th>Porcentaje Variación Leche</th>
                <th>Porcentaje Grasa</th>
                <th>Porcentaje Variación Grasa</th>
                <th>Porcentaje Sólidos</th>
                <th>Porcentaje Variación Sólidos</th>
                <th>Pago Leche</th>
                <th>Pago Grasa</th>
                <th>Pago Sólidos</th>
                <th>Bonificación frecuencia</th>
                <th>Descuento Variación Leche</th>
                <th>Descuento Variación Grasa</th>
                <th>Descuento Variación Sólidos</th>
                <th>Pago total</th>
                <th>Monto Retención</th>
                <th>Monto Final</th>
              </tr>
            </thead>
            <tbody>
              {this.state.pagos.length !== 0 && this.state.pagos.map((pagos) => (
                <tr key={pagos.id}>
                  <td> {pagos.quincena}</td>
                  <td> {pagos.codigo_proveedor}</td>
                  <td> {pagos.nombre_proveedor}</td>
                  <td> {pagos.total_klsleche} </td>
                  <td> {pagos.numero_dias_envio}</td>
                  <td> {pagos.promedio_diario_klsleche} </td>
                  <td> {pagos.porcentaje_variacion_leche}</td>
                  <td> {pagos.porcentaje_grasa} </td>
                  <td> {pagos.porcentaje_variacion_grasa}</td>
                  <td> {pagos.porcentaje_solidos} </td>
                  <td> {pagos.porcentaje_variacion_st}</td>
                  <td> {pagos.pago_leche} </td>
                  <td> {pagos.pago_grasa}</td>
                  <td> {pagos.pago_solidos} </td>
                  <td> {pagos.bonificacion_frecuencia}</td>
                  <td> {pagos.descuento_variacion_klsleche} </td>
                  <td> {pagos.descuento_variacion_grasa}</td>
                  <td> {pagos.descuento_variacion_st} </td>
                  <td> {pagos.pago_total}</td>
                  <td> {pagos.monto_retencion} </td>
                  <td> {pagos.monto_final}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    );
  }
}

export default ListarPagosComponent;
