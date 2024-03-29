# Loja Virtual - Transição para Microsserviços

## Sobre o Projeto

Este repositório contém um projeto de estudo em desenvolvimento que simula uma loja virtual(ainda em desenvolvimento). Inicialmente construído como uma aplicação monolítica, e atualmente em transição para uma arquitetura baseada em microsserviços, utiliza o ecossistema Spring, Node(next.js), Kafka para mensageria, e bancos de dados PostgreSQL e MongoDB.

## Objetivo do Projeto

O principal objetivo deste projeto é proporcionar um ambiente de aprendizado prático para a implementação e compreensão dos conceitos de microsserviços, bem como a integração e uso de diferentes tecnologias e ferramentas, como Spring Boot, Spring Cloud, Node, Kafka, PostgreSQL, e MongoDB.

## Estrutura Atual do Projeto

O projeto está estruturado da seguinte forma:

Cada microsserviço ira conter sua base de bados individual e ira se comunicar com os outros microsserviços atraves de mensageria kafka.

## Estrutura inicial dos microsserviços*

# Arquitetura de Microserviços

A seguinte arquitetura de microserviços foi projetada para otimizar a eficiência, escalabilidade e robustez do sistema de e-commerce. Abaixo estão os detalhes dos microserviços individuais, incluindo suas responsabilidades e as tecnologias utilizadas.

## Microserviços

# Arquitetura de Microserviços

A seguinte arquitetura de microserviços foi projetada para otimizar a eficiência, escalabilidade e robustez do sistema de e-commerce. Abaixo estão os detalhes dos microserviços individuais, incluindo suas responsabilidades, tecnologias utilizadas e nomes.

## Microserviços

### Serviço de Usuários/Autenticação e Autorização
- **Função**: Gerenciamento de informações de usuários e autenticação e autorização dos usuários na aplicação.
- **Tecnologia**: Spring, JPA, PostgreSQL.
- **Descrição**: Implementado com o ecossistema Spring, utiliza JPA para uma camada de persistência eficiente e PostgreSQL para armazenamento de dados.
- **Nome do microserviço**: `ms-user-management`

### Serviço de Produtos
- **Função**: Gestão de produtos disponíveis na loja.
- **Tecnologia**: Spring, JPA, PostgreSQL.
- **Descrição**: Gerencia entidades de produtos, aproveitando JPA para gestão e PostgreSQL para armazenamento confiável.
- **Nome do microserviço**: `ms-product-management`

### Serviço de Pedidos
- **Função**: Criação e gerenciamento de pedidos.
- **Tecnologia**: Spring, JPA, PostgreSQL.
- **Descrição**: Utiliza JDBC para controle detalhado das operações do banco de dados, essencial na gestão de pedidos.
- **Nome do microserviço**: `ms-order-management`

### Serviço de Notificação
- **Função**: Notificação de usuários sobre mudanças de status no sistema.
- **Tecnologia**: Spring, WebFux.
- **Descrição**: Emprega MongoDB para armazenamento de notificações e Spring para a integração e envio dessas notificações.
- **Nome do microserviço**: `ms-notification-service`

### Serviço de Calculadora
- **Função**: Auxiliar na precificação de mercadorias baseada em despesas mensais.
- **Tecnologia**: Spring, JPA, PostgreSQL.
- **Descrição**: Realiza cálculos complexos e armazena dados usando Spring e JPA, com PostgreSQL como banco de dados.
- **Nome do microserviço**: `ms-price-calculator`

### Serviço de Carrinho de Compras
- **Função**: Gerenciamento da sessão do carrinho de compras dos usuários.
- **Tecnologia**: Java, Spring Boot, Mongo.js ou Redis.
- **Descrição**: Desenvolvido com Spring Boot para um serviço responsivo e escalável, gerenciando a sessão do carrinho de compras.
- **Nome do microserviço**: `ms-shopping-cart`

### Serviço de Gerenciamento de Compras
- **Função**: Processamento de compras, incluindo histórico e devoluções.
- **Tecnologia**: Java, Spring Boot, Postgres.
- **Descrição**: Utiliza Node.js para análises em tempo real e Elasticsearch para buscas avançadas e sugestões de produtos.
- **Nome do microserviço**: `ms-purchase-management`

### API Gateway
- **Função**: Ponto de entrada unificado para roteamento de requisições e segurança.
- **Tecnologia**: Spring Cloud Gateway.
- **Descrição**: Fornece roteamento de requisições, autenticação e outras funcionalidades de segurança.
- **Nome do microserviço**: `ms-api-gateway`

### Serviço de Monitoramento e Logging
- **Função**: Monitoramento da saúde e desempenho dos microserviços.
- **Tecnologia**: Spring Boot Actuator, ELK Stack ou Prometheus e Grafana.
- **Descrição**: Coleta e análise de logs e métricas para manutenção e otimização do sistema.
- **Nome do microserviço**: `ms-monitoring-logging`
- 
- ### Serviço de Análise de Dados
- **Função**: Responsável por ler os dados do Serviço de Gerenciamento de Compras e indexá-los no Elasticsearch
- **Tecnologia**: Node, mongoDB, Elasticsearch
- **Descrição**: Estruturar os dados de forma a facilitar análises complexas e buscas rápidas, como encontrar os produtos mais vendidos, tendências de compra, etc.
- **Nome do microserviço**: `ms-data-analysis`

### Resumo

Esta arquitetura visa fornecer uma solução abrangente e eficiente para o sistema de e-commerce, com cada microserviço desempenhando um papel crucial no ecossistema geral, garantindo alta disponibilidade, escalabilidade e uma experiência de usuário fluida.

## Tecnologias Utilizadas

Esta seção descreve as principais tecnologias utilizadas na arquitetura dos microserviços do sistema, destacando suas funções e importância.

- **Spring Boot**
    - **Uso**: Desenvolvimento de microserviços (`ms-user-management`, `ms-product-management`, `ms-purchase-management`, `ms-order-management`, `ms-notification-service`, `ms-price-calculator`, `ms-shopping-cart`).
    - **Descrição**: Facilita a criação de aplicações baseadas em Spring autônomas e de fácil execução.

- **Spring Cloud**
    - **Uso**: Infraestrutura de microserviços (API Gateway, Configuração, Descoberta de Serviços).
    - **Componentes**: Gateway (`ms-api-gateway`), Config-Server (`config-server`), Spring Cloud Netflix Eureka(`service-registry`).
    - **Descrição**: Fornece ferramentas para desenvolvimento rápido e eficaz de padrões comuns em sistemas distribuídos.

- **Node.js**
    - **Uso**: Desenvolvimento de microserviços focados em operações assíncronas e I/O não bloqueante (`ms-purchase-management`).
    - **Descrição**: Ambiente de execução para JavaScript no servidor, ideal para construir aplicações de rede escaláveis.

- **Apache Kafka**
    - **Uso**: Sistema de mensageria para comunicação assíncrona entre microserviços.
    - **Descrição**: Plataforma de streaming distribuída que permite publicar, assinar, armazenar e processar streams de registros em tempo real.

- **PostgreSQL**
    - **Uso**: Banco de dados relacional para microserviços que exigem transações e integridade de dados (`ms-user-management`, `ms-product-management`, `ms-order-management`, `ms-price-calculator`).
    - **Descrição**: Sistema de gerenciamento de banco de dados relacional robusto e com recursos avançados.

- **MongoDB**
    - **Uso**: Banco de dados NoSQL para microserviços que necessitam de esquemas flexíveis e escalabilidade horizontal (`ms-purchase-management`).
    - **Descrição**: Banco de dados orientado a documentos, que oferece alto desempenho, alta disponibilidade e fácil escalabilidade.

- **Elasticsearch**
    - **Uso**: Sistema de busca e análise para o microserviço de gerenciamento de compras (`ms-purchase-management`).
    - **Descrição**: Mecanismo de busca baseado em Lucene, eficiente em buscas em texto completo e análises em grandes volumes de dados.

### Resumo

Essas tecnologias foram escolhidas para atender às diferentes necessidades de cada microserviço, garantindo um ecossistema de aplicação eficiente, escalável e resiliente.


## Como Executar o Projeto

Instruções detalhadas sobre como configurar e executar o projeto em seu ambiente local.

<p align="center"> 🚧  Under development...  🚧</p> <br>

## Contribuições

Este projeto é um ambiente de aprendizado e todas as contribuições são bem-vindas. Se você tem sugestões para melhorar este projeto, sinta-se à vontade para criar um pull request ou abrir um issue.

## Licença

Este projeto é licenciado sob a Creative Commons Attribution-ShareAlike 4.0 International License. Isso significa que você é livre para:

- **Compartilhar** — copiar e redistribuir o material em qualquer meio ou formato.
- **Adaptar** — remixar, transformar e desenvolver o material para qualquer finalidade, mesmo comercialmente.

Sob as seguintes condições:

- **Atribuição** — Você deve fornecer crédito apropriado, fornecer um link para a licença e indicar se foram feitas alterações. Você pode fazer isso de qualquer maneira razoável, mas não de maneira a sugerir que o licenciante endossa você ou seu uso.
- **ShareAlike** — Se você remixar, transformar ou criar a partir do material, deve distribuir suas contribuições sob a mesma licença que o original.

Para mais informações, consulte [CC BY-SA 4.0 License](https://creativecommons.org/licenses/by-sa/4.0/).


## Contato

[![Linkedin Badge](https://img.shields.io/badge/-Fernando-blue?style=flat-square&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/dev-luis-oliveira/)](https://www.linkedin.com/in/dev-luis-oliveira/)

Feito com ❤️ por Dev [Luis.Oliveira](https://www.linkedin.com/in/dev-luis-oliveira/)

