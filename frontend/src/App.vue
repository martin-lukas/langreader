<template>
  <div id="container">
    <TopNav/>
    <ContentArea/>
  </div>
</template>

<script>
  import TopNav from './components/TopNav';
  import ContentArea from './components/ContentArea';

  export default {
    name: 'app',
    components: {TopNav, ContentArea}
  }
</script>

<style>
  * {
    box-sizing: border-box;
  }

  *:focus, *:visited, *:active, *:hover  { outline:0 !important;}
  *::-moz-focus-inner {border:0;}

  html, body, ul, li, a, button, textarea, input {
    margin: 0;
    padding: 0;
    border: 0;
    font-family: Arial, Helvetica, sans-serif;
  }

  a, p, h1, h2, h3, h4 {
    color: #383838;
  }

  html, body {background-color: #f0f0f0;}

  ul, li {list-style: none}

  #container {
    margin: 0 auto;
    background-color: white;
    border-radius: 0 0 10px 10px;
    max-width: 900px;
  }

  h2 {
    font-size: 2.5em;
    font-weight: normal;
    margin: 0.5em;
    padding: 0;
  }
</style>
