<template>
  <div id="register-view">
    <h3>Register Page</h3>
    <div v-if="message">{{message}}</div>
    <form id="register-form" name="form" @submit.prevent="handleRegister">
      <div v-if="!isSuccessful">
        <div><!-- User field -->
          <label for="username-input">Username</label>
          <input id="username-input"
                 name="username"
                 type="text"
                 v-model="user.username"
                 v-validate="'required'"/>
          <div v-if="isSubmitted && errors.has('username')">
            {{errors.first('username')}}
          </div>
        </div><!-- Password field -->
        <div class="lower-form-group">
          <label for="password-input">Password</label>
          <input id="password-input"
                 type="password"
                 name="password"
                 v-model="user.password"
                 v-validate="'required'"/>
          <div v-if="isSubmitted && errors.has('password')">
            {{errors.first('password')}}
          </div>
        </div><!-- Confirm field -->
        <div class="lower-form-group">
          <label for="password-confirm-input">Confirm Password</label>
          <input id="password-confirm-input"
                 name="password-confirm"
                 type="password"
                 v-model="confirmPassword"
                 v-validate="'required|confirmed:password'"
                 ref="password"/>
          <div v-if="isSubmitted && errors.has('password-confirm')">
            {{ errors.first('password-confirm') }}
          </div>
        </div>
        <div id="register-button-div">
          <button>Sign Up</button>
        </div>
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
        user: new User('', ''),
        confirmPassword: '',
        isSubmitted: false,
        isSuccessful: false,
        message: ''
      };
    },
    computed: {
      loggedIn() {
        return this.$store.state.auth.status.loggedIn;
      }
    },
    mounted() {
      if (this.loggedIn) {
        this.$router.push('/library');
      }
    },
    methods: {
      handleRegister() {
        this.message = '';
        this.isSubmitted = true;
        this.$validator.validate().then(isValid => {
          if (isValid) {
            this.$store.dispatch('auth/register', this.user).then(
              data => {
                this.message = data.message;
                this.isSuccessful = true;
              },
              error => {
                this.message = error.response.data.message;
                this.isSuccessful = false;
              }
            );
          }
        });
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

  #register-button-div {
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

  button:hover {
    background-color: var(--active-el-color-dark);
  }
</style>
