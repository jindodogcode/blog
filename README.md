# Blog Site

## Table of contents

- [About](#about)
- [Technologies](#technologies)
- [Setup](#setup)
- [Todo](#todo)

## About

A project to practice using Spring with an SPA frontend.

## Technologies

- Java 11.0.7
- Gradle 6.3
- Spring Boot 2.3
- Node 12.16.3
- Vue 2.6.11
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

Enter `127.0.0.1:8080` into your browser.

### Without Docker

TODO

## Todo

TODO
