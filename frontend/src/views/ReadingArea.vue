<template>
  <div>
    <div v-if="!isEnriched" id="loading-div">
      <h3>Loading...</h3>
    </div>
    <div id="reading-area" v-else>
      <router-link to="/library" id="back-button">
        &#8678;
      </router-link>
      <h3>{{ textObj.title }}</h3>
      <p v-for="(paragraph, index) in paragraphs" :key="index">
        <template v-for="(string, index) in paragraph">
          <a v-if="string.isWord"
             href="#"
             :class="string.str.type"
             :key="index"
             @click.prevent
             @keyup.37="focusPrevious"
             @keyup.39="focusNext"
             @keyup.65="(e) => {updateWord(e, string.str, 'KNOWN')}"
             @keyup.83="(e) => {updateWord(e, string.str, 'STUDIED')}"
             @keyup.68="(e) => {updateWord(e, string.str, 'IGNORED')}"
             @keyup.82="(e) => {updateWord(e, string.str, null)}">{{ string.str.word }}</a>
          <template v-else>{{ string.str.word }}</template>
        </template>
      </p>
    </div>
  </div>
</template>

<script>
  import utils from "../utils/utils";
  import WordService from '../services/word.service';
  import TextService from '../services/text.service';

  export default {
    name: 'reading',
    data() {
      return {
        textObj: null,
        isEnriched: false,
        paragraphs: [],
        strObjs: [],
        strCounts: []
      }
    },
    created() {
      if (!this.currentUser) {
        this.$router.push('/login');
      } else {
        this.fetchText(this.$route.params.textId).then(response => {
          this.textObj = response.data;
          this.processInput(this.textObj);
          this.enrichWordsFromDB(this.getWordObjs());
        }).catch(err => {
          console.error(err);
          this.$router.push({name: 'notfound'});
        });
      }
    },
    computed: {
      currentUser() {
        return this.$store.state.auth.user;
      }
    },
    methods: {
      fetchText(textId) {
        return TextService.getTextById(textId);
      },
      processInput(textObj) {
        const paragraphTexts = textObj.text.split('\n');
        paragraphTexts.forEach((paragraphText) => {
          let result = utils.parseParagraph(paragraphText);
          this.strObjs = [...this.strObjs, ...result];
          this.strCounts.push(result.length);
        });
      },
      enrichWordsFromDB(wordObjs) {
        WordService.enrichWords(wordObjs).then(response => {
          this.updateStrObjs(response.data);
          this.createParagraphs();
          this.isEnriched = true;
        }).catch(err => {
          console.error(err);
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
      updateStrObjs(wordObjs) {
        wordObjs.forEach(wordObj => {
          this.strObjs.forEach(strObj => {
            if (strObj.str.word.toLowerCase() === wordObj.word.toLowerCase()) {
              strObj.str.type = wordObj.type;
            }
          });
        });
      },
      updateWord(event, wordObj, newType) {
        const oldType = wordObj.type;
        if (oldType !== newType) {
          const updatedWordObj = {word: wordObj.word.toLowerCase(), type: newType};
          this.updateStrObjs([updatedWordObj]);
          if (newType === null) {
            this.removeFromDB(updatedWordObj);
          } else if (oldType === null) {
            this.addToDB(updatedWordObj);
          } else {
            this.updateInDB(updatedWordObj);
          }
          if (newType !== null) {
            this.$nextTick(() => {
              this.focusNext(event);
            });
          }
        }
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
      focusPrevious(event) {
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
      },
      focusNext(event) {
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
      },
      addToDB(wordObj) {
        WordService.addWord(wordObj).catch(err => {
          console.error(err);
        });
      },
      updateInDB(wordObj) {
        WordService.updateWord(wordObj).catch(err => {
          console.error(err);
        });
      },
      removeFromDB(wordObj) {
        WordService.removeWord(wordObj).catch(err => {
          console.error(err);
        });
      }
    }
  }
</script>

<style scoped>
  #reading-area {
    user-select: none;
    font-size: 1.1em;
  }

  #loading-div {
    text-align: center;
  }

  #back-button {
    font-size: 1.5em;
    background-color: #4994d4;
    color: white;
    padding: 2px 7px;
    border: none;
    border-radius: 10px;
    outline: 0;
    cursor: pointer;
  }

  #back-button:hover {
    background-color: #327bbf;
  }

  p {
    line-height: 1.6;
  }

  a {
    display: inline;
    box-sizing: border-box;
    font-size: 1.05em;
    padding: 0 2px;
    background-color: #d2e7fc;
    border-radius: 4px;
    text-decoration: none;
    color: var(--default-text-color);
    cursor: pointer;
  }

  a:focus {
    outline: 0;
    padding: 1px 0;
    border: 2px solid #96c8ff;
  }

  .KNOWN, .IGNORED {
    background-color: white;
  }

  .STUDIED {
    background-color: #faf63d;
  }

  .KNOWN:focus {
    border: 2px solid #23f607;
  }

  .STUDIED:focus {
    border: 2px solid #fadb0f;
  }

  .IGNORED:focus {
    border: 2px solid #beb4c1;
  }
</style>
