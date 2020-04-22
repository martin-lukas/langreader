<template>
  <nav id="topnav" class="topnav">
    <a v-for="(menuItem, index) in menuItems" :key="index"
       href="#"
       :class="(menuItem.name === selected) ? 'active' : ''"
       @click.prevent="(e) => {
         if (isToggled) {toggleTopNav();}
         $emit('area-selected', menuItem.name);
       }">
      {{ menuItem.label }}
    </a>
    <a href="javascript:void(0);" class="icon" @click="toggleTopNav">
      <i class="fa fa-bars"></i>
    </a>
  </nav>
</template>

<script>
  export default {
    props: {
      selected: String
    },
    data() {
      return {
        isToggled: false,
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
    },
    methods: {
      toggleTopNav() {
        let navbarElement = document.getElementById("topnav");
        if (navbarElement.className === "topnav") {
          navbarElement.className += " responsive";
        } else {
          navbarElement.className = "topnav";
        }
        this.isToggled = !this.isToggled;
      }
    }
  }
</script>

<style scoped>
  /* Add a black background color to the top navigation */
  .topnav {
    background-color: #333;
    overflow: hidden;
    border-radius: 0 0 10px 10px;
  }

  /* Style the links inside the navigation bar */
  .topnav a {
    float: left;
    display: block;
    color: #f2f2f2;
    text-align: center;
    padding: 14px 16px;
    text-decoration: none;
    font-size: 17px;
    font-weight: bold;
  }

  /* Change the color of links on hover */
  .topnav a:hover {
    background-color: #ddd;
    color: black;
  }

  .topnav a:focus {
    outline: 0;
  }

  /* Add an active class to highlight the current page */
  .topnav a.active {
    /*background-color: #4CAF50; original */
    background-color: rgba(73, 150, 220, 0.94);
    color: white;
  }

  /* Hide the link that should open and close the topnav on small screens */
  .topnav .icon {
    display: none;
  }

  /* When the screen is less than 600 pixels wide, hide all links, except for the first one
  ("Library"). Show the link that contains should open and close the topnav (.icon) */
  @media screen and (max-width: 500px) {
    .topnav a:not(:first-child) {
      display: none;
    }

    .topnav a.icon {
      float: right;
      display: block;
    }
  }

  /* The "responsive" class is added to the topnav with JavaScript when the user clicks
  on the icon. This class makes the topnav look good on small screens (display the links
  vertically instead of horizontally) */
  @media screen and (max-width: 500px) {
    .topnav.responsive {
      position: relative;
    }

    .topnav.responsive a.icon {
      position: absolute;
      right: 0;
      top: 0;
    }

    .topnav.responsive a {
      float: none;
      display: block;
      text-align: left;
    }
  }
</style>