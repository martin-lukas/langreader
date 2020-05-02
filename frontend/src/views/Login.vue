<template>
  <div id="login-view">
    <h3>Login Page</h3>
    <div class="error-div" v-if="errMessage">{{errMessage}}</div>
    <div id="login-form">
      <form name="form" @submit.prevent="handleLogin">
        <div><!-- User field -->
          <label for="username-input">Username</label>
          <input id="username-input"
                 name="username"
                 type="text"
                 v-model="user.username"/>
        </div>
        <div class="lower-form-group">
          <label for="password-input">Password</label>
          <input id="password-input"
                 name="password"
                 type="password"
                 v-model="user.password"/>
        </div>
        <div id="login-button-div" class="lower-form-group">
          <button :disabled="loading">Login</button>
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
        errMessage: ''
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
        if (this.user.username && this.user.password) {
          this.$store.dispatch('auth/login', this.user).then(() => {
            if (!this.isAdmin) {
              this.$store.dispatch('lang/fetchUserLangs');
              this.$store.dispatch('lang/fetchChosenLang');
            }
            this.$router.push('/');
          }).catch(() => {
              this.loading = false;
              this.errMessage = "Incorrect username or password. Please try again.";
            }
          );
        } else {
          this.loading = false;
        }
      }
    }
  };
</script>

<style scoped>
  #login-view {
    text-align: center;
  }

  h3 {
    margin: 0 0 20px 0;
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

  .error-div {
    display: inline-block;
    padding: 12px;
    vertical-align: middle;
    color: #990008;
    background-color: #fde6e6;
    font-size: 1em;
    margin-bottom: 15px;
    border: 1px solid #a80303;
    border-radius: 3px;
  }
</style>
