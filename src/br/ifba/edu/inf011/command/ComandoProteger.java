package br.ifba.edu.inf011.command;
import br.ifba.edu.inf011.model.GerenciadorDocumentoModel;
import br.ifba.edu.inf011.model.documentos.Documento;
public class ComandoProteger extends BaseDocumentoCommand {
    public ComandoProteger(GerenciadorDocumentoModel model, Documento target) {
        super(model, target);
    }
    @Override
    public void execute() throws Exception {
        this.before = model.createMemento(target);
        Documento protectedDoc = model.applyProtect(target);
        this.after = model.createMemento(protectedDoc);
    }
    @Override
    public String getDescription() {
        return "Proteger documento";
    }
}