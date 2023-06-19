import axios from "axios";

const ACOPIO_API_URL = "http://localhost:8080/acopios/";

class AcopioService {
  subirAcopios(formData) {
    return axios.post(ACOPIO_API_URL, formData);
  }

  getAcopios() {
    return axios.get(ACOPIO_API_URL);
  }
}

export default new AcopioService();
