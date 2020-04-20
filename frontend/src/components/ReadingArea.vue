<template>
  <div id="reading-area" class="rounded-lg">
    <p v-for="(paragraph, index) in paragraphs" :key="index">
      <span :class="(string.isWord) ? 'word' : 'other'"
            v-for="(string, index) in paragraph"
            :key="index">
        <a href="#"
           :class="string.str.type"
           v-if="string.isWord"
           @click.prevent
           @keyup.37="focusPrevious"
           @keyup.39="focusNext"
           @keyup.65="(e) => {$emit('update-word-type', string.str, 'KNOWN'); focusNext(e)}"
           @keyup.83="(e) => {$emit('update-word-t    ype', string.str, 'STUDIED'); focusNext(e)}"
           @keyup.68="(e) => {$emit('update-word-type', string.str, 'IGNORED'); focusNext(e)}"
           @keyup.82="$emit('reset-word', string.str)">{{ string.str.word }}</a>
        <template v-else>{{ string.str.word }}</template>
      </span>
    </p>
  </div>
</template>

<script>
  import {getClassName, parseParagraph} from "../utils/utils";
  import axios from "axios";

  // const SERVER_URL = 'https://lang-reader.herokuapp.com';
  const SERVER_URL = 'http://localhost';
  const ENRICH_API = '/api/words/enrich';
  const UPDATE_WORD_API = '/api/words';
  const RESET_WORD_API = UPDATE_WORD_API;

  export default {
    props: {
      input: String
    },
    data() {
      return {
        paragraphs: [],
        strObjs: [],
        strCounts: []
      }
    },
    methods: {
      submit() {
        this.processInput();
        this.enrichWordsFromDB(this.getWordObjs());
      },
      processInput() {
        const paragraphTexts = this.input.split('\n');
        paragraphTexts.forEach((paragraphText) => {
          let result = parseParagraph(paragraphText);
          this.strObjs = [...this.strObjs, ...result];
          this.strCounts.push(result.length);
        });
      },
      updateWords(theWordObj, newType) {
        const oldType = theWordObj.type;
        // update Vue instance first
        theWordObj.type = newType;
        this.updateStrObjs([theWordObj]);
        // update DB
        const isNew = (oldType === null);
        const dbObject = {word: theWordObj.word.toLowerCase(), type: newType};
        this.updateDB(dbObject, isNew);
      },
      resetWord(removedWordObj) {
        if (removedWordObj.type !== null) { // start reset only if it's not a new word already
          removedWordObj.type = null;
          this.updateStrObjs([removedWordObj]);
          this.removeFromDB(removedWordObj);
        }
      },
      updateStrObjs(wordObjs) { // TODO: improve performance here... make it async?
        // ORIGINAL
        // wordObjs.forEach(wordObj => {
        //   const selectedStrObjs =
        //     this.strObjs.filter(strObj => {
        //       return strObj.isWord;
        //     })
        //       .filter(strWordObj => {
        //         return (strWordObj.str.word.toLowerCase() === wordObj.word.toLowerCase());
        //       });
        //   selectedStrObjs.forEach(selectedStrObj => {
        //     selectedStrObj.str.type = wordObj.type;
        //   });
        // });
        // NOT MUCH BETTER
        wordObjs.forEach(wordObj => {
          this.strObjs.forEach(strObj => {
            if (strObj.str.word.toLowerCase() === wordObj.word.toLowerCase()) {
              strObj.str.type = wordObj.type;
            }
          });
        });
      },
      getWordObjs() {
        return this.strObjs.filter(strObj => {
          return strObj.isWord;
        })
          .map(wordStrObj => {
            return wordStrObj.str;
          }) // remove duplicates - for performance reasons (ca. 3x quicker)
          .filter((wordObj, index, self) =>
            index === self.findIndex((comparedWordObj) => (
              wordObj.word.toLowerCase() === comparedWordObj.word.toLowerCase()
            ))
          );
      },
      enrichWordsFromDB(wordObjs) {
        axios.post(`${SERVER_URL}${ENRICH_API}`, wordObjs)
          .then(response => {
            this.updateStrObjs(response.data);
            this.isEnriched = true;
          })
          .catch(err => {
            console.log(err);
          });
      },
      updateDB(wordObj, isNewWord) {
        const httpMethod = (isNewWord) ? 'post' : 'put';
        axios({method: httpMethod, url: `${SERVER_URL}${UPDATE_WORD_API}`, data: wordObj})
          .then(response => {
            if (response.data.status !== 200) {
              this.errorMsg = 'The server didn\'t accept change on word \'' + wordObj.word + '\'';
            }
          })
          .catch(err => {
            console.log(err);
            this.errorMsg = err.toString();
          });
      },
      removeFromDB(wordObj) {
        axios({
          method: 'delete',
          url: `${SERVER_URL}${RESET_WORD_API}`,
          data: {
            word: wordObj.word.toLowerCase()
          }
        })
          .catch(err => {
            console.log(err);
          });
      },
      focusPrevious(event) {
        const previousWordElement = this.getPreviousWordElement(event.target);
        if (previousWordElement) {
          previousWordElement.focus({preventScroll: true});
        }
      },
      focusNext(event) {
        // DOM didn't update quickly enough so it might've jumped to a previously new word,
        // now known, but not updated in the DOM yet
        this.$nextTick(() => {
          const nextWordElement = this.getNextWordElement(event.target)
          if (nextWordElement) {
            nextWordElement.focus({preventScroll: true});
          }
        });
      },
      getPreviousWordElement(wordElement) {
        let previousWordElement = null;
        let previousParent = wordElement.parentNode.previousSibling;
        // look for closest new word
        // first looking through current paragraph
        while (previousParent) {
          if (getClassName(previousParent) !== 'other'
            && getClassName(previousParent.firstChild) === '') {

            previousWordElement = previousParent.firstChild;
            break;
          }
          previousParent = previousParent.previousSibling;
        }
        // now going through everything before current word, even in prev. paragraphs
        if (previousWordElement === null) {
          let previousParagraph = wordElement.parentNode.parentNode.previousSibling;
          // going paragraph by paragraph
          parLoop: while (previousParagraph) {
            let previousChild = previousParagraph.lastChild;
            // going through elements in each paragraph
            while (previousChild) {
              if (getClassName(previousChild) !== 'other'
                && getClassName(previousChild.firstChild) === '') {

                previousWordElement = previousChild.firstChild;
                break parLoop;
              }
              previousChild = previousChild.previousSibling;
            }
            previousParagraph = previousParagraph.previousSibling;
          }
        }

        return previousWordElement;
      },
      getNextWordElement(wordElement) {
        let nextWordElement = null;
        let nextParent = wordElement.parentNode.nextSibling;
        // look for closest new word
        // first looking through current paragraph
        while (nextParent) {
          if (getClassName(nextParent) !== 'other' && getClassName(nextParent.firstChild) === '') {
            nextWordElement = nextParent.firstChild;
            break;
          }
          nextParent = nextParent.nextSibling;
        }
        // now going through rest of the document
        if (nextWordElement === null) {
          let nextParagraph = wordElement.parentNode.parentNode.nextSibling;
          // going paragraph by paragraph
          parLoop: while (nextParagraph) {
            let nextChild = nextParagraph.firstChild;
            // going through elements in each paragraph
            while (nextChild) {
              if (getClassName(nextChild) !== 'other' && getClassName(nextChild.firstChild) === '') {
                nextWordElement = nextChild.firstChild;
                break parLoop;
              }
              nextChild = nextChild.nextSibling;
            }
            nextParagraph = nextParagraph.nextSibling;
          }
        }

        return nextWordElement;
      },
      focusOnFirst() {
        const firstPar = document.getElementById('reading-area').firstChild;
        let firstColoredWord = null;
        if (firstPar) {
          let firstWord = firstPar.firstChild;
          if (getClassName(firstWord) !== 'other' && getClassName(firstWord.firstChild) === '') {
            firstColoredWord = firstWord.firstChild;
          } else {
            firstColoredWord = this.getNextWordElement(firstWord.firstChild);
          }
          if (firstColoredWord) {
            firstColoredWord.focus({preventScroll: true});
          }
        }
      }
    },
    created() {
      const strings = [...this.strings];
      let paragraphsOfStrings = [];
      for (let i = 0; i < this.stringCounts.length; i++) {
        let paragraphOfStrings = [];
        for (let j = 0; j < this.stringCounts[i]; j++) {
          paragraphOfStrings.push(strings.shift());
        }
        paragraphsOfStrings.push(paragraphOfStrings);
      }
      this.paragraphs = paragraphsOfStrings;
    },
    mounted() {
      this.focusOnFirst();
    }
  }
</script>

<style scoped>
  #reading-area {
    border: 1px solid black;
    border-radius: 5px;
    user-select: none;
  }

  .other {
  }

  a {
    display: inline-block;
    padding: 1px 0;
    background-color: #cfe6ff;
    text-decoration: none;
    cursor: pointer;
  }

  a:focus {
    outline: 0;
    color: #e00202;
  }

  .KNOWN {
    background-color: white;
  }

  .STUDIED {
    background-color: yellow;
  }

  .IGNORED {
    background-color: white;
  }
</style>
