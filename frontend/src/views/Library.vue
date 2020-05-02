<template>
  <div id="library">
    <div id="library-toolbar">
      <input type="text"
             v-model="searchQuery"
             placeholder="Search.."
             @keyup="filterTexts"/>
      <button id="add-text-btn"
              @click="toggleAddTextForm"
              :class="(isToggledAddText) ? 'toggled' : ''">
        <i class="fas fa-plus"></i>
      </button>
    </div>
    <AddTextForm v-if="isToggledAddText" @add-text="splitTextAndAddToDB"/>
    <div class="text-item-div" v-for="textObj in shownTextObjs" :key="textObj.id">
      <router-link class="text-item-link" :to="'/reading/' + textObj.id">
        {{ textObj.title }}
      </router-link>
      <button class="text-item-edit-btn" @click="updateTextInDB(textObj)">
        <i class="far fa-edit"></i>
      </button>
      <button class="text-item-del-btn" @click="removeTextFromDB(textObj)">
        <i class="fas fa-ban"></i>
      </button>
    </div>
  </div>
</template>

<script>
  import TextService from '../services/text.service';
  import AddTextForm from "../components/AddTextForm";

  const TEXT_LIMIT = 10000;
  const TITLE_LIMIT = 300;
  const TOTAL_INPUT_LIMIT = 500000;

  export default {
    name: 'library',
    components: {AddTextForm},
    data() {
      return {
        searchQuery: '',
        textObjs: [],
        shownTextObjs: [],
        isToggledAddText: false
      }
    },
    computed: {
      currentUser() {
        return this.$store.state.auth.user;
      },
      chosenLang() {
        return this.$store.state.lang.chosenLang;
      }
    },
    created() {
      if (!this.currentUser) {
        this.$router.push('/login');
      } else {
        if (!this.chosenLang) {
          this.$router.push('/language');
        } else {
          this.fetchTextTitles();
        }
      }
    },
    methods: {
      fetchTextTitles() {
        if (this.chosenLang) {
          TextService.getTextTitles().then(response => {
            this.textObjs = response.data;
            this.shownTextObjs = this.textObjs;
          }).catch(err => {
            console.error(err);
          });
        }
      },
      filterTexts() {
        this.shownTextObjs = this.textObjs.filter(textObj => {
          return textObj.title.toLowerCase().includes(this.searchQuery.toLowerCase());
        });
      },
      splitTextAndAddToDB(textObj) {
        if (textObj.title < TITLE_LIMIT && textObj.text < TOTAL_INPUT_LIMIT) {
          this.isToggledAddText = false;
          if (textObj.text.length < TEXT_LIMIT) {
            this.addTextToDB(textObj);
          } else {
            this.addTextsToDB(this.splitIntoTexts(textObj));
          }
        }
      },
      addTextToDB(textObj) {
        TextService.addText(textObj).then(() => {
          this.fetchTextTitles();
        }).catch(err => {
          console.error(err);
        });
      },
      addTextsToDB(textObjs) {
        TextService.addTexts(textObjs).then(() => {
          this.fetchTextTitles();
        }).catch(err => {
          console.error(err);
        });
      },
      splitIntoTexts(textObj) {
        const title = textObj.title;
        const bigText = textObj.text;
        const smallerTextObjs = [{title: title + ' (1)', text: ''}]; // min. one text
        const pars = bigText.split('\n'); // split into paragraphs
        for (let i = 0; i < pars.length; i++) {
          const curText = smallerTextObjs[smallerTextObjs.length - 1].text;
          if (curText.length === 0) { // at least one paragraph per text, even if too long
            smallerTextObjs[smallerTextObjs.length - 1].text += pars[i];
            continue;
          }
          if ((curText.length + pars[i].length) < TEXT_LIMIT) {
            smallerTextObjs[smallerTextObjs.length - 1].text += '\n' + pars[i];
          } else {
            smallerTextObjs.push({
              title: title + ` (${smallerTextObjs.length + 1})`,
              text: pars[i]
            });
          }
        }
        return smallerTextObjs;
      },
      updateTextInDB(textObj) {
        // TextService.updateText(textObj).then(() => {
        //   this.fetchTextTitles();
        // }).catch(err => {
        //   console.log(err);
        // });
      },
      removeTextFromDB(textObj) {
        TextService.removeText(textObj).then(() => {
          this.fetchTextTitles();
        }).catch(err => {
          console.log(err);
        })
      },
      toggleAddTextForm() {
        this.isToggledAddText = !this.isToggledAddText;
      }
    }
  }
</script>

<style scoped>
  #library {
    text-align: right;
  }

  #library-toolbar {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
  }

  #add-text-btn {
    font-size: 1.4em;
    background-color: var(--active-el-color);
    color: white;
    padding: 2px 10px;
    margin-left: 10px;
    border: none;
    border-radius: 20%;
    outline: 0;
    cursor: pointer;
  }

  #add-text-btn:hover, #add-text-btn.toggled {
    background-color: var(--active-el-color-dark);
  }

  .text-item-div {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    border: 1px solid #747474;
    border-radius: 10px;
    margin-top: 15px;
    text-align: left;

  }

  .text-item-edit-btn, .text-item-del-btn {
    font-size: 1.1em;
    background-color: white;
    color: var(--input-border-gray-dark);
    padding: 2px 8px;
    border-color: var(--input-border-gray);
    border-width: 0 0 0 1px;
    border-style: solid;
    outline: 0;
    cursor: pointer;
  }

  .text-item-del-btn {
    border-radius: 0 10px 10px 0;
  }

  .text-item-edit-btn:hover {
    background-color: #fffbaa;
    color: #c9b502;
  }

  .text-item-del-btn:hover {
    background-color: #f5d0c9;
    color: #dc0b0b;
  }

  .text-item-link {
    display: block;
    width: 100%;
    padding: 12px;
    font-size: 1.1em;
    font-weight: bold;
    text-align: left;
    text-decoration: none;
    overflow: hidden;
    border-radius: 10px 0 0 10px;
    color: var(--default-text-color);
    outline: 0;
  }

  .text-item-link:hover {
    background-color: var(--input-border-gray-light);
  }
</style>