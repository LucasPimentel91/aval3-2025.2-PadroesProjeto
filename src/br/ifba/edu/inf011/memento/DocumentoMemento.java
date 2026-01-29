package br.ifba.edu.inf011.memento;

import br.ifba.edu.inf011.model.documentos.Documento;

/**
 * Memento (Questão II)
 *
 * Captura o estado necessário para restaurar um documento no repositório.
 */
public final class DocumentoMemento {

    private final int index;
    private final Documento documentoRef;
    private final String conteudo;

    public DocumentoMemento(int index, Documento documentoRef, String conteudo) {
        this.index = index;
        this.documentoRef = documentoRef;
        this.conteudo = conteudo;
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
}
