import AXIOS from '../utils/axios-instance';

class ContactService {
  sendMail(message) {
    console.log("Call: sendMail()");
    return AXIOS.post('/mail', message).then(response => {
      return Promise.resolve(response);
    }).catch(error => {
      return Promise.reject(error);
    });
  }
}

export default new ContactService();
