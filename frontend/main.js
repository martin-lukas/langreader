import Vue from 'vue';
import App from './src/App';
import BootstrapVue from 'bootstrap-vue';

import "./src/assets/global.css";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap-vue/dist/bootstrap-vue.css";

console.info(">>> Vue instance initialization");

Vue.use(BootstrapVue);

new Vue({
  render: h => h(App),
}).$mount('#app');
