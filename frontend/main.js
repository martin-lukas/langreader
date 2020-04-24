import Vue from 'vue';
import App from './src/App';
import router from './router';
import store from './store';

// TODO: fix 404 when accessing URL /reading/12trtrer or /reading/121654 etc.
// TODO: favicon disappears (404) when accessing something nonexistent...
// TODO: add loading circle when parsing reading text

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app');
