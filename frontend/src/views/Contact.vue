<template>
  <div>
    <h2>Contact Us</h2>

    <div id="message-div">
      <div class="info-div" v-if="infoMessage">{{ infoMessage }}</div>
      <div class="error-div" v-if="errMessage">{{ errMessage }}</div>
    </div>

    <form id="contact-form" v-if="!isSuccessful" name="form" @submit.prevent="handleSendMail">
      <div><!-- Email field -->
        <label for="email-input">*Email</label>
        <input id="email-input"
               type="text"
               v-model="message.email"/>
      </div><!-- Subject field -->
      <div class="lower-form-group">
        <label for="subject-input">Subject</label>
        <input id="subject-input"
               type="text"
               v-model="message.subject"/>
      </div><!-- Text textarea -->
      <div class="lower-form-group">
        <label for="input-text">Text</label>
        <textarea type="text"
                  id="input-text"
                  v-model="message.text"/>
      </div>
      <div id="send-message-btn-div">
        <button>Submit</button>
      </div>
      <p>* Your e-mail address will only be used for replying to your message.</p>
    </form>
  </div>
</template>

<script>
  import Message from "../models/message";
  import ContactService from '../services/contact.service';

  export default {
    data() {
      return {
        isSubmitted: false,
        isSuccessful: false,
        message: new Message('', '', ''),
        infoMessage: '',
        errMessage: ''
      }
    },
    methods: {
      handleSendMail() {
        this.infoMessage = '';
        this.isSubmitted = true;
        this.trimMessageObj();
        if (this.message.email) {
          if (this.message.subject) {
            if (this.message.text) {
              ContactService.sendMail(this.message).then(() => {
                this.errMessage = '';
                this.infoMessage = 'Message sent. We\'ll get back to you.';
                this.isSuccessful = true;
              }).catch(() => {
                this.errMessage = 'We encountered a problem while sending your message. ' +
                  'Please try again later.';
                this.isSuccessful = false;
              });
            } else {
              this.errMessage = 'The text of the message can\'t be empty.';
              this.isSuccessful = false;
            }
          } else {
            this.errMessage = 'The subject is empty. Please describe the purpose of your message.';
            this.isSuccessful = false;
          }
        } else {
          this.errMessage = 'The email is empty. Providing email is necessary for replying to your message.';
          this.isSuccessful = false;
        }
      },
      trimMessageObj() {
        this.message.email = this.message.email.trim();
        this.message.subject = this.message.subject.trim();
        this.message.text = this.message.text.trim();
      }
    }
  }
</script>

<style scoped>
  #contact-form {
    text-align: left;
    border-radius: 5px;
    background-color: #f2f2f2;
    margin-top: 10px;
    padding: 13px 13px;
  }

  #message-div {
    text-align: center;
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

  p {
    margin: 10px 0 0 0;
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

  .info-div, .error-div {
    display: inline-block;
    padding: 12px;
    vertical-align: middle;
    font-size: 1em;
    margin-bottom: 10px;
    border: 1px solid;
    border-radius: 3px;
  }

  .info-div {
    color: rgba(0, 97, 10, 0.91);
    background-color: #e6fdec;
    border-color: #019925;
  }

  .error-div {
    color: #990008;
    background-color: #fde6e6;
    border-color: #a80303;
  }
</style>