import React, { Component } from 'react';

class NavbarComponent extends Component {
  render() {
    return (
      <nav className="navbar navbar-expand-lg navbar-light bg-light">
        <a className="navbar-brand" href="/">MilkStgo</a>
        <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarNav">
          <ul className="navbar-nav">
            <li className="nav-item">
              <a className="nav-link" href="/proveedores">Listado Proveedores</a>
            </li>
            <li className="nav-item">
              <a className="nav-link" href="/crear-proveedores">Crear Proveedor</a>
            </li>
            <li className="nav-item">
              <a className="nav-link" href="/acopios">Listado Acopios</a>
            </li>
            <li className="nav-item">
              <a className="nav-link" href="/subir-acopio">Subir Acopio</a>
            </li>
            <li className="nav-item">
              <a className="nav-link" href="/propiedades">Listado Propiedades</a>
            </li>
            <li className="nav-item">
              <a className="nav-link" href="/subir-propiedades">Subir Propiedades</a>
            </li>
            <li className="nav-item">
              <a className="nav-link" href="/pagos">Listado Pagos</a>
            </li>
          </ul>
        </div>
      </nav>
    );
  }
}

export default NavbarComponent;
