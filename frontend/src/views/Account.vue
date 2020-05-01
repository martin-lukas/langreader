<template>
  <div id="account-page">
    <h2>Hi, {{ currentUser.username }}!</h2>
  </div>
</template>

<script>
  export default {
    name: 'account',
    computed: {
      currentUser() {
        return this.$store.state.auth.user;
      }
    },
    mounted() {
      if (!this.currentUser) {
        this.$router.push('/login');
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