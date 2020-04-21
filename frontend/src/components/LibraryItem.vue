<template>
  <div id="library-item">
    <ul>
      <li :text="textObj"
          v-for="textObj in textObjs"
          :key="textObj.id">
        <a href="#" @click.prevent="fetchText(textObj.id)">
          <p>{{ textObj.title }}</p>
        </a>
      </li>
    </ul>
  </div>
</template>

<script>
  import api from '../utils/backend-api';

  export default {
    props: {
      textObjs: Array
    },
    methods: {
      fetchText(textId) {
        api.getTextById(textId).then(response => {
          this.$emit('text-selected', response.data);
          this.$emit('area-selected', 'reading');
        })
          .catch(err => {
            console.log(err);
          });
      }
    }
  }
</script>