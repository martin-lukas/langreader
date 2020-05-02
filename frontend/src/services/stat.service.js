import AXIOS from '../utils/axios-instance';

class StatService {
  getLangStats() {
    console.log("Call: getLangStats()");
    return AXIOS.get('/stats').then(response => {
      return Promise.resolve(response);
    }).catch(error => {
      return Promise.reject(error);
    });
  }
}

export default new StatService();
