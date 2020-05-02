<template>
  <div id="account-page">
    <h1>Hi, {{ currentUser.username }}!</h1>
    <hr>
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
  </div>
</template>

<script>
  import StatService from '../services/stat.service';

  export default {
    name: 'account',
    data() {
      return {
        langStatObjs: []
      }
    },
    computed: {
      currentUser() {
        return this.$store.state.auth.user;
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
    }
  }
</script>

<style scoped>
  #account-page {
  }

  p {
    padding: 0.8em 0;
  }
</style>