#MS-SHIPPING
------------------------------------------------------------------------------------------
<p>Micro Serviço Responsável por gerar as entregas e vincular os entregadores, bem como cálcular frete.</p>

## Courier - Entregador

### Buscar todos os entregadores
<p>Endpoint responsável por busca todos os entregadores cadastrados</p>

Endereço: /api/courier <br>
Método: GET <br>

### Buscar entregador informado
<p>Endpoint responsável por buscar o entregador do id informado</p>

Endereço: /api/courier/{id} <br>
Método: GET <br>

### Gravar novo entregador 
<p>Endpoint responsável por gravar o registro do entregador na base</p>

Endereço: /api/courier <br>
Método: POST <br>
Request Body<br>
```json
{
    "name": "Cristina",
    "phone": "01198552121",
    "region": "SUL",
    "status": "AVAILABLE" 
}
```
### Atualizar Status
<p>Endpoint responsável por atualizar o status do entregador do id informado</p>

Endereço: /api/courier/updatestatus/{id} <br>
Método: PUT <br>

### Buscar Entregas
<p>Endpoint responsável por buscar todas as entregas para o entregador do id informado</p>

Endereço: /api/delivery-courier/{id} <br>
Método: GET <br>


## Delivery - Entrega

### Buscar entrega
<p>Endpoint responsável por buscar as entregas com o orderid informado</p>

Endereço: /api/delivery/order/{orderId} <br>
Método: GET <br>

### Criar entrega
<p>Endpoint responsável por Cria uma nova entrega para com os dados passados no RequestBody</p>

Endereço: /api/deliveries <br>
Método: POST <br>
```json
{
    "orderId": "1",
    "postalCode": "01510000"
}
```

## Shipping - Envio

### Buscar cep
<p>Endpoint responsável por buscar informações de envio para o cep informado</p>

Endereço: /api/api/shippings/{cep} <br>
Método: GET <br>


## Batch – Job que é executado a cada minuto de forma automática.

### DeliveryMonitorBatch

- Busca todas as entregas que tenham entregador já vinculados, que estejam com status ON_DELIVERY_ROUTE e que tenham latitude e longitude com valores entre 0 e 10;
- Atualiza a latitude e a longitude com mais 1 para cada até chegar a 10 (simula atualizada de rota de entrega).
- Ao chegar em 10 na latitude e longitude, muda o status da entrega para DELIVERED, muda o status do entregador para AVAILABLE(se não estiver com mais nenhuma outra entrega em rota) e chama o serviço de pedido (http://localhost:8091/api/{orderId}/finish) para finalizar o pedido.

### UpdateDeliveryBatch
- Busca todos as entregas por região e depois busca todos os entregadores que tem com status AVAILABLE para cada região, se encontrar entregador disponível, atualiza o id do entregador na entrega, muda o status da entrega e do entregador para ON_DELIVERY_ROUTE e por último chama o serviço de pedido  (http://localhost:8091/api/{orderId}/shipping) para atualizar o pedido para em rota de entrega. 

