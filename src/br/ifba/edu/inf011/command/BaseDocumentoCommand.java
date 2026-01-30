package br.ifba.edu.inf011.command;
import br.ifba.edu.inf011.memento.DocumentoMemento;
import br.ifba.edu.inf011.model.GerenciadorDocumentoModel;
import br.ifba.edu.inf011.model.documentos.Documento;
public abstract class BaseDocumentoCommand implements DocumentoCommand {
    protected final GerenciadorDocumentoModel model;
    protected Documento target;
    protected DocumentoMemento before;
    protected DocumentoMemento after;
    protected BaseDocumentoCommand(GerenciadorDocumentoModel model, Documento target) {
        this.model = model;
        this.target = target;
    }
    @Override
    public void undo() throws Exception {
        if (before != null) {
            model.restore(before);
        }
    }
    @Override
    public void redo() throws Exception {
        if (after != null) {
            model.restore(after);
        }
    }
}