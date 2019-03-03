# wirecard-challenge

## Estrutura do projeto

Os passos para obter o projeto em execução são os seguintes:

```
$ git clone https://github.com/I-am-Miguel/wirecard-challenge.git wirecard_miguel
$ cd wirecard_miguel
$ ls
build.gradle  gradle  gradlew  gradlew.bat  settings.gradle  src
$ gradle build
```
Após executar _gradle build_ todos os artefatos serão compilados, testados e empacotados para distribuição.

```
$ gradle bootRun
```
Após executar _gradle bootRun_ o projeto será executado e o ambiente de testes será inicializado.

### Clientes

Após construir o projeto, podemos obter as informações de clientes através da seguinte uri:

```
$ curl -X GET "http://localhost:8080/clients" -H "accept: application/json" 
=========================
Output: 
[
  {
    "id": 1
  }
...]

```
Neste exemplo, temos os seguintes parâmetros informados:
```
id: Id do Client, usuário pré-definido utilizado para o registro das requisições conseguinte
```

### Pagamentos

Após construir o projeto, podemos obter as informações de pagamentos através da seguinte uri:

#### Listagem de Pagamentos

```
$ curl -X GET "http://localhost:8080/payments" -H "accept: application/json"
=========================
Output: 
[
  {
    "id": 40,
    "amount": 100,
    "type": "CREDIT_CARD",
    "status": "APPROVED",
    "client": {
      "id": 1
    },
    "buyer": {
      "name": "Name Buyer",
      "email": "email@email.com",
      "cpf": "51170898041"
    },
    "card": {
      "id": 39,
      "holderName": "Welligton Miguel",
      "number": "4929340466625068",
      "expirationDate": "2019-04-02",
      "cvv": "12"
    },
    "boleto": null
  },
...]
```
#### Cadastro de Pagamentos(BOLETO)
```
$ curl -X POST "http://localhost:8080/payments" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"amount\": 1, \"buyer\": { \"cpf\": \"32014080003\", \"email\": \"email@email.com\", \"name\": \"Miguel\" }, \"client\": { \"id\": 1 }, \"type\": \"BOLETO\"}"
=========================
input:
{
  "amount": 100.00,
  "buyer": {
    "cpf": "32014080003",
    "email": "email@email.com",
    "name": "Miguel"
  },
  "client": {
    "id": 1
  },
  "type": "BOLETO"
}
```
#### Cadastro de Pagamentos(CREDIT_CARD)
```
$ curl -X POST "http://localhost:8080/payments" -H "accept: application/json" -H "Content-Type: application/json" -d "{\"client\": {\"id\": 1},\"card\": {\t\"holderName\": \"Welligton Miguel\",\t\"number\": \"5477239667148925\",\t\"expirationDate\": \"2020-12-29\",\t\"cvv\": 793},\"buyer\": {\"name\": \"Miguel\",\"email\": \"email@gmail.com\",\"cpf\": \"82613833009\"},\"amount\": 1000.58,\"type\": \"CREDIT_CARD\"}"
=========================
input:
{
"client": {
  "id": 1
},
"card": {
	"holderName": "Welligton Miguel",
	"number": "5477239667148925",
	"expirationDate": "2020-12-29",
	"cvv": 793
},
"buyer": {
  "name": "Miguel",
  "email": "email@gmail.com",
  "cpf": "82613833009"
},
"amount": 1000.58,
"type": "CREDIT_CARD"
}
```

O projeto foi realizado utilizando as seguintes tecnologias:

* Spring Boot 2.1
* Java 8
* H2 DataBase
* Lombok

Desenvolvimento:
* Bean Validation(BindingResult)
* ResponseStatusException
* Exception Handling

Documentação:
* Swagger 2
