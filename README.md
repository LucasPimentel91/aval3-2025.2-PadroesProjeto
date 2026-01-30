INF011 – Avaliação III (2025.2)

Este repositório contém a implementação da Avaliação III da disciplina INF011, com uma aplicação desktop (Java/Swing) para gerenciamento de documentos. O sistema foi estruturado para evidenciar a aplicação de padrões de projeto solicitados no enunciado.

Como executar

1. Importe o projeto em uma IDE (Eclipse ou IntelliJ) como projeto Java modular.
2. Execute a classe: br.ifba.edu.inf011.model.AppAvaliacaoIII
3. A aplicação iniciará a interface de gerenciamento de documentos.

Observação: não é necessário instalar Java Swing separadamente; o Swing faz parte do JDK. Recomenda-se utilizar JDK 17 ou superior.

Questão 1 – Strategy (Autenticador)

A geração do número de protocolo foi implementada com o padrão Strategy. O componente Autenticador delega a criação do protocolo para uma estratégia selecionável (EstrategiaNumeroProtocolo), permitindo a troca de regras sem alteração do contexto e viabilizando a inclusão de novas estratégias com impacto mínimo no código existente.

Participantes principais
- Strategy: EstrategiaNumeroProtocolo
- ConcreteStrategies: EstrategiaProtocoloCriminal, EstrategiaProtocoloPessoal, EstrategiaProtocoloExportacao, EstrategiaProtocoloPadrao
- Context: Autenticador

Questão 2 – Command + Memento (operações e histórico)

As operações sobre documentos (editar conteúdo, assinar, proteger e priorizar/urgência) foram encapsuladas como comandos (Command). O GerenciadorComandos atua como invocador e mantém pilhas para desfazer/refazer, além de registrar as operações em arquivo (operations.log). O estado necessário para Undo/Redo é armazenado via Memento (DocumentoMemento), criado e restaurado pelo modelo (GerenciadorDocumentoModel).

Undo/Redo
- Cada comando armazena “antes/depois” (mementos) e restaura o estado anterior quando solicitado.
- O conteúdo pode ser restaurado mesmo em documentos protegidos, pois o modelo realiza captura do estado de forma robusta (incluindo casos de proxy/decorator).

Macros
- Alterar e Assinar: executa (Editar conteúdo + Assinar) como uma única ação lógica.
- Priorizar: executa (Tornar urgente + Assinar) como uma única ação lógica.

Participantes principais
- Command: DocumentoCommand
- ConcreteCommands: ComandoEditarConteudo, ComandoAssinar, ComandoProteger, ComandoMarcarUrgente, ComandoPriorizar
- Macro: ComandoMacro
- Invoker/Caretaker: GerenciadorComandos
- Memento: DocumentoMemento
- Originator: GerenciadorDocumentoModel (cria/restaura mementos)
- Logger: RegistradorOperacoesArquivo (operations.log)
