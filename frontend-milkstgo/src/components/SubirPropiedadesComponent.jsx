import React, { Component } from "react";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import swal from "sweetalert";
import PropiedadesService from "../services/PropiedadesService";

class SubirPropiedadesComponent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      file: null,
    };
    this.onFileChange = this.onFileChange.bind(this);
  }

  onFileChange(event) {
    this.setState({ file: event.target.files[0] });
  }

  onFileUpload = () => {
    swal({
      title: "¿Está seguro de que desea cargar el archivo de texto?",
      text: "Tenga en cuenta que el archivo solo será cargado si su nombre es 'Data.txt' y si su formato es correcto.",
      icon: "warning",
      buttons: ["Cancelar", "Cargar"],
      dangerMode: true,
    }).then((respuesta) => {
      if (respuesta) {
        swal("Archivo cargado correctamente!", {
          icon: "success",
          timer: "3000",
        });
        const formData = new FormData();
        formData.append("file", this.state.file);
        PropiedadesService.subirPropiedades(formData).then((res) => {});
      } else {
        swal({ text: "Archivo no cargado.", icon: "error" });
      }
    });
  };

  render() {
    return (
      <div className="home">
          <div class="f">
            <div class="container">
              <h1>
                <b>Cargar el archivo de Propiedades</b>
              </h1>
              <Row className="mt-4">
                <Col col="12">
                  <Form.Group className="mb-3" controlId="formFileLg">
                    <Form.Control
                      type="file"
                      size="lg"
                      onChange={this.onFileChange}
                    />
                  </Form.Group>
                  <Button varian="primary" onClick={this.onFileUpload}>
                    Cargar el archivo a la Base de Datos
                  </Button>
                </Col>
              </Row>
            </div>
          </div>
          <br></br>
          <hr></hr>
          <div class="form1">
            <h5>
              <b>Recuerde que la extensión del archivo debe ser csv!</b>
            </h5>
          </div>
      </div>
    );
  }
}

export default SubirPropiedadesComponent;
