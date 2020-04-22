<template>
  <div id="add-text-form">
    <div id="title-fields">
      <label for="input-title">Title:</label>
      <input type="text"
             id="input-title"
             v-model="titleInput"/>
    </div>
    <div id="text-fields">
      <label for="input-text">Text:</label>
      <textarea type="text"
                id="input-text"
                v-model="textInput"/>
    </div>
    <div id="add-button-div">
      <button @click="sendAddText">Add</button>
    </div>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        titleInput: '',
        textInput: ''
      }
    },
    methods: {
      sendAddText() {
        const trimmedTitle = this.titleInput.trim();
        const trimmedText = this.textInput.trim();
        if (trimmedTitle && trimmedText) {
          this.$emit('add-text', {title: trimmedTitle, text: trimmedText});
        }
      }
    },
    mounted() {
      document.getElementById('input-title').focus();
    }
  }
</script>

<style scoped>
  #add-text-form {
    text-align: left;
    box-sizing: border-box;
    border-radius: 5px;
    background-color: #f2f2f2;
    margin-top: 10px;
    padding: 13px 13px;
  }

  #title-fields {
    box-sizing: border-box;
  }

  #text-fields {
    margin-top: 10px;
    box-sizing: border-box;
  }

  input[type=text] {
    font-size: 0.95em;
    display: block;
    box-sizing: border-box;
    width: 100%;
    padding: 8px;
    border: 1px solid #b4b4b4;
    border-radius: 5px;
  }

  input[type=text]:focus {
    border: 2px solid #4994d4;
    margin-bottom: -2px;
  }

  textarea {
    font-size: 0.95em;
    width: 100%;
    height: 80px;
    padding: 8px;
    box-sizing: border-box;
    border: 1px solid #b4b4b4;
    border-radius: 5px;
    resize: none;
  }

  textarea:focus {
    border: 2px solid #4994d4;
  }

  #add-button-div {
    text-align: right;
  }

  button {
    font-size: 1em;
    font-weight: bold;
    background-color: #4994d4;
    color: white;
    margin-top: 10px;
    padding: 8px 15px;
    border: none;
    border-radius: 5px;
    outline: 0;
    cursor: pointer;
  }

  button:hover, button:focus {
    background-color: #327bbf;
  }
</style>