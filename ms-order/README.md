# MS-ORDER

<p>Microsserviço responsável pelo gerenciamento dos pedidos, desde a criação, consulta, atualizações de status até a finalização de um pedido.</p>

### Endpoints
#### Consulta de todos os pedidos
<p>Endpoint responsável por retornar todos os pedidos que já foram realizados.</p>

Rota: /api/orders </br>
Método: GET </br>

#### Consulta de todos os pedidos de um cliente
<p>Endpoint responsável por retornar todos os pedidos de um cliente específico.</p>

Rota: /api/orders/customers/{cpf} </br>
Método: GET </br>
Path param: CPF do cliente </br>

#### Consulta de um pedido específico
<p>Endpoint responsável por retornar um pedido específico.</p>

Rota: /api/orders/{orderId} </br>
Método: GET </br>
Path param: Id do pedido </br>

#### Criação de um pedido novo
<p>Endpoint responsável por criar um pedido novo.</p>

Rota: /api/orders </br>
Método: POST </br>
Request body
```json
{
    "cpf": "46799709013",
    "products": [
        {
            "sku": 20,
            "quantity": 1,
            "unitaryValue": 380.74
        }
    ]
}
```

Regras:
- Busca as informações do cliente no microsserviço de customer
- Valida se o cliente informado existe
- Busca as informações de cada produto no microsserviço de product
- Valida se existe o estoque de cada produto enviado
- Valida se o valor do produto informado é válido
- Realiza a diminuição do estoque no microsserviço de product
- Busca o valor do frete para o CEP do endereço do cliente no microsserviço de shipping
- Cria o pedido na base
- Envia o pedido para o tópico de pedidos criados

#### Atualiza o pedido para o status "EM ROTA DE ENTREGA"
<p>Endpoint responsável por atualizar um pedido para o status de em rota de entrega.</p>

Rota: /api/orders/{orderId}/shipping </br>
Método: PUT </br>
Path param: Id do pedido </br>

Regras:
- Busca o pedido na base
- Valida se o pedido está no status "AGUARDANDO_ENVIO"
- Atualiza o status do pedido na base

#### Finaliza o pedido
<p>Endpoint responsável por gerar a data de finalização do pedido e atualizar o status do pedido para finalizado.</p>

Rota: /api/orders/{orderId}/finish </br>
Método: PUT </br>
Path param: Id do pedido </br>

Regras:
- Busca o pedido na base
- Valida se o pedido está no status "EM_ROTA_DE_ENTREGA"
- Gera data de finalização do pedido
- Atualiza o status do pedido na base

### Consumidores (Kafka)
#### Consumidor para pedidos criados
<p>Consome o tópico que armazena os pedidos recém-criados.</p>

Tópico: created-order-topic

Regras:
- Valida se o novo pedido é válido para seguir as próximas etapas
- Atualiza o status do pedido para "PENDENTE_PAGAMENTO"
- Envia o pedido para o tópico de pedidos pendentes de pagamento

#### Consumidor para pedidos pendente de pagamento
<p>Consome o tópico que armazena os pedidos válidos que estão pendentes de pagamento.</p>

Tópico: pending-payment-topic

Regras:
- Valida se o status do pedido é o correto para a etapa (PENDENTE_PAGAMENTO)
- Busca as informações de pagamento do cliente no microsserviço de customer
- Realiza o processamento do pagamento a partir do serviço de payment
- Se o pagamento foi aprovado:
  - Atualiza o pedido para o status "PROCESSADO"
  - Atualiza o status do pedido na base
  - Envia o pedido para o tópico de pedidos processados
- Se o pagamento não foi aprovado:
  - Retoma o estoque dos itens no microsserviço de product
  - Atualiza o pedido para o status "CANCELADO"
  - Atualiza o status do pedido na base

#### Consumidor para pedidos processados
<p>Consome o tópico que armazena os pedidos que já foram aprovados.</p>

Tópico: processed-order-topic

Regras:
- Valida se o status do pedido é o correto para a etapa (PROCESSADO)
- Busca as informações do cliente no microsserviço de customer
- Cria o pedido no microsserviço de shipping para iniciar a etapa da entrega
- Atualiza o pedido para o status "AGUARDANDO_ENVIO"
- Atualiza o status do pedido na base

### Fluxos
#### Fluxo da geração de um pedido até a finalização
- Cliente envia as informações do pedido a ser criado
- Pedido é validado
- Pedido é postado no tópico de pedidos criados
- Pedido passa por uma validação mais complexa
- Pedido é postado no tópico de pedidos aguardando pagamento
- Pagamento do pedido é realizado
- Pedido é postado no tópico de pedidos processados
- Pedido é criado no serviço responsável pela entrega
- Pedido é enviado (regra dentro do serviço de shipping)
- Pedido é entregue (regra dentro do serviço de shipping)
- Pedido é finalizado