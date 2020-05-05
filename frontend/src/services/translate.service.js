import AXIOS from '../utils/axios-instance';

class TranslateService {
  translate(chosenLangId, krWord) {
    console.log("Call: translateFromKrToEn()");
    return AXIOS.get(`/translate/${chosenLangId}?word=${krWord}`).then(response => {
      return Promise.resolve(response);
    }).catch(error => {
      return Promise.reject(error);
    });
  }
}

export default new TranslateService();
