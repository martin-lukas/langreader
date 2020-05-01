import Vue from 'vue';
import VeeValidate from 'vee-validate';
import App from './src/App';
import router from './src/router';
import store from './src/store';

// TODO: again happened that repeated word disappeared but focus didnt move after...
// TODO: start logging which user is doing what
// TODO: EXAMINE SQL INJECTION WITH ' CHARS IN WORDS
// TODO: add loading circle when parsing reading text
// TODO: add a text length limit (stop it before it gets sent to server)
// - also... maybe limit how much can be put inside the text area, and the input field

Vue.config.productionTip = false

Vue.use(VeeValidate);

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app');
