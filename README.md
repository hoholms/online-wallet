# Online Wallet

A simple, user-friendly web application to manage your finances efficiently.

## Table of Contents

1. [Installation](#installation)
    - [Requirements](#requirements)
    - [Running Locally](#running-locally)
2. [Configuration](#configuration)
3. [Running with Docker](#running-with-docker)
4. [Screenshots](#screenshots)

## Installation

### Requirements

Ensure the following are installed on your system:

- **PostgreSQL**: This application uses PostgreSQL as its database.
- **Git**: For cloning the repository.
- **Maven**: For building the project.

### Running Locally

1. **Clone the repository:**

    ```shell
    git clone https://github.com/hoholms/online-wallet.git
    cd online-wallet
    ```

2. **Set up the configuration:**

   Configure
   the [application.yml](https://github.com/hoholms/online-wallet/blob/main/src/main/resources/application.yml) file by
   setting the necessary environment variables or replacing them with actual values:

    ```yaml
    # HOSTNAME
    hostname: ${HOSTNAME}

    # DATABASE CONNECTION
    spring:
      datasource:
        url: jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME}
        username: ${DB_USER}
        password: ${DB_PASSWORD}
        driverClassName: org.postgresql.Driver

      # CSRF
      freemarker:
        expose-request-attributes: true

      # HIBERNATE
      jpa:
        generate-ddl: false
        show-sql: false
        hibernate:
          ddl-auto: validate
      flyway:
        enabled: true

      # SPRING SESSION JDBC
      session:
        jdbc:
          initialize-schema: always
          table-name: SPRING_SESSION

      # SMTP MAIL
      mail:
        host: ${MAIL_HOST}
        username: ${MAIL_USERNAME}
        password: ${MAIL_PASSWORD}
        port: ${MAIL_PORT}
        protocol: smtps

    mail:
      debug: false
    ```

3. **Build the application:**

    ```shell
    mvn clean package
    ```

4. **Run the application:**

   Execute the following script in the project root folder:

    ```shell
    #!/usr/bin/env bash
    ver=$(mvn help:evaluate -Dexpression=project.version | grep -e '^[^\[]')
    cd target
    java -jar online-wallet-"${ver}".jar
    ```

## Configuration

### `application.yml`

The `application.yml` file requires configuration to connect to your database and other services. You can set these
values using environment variables or directly within the file.

- **Database Configuration:**
    - `DB_HOST`: Database host
    - `DB_PORT`: Database port
    - `DB_NAME`: Database name
    - `DB_USER`: Database username
    - `DB_PASSWORD`: Database password

- **Mail Configuration:**
    - `MAIL_HOST`: SMTP mail host
    - `MAIL_USERNAME`: Mail username
    - `MAIL_PASSWORD`: Mail password
    - `MAIL_PORT`: Mail port

## Running with Docker

To run `Online Wallet` using Docker, follow these steps:

1. **Build the Docker image:**

    ```shell
    mvn clean package jib:dockerBuild
    ```

2. **Set up [docker-compose.yml](https://github.com/hoholms/online-wallet/blob/main/docker-compose.yml):**

   Configure the `docker-compose.yml` file similarly to `application.yml`.

3. **Run Docker Compose:**

    ```shell
    docker compose -f docker-compose.yml -p online-wallet up -d
    ```

## Screenshots

### Dashboard

![Dashboard](https://user-images.githubusercontent.com/46485037/205487889-8d065b18-8d76-4a4f-a76c-faa0ee25a7bf.png)

### Statistics

![Statistics](https://user-images.githubusercontent.com/46485037/205487904-4b78290f-b541-4cfd-9e3f-db1d1af95524.png)

---

This README provides a comprehensive guide to setting up and running the Online Wallet application. If you encounter any
issues or have questions, please refer to the
repository's [issues section](https://github.com/hoholms/online-wallet/issues) for support.