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
    <AddTextForm v-if="isToggledAddText"
                 @add-text="addTextToDB"/>
    <TextList :textObjs="shownTextObjs"/>
  </div>
</template>

<script>
  import api from '../utils/backend-api';
  import AddTextForm from "./subcomponents/AddTextForm";
  import TextList from "./subcomponents/TextList";

  export default {
    name: 'library',
    components: {AddTextForm, TextList},
    data() {
      return {
        searchQuery: '',
        textObjs: [],
        shownTextObjs: [],
        isToggledAddText: false
      }
    },
    methods: {
      fetchTextTitles() {
        api.getTextTitles().then(response => {
          this.textObjs = response.data;
          this.shownTextObjs = this.textObjs;
        }).catch(err => {
          console.error(err);
        });
      },
      filterTexts() {
        this.shownTextObjs = this.textObjs.filter(textObj => {
          return textObj.title.toLowerCase().includes(this.searchQuery.toLowerCase());
        });
      },
      addTextToDB(textObj) {
        this.isToggledAddText = false;
        api.addText(textObj).then(response => {
          this.fetchTextTitles();
        }).catch(err => {
          console.error(err);
        });
      },
      toggleAddTextForm() {
        this.isToggledAddText = !this.isToggledAddText;
      },
      selectText(textId) {
        this.$emit('text-selected', textId);
      },
      changeArea(contentAreaName) {
        this.$emit('area-selected', contentAreaName);
      }
    },
    mounted() {
      this.fetchTextTitles();
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

  input[type=text] {
    font-size: 0.95em;
    display: block;
    box-sizing: border-box;
    width: 100%;
    padding: 8px;
    border: 1px solid #b4b4b4;
    border-radius: 5px;
    /*background-image: url('searchicon.png');*/
    /*background-position: 10px 10px;*/
    /*background-repeat: no-repeat;*/
  }

  input[type=text]:focus {
    border: 2px solid #4994d4;
    margin-bottom: -2px;
  }

  button {
    font-size: 1.7em;
    background-color: #4994d4;
    color: white;
    padding: 2px 10px;
    margin-left: 10px;
    border: none;
    border-radius: 10px;
    outline: 0;
    cursor: pointer;
  }

  button:hover {
    background-color: #327bbf;
  }

  button.toggled {
    background-color: #0a569a;
  }
</style>