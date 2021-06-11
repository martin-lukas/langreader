import Vue from 'vue';
import Vuex from 'vuex';

import { auth } from './store/auth.module';
import { lang } from './store/lang.module';

Vue.use(Vuex);

const store = new Vuex.Store({
  modules: {
    auth,
    lang
  }
});

export default store;