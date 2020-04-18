import axios from "axios";

const SERVER_URL = 'https://lang-reader.herokuapp.com';
const PORT = '';
// const SERVER_URL = 'http://localhost';
// const PORT = ':9009';
const URL = `${SERVER_URL}${PORT}`;
const ENRICH_API = '/words/enrich';
const UPDATE_WORD_API = '/words';
const RESET_WORD_API = UPDATE_WORD_API;

export function enrichWordsFromDB(wordObjs, callback) {
  console.log(`${URL}${ENRICH_API}`);
  axios.post(`${URL}${ENRICH_API}`, wordObjs)
    .then(response => {
      callback(response.data);
    })
    .catch(err => {
      console.log(err);
      this.errorMsg = err.toString();
    });
}

export function updateDB(wordObj, isNewWord) {
  const httpMethod = (isNewWord) ? 'post' : 'put';
  axios({method: httpMethod, url: `${URL}${UPDATE_WORD_API}`, data: wordObj})
    .then(response => {
      if (response.data.status !== 200) {
        this.errorMsg = 'The server didn\'t accept change on word \'' + wordObj.word + '\'';
      }
    })
    .catch(err => {
      console.log(err);
      this.errorMsg = err.toString();
    });
}

export function removeFromDB(wordObj) {
  axios({
    method: 'delete',
    url: `${URL}${RESET_WORD_API}`,
    data: {
      word: wordObj.word.toLowerCase()
    }
  })
    .then(response => {
      if (response.data.status !== 200) {
        this.errorMsg = 'The server couldn\'t delete the word \'' + wordObj.word + '\'';
      }
    })
    .catch(err => {
      console.log(err);
      this.errorMsg = err.toString();
    });
}
