INF011 – Sistema de Gestão de Documentos (Avaliação III)

Este repositório contém o projeto final da disciplina INF011 (2025.2), consistindo em uma aplicação desktop para o gerenciamento de documentos e processos eletrônicos. O foco do desenvolvimento foi a aplicação prática de padrões de projeto para resolver problemas de extensibilidade, histórico de ações e operações compostas.



Como Executar:

O projeto é uma aplicação modular Java padrão. Para rodar:

Importe o diretório como um projeto Java na sua IDE de preferência (Eclipse, IntelliJ ou VS Code).



Certifique-se de estar utilizando o JDK 17 ou superior.



Execute a classe principal: br.ifba.edu.inf011.model.AppAvaliacaoIII.



Não há dependências externas complexas; a interface gráfica utiliza Swing nativo do JDK. Ao iniciar, a janela de gerenciamento de documentos será aberta automaticamente.



Arquitetura e Padrões de Projeto

Abaixo detalhamos as decisões arquiteturais tomadas para atender aos requisitos de flexibilidade e auditoria do sistema.



1\. Autenticação e Protocolos (Strategy)

Para resolver a rigidez na geração de números de protocolo, substituímos a lógica condicional original pelo padrão Strategy. O Autenticador atua como o Context, delegando a regra de negócio para implementações da interface EstrategiaNumeroProtocolo (Strategy). Isso nos permitiu criar regras variadas (como EstrategiaProtocoloCriminal, EstrategiaProtocoloPessoal, etc.) sem precisar modificar a classe principal de autenticação. Novas regras podem ser injetadas dinamicamente conforme a necessidade do órgão ou departamento.



2\. Operações, Histórico e Macros (Command + Memento + Composite)

A maior parte da lógica de manipulação de documentos foi refatorada para utilizar uma combinação de padrões que garantisse suporte a Undo/Redo e criação de macros.



Encapsulamento de Ações (Command): Todas as operações (editar, assinar, proteger, urgência) foram transformadas em comandos independentes (DocumentoCommand). O GerenciadorComandos funciona como Invoker, mantendo o histórico de execução e gerenciando as pilhas de desfazer/refazer. Além disso, ele utiliza um RegistradorOperacoesArquivo para persistir o log de auditoria em disco (operations.log).



Gestão de Estado (Memento): Para garantir que o Undo funcione corretamente — inclusive em documentos protegidos ou decorados — implementamos o padrão Memento. O GerenciadorDocumentoModel (Originator) é capaz de criar e restaurar snapshots do estado (DocumentoMemento), que são armazenados pelos comandos antes de qualquer execução.



Macros e Ações Compostas (Composite): Para atender ao requisito de "Ações Rápidas" (como Alterar e Assinar ou Priorizar), utilizamos o padrão Composite sobre os comandos. A classe ComandoMacro permite agrupar múltiplos comandos e executá-los como um bloco único. Para o sistema, uma macro é indistinguível de um comando simples, simplificando a lógica de chamada e reversão.

