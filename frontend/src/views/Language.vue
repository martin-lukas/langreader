<template>
  <div>
    <select v-model="selectedLanguage">
      <option :value="null" selected>Choose Language</option>
      <option v-for="language in allLangs"
              :key="language.id"
              :value="language">
        {{ language.fullName }}
      </option>
    </select>
    <button @click="addNewLanguage">Add</button>
    <table>
      <thead>
      <tr>
        <th>Code</th>
        <th>Languge</th>
        <th>Action</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="lang in userLangs" :key="lang.id">
        <td>
          {{ lang.code }}
        </td>
        <td>
          <span v-if="lang.code !== chosenLang.code" class="lang-span"
                @click="setCurrentLang(lang)">
            {{ lang.fullName }}
          </span>
          <span v-else class="current-lang-span">
            {{ lang.fullName }}
          </span>
        </td>
        <td>
          <a href="#"
             v-if="lang.code !== chosenLang.code"
             @click.prevent="removeUserLang(lang)">
            X
          </a>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        selectedLanguage: null
      }
    },
    computed: {
      userLangs() {
        return this.$store.state.lang.userLangs;
      },
      chosenLang() {
        return this.$store.state.lang.chosenLang;
      },
      allLangs() {
        return this.$store.state.lang.allLangs;
      }
    },
    mounted() {
      this.$store.dispatch('lang/fetchAllLangs');
    },
    methods: {
      setCurrentLang(lang) {
        this.$store.dispatch('lang/changeLang', lang);
      },
      removeUserLang(lang) {
        this.$store.dispatch('lang/removeUserLang', lang);
      },
      addNewLanguage() {
        if (this.selectedLanguage !== null
          && !this.containsLang(this.userLangs, this.selectedLanguage)) {
          this.$store.dispatch('lang/addUserLang', this.selectedLanguage);
        }
      },
      containsLang(array, lang) {
        for (let i = 0; i < array.length; i++) {
          if (lang.id === array[i].id) {
            return true
          }
        }
        return false;
      }
    }
  }
</script>

<style scoped>
  button {
    font-size: 1em;
    font-weight: bold;
    background-color: var(--active-el-color);
    color: white;
    padding: 6px 15px;
    margin-left: 10px;
    border: none;
    border-radius: 5px;
    outline: 0;
    cursor: pointer;
  }

  button:hover, button:focus {
    background-color: var(--active-el-color-dark);
  }

  .lang-span {
    color: var(--active-el-color-dark);
    cursor: pointer;
  }

  .current-lang-span {
    color: #016db3;
    font-weight: bold;
  }

</style>