<template>
  <div id="library">
    <div id="library-toolbar">
      <input type="text"
             v-model="searchQuery"
             placeholder="Search.."
             @keyup="filterTexts"/>
      <button @click="toggleAddTextForm"
              :class="(isToggledAddText) ? 'toggled' : ''">
        +
      </button>
    </div>
    <AddTextForm v-if="isToggledAddText" @add-text="addTextToDB"/>
    <router-link v-for="textObj in shownTextObjs" :key="textObj.id" :to="'/reading/' + textObj.id">
      {{ textObj.title }}
    </router-link>
  </div>
</template>

<script>
  import TextService from '../services/text.service';
  import AddTextForm from "../components/AddTextForm";

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
      addTextToDB(textObj) {
        this.isToggledAddText = false;
        TextService.addText(textObj).then(() => {
          this.fetchTextTitles();
        }).catch(err => {
          console.error(err);
        });
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

  button {
    font-size: 1.7em;
    background-color: var(--active-el-color);
    color: white;
    padding: 2px 10px;
    margin-left: 10px;
    border: none;
    border-radius: 10px;
    outline: 0;
    cursor: pointer;
  }

  button:hover, button.toggled {
    background-color: var(--active-el-color-dark);
  }

  a {
    display: block;
    border: 1px solid #747474;
    border-radius: 10px;
    margin-top: 15px;
    text-decoration: none;
    padding: 12px;
    overflow: hidden;
    outline: 0;
    font-size: 1.1em;
    font-weight: bold;
    text-align: left;
  }

  a:hover {
    background-color: #f0f0f0;
  }
</style>