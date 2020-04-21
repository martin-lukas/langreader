<template>
  <div id="reading-area" v-if="isEnriched">
    <p v-for="(paragraph, index) in paragraphs" :key="index">
      <template v-for="(string, index) in paragraph">
        <a v-if="string.isWord"
           href="#"
           :class="string.str.type"
           :key="index"
           @click.prevent
           @keyup.37="focusPrevious"
           @keyup.39="focusNext"
           @keyup.65="(e) => {updateWords(string.str, 'KNOWN'); focusNext(e)}"
           @keyup.83="(e) => {updateWords(string.str, 'STUDIED'); focusNext(e)}"
           @keyup.68="(e) => {updateWords(string.str, 'IGNORED'); focusNext(e)}"
           @keyup.82="resetWord(string.str)">

          {{ string.str.word }}
        </a>
        <template v-else>{{ string.str.word }}</template>
      </template>
    </p>
  </div>
</template>

<script>
  import utils from "../utils/utils";
  import api from '../utils/backend-api';

  export default {
    props: {
      textObj: Object
    },
    data() {
      return {
        isEnriched: false,
        isLoaded: false,
        paragraphs: [],
        strObjs: [],
        strCounts: []
      }
    },
    created() {
      this.processInput();
      this.enrichWordsFromDB(this.getWordObjs());
    },
    updated() {
      this.$nextTick(this.focusOnFirst());
    },
    methods: {
      processInput() {
        const paragraphTexts = this.textObj.text.split('\n');
        paragraphTexts.forEach((paragraphText) => {
          let result = utils.parseParagraph(paragraphText);
          this.strObjs = [...this.strObjs, ...result];
          this.strCounts.push(result.length);
        });
      },
      enrichWordsFromDB(wordObjs) {
        api.enrichWords(wordObjs).then(response => {
          this.updateStrObjs(response.data);
          this.createParagraphs();
          this.isEnriched = true;
        }).catch(err => {
          console.log(err);
        });
      },
      getWordObjs() {
        return this.strObjs.filter(strObj => {
          return strObj.isWord;
        }).map(wordStrObj => {
          return wordStrObj.str;
        }).filter((wordObj, index, self) => // remove duplicates - for performance reasons (ca. 3x quicker)
          index === self.findIndex((comparedWordObj) => (
            wordObj.word.toLowerCase() === comparedWordObj.word.toLowerCase()
          ))
        );
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
      createParagraphs() {
        const strings = [...this.strObjs];
        let paragraphsOfStrings = [];
        for (let i = 0; i < this.strCounts.length; i++) {
          let paragraphOfStrings = [];
          for (let j = 0; j < this.strCounts[i]; j++) {
            paragraphOfStrings.push(strings.shift());
          }
          paragraphsOfStrings.push(paragraphOfStrings);
        }
        this.paragraphs = paragraphsOfStrings;
      },
      focusOnFirst() {
        console.time('Focus first');
        const allWords = document.getElementById('reading-area').getElementsByTagName('a');
        for (let i = 0; i < allWords.length; i++) {
          if (utils.getClassName(allWords[i]) === '') {
            allWords[i].focus({preventScroll: true});
            break;
          }
        }
        console.timeEnd('Focus first');
      },
      focusPrevious(event) {
        console.time('Focus previous');
        const currentWord = event.target;
        const allWords = document.getElementById('reading-area').getElementsByTagName('a');
        forwardLoop: for (let i = 0; i < allWords.length; i++) {
          if (currentWord === allWords[i]) {
            backwardLoop: for (let j = i - 1; j >= 0; j--) {
              if (utils.getClassName(allWords[j]) === '') {
                allWords[j].focus({preventScroll: true});
                break forwardLoop;
              }
            }
          }
        }
        console.timeEnd('Focus previous');
      },
      focusNext(event) {
        // DOM didn't update quickly enough so it might've jumped to a previously new word,
        // now known, but not updated in the DOM yet
        this.$nextTick(() => {
          console.time('Focus next');
          const currentWord = event.target;
          const allWords = document.getElementById('reading-area').getElementsByTagName('a');
          forwardLoop: for (let i = 0; i < allWords.length; i++) {
            if (currentWord === allWords[i]) {
              forwardForwardLoop: for (let j = i + 1; j < allWords.length; j++) {
                if (utils.getClassName(allWords[j]) === '') {
                  allWords[j].focus({preventScroll: true});
                  break forwardLoop;
                }
              }
            }
          }
          console.timeEnd('Focus next');
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
        this.updateInDB(dbObject, isNew);
      },
      addToDB(wordObj) {
        api.addWord(wordObj).catch(err => {
          console.log(err);
        });
      },
      resetWord(removedWordObj) {
        if (removedWordObj.type !== null) { // start reset only if it's not a new word already
          removedWordObj.type = null;
          this.updateStrObjs([removedWordObj]);
          this.removeFromDB(removedWordObj);
        }
      },
      updateInDB(wordObj) {
        api.updateWord(wordObj).catch(err => {
          console.log(err);
        });
      },
      removeFromDB(wordObj) {
        api.removeWord(wordObj)
          .catch(err => {
            console.log(err);
          });
      }
    }
  }
</script>

<style scoped>
  #reading-area {
    user-select: none;
  }

  #reading-area a {
    display: inline-block;
    padding: 1px 0;
    background-color: #cfe6ff;
    border-radius: 3px;
    text-decoration: none;
    color: #313131;
    cursor: pointer;
  }

  #reading-area a:focus {
    outline: 0;
    color: #e00202;
  }

  #reading-area .KNOWN {
    background-color: white;
  }

  #reading-area .STUDIED {
    background-color: yellow;
  }

  #reading-area .IGNORED {
    background-color: white;
  }
</style>
