package br.ifba.edu.inf011.command;
import br.ifba.edu.inf011.model.GerenciadorDocumentoModel;
import br.ifba.edu.inf011.model.documentos.Documento;
public class ComandoMarcarUrgente extends BaseDocumentoCommand {

    public ComandoMarcarUrgente(GerenciadorDocumentoModel model, Documento target) {
        super(model, target);
    }

    @Override
    public void execute() throws Exception {
        this.before = model.createMemento(target);
        Documento urgent = model.applyUrgent(target);
        this.after = model.createMemento(urgent);
    }

    @Override
    public String getDescription() {
        return "Tornar urgente";
    }
}
