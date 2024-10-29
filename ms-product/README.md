Catálogo de Produtos (Lobao)
NOME DA APP: ms-product
	- CRUD de produto
	- Endpoint para controle de estoque (1 pra adicionar / 1 para subtrair)
	- Base pode ser relacional ou noSQL
	- Spring JPA
	- Batch para importação de produtos por um arquivo (CSV ou JSON)

	Produto
		- ID
		- SKU Id (Deve ser ÚNICO)
		- Nome
		- Descricao
		- Preco
		- Qtd em estoque

Observações para o job
Job deve conter essa estrutura de pastas:
-> Pendente: Arquivos que necessitam execucao
-> Executados: Arquivos já executados
Obs: O processo deve executar os arquivos que estejam na pasta pendente e ao finalizar,
mover para a pasta de executados para nao duplicar os dados
