import Vue from 'vue';
import App from './App';
import router from './router';
import store from './store';

// TODO: choose translation languages
// TODO: limit input stream (ByteStreams.limit() from Guava or BoundedInputStream from Apache Commons)

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app');
