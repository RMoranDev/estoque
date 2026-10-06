# Inventory API

API REST para gerenciamento de estoque, desenvolvida para centralizar o controle de produtos, categorias e movimentações de estoque.

O projeto está em **desenvolvimento** e tem como objetivo aplicar conceitos de Java, Spring Boot, persistência de dados e desenvolvimento de APIs REST em uma aplicação prática.

## 🚀 Tecnologias

- Java 21
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- Bean Validation
- Lombok
- Git e GitHub

## 📦 Funcionalidades

Atualmente, o projeto possui funcionalidades relacionadas a:

- Cadastro e gerenciamento de produtos
- Cadastro e gerenciamento de categorias
- Controle de quantidade em estoque
- Registro de entradas e saídas de estoque
- Histórico de movimentações
- Cadastro de usuários
- Validação de dados
- Regras básicas de negócio
- Controle de concorrência com `@Version`

As movimentações de estoque são representadas pelos tipos `ENTRADA` e `SAIDA`, permitindo registrar alterações na quantidade dos produtos.

## 🏗️ Estrutura

O backend segue uma organização em camadas, separando responsabilidades entre:

```text
controller
service
repository
model
 ├── entities
 ├── dto
 └── enums
config
exception
```

A camada de serviço concentra regras de negócio, enquanto os repositories utilizam Spring Data JPA para acesso aos dados.

## 🔄 Modelo do sistema

Principais entidades:

```text
User
  │
  └── InventoryTransaction
           │
           └── Product
                  │
                  └── Category
```

Produtos possuem categoria, quantidade atual e quantidade mínima para controle do estoque. 

As movimentações relacionam produto e usuário, registrando o tipo, quantidade, observações e data da operação. 

## 🎯 Objetivo

O objetivo do Inventory API é fornecer uma base backend organizada para um sistema de gerenciamento de estoque, permitindo que aplicações web ou mobile possam consumir seus recursos através de uma API REST.


## 🚧 Status

**Em desenvolvimento.**

Novas funcionalidades, melhorias e testes serão adicionados conforme o projeto evolui.

## 👨‍💻 Autor

**Ricardo Moran**
Desenvolvedor Java.
