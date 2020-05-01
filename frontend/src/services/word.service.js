import AXIOS from '../utils/axios-instance';

class WordService {
  enrichWords(wordObjs) {
    console.log('Call: enrichWords()');
    return AXIOS.post('/words/enrich', wordObjs);
  }
  addWord(wordObj) {
    console.log('Call: addWord()');
    return AXIOS.post('/words', wordObj);
  }
  updateWord(wordObj) {
    console.log('Call: updateWord()');
    return AXIOS.put('/words', wordObj);
  }
  removeWord(wordObj) {
    console.log('Call: removeWord()');
    return AXIOS.delete('/words', {data: wordObj});
  }
}

export default new WordService();
