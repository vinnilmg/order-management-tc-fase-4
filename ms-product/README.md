
# Catalógo de produtos(MS-PRODUCT)

Este projeto implementa um sistema para o processamento de arquivos CSV em um ambiente de e-commerce, que gerencia a importação de dados de produtos. Ele usa uma arquitetura baseada em microserviços com Spring Boot, que lida com a movimentação de arquivos e o processamento de dados de produtos para preenchimento no banco de dados.

A seguir, estão as regras de negócio e a explicação dos endpoints principais.

## Regras de Negócio

1. **Diretórios de Arquivos**:
	- **`pending`**: Contém arquivos CSV que ainda não foram processados..
	- **`waiting`**: Após a detecção do(s) arquivo(s) CSV na pasta `pending`, o arquivo é movido para `waiting`, onde ele aguardará para ser processado.
	- **`finished`**: Quando o arquivo CSV é processado, ele é movido para a pasta `finished`, e os dados são inseridos no banco de dados.

2. **Processamento dos Arquivos**:
	- Um *scheduler* verifica periodicamente a pasta `pending` e move os arquivos para a pasta `waiting`.
	- O endpoint de processamento (`/api/batch/process`) é acionado para realizar o carregamento dos arquivos CSV no banco de dados. Após o processamento, os arquivos são movidos para a pasta `finished`.

3. **Entidades e Estruturas**:
	- **`CsvLoader`**: Representa o arquivo CSV a ser processado, contendo informações como o nome do arquivo e o status do processamento (pending, waiting, finished).
	- **`ApiResponse`**: Usado para padronizar as respostas em geral da API.
	- **`Product`**: A entidade que será populada no banco de dados a partir dos dados presentes no arquivo CSV.
	- **`LogError`**: A entidade que representa erros de processamento do batch ou de algum fluxo específico da aplicação.

## Estrutura de Diretórios

- **`/api/files/pending`**: Endpoint para recuperar a lista de arquivos CSV pendentes de processamento.
- **`/api/batch/process`**: Endpoint para iniciar o processamento dos arquivos CSV e inserir os dados no banco de dados.

## Endpoints de Gerenciamento de Produtos

1. **`PUT /api/products/{skuId}/decrease-stock`**
	- **Descrição**: Reduz a quantidade de estoque disponível para um produto com base no `skuId`.
	- **Parâmetros**:
		- **`skuId`** (PathVariable): Identificador único do produto no estoque.
		- **`productQuantityDTO`** (RequestBody): Objeto contendo a quantidade a ser subtraída do estoque.

2. **`PUT /api/products/{skuId}/additional-stock`**
	- **Descrição**: Adiciona uma quantidade ao estoque disponível de um produto com base no `skuId`.
	- **Parâmetros**:
		- **`skuId`** (PathVariable): Identificador único do produto no estoque.
		- **`productQuantityDTO`** (RequestBody): Objeto contendo a quantidade a ser adicionada ao estoque.

3. **`GET /api/products/{id}`**
	- **Descrição**: Busca os detalhes de um produto com base no seu `id`.
	- **Parâmetros**:
		- **`id`** (PathVariable): Identificador único do produto no banco de dados.

4. **`GET /api/products/skuId/{skuId}`**
	- **Descrição**: Busca os detalhes de um produto com base no seu `skuId`.
	- **Parâmetros**:
		- **`skuId`** (PathVariable): Identificador único do produto no estoque.



## Fluxo de Processamento

1. **Detecção e Movimentação de Arquivos**:
	- O *scheduler* verifica periodicamente o diretório `pending` em busca de novos arquivos CSV. Quando encontra um arquivo, ele é movido para o diretório `waiting` para aguardar o processamento.

2. **Processamento de Arquivos**:
	- Ao acionar o endpoint `/api/batch/process`, o sistema começa o processamento dos arquivos na pasta `waiting`. Cada arquivo é lido e seus dados são inseridos no banco de dados.
	- Após o processamento bem-sucedido, o arquivo é movido para o diretório `finished`, indicando que ele foi completamente processado.

## Tecnologias Utilizadas

- **Spring Boot**: Framework para construir a aplicação RESTful.
- **Spring Scheduler**: Usado para automatizar a detecção de novos arquivos e movê-los entre os diretórios.
- **Spring Batch**: Processar dados dentro dos arquivos CSV(s).
- **JPA/Hibernate**: Para a persistência de dados no banco de dados.
- **Docker**: Para ambientes de desenvolvimento e testes.
- **PostgreSQL**: Banco de dados utilizado para armazenar os dados de produtos.

## Configurações

As configurações dos diretórios de arquivos pendentes, esperando e finalizados devem ser definidas no arquivo `application.properties` ou `application.yaml`.

Exemplo de configuração no `application.yaml`:

```properties
##Configuração Local
app:
file-directory:
pending: ms-product/data/imports/pending
waiting: ms-product/data/imports/waiting
finished: ms-product/data/imports/finished
```
```properties
##Configuração Prod
app:
file-directory:
pending: /app/data/imports/pending
waiting: /app/data/imports/waiting
finished: /app/data/imports/finished
```
```properties
##Configuração Prod
app:
file-directory:
pending: /app/data/imports/pending
waiting: /app/data/imports/waiting
finished: /app/data/imports/finished
```


