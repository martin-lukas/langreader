<template>
  <div id="text-list">
    <a :text="textObj"
       v-for="textObj in textObjs"
       :key="textObj.id"
       href="#"
       @click.prevent="(e) => {
         $emit('text-selected', textObj.id);
         $emit('area-selected', 'Reading');
       }">
      {{ textObj.title }}
    </a>
  </div>
</template>

<script>
  export default {
    props: {
      textObjs: Array
    }
  }
</script>

<style scoped>
  a {
    display: block;
    border: 1px solid #747474;
    border-radius: 10px;
    margin-top: 15px;
    text-decoration: none;
    padding: 12px;
    overflow: hidden;
    outline: 0;
    font-size: 1.1em;
    font-weight: bold;
    text-align: left;
  }

  a:hover {
    background-color: #f0f0f0;
  }
</style>