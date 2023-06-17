import axios from 'axios';

const ACOPIO_API_URL = "http://localhost:8080/acopios/";

class AcopioService{

    subirAcopios(){
        return axios.post(ACOPIO_API_URL);
    } 

    getAcopios(){
        return axios.get(ACOPIO_API_URL);
    }   
}

export default new AcopioService();