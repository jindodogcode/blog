# PostApi

All URIs are relative to *http://127.0.0.1:8080/api/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addUserPost**](PostApi.md#addUserPost) | **POST** /users/{username}/posts | Create a new post
[**getPost**](PostApi.md#getPost) | **GET** /posts/{id} | Retreives a specific post
[**getPostReplies**](PostApi.md#getPostReplies) | **GET** /posts/{id}/replies | Retreive replies
[**getPosts**](PostApi.md#getPosts) | **GET** /posts | Retreives posts
[**getReplyReplies**](PostApi.md#getReplyReplies) | **GET** /replies/{id}/replies | Retreives replies
[**getUserPosts**](PostApi.md#getUserPosts) | **GET** /users/{username}/posts | Retreive posts by the user specified
[**updateUserPost**](PostApi.md#updateUserPost) | **PATCH** /users/{username}/posts/{id} | Update post


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

<a name="getPost"></a>
# **getPost**
> Post getPost(id)

Retreives a specific post

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Integer**|  | [default to null]

### Return type

[**Post**](..//Models/Post.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json, applicaton/json

<a name="getPostReplies"></a>
# **getPostReplies**
> PageableReplies getPostReplies(id, page, pagesize, after)

Retreive replies

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Integer**|  | [default to null]
 **page** | **Integer**|  | [optional] [default to null]
 **pagesize** | **Integer**|  | [optional] [default to null]
 **after** | **Date**|  | [optional] [default to null]

### Return type

[**PageableReplies**](..//Models/PageableReplies.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json, applicaton/json

<a name="getPosts"></a>
# **getPosts**
> PageablePosts getPosts(page, pagesize, after)

Retreives posts

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **page** | **Integer**|  | [optional] [default to null]
 **pagesize** | **Integer**|  | [optional] [default to null]
 **after** | **Date**|  | [optional] [default to null]

### Return type

[**PageablePosts**](..//Models/PageablePosts.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

<a name="getReplyReplies"></a>
# **getReplyReplies**
> PageableReplies getReplyReplies(id, page, pagesize)

Retreives replies

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Integer**|  | [default to null]
 **page** | **Integer**|  | [optional] [default to null]
 **pagesize** | **Integer**|  | [optional] [default to null]

### Return type

[**PageableReplies**](..//Models/PageableReplies.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json, applicaton/json

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

