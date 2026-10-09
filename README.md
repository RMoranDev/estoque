# Inventory API

API REST para gerenciamento de estoque. O projeto organiza o cadastro de usuários e produtos, incluindo validações de dados, associação de produtos a categorias e controle de quantidade.

Construída com Spring Boot, a aplicação separa a entrada HTTP, as regras de negócio e a persistência para manter o código simples de evoluir e testar.

## Tecnologias

- Java 21
- Spring Boot 4.1.1
- Spring Web MVC
- Spring Data JPA / Hibernate
- MySQL
- Maven e Maven Wrapper
- Bean Validation
- Lombok
- Spring Security Crypto (BCrypt)
- JUnit 5 (testes)

## Como funciona

As requisições chegam aos controllers REST de usuários e produtos. Eles validam os dados recebidos e encaminham o processamento para a camada de serviço.

Os services aplicam as regras de negócio — como impedir e-mails e SKUs duplicados, validar a categoria do produto e criptografar senhas com BCrypt. Em seguida, os repositories, baseados em Spring Data JPA, persistem e consultam as entidades no MySQL. As respostas da API usam DTOs para expor somente os dados necessários.

Também há serviços para categorias e movimentações de estoque. A movimentação registra entradas e saídas, impede saída maior que a quantidade disponível e mantém o histórico por produto. Esses recursos ainda não possuem controllers REST no projeto atual.

## Estrutura do projeto

```text
src/main/java/com/inventory/api
├── config        → configurações, incluindo o codificador de senha
├── controller    → endpoints REST de usuários e produtos
├── model
│   ├── dto       → objetos de entrada e saída da API
│   ├── entities  → entidades JPA: User, Product, Category e InventoryTransaction
│   └── enums     → tipos de movimentação de estoque
├── repository    → acesso a dados com Spring Data JPA
└── service       → regras de negócio e transações
```

## Como executar

### Pré-requisitos

- Java 21
- MySQL em execução
- Git

O projeto inclui Maven Wrapper, portanto não é necessário instalar o Maven separadamente.

### 1. Clone o projeto

```bash
git clone https://github.com/RMoranDev/inventory-api.git
cd inventory-api
```

### 2. Configure o banco de dados

Crie um banco MySQL chamado `inventory`, ou mantenha `createDatabaseIfNotExist=true` na URL de conexão para que ele seja criado automaticamente pelo MySQL.

Abra `src/main/resources/application.properties` e defina as credenciais locais nas propriedades abaixo.

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/inventory?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
```

As tabelas são atualizadas automaticamente na inicialização pela configuração `spring.jpa.hibernate.ddl-auto=update`.

### 3. Execute a aplicação

No Windows:

```bash
.\mvnw.cmd spring-boot:run
```

No macOS ou Linux:

```bash
./mvnw spring-boot:run
```

Como alternativa, execute a classe `Application` pela sua IDE. Para verificar a compilação e os testes:

```bash
.\mvnw.cmd test
```

## Funcionalidades

- Cadastro, consulta, atualização, ativação e desativação de usuários
- Cadastro, consulta e atualização de produtos
- Validação de campos de entrada com Bean Validation
- Prevenção de e-mails, nomes de categoria e SKUs duplicados
- Associação de produtos a categorias
- Senhas armazenadas com hash BCrypt
- Regras de entrada e saída de estoque e histórico de movimentações na camada de serviço
- Controle otimista de concorrência em produtos com `@Version`

## O que este projeto demonstra

- Desenvolvimento de API REST com Spring Boot
- Organização em camadas e separação de responsabilidades
- Persistência relacional com JPA/Hibernate e MySQL
- DTOs, validação de requisições e regras de negócio transacionais
- Tratamento de erros HTTP com `ResponseStatusException`
- Boas práticas básicas de segurança para senhas

## Observações

- Os endpoints REST implementados atualmente são `/users` e `/products`.
- Embora o projeto contenha `CategoryService` e `InventoryService`, ainda não há controllers para expor categorias e movimentações pela API.
- O arquivo `application.properties` existente contém uma senha de banco. Substitua-a por uma credencial local.
