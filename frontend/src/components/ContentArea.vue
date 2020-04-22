<template>
  <div id="content-area">
    <Library v-if="selected === 'Library'"
             @text-selected="selectText"
             @area-selected="changeArea"/>
    <Account v-else-if="selected === 'Account'"/>
    <ReadingArea v-else-if="selected === 'Reading' && selectedTextId >= 0"
                 :textId="selectedTextId"
                 @area-selected="changeArea"/>
  </div>
</template>

<script>
  import Library from './Library';
  import Account from './Account';
  import ReadingArea from './ReadingArea';

  export default {
    components: {Library, Account, ReadingArea},
    props: {
      selected: String
    },
    data() {
      return {
        selectedTextId: -1
      }
    },
    methods: {
      selectText(textId) {
        this.selectedTextId = textId;
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