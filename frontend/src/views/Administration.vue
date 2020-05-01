<template>
  <div>
    <table>
      <thead>
      <tr>
        <th>ID</th>
        <th>Username</th>
        <th>Roles</th>
        <th>Action</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="user in users" :key="user.id">
        <td>{{ user.id }}</td>
        <td>{{ user.username }}</td>
        <td><span v-for="(role, index) in user.roles" :key="index">{{ role.name }}</span></td>
        <td>
          <a href="#"
             v-if="user.username !== currentUser.username"
             @click.prevent="removeUserFromDB(user.username)">
            X
          </a>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
  import UserService from '../services/user.service';

  export default {
    name: 'Admin',
    data() {
      return {
        users: []
      };
    },
    computed: {
      currentUser() {
        return this.$store.state.auth.user;
      }
    },
    mounted() {
      UserService.getAllUsers().then((response) => {
          this.users = response.data;
        }).catch((error) => {
          console.log(error);
        }
      );
    },
    methods: {
      removeUserFromDB(username) {
        UserService.removeUser({param: username}).then(() => {
          this.users = this.users.filter(user => {
            return user.username !== username;
          });
        }).catch(error => {
          console.log(error);
        });
      }
    }
  };
</script>
