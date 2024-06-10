# Sky User Manager

## Overview

This project is focused on demonstrating a simple REST API. Its responsibilities are split into three parts.

### Security layer

- Allows users to authenticate using basic authentication - no endpoints are accessible without authentication
- Manages optional in-memory database, created with a single "admin" user if specified via app configuration - see "
  Developer quickstart" section for details

### Base API

- List information for users
- List information for projects
- Manage users details for own accounts (email, name, password)

### Admin API

- Manage user permissions (assigned projects, user roles)
- Manage projects (add, update, delete)

The project serves only as a demonstration - for being production ready, there are multiple issues that would need to be
resolved first (see "Known issues" section for more details).

Note: Admin role is required in order to access admin API.

## Developer quickstart

The whole project is containerized, it is enough to have Docker installed.
Optionally, for testing purposes, admin account details can be specified under the following app config keys:

- com.sky.usermanager.security.admin.user=[admin_username]
- com.sky.usermanager.security.admin.password=[admin_password]

Please note that this is only an in-memory user - meaning it won't appear on results listed via "/users" API, and cannot
be managed via "/admin/users" API. It is not recommended to use in production environment.

Once application.properties file is configured as desired, run with:

```console
$ ./mvnw clean package
$ docker-compose up --build
```

# Endpoints
Detailed information for all endpoints can be found via:
- http://localhost:8080/api/docs (for openapi docs)
- http://localhost:8080/api/swagger-ui/index.html (for swagger docs)

Below is a list of possible endpoints, examples, and response data format.

| Response code | Description                  |
|---------------|------------------------------|
| 200           | Success (with response data) |
| 204           | Success (no content)         |
| 401           | Not authorized               |
| 403           | Forbidden                    |
| 404           | Object not found             |


## /api/users

### Get all users

```console
$ curl -X GET localhost:8080/api/users -H "Content-Type: application/json" -u admin:admin
```

Response:

```
[{"id":1,"email":"test_user","name":"test updated"}]
```

| Key   | Value             |
|-------|-------------------|
| id    | unique identifier |
| email | user email        |
| name  | custom username   |

### Get user details

```console
$ curl -X GET localhost:8080/api/users/1 -H "Content-Type: application/json" -u admin:admin
```

Response:

```
{"id":1,"email":"test_user","name":"test updated","role":"DEFAULT","projects":[]}
```

| Key      | Value                      |
|----------|----------------------------|
| id       | unique identifier          |
| email    | user email                 |
| name     | custom username            |
| role     | user role                  |
| projects | array of assigned projects |

### Update user information

```console
$ curl -X PUT localhost:8080/api/users/1 -H "Content-Type: application/json" -u test_user:9703c382-0bb2-408a-93e2-628031546127 -d '{"email": "test_user", "name": "test_user_updated"}'
```

Request:

| Key   | Value             |
|-------|-------------------|
| id    | unique identifier |
| email | user email        |
| name  | custom username   |

Response:

```
{"id":1,"email":"test_user","name":"test_user_updated"}
```

| Key   | Value             |
|-------|-------------------|
| id    | unique identifier |
| email | user email        |
| name  | custom username   |

### Update password

```console
$ curl -X PUT localhost:8080/api/users -H "Content-Type: text/plain" -u test_user:9703c382-0bb2-408a-93e2-628031546127 -d "new_password"
```

Response:

```
new_password
```

## /api/admin/users

### Create user

```console
$ curl -X POST localhost:8080/api/admin/users -H "Content-Type: application/json" -u admin:admin -d '{"email": "test_user2", "name": "test_user", "role": "DEFAULT"}'
```

Request:

| Key   | Value                     |
|-------|---------------------------|
| email | user email                |
| name  | custom username           |
| role  | user role (DEFAULT/ADMIN) |

Response:

```
[auto_generated_password]
```

### Update user role

```console
$ curl -X PUT localhost:8080/api/admin/users/1 -H "Content-Type: application/json" -u admin:admin -d '{"email": "test_user2", "name": "test_user", "role": "ADMIN"}'
```

Request:

| Key   | Value                     |
|-------|---------------------------|
| id    | unique identifier         |
| email | user email                |
| name  | custom username           |
| role  | user role (DEFAULT/ADMIN) |

Response:

```
{"id":1,"email":"test_user2","name":"test_user","role":"ADMIN","projects":[]}
```

| Key      | Value             |
|----------|-------------------|
| id       | unique identifier |
| email    | user email        |
| name     | custom username   |
| role     | user role         |
| projects | assigned projects |

### Delete user access

```console
$ curl -X DELETE localhost:8080/api/admin/users/1 -u admin:admin
```

### Add user to project

```console
$ curl -X PUT localhost:8080/api/admin/users/1/projects -H "Content-Type: application/json" -u admin:admin -d '{"id": 1, "name": "test_project"}'
```

Request:

| Key   | Value                     |
|-------|---------------------------|
| id    | unique identifier         |
| email | user email                |
| name  | custom username           |
| role  | user role (DEFAULT/ADMIN) |

Response:

```
{"id":1,"email":"test_user2","name":"test_user","role":"ADMIN","projects":[{"id": 1, "name": "test_project"}]}
```

| Key      | Value             |
|----------|-------------------|
| id       | unique identifier |
| email    | user email        |
| name     | custom username   |
| role     | user role         |
| projects | assigned projects |

### Remove user from project

```console
$ curl -X DELETE localhost:8080/api/admin/users/1/projects/1 -u admin:admin
```

## /api/projects

### Get all projects

```console
$ curl -X GET localhost:8080/api/projects -H "Content-Type: application/json" -u admin:admin
```

Response:

```
[{"id":1,"name":"test project"}]%
```

| Key  | Value               |
|------|---------------------|
| id   | unique identifier   |
| name | custom project name |

### Get project details

```console
$ curl -X GET localhost:8080/api/projects/1 -H "Content-Type: application/json" -u admin:admin
```

Response:

```
{"id":1,"name":"updated test project","users":[]}
```

| Key   | Value                   |
|-------|-------------------------|
| id    | unique identifier       |
| name  | custom project name     |
| users | array of assigned users |

## /api/admin/projects

### Create project

```console
$ curl -X POST localhost:8080/api/admin/projects -H "Content-Type: application/json" -u admin:admin -d '{"name": "new_project"}'
```

Request:

| Key   | Value               |
|-------|---------------------|
| email | user email          |
| name  | custom project name |

Response:

```
{"id":3,"name":"new_project"}
```

| Key  | Value               |
|------|---------------------|
| id   | unique identifier   |
| name | custom project name |

### Update project

```console
$ curl -X PUT localhost:8080/api/admin/projects/1 -H "Content-Type: application/json" -u admin:admin -d '{"name": "test_project_updated"}'
```

Request:

| Key  | Value           |
|------|-----------------|
| name | custom username |

Response:

```
{"id":1,"name":"test_project_updated"}
```

| Key  | Value               |
|------|---------------------|
| id   | unique identifier   |
| name | custom project name |

### Delete project

```console
$ curl -X DELETE localhost:8080/api/admin/projects/1 -u admin:admin
```

## Known issues
- Only unit testing is implemented, and mostly for happy-paths only
- Most of the errors are grouped into 400
- Only basic auth is implemented - in a real-life scenario, using token-based auth (JWT) or session-based auth (for stateful APIs) might be preferable
- Spring AOP is used for code weaving - on larger projects, this would probably need to be refactored
- Only very simple monitoring is implemented via logging of method execution times - this would need to be extended
- No profiling for different environments implemented
- Credentials for MySQL DB are stored in docker-compose