<template>
  <div id="container">
    <NavBar :selected="selectedArea" @area-selected="changeArea"/>
    <ContentArea :selected="selectedArea" @area-selected="changeArea"/>
  </div>
</template>

<script>
  import NavBar from './components/Navbar';
  import ContentArea from './components/ContentArea';

  export default {
    components: {NavBar, ContentArea},
    data() {
      return {
        selectedArea: 'home'
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
  }

  ul, li {list-style: none}

  #container {
    display: flex;
    flex-direction: column;
    justify-content: center;
    font-family: serif;
    margin: 0 auto;
    max-width: 900px;
  }

  h2 {
    font-size: 2.5em;
    font-weight: normal;
    margin: 0.5em;
    padding: 0;
  }
</style>
