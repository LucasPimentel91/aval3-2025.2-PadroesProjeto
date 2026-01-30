package br.ifba.edu.inf011.memento;
import br.ifba.edu.inf011.model.documentos.Documento;
public final class DocumentoMemento {
    private final int index;
    private final Documento documentoRef;
    private final String conteudo;
    private final Boolean urgente;
    public DocumentoMemento(int index, Documento documentoRef, String conteudo, Boolean urgente) {
        this.index = index;
        this.documentoRef = documentoRef;
        this.conteudo = conteudo;
        this.urgente = urgente;
    }
    public int getIndex() {
        return index;
    }
    public Documento getDocumentoRef() {
        return documentoRef;
    }
    public String getConteudo() {
        return conteudo;
    }
    public Boolean getUrgente() {
        return urgente;
    }
}
