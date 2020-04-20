<template>
  <div id="app">
    <b-container fluid="lg" class="text-left">
      <b-navbar toggleable="sm" type="light" variant="light">
        <b-navbar-brand href=".">LangReader</b-navbar-brand>
        <b-navbar-toggle target="nav-collapse"/>
        <b-collapse id="nav-collapse" is-nav>
          <b-navbar-nav class="ml-auto">
            <b-nav-item href="#"
                        :class="(isLibrary) ? 'active' : ''"
                        @click.prevent="showLibrary">
              Library
            </b-nav-item>
            <b-nav-item-dropdown right>
              <template v-slot:button-content>
                Account
              </template>
              <b-dropdown-item href="#" :class="(isManageLangs) ? 'active' : ''">
                Manage Languages
              </b-dropdown-item>
              <b-dropdown-item href="#" :class="(isStatistics) ? 'active' : ''">
                Statistics
              </b-dropdown-item>
              <b-dropdown-item disabled>
                <hr>
              </b-dropdown-item>
              <b-dropdown-item href="#">
                Log Out
              </b-dropdown-item>
            </b-nav-item-dropdown>
          </b-navbar-nav>
        </b-collapse>
      </b-navbar>
      <Library v-if="isLibrary"/>
      <ReadingArea v-if="isReadingArea" :input="input"/>
    </b-container>
  </div>
</template>

<script>
  import Library from "./components/Library";
  import ReadingArea from "./components/ReadingArea";

  export default {
    components: {Library, ReadingArea},
    data() {
      return {
        isLibrary: true,
        isReadingArea: false,
        isManageLangs: false, // to be impl
        isStatistics: false, // to be impl
        input: ''
      }
    },
    methods: {
      showLibrary() {
        this.isLibrary = true;
        this.isReadingArea = false;
      }
    }
  }
</script>

<style>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    font-size: 18px;
    line-height: 1.4;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    background-color: #f8f9fa;
    text-align: center;
    color: #222222;
  }
</style>
