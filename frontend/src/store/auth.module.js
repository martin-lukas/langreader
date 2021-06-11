import AuthService from '../services/auth.service';

const user = JSON.parse(localStorage.getItem('user'));
const initialState = (user)
  ? {status: {loggedIn: true}, user: user}
  : {status: {loggedIn: false}, user: null};

export const auth = {
  namespaced: true,
  state: initialState,
  getters: {
    isAdmin(state) {
      return state.user.roles.includes('ROLE_ADMIN');
    },
    isLoggedIn(state) {
      return !!(state.user);
    }
  },
  actions: {
    login({commit}, user) {
      return AuthService.login(user).then(
        response => {
          commit('loginSuccess', response.data);
          return Promise.resolve(response);
        },
        error => {
          commit('loginFailure');
          return Promise.reject(error);
        }
      );
    },
    logout({dispatch, commit}) {
      AuthService.logout();
      commit('logout');
      dispatch('lang/clearChosenLang', null, { root: true });
      dispatch('lang/clearUserLangs', null, { root: true });
      dispatch('lang/clearNativeLang', null, { root: true });
      dispatch('lang/clearAllLangs', null, { root: true });
    },
    register({commit}, user) {
      return AuthService.register(user).then(
        response => {
          commit('registerSuccess');
          return Promise.resolve(response.data);
        },
        error => {
          commit('registerFailure');
          return Promise.reject(error);
        }
      );
    },
  },
  mutations: {
    loginSuccess(state, user) {
      state.status.loggedIn = true;
      state.user = user;
    },
    loginFailure(state) {
      state.status.loggedIn = false;
      state.user = null;
    },
    logout(state) {
      state.status.loggedIn = false;
      state.user = null;
    },
    registerSuccess(state) {
      state.status.loggedIn = false;
    },
    registerFailure(state) {
      state.status.loggedIn = false;
    }
  }
};