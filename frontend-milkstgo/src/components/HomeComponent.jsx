import React, { Component } from "react";
import styled from "styled-components";
import { createGlobalStyle } from "styled-components";

export default function Home() {
  return (
    <div>
      <GlobalStyle />
      <HomeStyle>hola</HomeStyle>
    </div>
  );
}

const GlobalStyle = createGlobalStyle`
    body { 
        background-color: #262626;
`;
const HomeStyle = styled.nav`
  .contenedor {
    background-color: red;
  }
`;
