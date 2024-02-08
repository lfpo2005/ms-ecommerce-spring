# Projeto de Estudo: Loja Virtual - Transição para Microsserviços

## Sobre o Projeto

Este repositório contém um projeto de estudo em desenvolvimento que simula uma loja virtual(ainda em desenvolvimento). Inicialmente construído como uma aplicação monolítica, e atualmente em transição para uma arquitetura baseada em microsserviços, utiliza o ecossistema Spring, Node(next.js), Kafka para mensageria, e bancos de dados PostgreSQL e MongoDB.

## Objetivo do Projeto

O principal objetivo deste projeto é proporcionar um ambiente de aprendizado prático para a implementação e compreensão dos conceitos de microsserviços, bem como a integração e uso de diferentes tecnologias e ferramentas, como Spring Boot, Spring Cloud, Node, Kafka, PostgreSQL, e MongoDB.

## Estrutura Atual do Projeto

O projeto está estruturado da seguinte forma:

Cada microsserviço ira conter sua base de bados individual e ira se comunicar com os outros microsserviços atraves de mensageria kafka.

- **Serviço de Usuários**: Gerencia informações de usuários.
- **Serviço de Produtos**: Responsável pela gestão de produtos disponíveis na loja.
- **Serviço de Pedidos**: Lida com a criação e gerenciamento de pedidos.
- **Serviço de Calculadora**: Ajudara o vendedor a calcular os preços das mecadorias baseadas nas despesas menssais como(custo fixo, variavel, funcionario...).
- **Gateway**: Um ponto de entrada unificado para os microsserviços.

Cada um destes serviços é uma aplicação, que se comunica com os outros serviços através de mensagens Kafka e operações de banco de dados.

## Tecnologias Utilizadas

- **Spring Boot**: Microsserviços.
- **Spring Cloud**: Gateway, Config-Server e Gerenciamento.
- **Node**: Microsserviços.
- **Apache Kafka**: Como sistema de mensageria.
- **PostgreSQL**: Como banco de dados relacional.
- **MongoDB**: Como banco de dados NoSQL.

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

