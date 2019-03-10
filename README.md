# Transfer money RESTful API
A Java RESTful API for retrieve balance and make transaction

## How to run
`./mvnw spring-boot:run`

## Unit test
Run TransferApplicationTests

## Available Services
1. Use GET method
`http://localhost:8080/accounts`
for get all accounts
### response:
{
  "totalAccounts": 3,
  "accounts": [
    {
      "id": "1",
      "name": "USD Account",
      "herf": "/accounts/1"
    },
    {
      "id": "2",
      "name": "HKD Account",
      "herf": "/accounts/2"
    },
    {
      "id": "3",
      "name": "HKD Account",
      "herf": "/accounts/3"
    }
}

2. Use GET method
`http://localhost:8080/accounts/1`
for get accounts/1 detail
### response:
{
  "id": "1",
  "accountName": "USD Account",
  "balance": {
    "amount": 1000,
    "currencyCode": "USD"
  }
}

3. Use POST method
`http://localhost:8080/transaction`
for make transfer
### request:
{
  "currencyCode": "HKD",
  "amount": 100,
  "fromAccountId": "2",
  "toAccountId": "3"
}

### response:
{
  "code": 100,
  "message": "Success",
  "content": [
    {
      "balance": {
        "amount": 900,
        "currencyCode": "HKD"
      },
      "accountName": "HKD Account",
      "id": "2"
    },
    {
      "balance": {
        "amount": 1100,
        "currencyCode": "HKD"
      },
      "accountName": "HKD Account",
      "id": "3"
    }
  ]
}

## API document
http://localhost:8080/swagger-ui.html

## 3rd party library used
com.alibaba.fastjson-1.2.56
