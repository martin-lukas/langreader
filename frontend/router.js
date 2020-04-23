import Vue from 'vue';
import Router from 'vue-router';

import Library from './src/components/Library';
import ReadingArea from './src/components/ReadingArea';
import Account from './src/components/Account';
import NotFoundComponent from './src/components/NotFoundComponent';

Vue.use(Router);

export default new Router({
  mode: 'history',
  linkExactActiveClass: 'active-nav-item',
  routes: [
    {
      path: '/',
      alias: '/library',
      name: 'library',
      component: Library
    },
    {
      path: '/reading/:textId',
      name: 'reading',
      component: ReadingArea
    },
    {
      path: '/account',
      name: 'account',
      component: Account
    },
    {
      path: '*',
      component: NotFoundComponent
    }
  ]
});
