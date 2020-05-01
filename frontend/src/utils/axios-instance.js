import axios from 'axios';

function authHeader() {
  let user = JSON.parse(localStorage.getItem('user'));
  if (user && user.token) {
    return {Authorization: 'Bearer ' + user.token};
  } else {
    return {};
  }
}

const axiosInstance = axios.create({
  baseURL: process.env.NODE_ENV !== 'production'
    ? 'http://localhost:8080/api'
    : 'https://langreader.net/api'
});

axiosInstance.interceptors.request.use((config) => ({
  ...config,
  headers: {
    ...{
      'Content-type': 'application/json'
    },
    ...authHeader()
  }
}))

export default axiosInstance;