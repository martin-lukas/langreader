<template>
  <div id="add-text-area">
    <div id="method-btns-area">
      <label>Choose method:</label>
      <button class="method-btn" @click="() => { this.isTextChosen = true }">Text</button>
      <button class="method-btn" @click="() => { this.isTextChosen = false }">from URL</button>
    </div>
    <div>
      <label for="input-title">Title:</label>
      <input type="text"
             id="input-title"
             v-model="titleInput"/>
    </div>
    <div class="lower-form-group">
      <label for="input-text">
        <template v-if="isTextChosen">Text:</template>
        <template v-else>URL:</template>
      </label>
      <template v-if="isTextChosen">
        <textarea type="text"
                  id="input-text"
                  v-model="textInput"/>
      </template>
      <template v-else>
        <input type="text" id="input-url" v-model="urlInput"/>
      </template>
    </div>
    <div id="add-button-div">
      <button @click="sendAddText">Add</button>
      <div class="error-div" v-if="errMessage">{{errMessage}}</div>
    </div>
  </div>
</template>

<script>
  import ExtService from '../services/ext.service';

  export default {
    data() {
      return {
        titleInput: '',
        textInput: '',
        urlInput: '',
        isTextChosen: true,
        errMessage: ''
      }
    },
    methods: {
      sendAddText() {
        const trimmedTitle = this.titleInput.trim();
        if (trimmedTitle) {
          if (this.isTextChosen) {
            const trimmedText = this.textInput.trim();
            if (trimmedText) {
              this.$emit('add-text', {title: trimmedTitle, text: trimmedText});
              this.errMessage = '';
            }
          } else {
            const trimmedUrl = this.urlInput.trim();
            if (trimmedUrl) {
              ExtService.getExtResponse(trimmedUrl).then(response => {
                this.$emit('add-text', {title: trimmedTitle, text: response.data});
                this.errMessage = '';
              }).catch(() => {
                this.errMessage = 'Couldn\'t parse the provided URL.';
              });
            }
          }
        }
      },
      mounted() {
        document.getElementById('input-title').focus();
      }
    }
  }
</script>

<style scoped>
  #add-text-area {
    text-align: left;
    border-radius: 5px;
    background-color: #f2f2f2;
    margin-top: 10px;
    padding: 13px 13px;
  }

  #method-btns-area {
    padding: 0 0 10px 0;
  }

  .method-btn {
    margin: 0 0 0 10px;
    font-size: 1em;
    font-weight: normal;
    background-color: white;
    color: var(--default-text-color);
    padding: 3px 8px;
    border-width: 1px;
    border-style: solid;
    border-color: var(--input-border-gray-dark);
    outline: 0;
    cursor: pointer;
  }

  .method-btn:hover {
    background-color: var(--input-border-gray);
  }

  .method-btn:focus {
    background-color: var(--input-border-gray-dark);
  }

  .lower-form-group {
    margin-top: 10px;
  }

  textarea {
    height: 150px;
    resize: none;
  }

  #add-button-div {
    display: flex;
    flex-direction: row-reverse;
    justify-content: space-between;
    align-items: baseline;
  }

  button {
    font-size: 1em;
    font-weight: bold;
    background-color: var(--active-el-color);
    color: white;
    margin-top: 10px;
    padding: 8px 15px;
    border: none;
    border-radius: 5px;
    outline: 0;
    cursor: pointer;
  }

  button:hover, button:focus {
    background-color: var(--active-el-color-dark);
  }

  .error-div {
    display: inline-block;
    margin: 8px 10px;
    color: #990008;
    font-size: 1em;
  }
</style>