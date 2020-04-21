import Vue from 'vue';
import App from './src/App';
import "./src/assets/global.css";

new Vue({
  render: h => h(App),
}).$mount('#app');
