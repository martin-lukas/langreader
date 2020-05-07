import AXIOS from '../utils/axios-instance';

class TranslateService {
  translate(word) {
    console.log(`Call: translate(${word})`);
    return AXIOS.get(`/translate?word=${word}`).then(response => {
      return Promise.resolve(response);
    }).catch(error => {
      return Promise.reject(error);
    });
  }
}

export default new TranslateService();
