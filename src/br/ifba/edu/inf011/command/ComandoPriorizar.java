package br.ifba.edu.inf011.command;
import br.ifba.edu.inf011.model.GerenciadorDocumentoModel;
import br.ifba.edu.inf011.model.documentos.Documento;
public class ComandoPriorizar extends BaseDocumentoCommand {
    public ComandoPriorizar(GerenciadorDocumentoModel model, Documento target) {
        super(model, target);
    }
    @Override
    public void execute() throws Exception {
        this.before = model.createMemento(target);

        Documento urgente = model.applyUrgent(target);
        this.after = model.createMemento(urgente);
    }
    @Override
    public String getDescription() {
        return "Priorizar (Urgente)";
    }
}