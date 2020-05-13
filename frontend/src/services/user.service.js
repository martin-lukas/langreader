import AXIOS from '../utils/axios-instance';

class UserService {
  getAllUsers() {
    console.log('Call: getAllUsers()');
    return AXIOS.get('/users');
  }

  removeUser(userObj) {
    console.log('Call: removeUser()');
    return AXIOS.delete('/users', {data: userObj});
  }

  removeUserSelf() {
    console.log('Call: removeUserSelf()');
    return AXIOS.delete('/users/self');
  }
}

export default new UserService();
