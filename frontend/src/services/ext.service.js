import AXIOS from '../utils/axios-instance';

class ExtService {
  getExtResponse(url) {
    console.log("Call: getExtResponse()");
    return AXIOS.get(`/ext?url=${url}`).then(response => {
      return Promise.resolve(response);
    }).catch(error => {
      return Promise.reject(error);
    });
  }
}

export default new ExtService();
