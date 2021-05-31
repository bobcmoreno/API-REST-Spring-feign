# API-REST-Spring-feign
Aplicação para controlar veículos de usuários, usando Java com Spring Tools + JPA Hibernate + Spring Cloud Feign, PostgreSQL como Banco de Dados, e Postman para fazer os testes.
Para criar essa aplicação foram executadas as seguintes etapas:

1 – Criação de um projeto no Spring Tools

2 – Estabelecer a conexão do projeto ao gerenciador de banco de dados PostgreSQL

3 – Inclusão de dependências necessárias

4 – Criação das classes (Modelos de dados para Usuários x Veículos).

5 – Criação das interfaces que farão a ligação das nossas classes com o bando de dados

6 – Criação da classe de controle para execução dos processos da nossa aplicação

7 – Realização de testes de funcionamento com o Postman

8 - Atualização do repositório no GitHUB

- Não foi utilizado lombook
- Foi implementada uma chamada de um serviço externos API FIPE para obter o valor do veículo, usando o Spring Cloud Feign.
- No mapeamento das classes foi implementado relacionamento @OneToMany da tabela de Usuários com a tabela de veículos
- Foram considerados atributos obrigatórios e únicos
- Foram criados 3 Endpoints:
  - Listagem de Usários x Veiculos
  - Endopoint Usuários ( Inclusão, Consulta, Alteração e Exclusão com delete cascade ( exclui todos os veículos do usuário que está sendo excluído )
  - Endopoint Veículos ( Inclusão, Consulta, Alteração e Exclusão )
