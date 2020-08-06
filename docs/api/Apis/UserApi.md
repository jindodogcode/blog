# UserApi

All URIs are relative to *http://127.0.0.1:8080/api/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addUser**](UserApi.md#addUser) | **POST** /users | Creates a new user
[**addUserPost**](UserApi.md#addUserPost) | **POST** /users/{username}/posts | Create a new post
[**addUserReply**](UserApi.md#addUserReply) | **POST** /users/{username}/replies | Create a new reply
[**deleteUser**](UserApi.md#deleteUser) | **DELETE** /users/{username} | Delete user
[**getUser**](UserApi.md#getUser) | **GET** /users/{username} | Retreives user information
[**getUserPosts**](UserApi.md#getUserPosts) | **GET** /users/{username}/posts | Retreive posts by the user specified
[**getUserReplies**](UserApi.md#getUserReplies) | **GET** /users/{username}/replies | Retreive replies by specific user
[**updateUser**](UserApi.md#updateUser) | **PATCH** /users/{username} | Updates user
[**updateUserPost**](UserApi.md#updateUserPost) | **PATCH** /users/{username}/posts/{id} | Update post
[**updateUserReply**](UserApi.md#updateUserReply) | **PATCH** /users/{username}/replies/{id} | Updates a specific reply


<a name="addUser"></a>
# **addUser**
> User addUser(newUser)

Creates a new user

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **newUser** | [**NewUser**](..//Models/NewUser.md)| Create a new user |

### Return type

[**User**](..//Models/User.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

<a name="addUserPost"></a>
# **addUserPost**
> Post addUserPost(username, newPost, jsessionid)

Create a new post

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **username** | **String**|  | [default to null]
 **newPost** | [**NewPost**](..//Models/NewPost.md)| Object representing a new post |
 **jsessionid** | **String**|  | [optional] [default to null]

### Return type

[**Post**](..//Models/Post.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json, applicaton/json

<a name="addUserReply"></a>
# **addUserReply**
> Reply addUserReply(username, newReply, jsessionid)

Create a new reply

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **username** | **String**|  | [default to null]
 **newReply** | [**NewReply**](..//Models/NewReply.md)| Object representing a new reply |
 **jsessionid** | **String**|  | [optional] [default to null]

### Return type

[**Reply**](..//Models/Reply.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json, applicaton/json

<a name="deleteUser"></a>
# **deleteUser**
> deleteUser(username, jsessionid)

Delete user

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **username** | **String**|  | [default to null]
 **jsessionid** | **String**|  | [optional] [default to null]

### Return type

null (empty response body)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: applicaton/json

<a name="getUser"></a>
# **getUser**
> User getUser(username)

Retreives user information

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **username** | **String**|  | [default to null]

### Return type

[**User**](..//Models/User.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

<a name="getUserPosts"></a>
# **getUserPosts**
> PageablePosts getUserPosts(username, page, pagesize, after)

Retreive posts by the user specified

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **username** | **String**|  | [default to null]
 **page** | **Integer**|  | [optional] [default to null]
 **pagesize** | **Integer**|  | [optional] [default to null]
 **after** | **Integer**|  | [optional] [default to null]

### Return type

[**PageablePosts**](..//Models/PageablePosts.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json, applicaton/json

<a name="getUserReplies"></a>
# **getUserReplies**
> PageableReplies getUserReplies(username, page, pagesize)

Retreive replies by specific user

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **username** | **String**|  | [default to null]
 **page** | **Integer**|  | [optional] [default to null]
 **pagesize** | **Integer**|  | [optional] [default to null]

### Return type

[**PageableReplies**](..//Models/PageableReplies.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json, applicaton/json

<a name="updateUser"></a>
# **updateUser**
> User updateUser(username, updateUser, jsessionid)

Updates user

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **username** | **String**|  | [default to null]
 **updateUser** | [**UpdateUser**](..//Models/UpdateUser.md)| Create a new user |
 **jsessionid** | **String**|  | [optional] [default to null]

### Return type

[**User**](..//Models/User.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json, applicaton/json

<a name="updateUserPost"></a>
# **updateUserPost**
> Post updateUserPost(username, id, updatePost, jsessionid)

Update post

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **username** | **String**|  | [default to null]
 **id** | **Integer**|  | [default to null]
 **updatePost** | [**UpdatePost**](..//Models/UpdatePost.md)| Object representing the fields to update in the post |
 **jsessionid** | **String**|  | [optional] [default to null]

### Return type

[**Post**](..//Models/Post.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json, applicaton/json

<a name="updateUserReply"></a>
# **updateUserReply**
> Reply updateUserReply(username, id, updateReply, jsessionid)

Updates a specific reply

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **username** | **String**|  | [default to null]
 **id** | **Integer**|  | [default to null]
 **updateReply** | [**UpdateReply**](..//Models/UpdateReply.md)| Object containing the fields to update a reply |
 **jsessionid** | **String**|  | [optional] [default to null]

### Return type

[**Reply**](..//Models/Reply.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json, applicaton/json

