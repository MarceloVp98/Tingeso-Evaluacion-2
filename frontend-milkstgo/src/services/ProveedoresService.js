import axios from "axios";

const PROVEEDOR_API_URL = "http://localhost:8080/proveedores/";

class ProveedorService{

    createProveedor(){
        return axios.post(PROVEEDOR_API_URL)
    }
    
    getProveedores(){
        return axios.get(PROVEEDOR_API_URL);
    }   
}

export default new ProveedorService();