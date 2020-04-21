<template>
  <nav id="navbar">
    <a id="logo" href="#" @click.prevent="$emit('area-selected', 'home')">LangReader</a>
    <ul>
      <li v-for="(menuItem, index) in menuItems" :key="index">
        <a href="#"
           :class="(menuItem.name === selected) ? 'active' : ''"
           @click.prevent="$emit('area-selected', menuItem.name)">{{ menuItem.label }}</a>
      </li>
    </ul>
  </nav>
</template>

<script>
  export default {
    props: {
      selected: String
    },
    data() {
      return {
        menuItems: [
          {
            name: 'library',
            label: 'Library'
          },
          {
            name: 'account',
            label: 'Account'
          }
        ]
      }
    }
  }
</script>

<style scoped>
  #navbar {
    display: inline-flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    background-color: #e8e8e8;
    padding-left: 10px;
    border-radius: 0 0 10px 10px;
  }

  #logo {
    font-family: 'Times New Roman', serif;
    display: block;
    font-size: 35px;
    font-weight: bold;
    color: #012a9a;
    user-select: none;
    text-decoration: none;
  }

  ul {
    list-style-type: none;
    overflow: hidden;
    background-color: #e8e8e8;
    border-radius: 0 0 10px 0;

  }

  li {
    float: left;
  }

  li a {
    display: block;
    font-weight: bold;
    color: #2d2d2d;
    text-align: center;
    padding: 14px 16px;
    text-decoration: none;
  }

  li a:hover {
    background-color: #c4c4c4;
  }

  .active {
    background-color: #0055ff;
    color: white;
  }
</style>