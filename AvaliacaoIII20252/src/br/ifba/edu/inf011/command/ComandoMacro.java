package br.ifba.edu.inf011.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import br.ifba.edu.inf011.model.documentos.Documento;

public class ComandoMacro implements DocumentoCommand {
    private final String description;
    private final List<DocumentoCommand> commands;

    public ComandoMacro(String description) {
        this.description = description;
        this.commands = new ArrayList<>();
    }

    public void addCommand(DocumentoCommand command) {
        this.commands.add(command);
    }

    @Override
    public void execute() throws Exception {
        Documento documentoAtual = null;

        for (DocumentoCommand cmd : commands) {
            // Se tivermos um documento atualizado de uma operação anterior e o
            // próximo comando for um comando base, atualizamos o alvo dele.
            if (documentoAtual != null && cmd instanceof BaseDocumentoCommand baseCmd) {
                baseCmd.setTarget(documentoAtual);
            }

            // Executa o comando
            cmd.execute();

            // Após executar, tentamos pegar a referência atualizada do documento.
            // Isso assume que o comando atualizou o 'target' interno dele ou o Model.
            if (cmd instanceof BaseDocumentoCommand baseCmd) {
                // Pega o target que acabou de ser modificado/decorado
                documentoAtual = baseCmd.getTarget();
            }
        }
    }

    @Override
    public void undo() throws Exception {
        // Desfaz na ordem inversa
        List<DocumentoCommand> reverse = new ArrayList<>(commands);
        Collections.reverse(reverse);
        for (DocumentoCommand cmd : reverse) {
            cmd.undo();
        }
    }

    @Override
    public void redo() throws Exception {
        for (DocumentoCommand cmd : commands) {
            cmd.redo();
        }
    }

    @Override
    public String getDescription() {
        return "MACRO: " + description;
    }
}