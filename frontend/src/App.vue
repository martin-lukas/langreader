<template>
  <div id="container">
    <TopNav :selected="selectedArea" @area-selected="changeArea"/>
    <ContentArea :selected="selectedArea" @area-selected="changeArea"/>
  </div>
</template>

<script>
  import TopNav from './components/TopNav';
  import ContentArea from './components/ContentArea';

  export default {
    components: {TopNav, ContentArea},
    data() {
      return {
        selectedArea: 'library'
      }
    },
    methods: {
      changeArea(area) {
        this.selectedArea = area;
      }
    }
  }
</script>

<style>
  html, body, ul, li, a {
    margin: 0;
    padding: 0;
    border: 0;
    font-family: Arial, Helvetica, sans-serif;
  }

  html, body {background-color: #CCC; height: 100vh}

  ul, li {list-style: none}

  #container {
    margin: 0 auto;
    background-color: white;
    max-width: 900px;
    height: 100vh;
  }

  h2 {
    font-size: 2.5em;
    font-weight: normal;
    margin: 0.5em;
    padding: 0;
  }
</style>
