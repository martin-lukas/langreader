<template>
  <div id="library">
    <TextList :textObjs="textObjs"
              @text-selected="selectText"
              @area-selected="changeArea"/>
  </div>
</template>

<script>
  import api from '../utils/backend-api';
  import TextList from "./TextList";

  export default {
    components: {TextList},
    data() {
      return {
        textObjs: [],
      }
    },
    methods: {
      fetchTextTitles() {
        api.getTextTitles().then(response => {
          this.textObjs = response.data;
        }).catch(err => {
          console.error(err.response);
        });
      },
      selectText(textId) {
        this.$emit('text-selected', textId);
      },
      changeArea(contentAreaName) {
        this.$emit('area-selected', contentAreaName);
      }
    },
    mounted() {
      this.fetchTextTitles();
    }
  }
</script>

<style>
</style>