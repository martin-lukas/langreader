<template>
  <div id="login-view">
    <h3>Login Page</h3>
    <div id="login-form">
      <form name="form" @submit.prevent="handleLogin">
        <div><!-- User field -->
          <label for="username-input">Username</label>
          <input id="username-input"
                 name="username"
                 type="text"
                 v-model="user.username"
                 v-validate="'required'"/>
          <div v-if="isSubmitted && errors.has('username')" role="alert">
            Username is required!
          </div>
        </div>
        <div class="lower-form-group">
          <label for="password-input">Password</label>
          <input id="password-input"
                 name="password"
                 type="password"
                 v-model="user.password"
                 v-validate="'required'"/>
          <div v-if="errors.has('password')" role="alert">
            Password is required!
          </div>
        </div>
        <div id="login-button-div" class="lower-form-group">
          <button :disabled="loading">Login</button>
          <div v-if="message" role="alert">{{message}}</div>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
  import User from '../models/user';

  export default {
    name: 'Login',
    data() {
      return {
        user: new User('', ''),
        loading: false,
        isSubmitted: false,
        message: ''
      };
    },
    computed: {
      loggedIn() {
        return this.$store.state.auth.status.loggedIn;
      },
      isAdmin() {
        return this.$store.getters["auth/isAdmin"];
      }
    },
    created() {
      if (this.loggedIn) {
        this.$router.push('/');
      }
    },
    methods: {
      handleLogin() {
        this.loading = true;
        this.$validator.validateAll().then(isValid => {
          if (!isValid) {
            this.loading = false;
            return;
          }
          if (this.user.username && this.user.password) {
            this.$store.dispatch('auth/login', this.user).then(
              () => {
                if (!this.isAdmin) {
                  this.$store.dispatch('lang/fetchUserLangs');
                  this.$store.dispatch('lang/fetchChosenLang');
                }
                this.$router.push('/');
              },
              (error) => {
                this.loading = false;
                this.message =
                  (error.response && error.response.data) ||
                  error.message ||
                  error.toString();
              }
            );
          }
        });
      }
    }
  };
</script>

<style scoped>
  #login-view {
    text-align: center;
  }

  #login-form {
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

  #login-button-div {
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
