# Blog Site

## Table of contents

- [About](#about)
- [Technologies](#technologies)
- [Setup](#setup)
- [API Docs](#api-docs)
- [Todo](#todo)

## About

A simple blog application. Made for practice and retain what I learned from
reading Beggining Spring 5. The general design goals of the application are:

- crud application
- rest api
- SPA frontend
- document api with openapi
- paging
- deploy application using docker

Eventually, I intend to rewrite the backend using Spring WebFlux and / or Vertx.

## Technologies

- Java 11.0.7
- Gradle 6.3
- Spring Boot 2.3
- Node 12.16.3
- Vue 2.6.11
- TailwindCSS 1.4.6
- OpenAPI 3.0.0
- OpenAPI Generator CLI 4.3.1
- Docker 19.03.8
- PostgreSQL 12.3

## Setup

### With Docker

#### Docker Compose

```bash
git pull https://github.com/jindodogcode/blog.git
cd blog
docker-compose up -d
```

Enter `127.0.0.1:8080` into your browser.

#### Docker Swarm

```bash
git pull https://github.com/jindodogcode/blog.git
cd blog
docker stack deploy -c docker-stack.yml blog
```

Wait a minute while Docker pulls the images then enter `127.0.0.1:8080` into
your browser.

### Without Docker

TODO

## API Docs

[API Docs](./docs/api/README.md)

## Todo

- better post creation form
- store posts and replies as html
- user avatar images
- beautify
- add search functionality
