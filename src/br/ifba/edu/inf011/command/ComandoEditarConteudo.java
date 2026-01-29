package br.ifba.edu.inf011.command;

import br.ifba.edu.inf011.model.GerenciadorDocumentoModel;
import br.ifba.edu.inf011.model.documentos.Documento;

public class ComandoEditarConteudo extends BaseDocumentoCommand {

    private final String newContent;

    public ComandoEditarConteudo(GerenciadorDocumentoModel model, Documento target, String newContent) {
        super(model, target);
        this.newContent = (newContent != null) ? newContent : "";
    }

    @Override
    public void execute() throws Exception {
        this.before = model.createMemento(target);
        model.applySetContent(target, newContent);
        this.after = model.createMemento(target);
    }

    @Override
    public String getDescription() {
        return "Editar conteúdo";
    }
}
