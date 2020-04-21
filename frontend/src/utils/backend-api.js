import axios from 'axios';

const AXIOS = axios.create({
  // baseURL: 'http://18.185.128.105/api',
  baseURL: 'http://localhost:8088/api',
  headers: {
    'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Methods': 'POST, GET, PUT, DELETE',
    'Access-Control-Allow-Headers': 'Content-Type, X-Auth-Token, Origin, Authorization'
  }
});

export default {
  enrichWords(wordObjs) {
    return AXIOS.post('/words/enrich', wordObjs);
  },
  addWord(wordObj) {
    return AXIOS.post('/words', wordObj);
  },
  updateWord(wordObj) {
    return AXIOS.put('/words', wordObj);
  },
  removeWord(wordObj) {
    return AXIOS.delete('/words', {data: wordObj}); // different params for delete method...
  },
  getTextTitles() {
    return AXIOS.get('/texts');
  },
  getTextById(textId) {
    return AXIOS.get(`/texts/${textId}`);
  }
}