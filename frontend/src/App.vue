<template>
  <div id="container">
    <Header/>
    <TopNav/>
    <div id="content-area">
      <router-view/>
    </div>
  </div>
</template>

<script>
  import Header from './components/Header';
  import TopNav from './components/TopNav';

  export default {
    name: 'app',
    components: {Header, TopNav},
    methods: {
      logOut() {
        this.$store.dispatch('auth/logout');
        this.$router.push('/login');
      }
    }
  }
</script>

<style>
  :root {
    --active-el-color: #4994d4;
    --active-el-color-light: #67b6f6;
    --active-el-color-dark: #2175bf;
    --default-text-color: #333;
    --input-border-gray: #e5e5e5;
    --input-border-gray-dark: #bbbbbb;
  }

  * {
    box-sizing: border-box;
  }

  *:focus, *:visited, *:active, *:hover {
    outline: 0 !important;
  }

  *::-moz-focus-inner {
    border: 0;
  }

  html, body, ul, li, a, button, form, textarea, input {
    margin: 0;
    padding: 0;
    border: 0;
    font-family: Arial, Helvetica, sans-serif;
  }

  body {
    color: var(--default-text-color);
  }

  html, body {
    background-color: #f0f0f0;
  }

  ul, li {
    list-style: none
  }

  h2 {
    font-size: 2.5em;
    font-weight: normal;
    margin: 0.5em;
    padding: 0;
  }

  input, textarea {
    font-size: 0.95em;
    width: 100%;
    padding: 8px;
    border: 2px groove var(--input-border-gray);
    border-radius: 5px;
  }

  input:focus, textarea:focus {
    border: 2px groove var(--active-el-color-light);
  }

  table {
    border-collapse: collapse;
    width: 100%;
  }

  th, td {
    padding: 8px;
    text-align: left;
    border-bottom: 1px solid #ddd;
  }

  #container {
    margin: 0 auto;
    background-color: white;
    border-radius: 0 0 10px 10px;
    max-width: 900px;
    min-width: 210px;
  }

  #content-area {
    padding: 15px;
  }
</style>
