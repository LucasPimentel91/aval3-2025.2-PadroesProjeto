package br.ifba.edu.inf011.command;

import br.ifba.edu.inf011.model.GerenciadorDocumentoModel;
import br.ifba.edu.inf011.model.documentos.Documento;

public class ComandoAssinar extends BaseDocumentoCommand {

    public ComandoAssinar(GerenciadorDocumentoModel model, Documento target) {
        super(model, target);
    }

    @Override
    public void execute() throws Exception {
        this.before = model.createMemento(target);
        Documento signed = model.applySign(target);
        this.after = model.createMemento(signed);
    }

    @Override
    public String getDescription() {
        return "Assinar documento";
    }
}
