import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import ListarAcopiosComponent from "./components/ListarAcopiosComponent";
import ListarPropiedadesComponent from "./components/ListarPropiedadesComponent";
import ListarPagosComponent from "./components/ListarPagosComponent";
import ListarProveedoresComponent from "./components/ListarProveedoresComponent";
import CrearProveedorComponent from "./components/CrearProveedorComponent";
import SubirAcopioComponent from "./components/SubirAcopioComponent";
import SubirPropiedadesComponent from "./components/SubirPropiedadesComponent";
import NavbarComponent from "./components/NavbarComponent";

function App() {
  return (
    <div>
      <NavbarComponent />
      <BrowserRouter>
        <Routes>
            <Route path="/acopios" Component={ListarAcopiosComponent}></Route>
            <Route path="/propiedades" Component={ListarPropiedadesComponent}></Route>
            <Route path="/pagos" Component={ListarPagosComponent}></Route>
            <Route path="/proveedores" Component={ListarProveedoresComponent}></Route>
            <Route path="/crear-proveedores" Component={CrearProveedorComponent}></Route>
            <Route path="/subir-acopio" Component={SubirAcopioComponent}></Route>
            <Route path="/subir-propiedades" Component={SubirPropiedadesComponent}></Route>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
