import React, { Component } from "react";
import PropiedadesService from "../services/PropiedadesService";

class ListarPropiedadesComponent extends Component {
  constructor(props) {
    super(props);

    this.state = {
      propiedades: [],
    };
  }

  componentDidMount() {
    PropiedadesService.getPropiedades()
      .then((res) => {
        this.setState({ propiedades: res.data });
      })
      .catch((error) => {
        console.error("Error en la solicitud de propiedades:", error);
        this.setState({ propiedades: [] });
      });
  }

  render() {
    return (
      <div>
        <h2 className="text-center">Lista de Propiedades</h2>
        <div className="row"></div>
        <br></br>
        <div className="row">
          <table className="table table-striped table-bordered tabla-acopios">
            <thead>
              <tr>
                <th> Código Proveedor</th>
                <th> Porcentaje Grasas</th>
                <th> Porcentaje Sólidos</th>
              </tr>
            </thead>
            <tbody>
              {this.state.propiedades.length !== 0 &&
                this.state.propiedades.map((propiedades) => (
                  <tr key={propiedades.id}>
                    <td> {propiedades.codigo_proveedor}</td>
                    <td> {propiedades.porcentaje_grasa}</td>
                    <td> {propiedades.porcentaje_solidos} </td>
                  </tr>
                ))}
            </tbody>
          </table>
        </div>
      </div>
    );
  }
}

export default ListarPropiedadesComponent;
