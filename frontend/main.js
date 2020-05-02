import Vue from 'vue';
import App from './src/App';
import router from './src/router';
import store from './src/store';

// TODO: limit input stream (ByteStreams.limit() from Guava or BoundedInputStream from Apache Commons)
// TODO: start logging which user is doing what

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app');
