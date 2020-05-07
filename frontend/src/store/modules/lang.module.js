import LangService from '../../services/lang.service';

const chosenLangInLS = JSON.parse(localStorage.getItem('chosenLang'));
const initChosenLang = (chosenLangInLS) ? chosenLangInLS : null;
const userLangsInLS = JSON.parse(localStorage.getItem('userLangs'));
const initUserLangs = (userLangsInLS) ? userLangsInLS : [];
const allLangsInLS = JSON.parse(localStorage.getItem('allLangs'));
const initAllLangs = (allLangsInLS) ? allLangsInLS : [];
const nativeLangInLS = JSON.parse(localStorage.getItem('nativeLang'));
const initNativeLang = (nativeLangInLS) ? nativeLangInLS : [];

export const lang = {
  namespaced: true,
  state: {
    userLangs: initUserLangs,
    chosenLang: initChosenLang,
    allLangs: initAllLangs,
    nativeLang: initNativeLang
  },
  getters: {
    chosenLang(state) {
      return state.chosenLang;
    },
    nativeLang(state) {
      return state.nativeLang;
    },
    allLangs(state) {
      return state.allLangs;
    }
  },
  actions: {
    fetchUserLangs({commit}) {
      LangService.getUserLangs().then(response => {
        commit('setUserLangs', response.data);
      }).catch(err => {
        console.log(err);
      })
    },
    fetchChosenLang({commit}) {
      LangService.getChosenLang().then(response => {
        commit('changeLangTo', response.data);
      }).catch(err => {
        console.log(err);
      })
    },
    fetchNativeLang({commit}) {
      LangService.getNativeLang().then(response => {
        commit('setNativeLang', response.data);
      }).catch(err => {
        console.log(err);
      })
    },
    fetchAllLangs({commit, state}) {
      if (state.allLangs.length === 0) {
        LangService.getAllLangs().then(response => {
          commit('setAllLangs', response.data);
        }).catch(error => {
          console.log(error);
        });
      }
    },
    addUserLang({commit, state, dispatch}, language) {
      LangService.addUserLang(language).then(() => {
        commit('addUserLang', language);
        if (state.userLangs.length === 1) { // if it's the 1st users lang -> set as chosen
          dispatch('changeLang', language);
        }
      }).catch(err => {
        console.log(err);
      });
    },
    removeUserLang({commit}, language) {
      LangService.removeUserLang(language).then(() => {
        commit('removeUserLang', language);
      }).catch(err => {
        console.log(err);
      });
    },
    changeLang({commit}, language) {
      LangService.setChosenLang(language).then(() => {
        commit('changeLangTo', language);
      }).catch((err) => {
        console.log(err)
      });
    },
    clearChosenLang({commit}) {
      commit('wipeChosenLang');
    },
    clearUserLangs({commit}) {
      commit('wipeUserLangs');
    },
    clearNativeLang({commit}) {
      commit('wipeNativeLang');
    },
    clearAllLangs({commit}) {
      commit('wipeAllLangs');
    }
  },
  mutations: {
    setUserLangs(state, languages) {
      state.userLangs = languages;
    },
    setAllLangs(state, languages) {
      state.allLangs = languages;
    },
    setNativeLang(state, language) {
      state.nativeLang = language;
    },
    addUserLang(state, language) {
      state.userLangs = [...state.userLangs, language];
    },
    removeUserLang(state, language) {
      state.userLangs = state.userLangs.filter(aLang => {
        return aLang.code !== language.code
      });
    },
    changeLangTo(state, language) {
      state.chosenLang = language;
    },
    wipeChosenLang(state) {
      state.chosenLang = null;
    },
    wipeUserLangs(state) {
      state.userLangs = [];
    },
    wipeNativeLang(state) {
      state.nativeLang = null;
    },
    wipeAllLangs(state) {
      state.allLangs = [];
    }
  }
};