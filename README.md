Boa noite, estou entregando a solução proposta, conforme o combinado seria até dia 11/06.
no dia 11/06 tenho algumas pendências no trabalho, e não poderei trabalhar nesse projeto em paralelo. Então estou entregando o que consegui implementar
até aqui, apesar de estar incompleto, acredito que dê para entender a solução.

Foram criadas duas APIs:.

api-product;
api-sale;

A api-product é responsável pelo CRUD de produtos, utilizando o padrão MVC, então temos um DTOInput 
para que o cliente informe os dados do produto, temos a entidade Produto que mapeia o Produto do banco, e temos um DTO para exibição desses dados de retorno. Temos o Service, responsável pela lógica
de cada método e chamada do repositório , que por sua vez extende do JPARepository, assim facilitando operações CRUD e consultas.
Ao levantar essa API ela estará rodando na porta 8080.

Temos a api-sale(porta 8585), responsável pela Venda em si, e a consulta dessas vendas.
Através dela, podemos realizar a venda dos produtos, utilizando os produtos adicionados na api-product, fazendo essa conexão entre APIs através do webClient.
As operações implementadas ao realizar a venda foram:

Um Service específico para Realização de Venda, para que as entidades continuem com o princípio da Responsabilidade Única;
Quantidade de estoque do produto sendo diminuida quando cada venda é realizada;
Cálculo total por venda e por produtos de venda;

Também criei uma ApiExceptionHandler para cada api, para melhor tratamento e retorno de mensagens de erro das possíveis exceções;

Mais tecnologias utilizadas:
Model Mapper para fazer a corrspondência entre os DTOs e as entidades;
O banco de dados e controle de versionamento está sendo feito através do Flyway, assim criando a base de dados, e as tabelas automaticamente ao subir o projeto, sem precisar desse controle manualmente(banco de dados utilizado foi o mysql-workbench);
Documentação da api através do Swagger API, facilitando o entedimento e rotas das APIs;
Teste unitário do produtoController na api-product, utilizando JUnit5 e Mockito. Não consegui implementar os demais testes, 
mas a ideia era construir além do ProdutoControllerTest, também o ProdutoRepositoryTest, e o ProdutoServiceTest, para testar apis em sua totalidade. Além de claro, testes do api-sale;
Utilização do javax.validation para validação da entrada de dados informados;
Utilização do Lombok para evitar códigos boiler plate;
Utilização do github para versionamento;

Este projeto será mudado para modo privado no Github dia 25/05/2020 


