<template>
  <div id="register-view">
    <h3>Register Page</h3>
    <div class="info-div" v-if="message">{{message}}</div>
    <div class="error-div" v-if="errMessage">{{errMessage}}</div>
    <form id="register-form" v-if="!isSuccessful" name="form" @submit.prevent="handleRegister">
      <div><!-- User field -->
        <label for="username-input">Username</label>
        <input id="username-input"
               name="username"
               type="text"
               v-model="user.username"/>
      </div><!-- Password field -->
      <div class="lower-form-group">
        <label for="password-input">Password</label>
        <input id="password-input"
               type="password"
               name="password"
               v-model="user.password"/>
      </div><!-- Confirm field -->
      <div class="lower-form-group">
        <label for="password-confirm-input">Confirm Password</label>
        <input id="password-confirm-input"
               name="password-confirm"
               type="password"
               v-model="confirmPassword"/>
      </div>
      <div class="lower-form-group">
        <label id="lang-label" for="native-lang-select">Select Your Native Language:</label>
        <select v-model="user.nativeLang" id="native-lang-select">
          <option :value="null" selected>Choose Language</option>
          <option v-for="language in allLangs"
                  :key="language.id"
                  :value="language">
            {{ language.fullName }}
          </option>
        </select>
      </div>
      <div class="lower-form-group" id="consent-div">
        <input type="checkbox" id="consent-checkbox" v-model="isConsentChecked">
        <label id="consent-label" for="consent-checkbox">
          I give consent to storing my user credentials according to the
          <router-link to="/privacy">Privacy Policy</router-link>. I've also read and agree to the
          <router-link to="/tos">Terms of Service</router-link>.
        </label>
      </div>
      <div id="register-button-div">
        <button>Sign Up</button>
      </div>
    </form>
  </div>
</template>

<script>
  import User from '../models/user';

  export default {
    name: 'Register',
    data() {
      return {
        user: new User('', '', null),
        confirmPassword: '',
        isConsentChecked: false,
        isSubmitted: false,
        isSuccessful: false,
        message: '',
        errMessage: ''
      };
    },
    computed: {
      loggedIn() {
        return this.$store.getters["auth/isLoggedIn"];
      },
      allLangs() {
        return this.$store.getters["lang/allLangs"];
      }
    },
    mounted() {
      if (this.loggedIn) {
        this.$router.push('/library');
      } else {
        if (this.allLangs.length === 0) {
          console.log("fetching");
          this.$store.dispatch('lang/fetchAllLangs');
        }
      }
    },
    methods: {
      handleRegister() {
        this.message = '';
        this.isSubmitted = true;
        if (this.isUsernameValid(this.user.username)) {
          if (this.isPasswordValid(this.user.password)) {
            if (this.arePasswordsEqual(this.user.password, this.confirmPassword)) {
              if (this.user.nativeLang !== null) {
                if (this.isConsentChecked) {
                  this.$store.dispatch('auth/register', this.user).then(() => {
                    this.errMessage = '';
                    this.message = 'Registration successful. You can log in now.';
                    this.isSuccessful = true;
                  }).catch(() => {
                    this.errMessage = 'Registration unsuccessful. This username is already taken.';
                    this.isSuccessful = false;
                  });
                } else {
                  this.errMessage = 'You have to consent to storing your user credentials to continue.';
                  this.isSuccessful = false;
                }
              } else {
                this.errMessage = 'Please choose your native language (it will be used for ' +
                  'translation in English texts).';
                this.isSuccessful = false;
              }
            } else {
              this.errMessage = 'The passwords are different. ' +
                'Make sure you typed in your passwords correctly.';
              this.isSuccessful = false;
            }
          } else {
            this.errMessage = 'The password is not valid. ' +
              'Passwords must be at least 8 characters long.';
            this.isSuccessful = false;
          }
        } else {
          this.errMessage = 'The username is not valid. ' +
            'Usernames have to be at least 5 characters long.';
          this.isSuccessful = false;
        }
      },
      isUsernameValid(username) {
        return (username.length >= 5 && username.length <= 50);
      },
      isPasswordValid(password) {
        return (password.length >= 8 && password.length <= 100);
      },
      arePasswordsEqual(password, confirmPassword) {
        return password === confirmPassword;
      }
    }
  };
</script>

<style scoped>
  #register-view {
    text-align: center;
  }

  #register-form {
    text-align: left;
    border-radius: 5px;
    background-color: #f2f2f2;
    margin: 0 auto;
    padding: 13px 13px;
    max-width: 300px;
  }

  .lower-form-group {
    margin-top: 10px;
  }

  #lang-label {
    display: block;
    margin-bottom: 5px;
  }

  #consent-checkbox {
    width: 20px;
  }

  #consent-label {
    font-size: 0.9em;
  }

  #register-button-div {
    text-align: right;
  }

  h3 {
    margin: 0 0 20px 0;
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

  button:hover {
    background-color: var(--active-el-color-dark);
  }

  .info-div, .error-div {
    display: inline-block;
    padding: 12px;
    vertical-align: middle;
    font-size: 1em;
    margin-bottom: 15px;
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
