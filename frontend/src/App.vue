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
        selectedArea: 'reading'
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
  #container {
    display: flex;
    flex-direction: column;
    justify-content: center;
    font-family: serif;
    margin: 0 auto;
    max-width: 900px;
  }
</style>
