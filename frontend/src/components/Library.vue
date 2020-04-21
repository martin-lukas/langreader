<template>
  <div id="library">
    <LibraryItem :textObjs="textObjs"
                     @text-selected="selectText"
                     @area-selected="changeArea"/>
  </div>
</template>

<script>
  import api from '../utils/backend-api';
  import LibraryItem from "./LibraryItem";

  export default {
    components: {LibraryItem},
    data() {
      return {
        textObjs: [],
      }
    },
    methods: {
      fetchTexts() {
        api.getTextTitles().then(response => {
          this.textObjs = response.data;
        }).catch(err => {
            console.log(err);
          });
      },
      selectText(textObj) {
        this.$emit('text-selected', textObj);
      },
      changeArea(contentArea) {
        this.$emit('area-selected', contentArea);
      }
    },
    mounted() {
      this.fetchTexts();
    }
  }
</script>

<style>
</style>