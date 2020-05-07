import AXIOS from '../utils/axios-instance';

class LangService {
  getUserLangs() {
    console.log("Call: getUserLangs()");
    return AXIOS.get('/langs').then(response => {
      localStorage.setItem('userLangs', JSON.stringify(response.data));
      return Promise.resolve(response);
    }).catch(error => {
      return Promise.reject(error);
    });
  }

  addUserLang(language) {
    console.log("Call: addUserLang()");
    return AXIOS.post('/langs', language).then(response => {
      const currentLangsLS = JSON.parse(localStorage.getItem('userLangs'));
      localStorage.setItem('userLangs', JSON.stringify([...currentLangsLS, language]));
      return Promise.resolve(response);
    });
  }

  removeUserLang(language) {
    console.log("Call: removeUserLang()");
    return AXIOS.delete('/langs', {data: language}).then(response => {
      const currentLangsLS = JSON.parse(localStorage.getItem('userLangs'));
      const updatedUserLangs = currentLangsLS.filter(aLang => {
        return aLang.code !== language.code;
      });
      localStorage.setItem('userLangs', JSON.stringify(updatedUserLangs));
      return Promise.resolve(response);
    }).catch(err => {
      return Promise.reject(err);
    });
  }

  getChosenLang() {
    console.log("Call: getChosenLang()");
    return AXIOS.get('/langs/chosen').then(response => {
      localStorage.setItem('chosenLang', JSON.stringify(response.data));
      return Promise.resolve(response);
    }).catch(error => {
      return Promise.reject(error);
    });
  }

  setChosenLang(lang) {
    console.log("Call: setChosenLang()");
    return AXIOS.put('/langs/chosen', lang).then(response => {
      localStorage.setItem('chosenLang', JSON.stringify(lang));
      return Promise.resolve(response);
    }).catch(error => {
      return Promise.reject(error);
    });
  }

  getNativeLang() {
    console.log("Call: getNativeLang()");
    return AXIOS.get('/langs/native').then(response => {
      localStorage.setItem('nativeLang', JSON.stringify(response.data));
      return Promise.resolve(response);
    }).catch(error => {
      return Promise.reject(error);
    });
  }

  getAllLangs() {
    console.log("Call: getAllLangs()");
    return AXIOS.get('/langs/all').then(response => {
      localStorage.setItem('allLangs', JSON.stringify(response.data));
      return Promise.resolve(response);
    }).catch(error => {
      return Promise.reject(error);
    });
  }
}

export default new LangService();
