<template>
  <div>
    <select v-model="selectedLanguage">
      <option :value="null" selected>Choose Language</option>
      <option v-for="language in allLangs"
              :key="language.id"
              :value="language">
        {{ language.fullName }}
      </option>
    </select>
    <button @click="addNewLanguage">Add</button>

    <div class="lang-item-div"
         :class="isChosenLang(lang) ? 'chosen-lang-div' : ''"
         v-for="lang in userLangs"
         :key="lang.id">
      <a href="#"
         class="lang-item-link"
         :class="isChosenLang(lang) ? 'chosen-lang-item-link' : ''"
         @click.prevent="setCurrentLang(lang)">
        {{ lang.fullName }}
      </a>
      <button v-if="!isChosenLang(lang)"
              class="lang-item-del-btn"
              @click="removeUserLang(lang)">
        <i class="fas fa-ban"></i>
      </button>
    </div>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        selectedLanguage: null
      }
    },
    computed: {
      userLangs() {
        return this.$store.state.lang.userLangs;
      },
      chosenLang() {
        return this.$store.state.lang.chosenLang;
      },
      nativeLang() {
        return this.$store.state.lang.nativeLang;
      },
      allLangs() {
        return this.$store.state.lang.allLangs;
      }
    },
    mounted() {
      if (this.allLangs.length === 0) {
        this.$store.dispatch('lang/fetchAllLangs');
      }
    },
    methods: {
      setCurrentLang(lang) {
        this.$store.dispatch('lang/changeLang', lang);
      },
      removeUserLang(lang) {
        this.$store.dispatch('lang/removeUserLang', lang);
      },
      addNewLanguage() {
        if (this.selectedLanguage !== null
          && !this.containsLang(this.userLangs, this.selectedLanguage)) {
          this.$store.dispatch('lang/addUserLang', this.selectedLanguage);
        }
      },
      containsLang(array, lang) {
        for (let i = 0; i < array.length; i++) {
          if (lang.id === array[i].id) {
            return true
          }
        }
        return false;
      },
      isChosenLang(lang) {
        return lang.id === this.chosenLang.id;
      }
    }
  }
</script>

<style scoped>
  button {
    font-size: 1em;
    font-weight: bold;
    background-color: var(--active-el-color);
    color: white;
    padding: 6px 15px;
    margin-left: 10px;
    border: none;
    border-radius: 5px;
    outline: 0;
    cursor: pointer;
  }

  button:hover, button:focus {
    background-color: var(--active-el-color-dark);
  }

  .lang-item-div {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    border: 1px solid #747474;
    border-radius: 10px;
    margin-top: 15px;
    text-align: left;

  }

  .lang-item-del-btn {
    margin: 0;
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

  .lang-item-del-btn {
    border-radius: 0 10px 10px 0;
  }

  .lang-item-edit-btn:hover {
    background-color: #fffbaa;
    color: #c9b502;
  }

  .lang-item-del-btn:hover {
    background-color: #f5d0c9;
    color: #dc0b0b;
  }

  .lang-item-link {
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

  .lang-item-link:hover {
    background-color: var(--input-border-gray-light);
  }

  .chosen-lang-div {
    border-color: var(--active-el-color);
    border-width: 2px;
  }

  .chosen-lang-item-link {
    cursor: default;
    color: var(--active-el-color-dark);
    border-radius: 10px;
  }

  .chosen-lang-item-link:hover {
    background-color: white;
  }
</style>