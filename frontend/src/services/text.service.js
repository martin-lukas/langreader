import AXIOS from '../utils/axios-instance';

class TextService {
  getTextTitles() {
    console.log("Call: getTextTitles()");
    return AXIOS.get(`/texts`);
  }
  getTextById(textId) {
    console.log(`Call: getTextById(${textId})`);
    return AXIOS.get(`/texts/${textId}`);
  }
  addText(textObj) {
    console.log('Call: addText()');
    return AXIOS.post('/texts', textObj);
  }
  updateText(textObj) {
    console.log('Call: updateText()');
    return AXIOS.put('/texts', textObj);
  }
  removeText(textObj) {
    console.log('Call: removeText()');
    return AXIOS.delete('/texts', {data: textObj});
  }
}

export default new TextService();
