# MS-PAYMENT-PROCESSOR

Microsserviço responsável por simular a aprovação de um pagamento.

### Endpoints
#### Processamento de pagamento
<p>Endpoint responsável por simular o processamento de um pagamento a partir dos dados de cartão de crédito do cliente.</p>

Rota: /api/payment/processor </br>
Método: POST </br>
Request body
```json
{
    "cardNumber": "5538360554254365",
    "expirationDate": "30/09/2026",
    "code": "123"
}
```

Regras:
- Caso o código do cartão (CVV) finalize com dígito diferente de 9 o retorno será de pagamento aprovado
- Caso o código do cartão (CVV) finalize com dígito igual a 9 o retorno será de pagamento não aprovado

Obs: Esse endpoint foi criado para apenas simular um pagamento e para exemplificar os dois cenário (aprovado e não aprovado) a regra acima foi definida.
