import React, { Component } from "react";
import ProveedoresService from "../services/ProveedoresService";

class ListarProveedoresComponent extends Component {
  constructor(props) {
    super(props);

    this.state = {
      proveedores: [],
    };
  }

  componentDidMount() {
    ProveedoresService.getProveedores().then((res) => {
      this.setState({ proveedores: res.data });
    });
  }

  render() {
    return (
      <div>
        <h2 className="text-center">Lista de proveedores</h2>
        <div className="row"></div>
        <br></br>
        <div className="row">
          <table className="table table-striped table-bordered tabla-acopios">
            <thead>
              <tr>
                <th> Código</th>
                <th> Nombre</th>
                <th> Categoría</th>
                <th> Retencion</th>
              </tr>
            </thead>
            <tbody>
              {this.state.proveedores.map((proveedor) => (
                <tr>
                  <td> {proveedor.codigo} </td>
                  <td> {proveedor.nombre}</td>
                  <td> {proveedor.categoria}</td>
                  <td> {proveedor.retencion}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    );
  }
}

export default ListarProveedoresComponent;
