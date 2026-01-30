package br.ifba.edu.inf011.command;
import br.ifba.edu.inf011.model.GerenciadorDocumentoModel;
import br.ifba.edu.inf011.model.documentos.Documento;
public class ComandoMarcarUrgente extends BaseDocumentoCommand {
    public ComandoMarcarUrgente(GerenciadorDocumentoModel model, Documento target) {
        super(model, target);
    }
    @Override
    public void execute() throws Exception {
        if (this.target == null) return;
        this.before = model.createMemento(this.target);
        Documento urgente = model.applyUrgent(this.target);
        this.target = urgente;
        this.after = model.createMemento(this.target);
    }
    @Override
    public String getDescription() {
        return "Tornar urgente";
    }
}