<template>
  <div id="content-area">
    <Home v-if="selected === 'home'"/>
    <Library v-else-if="selected === 'library'"
             @text-selected="selectText"
             @area-selected="changeArea"/>
    <Account v-else-if="selected === 'account'"/>
    <ReadingArea v-else-if="selected === 'reading'" :textObj="selectedTextObj"/>
  </div>
</template>

<script>
  import Home from './Home';
  import Library from './Library';
  import Account from './Account';
  import ReadingArea from './ReadingArea';

  export default {
    components: {Home, Library, Account, ReadingArea},
    props: {
      selected: String
    },
    data() {
      return {
        selectedTextObj: null
      }
    },
    methods: {
      selectText(textObj) {
        this.selectedTextObj = textObj;
      },
      changeArea(contentArea) {
        this.$emit('area-selected', contentArea);
      }
    }
  }
</script>

<style>
  #content-area {
    padding: 15px;
  }
</style>