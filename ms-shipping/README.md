#MS-SHIPPING
------------------------------------------------------------------------------------------
<p>Micro Serviço Responsável por gerar as entregas e vincular os entregadores, bem como cálcular frete.</p>

## Courier - Entregador

Get  -> http://localhost:8090/api/courier
Busca todos os entregadores cadastrados

Get  -> http://localhost:8090/api/courier/{id}
Busca o entregador do id informado

Post -> http://localhost:8090/api/courier
Grava o registro do entregador na base (Campos: name, status, phone e region)

Put -> http://localhost:8090/api/updatestatus/{id}
Atualiza o status do carregar do id informado

Get -> http://localhost:8090/api/delivery-courier/{id}
Busca todas as entregas para o entregador do id informado

## Delivery - Entrega

Get  -> http://localhost:8090/api/delivery/order/{orderId}
Busca as entregas com o orderid informado

Post  -> http://localhost:8090/api/deliveries
Cria uma nova entrega para com os dados passados no RequestBody(orderId e postalCode)

## Shipping - Envio

Get  -> http://localhost:8090/api/shippings/{cep}
Busca informações de envio para o cep informado(daysToDelivery, deliveryPrice e postalCode)

## Batch – Job que é executado a cada minuto de forma automática.

### DeliveryMonitorBatch
- Busca todas as entregas que tenham entregador já vinculados, que estejam com status ON_DELIVERY_ROUTE e que tenham latitude e longitude com valores entre 0 e 10;
- Atualiza a latitude e a longitude com mais 1 para cada até chegar a 10 (simula atualizada de rota de entrega).
- Ao chegar em 10 na latitude e longitude, muda o status da entrega para DELIVERED, muda o status do entregador para AVAILABLE(se não estiver com mais nenhuma outra entrega em rota) e chama o serviço de pedido (http://localhost:8091/api/{orderId}/finish) para finalizar o pedido.

### UpdateDeliveryBatch
- Busca todos as entregas por região e depois busca todos os entregadores que tem com status AVAILABLE para cada região, se encontrar entregador disponível, atualiza o id do entregador na entrega, muda o status da entrega e do entregador para ON_DELIVERY_ROUTE e por último chama o serviço de pedido  (http://localhost:8091/api/{orderId}/shipping) para atualizar o pedido para em rota de entrega. 

