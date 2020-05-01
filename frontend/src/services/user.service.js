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
}

export default new UserService();
