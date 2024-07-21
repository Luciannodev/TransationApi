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
- `GET /transactions/{id}`: Retrieves a transaction by its ID.
- `DELETE /transactions/{id}`: Deletes a transaction by its ID.

### Business Rules

The API implements the following business rules:

- Transactions are mapped to a category based on their MCC code.
- If a transaction cannot be mapped to a category or if the balance in the mapped category is not sufficient, the transaction is processed against the CASH category.
- The merchant name can override the MCC code for mapping transactions to categories.

### Running the API

To run the API, use the following command:


### Running Tests



### Future Enhancements

