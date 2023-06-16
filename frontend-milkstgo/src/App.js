import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import ListaAcopiosComponent from "./components/ListaAcopiosComponent";

function App() {
  return (
    <div>
      <BrowserRouter>
        <Routes>
            <Route path="/acopios" Component={ListaAcopiosComponent}></Route>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
