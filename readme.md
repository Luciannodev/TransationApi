# README

## Transaction Authorization API

This API is designed to handle credit card transactions. It provides endpoints for creating and processing transactions, and implements several business rules to determine whether a transaction should be approved or rejected.

### Technologies Used

- Java 21
- Spring Boot
- Spring Data JPA

### Database Schema

The database consists of the following tables:

- `category`: Stores the different categories of transactions.
- `merchant`: Stores information about the merchants.
- `account`: Stores information about the accounts.
- `account_category_balance`: Stores the balance for each category in each account.
- `transation`: Stores the transactions.

### API Endpoints

The API provides the following endpoints:

- `POST /transactions`: Creates a new transaction. DONE
- `GET /transactions/{id}`: Retrieves a transaction.DONE

### Business Rules

The API implements the following business rules:

- Transactions are mapped to a category based on their MCC code.
- If a transaction cannot be mapped to a category or if the balance in the mapped category is not sufficient, the transaction is processed against the CASH category.
- The merchant name can override the MCC code for mapping transactions to categories.

## Consuming the API

To consume the API you've created, you can follow these steps:

1. **Environment Setup and Installation**
   Ensure you have Java and Gradle installed on your system. You'll also need an HTTP client, such as Postman or cURL, to make requests to the API.

2. **Starting the Application**
   Navigate to the root directory of the project and run the command `./gradlew bootRun`. This will start the Spring Boot application on port 8080.

3. **Consuming the API**
   Now you can make HTTP requests to the API. Here are some examples of how you can do this:

    - **Creating a Transaction**
      To create a transaction, you can make a POST request to the `/transactions` endpoint. Here's an example request body:
     
    - **Request example** 
    - ```json
          {
          "account": "1",
          "totalAmount": 100,
          "mcc": "5811",
          "merchant": "PADARIA DO ZE               SAO PAULO BR"
          }
      ```
      You can make this request using Postman or cURL. Here's an example cURL command:
      ```bash
        curl -X POST http://localhost:8080/transactions -H "Content-Type: application/json" -d '{"account": "1", "totalAmount": 100, "mcc": "5811", "merchant": "PADARIA DO ZE               SAO PAULO BR"}'
      ```
    - **Response example**
    - ```json
           {
              "code": "00", 
              "message": "Transaction approved."
           }
      ```
    - **Retrieving a Transaction**
        To retrieve a transaction, you can make a GET request to the `/transactions/t` endpoint, where `{id}` is the ID of the transaction you want to retrieve. Here's an example cURL command:
        ```bash
            curl --request GET \
            --url 'http://localhost:8080/transactions' 
        ```
    - **Response example**
    - ```json
          [
           {
           "transactionCode": 1,
           "code": "00",
           "merchantCode": 2,
           "totalAmount": 100.0,
           "accountCode": 1,
           "mmc": 5411
           },
           {
           "transactionCode": 2,
           "code": "00",
           "merchantCode": 1,
           "totalAmount": 100.0,
           "accountCode": 1,
           "mmc": 5411
           },
           {
           "transactionCode": 3,
           "code": "00",
           "merchantCode": 2,
           "totalAmount": 100.0,
           "accountCode": 1,
           "mmc": 5811
           }
           ]
      ```
    - 
      

### Running Tests

1. **Unit Tests**
   To run the unit tests, navigate to the root directory of the project and run the command `./gradlew test`.
2. **Check Results**
   After running the tests, you can check the results in the console. You can also view the test reports in the `build/reports/tests/test` directory.
3. **Test Coverage**
   To check the test coverage, navigate to the root directory of the project and run the command `./gradlew test jacocoTestReport`. You can view the test coverage report in the `build/reports/jacoco/test/html` directory.



