import axios from 'axios';

const AXIOS = axios.create({
  baseURL: process.env.NODE_ENV !== 'production'
    ? 'http://localhost:8088/api'
    : 'http://langreader.net/api',
  headers: {
    'Content-type': 'application/json',
    'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Methods': 'POST, GET, PUT, DELETE',
    'Access-Control-Allow-Headers': 'Content-Type, X-Auth-Token, Origin, Authorization'
  }
});

export default {
  authUser() {
    return AXIOS.get('/auth/user');
  },
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
  },
  addText(textObj) {
    return AXIOS.post('/texts', textObj);
  },
  updateText(textObj) {
    return AXIOS.put('/texts', textObj);
  },
  removeText(textObj) {
    return AXIOS.delete('/texts', {data: textObj});
  }
}