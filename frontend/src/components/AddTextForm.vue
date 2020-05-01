<template>
  <div id="add-text-form">
    <div>
      <label for="input-title">Title:</label>
      <input type="text"
             id="input-title"
             v-model="titleInput"/>
    </div>
    <div class="lower-form-group">
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
          this.$emit('add-text', { title: trimmedTitle, text: trimmedText });
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
    border-radius: 5px;
    background-color: #f2f2f2;
    margin-top: 10px;
    padding: 13px 13px;
  }

  .lower-form-group {
    margin-top: 10px;
  }

  textarea {
    height: 80px;
    resize: none;
  }

  #add-button-div {
    text-align: right;
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
</style>