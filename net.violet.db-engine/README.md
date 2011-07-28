# Content Handler

## Description

Low level database access classes ?

## Pre-requisites

For the test to succeed you'll need a local mysql server, with the correct DBs and users. The DBs can be empty apparently.

Create DBs:

```
CREATE DATABASE `bdCore`;
CREATE DATABASE `bdCoreTest`;
CREATE DATABASE `bdCoreRead`;
CREATE DATABASE `bdCoreWrite`;
CREATE DATABASE `ejabberdnabaztagcom`;
```

Create Users:

```
CREATE USER 'dev'@'localhost' IDENTIFIED BY '123';
CREATE USER 'ejabberdjava'@'localhost' IDENTIFIED BY '1b8d750b411a2a51640aef79ac6beeaa';

GRANT ALL PRIVILEGES ON bdCore.* TO 'dev'@'localhost';
GRANT ALL PRIVILEGES ON bdCoreRead.* TO 'dev'@'localhost';
GRANT ALL PRIVILEGES ON bdCoreWrite.* TO 'dev'@'localhost';
GRANT ALL PRIVILEGES ON bdCoreTest.* TO 'dev'@'localhost';
GRANT ALL PRIVILEGES ON ejabberdnabaztagcom.* TO 'ejabberdjava'@'localhost' identified by '1b8d750b411a2a51640aef79ac6beeaa';
```

## TODO

* Switch to an embedded database for the tests (HSQLDB ?)
* At least add setup / tear down steps to create what's necessary
* Check the commented tests
* Switch some constants to property files (usernames, passwords, etc.)
