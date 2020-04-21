<template>
  <ul id="text-list">
    <li :text="textObj"
        v-for="textObj in textObjs"
        :key="textObj.id">
      <a href="#"
         @click.prevent="(e) => {
             $emit('text-selected', textObj.id);
             $emit('area-selected', 'reading');
           }">
        <p>{{ textObj.title }}</p>
      </a>
    </li>
  </ul>
</template>

<script>
  export default {
    props: {
      textObjs: Array
    }
  }
</script>
