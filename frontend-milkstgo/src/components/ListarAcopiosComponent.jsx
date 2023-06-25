import React, { Component } from "react";
import AcopiosService from "../services/AcopiosService";

class ListarAcopiosComponent extends Component {
  constructor(props) {
    super(props);

    this.state = {
      acopios: [],
    };
  }

  componentDidMount() {
    AcopiosService.getAcopios()
      .then((res) => {
        this.setState({ acopios: res.data });
      })
      .catch((error) => {
        console.error("Error en la solicitud de acopios:", error);
        this.setState({ acopios: [] });
      });
  }

  render() {
    return (
      <div>
        <h2 className="text-center">Lista de acopios</h2>
        <div className="row"></div>
        <br></br>
        <div className="row">
          <table className="table table-striped table-bordered tabla-acopios">
            <thead>
              <tr>
                <th> Fecha</th>
                <th> Turno</th>
                <th> Código Proveedor</th>
                <th> Kilos de leche</th>
              </tr>
            </thead>
            <tbody>
              {this.state.acopios.length !== 0 && this.state.acopios.map((acopio) => (
                <tr key={acopio.id}>
                  <td> {acopio.fecha} </td>
                  <td> {acopio.turno}</td>
                  <td> {acopio.codigo_proveedor}</td>
                  <td> {acopio.kilos_leche}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    );
  }
}

export default ListarAcopiosComponent;
