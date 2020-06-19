import {
  ApiClient,
  DefaultApi,
  UserApi,
  PostApi,
  ReplyApi,
  User,
  Post,
  Reply,
  PageableUsers,
  PageablePosts,
  PageableReplies,
  NewUser,
  NewPost,
  NewReply,
  UpdateUser,
  UpdatePost,
  UpdateReply,
} from "@jindodog/blog-client";

let apiClient = new ApiClient();
apiClient.enableCookies = true;

let defaultApi = new DefaultApi(apiClient);
let userApi = new UserApi(apiClient);
let postApi = new PostApi(apiClient);
let replyApi = new ReplyApi(apiClient);

let model = {
  User,
  Post,
  Reply,
};

let pages = {
  PageableUsers,
  PageablePosts,
  PageableReplies,
};

let forms = {
  new: {
    NewUser,
    NewPost,
    NewReply,
  },
  update: {
    UpdateUser,
    UpdatePost,
    UpdateReply,
  },
};

export default {
  defaultApi,
  userApi,
  postApi,
  replyApi,
  model,
  pages,
  forms,
};
