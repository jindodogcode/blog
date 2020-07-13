import { DisplayError } from "@/errors";

const state = {
  popupErrors: [],
  pageErrors: [],
};

const mutations = {
  /**
   * Add popup error to app state
   *
   * @param {Object} state
   * @param {Array} state.popupErrors
   * @param {DisplayError} payload
   */
  ADD_POPUP_ERROR(state, payload) {
    state.popupErrors.push(payload);
  },

  /**
   * Add page error to app state
   *
   * @param {Object} state
   * @param {Array} state.pageErrors
   * @param {DisplayError} payload
   */
  ADD_PAGE_ERROR(state, payload) {
    state.pageErrors.push(payload);
  },

  /**
   * Reset error state
   *
   * @param {Object} state
   * @param {Array} state.errors
   */
  CLEAR_ERRORS(state) {
    state.popupErrors = [];
    state.pageErrors = [];
  },
};

const actions = {
  /**
   * Add popup error to app state
   *
   * @param {Object} context
   * @param {Function} commit
   * @param {DisplayError} error
   */
  addPopupError({ commit }, error) {
    if (!(error instanceof DisplayError)) {
      throw new TypeError("Bad error type");
    }
    commit("ADD_POPUP_ERROR", error);
  },

  /**
   * Add page error to app state
   *
   * @param {Object} context
   * @param {Function} commit
   * @param {DisplayError} error
   */
  addPageError({ commit }, error) {
    if (!(error instanceof DisplayError)) {
      throw new TypeError("Bad error type");
    }
    commit("ADD_PAGE_ERROR", error);
  },

  /**
   * Reset errors state
   *
   * @param {Object} context
   * @param {Function} commit
   */
  clearErrors({ commit }) {
    commit("CLEAR_ERRORS");
  },
};

const getters = {
  errors(state) {
    return state;
  },
};

const errors = {
  state,
  mutations,
  actions,
  getters,
};

export default errors;
