# Maybank SpringBoot Demo Application

This is a SpringBoot application to demo CRUD operations, MSSQL database and third party calling.

## Tech Stack

**Java:** JDK 21

**Database:** MSSQL

## Run

To run the program

```bash
    mvn spring-boot:run
```

## API Reference

#### Create Person record

```http
  POST /api/v1/person/createPerson
```

| Parameter | Type     |
| :-------- | :------- | 
| `name` | `string` |
| `email` | `string` |
| `phoneNo` | `string` | 
| `city` | `string` |  

#### Get Person record

```http
  GET /api/v1/person/getPerson/${personId}
```

| Parameter | Type     |
| :-------- | :------- | 
| `personId`      | `string` | 

#### Update Person record

```http
  PUT /api/v1/person/updatePerson
```

| Parameter | Type     |
| :-------- | :------- | 
| `name` | `string` |
| `email` | `string` |
| `phoneNo` | `string` | 
| `city` | `string` |  
| `personId` | `int` |  

#### Create Transaction record by Person

```http
  POST /api/v1/person/createTransaction
```

| Parameter | Type     |
| :-------- | :------- | 
| `personId` | `int` |
| `amount` | `long` |

#### Retrieve Transaction records with pagination (Each page 10 entries)

```http
  GET /api/v1/person/getTransaction/${personId}/page/${pageNo}
```

| Parameter | Type     |
| :-------- | :------- | 
| `personId` | `int` |
| `pageNo` | `int` |

#### Retrieve user records by calling third party

```http
  GET /api/v1/person/retrieve3rdPartyUsers
```




