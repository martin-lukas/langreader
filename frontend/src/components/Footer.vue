<template>
  <div id="footer">
    &copy; {{ currentYear }} Martin Lukáš
    |
    <router-link to="/contact">Contact Us</router-link>
    |
    <router-link to="/tos">Terms of Service</router-link>
    |
    <router-link to="/privacy">Privacy Policy</router-link>
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
</style>