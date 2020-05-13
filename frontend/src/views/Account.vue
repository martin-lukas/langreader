<template>
  <div id="account-page">
    <h1>Hi, {{ currentUser.username }}!</h1>
    <hr>
    <h4>Native Language: {{ nativeLang.fullName }}</h4>
    <h2>Language Statistics</h2>
    <table>
      <thead>
      <tr>
        <th>Language</th>
        <th># of known</th>
        <th># of studied</th>
        <th># of ignored</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(langStatObj, index) in langStatObjs" :key="index">
        <td>{{ langStatObj.language.fullName }}</td>
        <td>{{ langStatObj.knownCount }}</td>
        <td>{{ langStatObj.studiedCount }}</td>
        <td>{{ langStatObj.ignoredCount }}</td>
      </tr>
      </tbody>
    </table>
    <div id="delete-div">
      <a href="#" @click.prevent="toggleDelete">Click here to proceed to account removal.</a>
      <div v-if="isDeleteToggled" id="del-consent">
        <input type="checkbox" id="delete-account-box" v-model="isDeleteConsent">
        <label for="delete-account-box">I request a complete removal of this account, including
          all the personal data and uploaded content associated with it.</label>
      </div>
      <button v-if="isDeleteToggled && isDeleteConsent" @click="deleteUser">Delete Account</button>
    </div>
  </div>
</template>

<script>
  import StatService from '../services/stat.service';
  import UserService from "../services/user.service";

  export default {
    name: 'account',
    data() {
      return {
        langStatObjs: [],
        isDeleteToggled: false,
        isDeleteConsent: false
      }
    },
    computed: {
      currentUser() {
        return this.$store.state.auth.user;
      },
      nativeLang() {
        return this.$store.getters["lang/nativeLang"];
      }
    },
    created() {
      if (!this.currentUser) {
        this.$router.push('/login');
      } else {
        StatService.getLangStats().then((response) => {
          this.langStatObjs = response.data;
        }).catch(err => {
          console.log(err);
        })
      }
    },
    methods: {
      toggleDelete() {
        this.isDeleteToggled = !this.isDeleteToggled;
      },
      deleteUser() {
        UserService.removeUserSelf().then(() => {
          this.$store.dispatch('auth/logout');
          this.$router.push('/login');
        }).catch(error => {
          console.log(error);
        });
      }
    }
  }
</script>

<style scoped>
  #account-page {
  }

  h4 {
    font-size: 1.3em;
  }

  p {
    padding: 0.8em 0;
  }

  a {
    display: block;
    text-decoration: underline;
    color: #5a5a5a;
  }

  a:hover {
    color: var(--default-text-color);
  }

  #delete-div {
    margin-top: 50px;
    text-align: center;
  }

  #del-consent {
    margin-top: 10px;
  }

  #delete-account-box {
    width: 20px;
    margin-right: 10px;
  }

  button {
    font-size: 1em;
    font-weight: bold;
    background-color: #c10808;
    color: white;
    margin-top: 10px;
    padding: 8px 15px;
    border: none;
    border-radius: 5px;
    outline: 0;
    cursor: pointer;
  }

  button:hover, button:focus {
    background-color: #890000;
  }
</style>