import AXIOS from '../utils/axios-instance';

class AuthService {
  login(user) {
    console.log("Call: login()");
    return AXIOS.post('/auth/signin', {
        username: user.username,
        password: user.password
      }).then(response => {
        if (response.data.token) {
          localStorage.setItem('user', JSON.stringify(response.data));
        }
        return response;
      });
  }
  register(user) {
    console.log("Call: register()");
    return AXIOS.post('/auth/signup', user);
  }
  logout() {
    localStorage.clear();
  }
}

export default new AuthService();
