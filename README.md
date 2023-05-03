# Online Wallet
Simple user friendly web application to control your finances.

Made on [Bootstrap 5.2](https://getbootstrap.com/) and [Spring Boot](https://spring.io/)

By [hoholms](https://github.com/hoholms) and [VetrovMilka](https://github.com/VetrovMilka)

## Demo
You can try [Online Wallet](https://onlinewallet.dubrovschii.com) live demo

## Installation
### Requirements
This application using **postgresql** database. Install it.

Also, you need to install **git** and **maven**.
### Installation
Clone repository:
```bash
git clone https://github.com/hoholms/online-wallet.git
```

To get this application working you shoud set up ```application.properties``` file:

```properties
# HOSTNAME
hostname=YOUR_HOSTNAME
# DATABASE CONNECT
spring.datasource.url=jdbc:postgresql://YOUR_DB_URL:5432/online_wallet
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD
# CSRF
spring.freemarker.expose-request-attributes=true
# HIBERNATE
spring.jpa.generate-ddl=false
spring.jpa.show-sql=false
spring.jpa.hibernate.ddl-auto=validate
spring.flyway.enabled=true
# SMTP MAIL
spring.mail.host=YOUR_MAIL_HOST
spring.mail.username=YOUR_EMAIL
spring.mail.password=YOUR_EMAIL_PASSWORD
spring.mail.port=YOUR_MAIL_PORT
spring.mail.protocol=smtps
mail.debug=false
# SPRING SESSION JDBC
spring.session.jdbc.initialize-schema=always
spring.session.jdbc.table-name=SPRING_SESSION
```

After setting up ```application.properties``` you can run application, or build artifacts with:
```bash
mvn clean package
```
And then run artifact executing this script in project root folder:
```bash
#!/usr/bin/env bash
ver=$(mvn help:evaluate -Dexpression=project.version | grep -e '^[^\[]')
cd target
java -jar online-wallet-"${ver}".jar
```
## Screenshots
### Dashboard
<img width="1452" alt="dashboard" src="https://user-images.githubusercontent.com/46485037/205487889-8d065b18-8d76-4a4f-a76c-faa0ee25a7bf.png">

### Statistics
<img width="1452" alt="statistics" src="https://user-images.githubusercontent.com/46485037/205487904-4b78290f-b541-4cfd-9e3f-db1d1af95524.png">
