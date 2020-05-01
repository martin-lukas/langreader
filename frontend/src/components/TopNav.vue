<template>
  <nav id="top-nav" class="top-nav" @click="collapseTopNav">
    <router-link to="/" class="always-visible nav-link">Home</router-link>
    <router-link v-if="currentUser && !isAdmin && chosenLang"
                 to="/library"
                 class="always-visible nav-link">
      Library
    </router-link>
    <div class="right-aligned">
      <router-link v-if="!currentUser" to="/login" class="nav-link">Log In</router-link>
      <router-link v-if="!currentUser" to="/register" class="nav-link">Sign Up</router-link>
      <router-link v-if="currentUser && !isAdmin"
                   to="/language"
                   class="nav-link lang-link">
        Lang: {{ chosenLangLabel }}
      </router-link>
      <router-link v-if="isAdmin" to="/admin" class="nav-link">Administration</router-link>
      <router-link v-if="currentUser" to="/account" class="nav-link">My Account</router-link>
      <a v-if="currentUser" @click.prevent="logOut" class="nav-link">Log Out</a>
    </div>
    <a href="javascript:void(0);" class="icon" @click.stop="toggleTopNav">
      <i class="fa fa-bars"></i>
    </a>
  </nav>
</template>

<script>
  export default {
    computed: {
      currentUser() {
        return this.$store.state.auth.user;
      },
      isAdmin() {
        if (this.currentUser && this.currentUser.roles) {
          return this.currentUser.roles.includes('ROLE_ADMIN');
        }
        return false;
      },
      chosenLang() {
        return this.$store.getters["lang/chosenLang"];
      },
      chosenLangLabel() {
        return (this.chosenLang) ? this.chosenLang.fullName : 'none';
      }
    },
    methods: {
      toggleTopNav() {
        let navBarElement = document.getElementById("top-nav");
        if (navBarElement.className === "top-nav") {
          navBarElement.className += " responsive";
        } else {
          navBarElement.className = "top-nav";
        }
      },
      collapseTopNav() {
        let navBarElement = document.getElementById("top-nav");
        navBarElement.className = "top-nav";
      },
      logOut() {
        this.$store.dispatch('auth/logout');
        this.$router.push('/login');
      }
    }
  }
</script>

<style scoped>
  .top-nav {
    background-color: var(--default-text-color);
    overflow: hidden;
    border-radius: 0 0 10px 10px;
    border-top: 2px groove var(--active-el-color);
    font-size: 0.95em;
    user-select: none;
  }

  .right-aligned {
    float: right;
  }

  a.nav-link, a.icon {
    overflow: visible;
    float: left;
    display: block;
    color: #f2f2f2;
    text-align: center;
    padding: 10px 16px;
    text-decoration: none;
    font-weight: bold;
    cursor: pointer;
  }

  a.lang-link {
    color: var(--active-el-color-light);
  }

  a.lang-link.active-nav-item {
    color: white;
  }

  .active-nav-item {
    background-color: var(--active-el-color);
    color: white;
  }

  .active-nav-item:hover {
    background-color: var(--active-el-color-dark);
    color: white;
  }

  a:hover {
    background-color: #eee;
    color: var(--default-text-color);
  }

  /* Hide the link that should open and close the top-nav on small screens */
  .top-nav .icon {
    display: none;
  }

  /* When the screen is less than 600 pixels wide, hide all links */
  @media screen and (max-width: 500px) {
    .top-nav a:not(.always-visible) {
      display: none;
    }

    .top-nav a.icon {
      float: right;
      display: block;
    }
  }

  /* The "responsive" class is added to the top-nav with JavaScript when the user clicks
  on the icon. This class makes the top-nav look good on small screens (display the links
  vertically instead of horizontally) */
  @media screen and (max-width: 500px) {
    .top-nav.responsive {
      position: relative;
    }

    .top-nav.responsive a.icon {
      position: absolute;
      right: 0;
      top: 0;
    }

    .top-nav.responsive a {
      float: none;
      display: block;
      text-align: left;
    }

    .top-nav.responsive .right-aligned {
      float: none;
    }
  }
</style>