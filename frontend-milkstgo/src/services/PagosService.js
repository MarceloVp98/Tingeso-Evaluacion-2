import axios from "axios";

const PAGOS_API_URL = "http://localhost:8080/pagos/";

class PagoService {
  getPagos() {
    return axios.get(PAGOS_API_URL);
  }
}

export default new PagoService();
