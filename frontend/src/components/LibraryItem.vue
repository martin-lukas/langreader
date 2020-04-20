<template>
  <div class="library-item">
    <p>{{ text.text }}</p>
    <span class="badge badge-primary badge-pill">14</span>
  </div>
</template>

<script>
  export default {
    props: {
      text: Object
    },
    data() {
      return {

      }
    }
  }
</script>

<style>

</style>