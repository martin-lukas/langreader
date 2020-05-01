import Vue from 'vue';
import Router from 'vue-router';

import Home from './views/Home';
import Login from './views/Login';
import Register from './views/Register.vue';
import NotFoundComponent from './views/NotFoundView';

Vue.use(Router);

const router = new Router({
  mode: 'history',
  linkExactActiveClass: 'active-nav-item',
  routes: [
    {path: '/', name: 'home', component: Home},
    {path: '/login', name: 'login', component: Login},
    {path: '/register', name: 'register', component: Register},
    {path: '/admin', name: 'admin', component: () => import('./views/Administration')},
    {path: '/library', name: 'library', component: () => import('./views/Library')},
    {path: '/reading/:textId', name: 'reading', component: () => import('./views/ReadingArea')},
    {path: '/language', name: 'language', component: () => import('./views/Language')},
    {path: '/account', name: 'account', component: () => import('./views/Account')},
    {path: '/notfound', name: 'notfound', component: NotFoundComponent},
    {path: '*', redirect: '/notfound'}
  ]
});

router.beforeEach((to, from, next) => {
  const publicPages = ['/login', '/register', '/'];
  const adminPages = ['/admin'];
  const authRequired = !publicPages.includes(to.path);
  const adminRequired = adminPages.includes(to.path);

  const userInLS = localStorage.getItem('user');

  if (authRequired && !(userInLS)) {
    next('/login');
  } else {
    if (adminRequired) {
      const user = JSON.parse(userInLS);
      const isAdmin = user.roles.includes('ROLE_ADMIN');
      if (!isAdmin) {
        next('/notfound');
      } else {
        next();
      }
    } else {
      next();
    }
  }
});

export default router;