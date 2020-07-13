<template>
  <section>
    <h4 class="form-title">Register</h4>
    <form @submit.prevent="handleSubmit()" class="form reg-form">
      <div class="form-row reg-form-row">
        <label for="username">Username:</label>
        <input
          type="text"
          id="username"
          minlength="3"
          v-model="newUserForm.username"
        />
      </div>
      <div class="form-row reg-form-row">
        <label for="email">Email Address:</label>
        <input type="email" id="email" v-model="newUserForm.email" />
      </div>
      <div class="form-row reg-form-row">
        <label for="first-name">First Name:</label>
        <input
          type="text"
          id="first-name"
          minlength="1"
          maxlength="32"
          v-model="newUserForm.firstName"
        />
      </div>
      <div class="form-row reg-form-row">
        <label for="last-name">Last Name:</label>
        <input
          type="text"
          id="last-name"
          minlength="1"
          maxlength="32"
          v-model="newUserForm.lastName"
        />
      </div>
      <div class="form-row reg-form-row">
        <label for="password">Password:</label>
        <input
          type="password"
          id="password"
          minlength="8"
          maxlength="64"
          v-model="newUserForm.password"
        />
      </div>
      <input type="submit" value="Register" />
    </form>
  </section>
</template>

<script>
import BlogClient from "@/client";
import { ConflictError } from "@/errors";

export default {
  name: "Registration",
  data: function() {
    return {
      newUserForm: {
        username: "",
        email: "",
        firstName: "",
        lastName: "",
        about: "",
        password: "",
      },
    };
  },
  methods: {
    handleSubmit: async function() {
      try {
        await BlogClient.userApi.addUser(this.newUserForm);
        this.$router.push("/");
      } catch (err) {
        if (err.status === 409) {
          this.$store.dispatch("addPopupError", new ConflictError());
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
.reg-form {
  @apply flex flex-col content-center;
}

.reg-form-row {
  @apply flex flex-col;
}

.reg-form input[type="submit"] {
  @apply self-center;
}

@media (min-width: 768px) {
  .reg-form-row {
    @apply flex-row;
  }

  .reg-form-row label {
    @apply pr-2;
  }

  .reg-form-row input {
    @apply ml-2;
  }

  .reg-form-row label {
    @apply w-1/2 text-right;
  }
}
</style>

