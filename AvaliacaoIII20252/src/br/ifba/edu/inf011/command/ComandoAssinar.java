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

        // O Model aplica a decoração e retorna a NOVA referência
        Documento signed = model.applySign(target);

        // CRUCIAL: Atualizamos o target interno para que a Macro possa pegá-lo
        // e passar para o próximo comando.
        this.target = signed;

        this.after = model.createMemento(signed);
    }

    @Override
    public String getDescription() {
        return "Assinar documento";
    }
}