import Vue from 'vue';
import App from './App';

console.info(">>> Vue instance initialization");

new Vue({
  render: h => h(App),
}).$mount('#app');
