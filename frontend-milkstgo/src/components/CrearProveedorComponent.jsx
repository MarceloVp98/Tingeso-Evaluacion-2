import React, { Component } from "react";
import ProveedoresService from "../services/ProveedoresService";

class CrearProveedorComponent extends Component {
  constructor(props) {
    super(props);

    this.state = {
      codigo: "",
      nombre: "",
      categoria: "",
      retencion: "",
    };
    this.changeCodigoHandler = this.changeCodigoHandler.bind(this);
    this.changeNombreHandler = this.changeNombreHandler.bind(this);
    this.changeCategoriaHandler = this.changeCategoriaHandler.bind(this);
    this.changeRetencionHandler = this.changeRetencionHandler.bind(this);
    this.guardarProveedor = this.guardarProveedor.bind(this);
  }

  guardarProveedor = (e) => {
    e.preventDefault();
    let proveedor = {
      codigo: this.state.codigo,
      nombre: this.state.nombre,
      categoria: this.state.categoria,
      retencion: this.state.retencion,
    };

    ProveedoresService.createProveedor(proveedor).then((res) => {
      this.props.history.push("/proveedores");
    });
  };

  changeCodigoHandler = (event) => {
    this.setState({ codigo: event.target.value });
  };

  changeNombreHandler = (event) => {
    this.setState({ nombre: event.target.value });
  };

  changeCategoriaHandler = (event) => {
    this.setState({ categoria: event.target.value });
  };

  changeRetencionHandler = (event) => {
    this.setState({ retencion: event.target.value });
  };

  cancel() {
    this.props.history.push("/proveedores");
  }

  render() {
    return (
      <div>
        <br></br>
        <div className="container">
          <div className="row">
            <div className="card col-md-6 offset-md-3 offset-md-3">
              <h3 className="text-center">Crear Proveedor</h3>
              <div className="card-body">
                <form>
                  <div className="form-group">
                    <label> Código: </label>
                    <input
                      placeholder="Código Proveedor"
                      name="codigo"
                      className="form-control"
                      value={this.state.codigo}
                      onChange={this.changeCodigoHandler}
                    />
                  </div>
                  <div className="form-group">
                    <label> Nombre: </label>
                    <input
                      placeholder="Nombre"
                      name="nombre"
                      className="form-control"
                      value={this.state.nombre}
                      onChange={this.changeNombreHandler}
                    />
                  </div>
                  <div className="form-group">
                    <label> Categoría: </label>
                    <input
                      placeholder="Categoría"
                      name="categoria"
                      className="form-control"
                      value={this.state.categoria}
                      onChange={this.changeCategoriaHandler}
                    />
                  </div>
                  <div className="form-group">
                    <label> Retención: </label>
                    <input
                      placeholder="Retención"
                      name="retencion"
                      className="form-control"
                      value={this.state.retencion}
                      onChange={this.changeRetencionHandler}
                    />
                  </div>
                  <button
                    className="btn btn-success"
                    onClick={this.guardarProveedor}
                  >
                    Crear
                  </button>
                  <button
                    className="btn btn-danger"
                    onClick={this.cancel.bind(this)}
                    style={{ marginLeft: "10px" }}
                  >
                    Cancel
                  </button>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default CrearProveedorComponent;
