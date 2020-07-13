<template>
  <div class="p-4">
    <div class="mb-2 text-center text-red-400">{{ error }}</div>
    <h4 class="form-title">Login</h4>
    <form @submit.prevent="handleSubmit" class="form login-form">
      <div class="form-row">
        <input
          type="text"
          v-model="loginForm.username"
          minlength="3"
          placeholder="Username or Email"
        />
      </div>
      <div class="form-row">
        <input
          type="password"
          v-model="loginForm.password"
          minlength="8"
          placeholder="Password"
        />
      </div>
      <input type="submit" value="Submit" />
    </form>
  </div>
</template>

<script>
import { UnauthorizedError } from "@/errors";

export default {
  name: "LoginForm",
  data: function() {
    return {
      loginForm: {
        username: "",
        password: "",
      },
      error: "",
    };
  },
  methods: {
    handleSubmit: async function() {
      try {
        await this.$store.dispatch("login", this.loginForm);
        this.$store.dispatch("setLoginFormOpen", false);
        this.$router.push("/");
      } catch (err) {
        if (err instanceof UnauthorizedError) {
          this.error = err.message;
        }
      }
    },
  },
  mounted: function() {
    document.querySelector(".form > .form-row:first-of-type input").focus();
  },
};
</script>

<style lang="postcss" scoped>
.login-form {
  @apply flex flex-col items-center;
}
</style>
