<template>
  <section>
    <h4 class="form-title">Register</h4>
    <form @submit.prevent="handleSubmit()" class="form reg-form">
      <div class="form-row reg-form-row">
        <label>Username:</label>
        <input type="text" minlength="3" v-model="newUserForm.username" />
      </div>
      <div class="form-row reg-form-row">
        <label>Email Address:</label>
        <input type="email" v-model="newUserForm.email" />
      </div>
      <div class="form-row reg-form-row">
        <label>First Name:</label>
        <input type="text" v-model="newUserForm.firstName" />
      </div>
      <div class="form-row reg-form-row">
        <label>Last Name:</label>
        <input type="text" v-model="newUserForm.lastName" />
      </div>
      <div class="form-row reg-form-row">
        <label>Password:</label>
        <input type="password" v-model="newUserForm.password" />
      </div>
      <input type="submit" value="Register" />
    </form>
  </section>
</template>

<script>
import BlogClient from "@/client";

export default {
  name: "Registration",
  data: function() {
    return {
      newUserForm: {
        username: "",
        email: "",
        firstName: "",
        lastName: "",
        password: "",
      },
    };
  },
  methods: {
    handleSubmit: async function() {
      await BlogClient.userApi.addUser(this.newUserForm);
      this.$router.push("/");
    },
  },
  mounted: function() {
    document.querySelector(".form > .form-row:first-child input").focus();
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
