<template>
  <div id="library" class="rounded-lg">
    <div>
      <b-spinner label="Loading..." v-if="isLoading"></b-spinner>
    </div>
    <ul class="list-group">
      <li class="list-group-item">
        <LibraryItem v-for="text in texts" :key="text.id" :text="text"/>
      </li>
    </ul>
  </div>
</template>

<script>
  import LibraryItem from "./LibraryItem";
  import axios from "axios";

  const SERVER_URL = 'http://localhost';
  const UPDATE_TEXT_API = '/api/texts';

  export default {
    components: {LibraryItem},
    data() {
      return {
        textAreaInput: '',
        texts: [],
        isLoading: false,
      }
    },
    methods: {
      fetchTexts() {
        axios.get(`${SERVER_URL}${UPDATE_TEXT_API}`)
          .then(response => {
            this.isLoading = false;
            this.texts = response.data;
          })
          .catch(err => {
            console.log(err);
          });
      }
    },
    created() {
      this.isLoading = true;
    },
    mounted() {
      this.fetchTexts();
    }
  }
</script>

<style>
</style>