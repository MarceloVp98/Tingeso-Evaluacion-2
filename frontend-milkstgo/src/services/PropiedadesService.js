import axios from "axios";

const PROPIEDADES_API_URL = "http://localhost:8080/propiedades/";

class PropiedadesService{

    subirPropiedades(){
        return axios.post(PROPIEDADES_API_URL);
    }  

    getPropiedades(){
        return axios.get(PROPIEDADES_API_URL);
    }   
}

export default new PropiedadesService();