import Vue from 'vue';
import Vuex from 'vuex';

import { auth } from './modules/auth.module';
import { lang } from './modules/lang.module';

Vue.use(Vuex);

const store = new Vuex.Store({
  modules: {
    auth,
    lang
  }
});

export default store;