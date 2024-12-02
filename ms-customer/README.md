# MS-CUSTOMER

<p>Microsserviço responsável pelo gerenciamento dos clientes, assim como seus endereços e formas de pagamento.</p>

### Endpoints
#### Consulta de todos os clientes
<p>Endpoint responsável por retornar todos os clientes que já foram cadastrados.</p>

Rota: /api/customers </br>
Método: GET </br>


#### Consulta de um cliente específico
<p>Endpoint responsável por retornar um cliente específico.</p>

Rota: /api/customers/id/{id} </br>
Método: GET </br>
Path param: Id do cliente </br>

#### Consulta de um cliente específico por CPF
<p>Endpoint responsável por retornar um cliente específico por CPF.</p>

Rota: /api/customers/cpf/{cpf} </br>
Método: GET </br>
Path param: CPF do cliente </br>

#### Consulta de um endereço pelo CPF do cliente
<p>Endpoint responsável por retornar um endereço de acordo com o CPF do cliente.</p>

Rota: /api/customers/addressInfo/{cpf} </br>
Método: GET </br>
Path param: CPF do cliente </br>

#### Consulta de um modo de pagamento pelo CPF do cliente
<p>Endpoint responsável por retornar um modo de pagamento  de acordo com o CPF do cliente.</p>

Rota: /api/customers/paymentInfo/{cpf} </br>
Método: GET </br>
Path param: CPF do cliente </br>

#### Criação de um cliente novo
<p>Endpoint responsável por criar um cliente novo.</p>

Rota: /api/customers </br>
Método: POST </br>
Request body
```json
{
  "cpf": "51488248087",
  "name": "Jenna Coleman",
  "address": {
    "street": "Praça Monsenhor Renato Galvão",
    "number": "237",
    "complement": null,
    "district": "Centro",
    "city": "Feira de Santana",
    "state": "BA",
    "cep": "44002120"
  },
  "birthDate": "1986-04-27",
  "payment": {
    "cardNumber": "4716343709327531",
    "expirationDate": "2025-10-09",
    "cvvCode": "185"
  }
}
```

#### Atualização de um cliente
<p>Endpoint responsável por atualizar um cliente.</p>

Rota: /api/customers/{id} </br>
Método: PUT </br>
Path param: Id do cliente
Request body
```json
{
  "cpf": "51488248087",
  "name": "Jenna",
  "address": {
    "street": "Praça",
    "number": "237",
    "complement": null,
    "district": "Centro",
    "city": "Feira de Santana",
    "state": "BA",
    "cep": "44002120"
  },
  "birthDate": "1986-04-27",
  "payment": {
    "cardNumber": "4716343709327531",
    "expirationDate": "2025-10-09",
    "cvvCode": "185"
  }
}

```

#### Exclusão de um cliente
<p>Endpoint responsável por deletar um cliente.</p>

Rota: /api/customers/{id} </br>
Método: DELETE </br>
Path param: Id do cliente

