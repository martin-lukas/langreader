const nonWordChars = '.,!?:;"(){}\\[\\]<>„“”…¿¡=@#';
const regexp = new RegExp(`([${nonWordChars}]*)([^0-9${nonWordChars}]+)([${nonWordChars}]*)`);

export default {
  parseString(string) {
    const matches = string.match(regexp);
    let results = [];
    if (matches === null) {
      results.push({str: {word: string, type: null}, isWord: false});
    } else {
      for (let i = 1; i <= 3; i++) {
        if (matches[i]) {
          results.push({str: {word: matches[i], type: null}, isWord: (i === 2)}); // matches[2] contains word
        }
      }
    }
    return results;
  },
  parseParagraph(paragraphText) {
    let strings = paragraphText.trim().split(/\s+/); // + / char for splitting;
    let results = [];
    strings.forEach(string => {
      results = [...results, ...this.parseString(string), ...[{
        str: {word: ' ', type: null},
        isWord: false
      }]];
    });
    return results;
  },
  // returns the first class of an element, or '' if there is no class
  getClassName(element) {
    return element.className.split(' ')[0];
  }
}
