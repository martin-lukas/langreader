<template>
  <div id="footer">
    &copy; {{ currentYear }} Martin Lukáš
    |
    <a href="#">Contact</a>
    |
    <a href="#">Privacy Policy</a>
  </div>
</template>

<script>
  export default {
    computed: {
      currentYear() {
        return new Date().getFullYear();
      }
    }
  }
</script>

<style scoped>
  #footer {
    text-align: center;
    font-size: 1em;
    color: #5a5a5a;
    padding: 20px 0 10px 0;
  }

  a {
    text-decoration: underline;
    color: #5a5a5a;
  }

  a:hover {
    color: var(--default-text-color);
  }

  /*@media screen and (min-width: 500px) {*/
  /*  #header {*/
  /*    font-size: 60px;*/
  /*  }*/
  /*}*/
</style>