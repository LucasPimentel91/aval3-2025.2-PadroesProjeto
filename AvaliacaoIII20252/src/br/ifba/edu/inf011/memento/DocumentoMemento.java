package br.ifba.edu.inf011.memento;

import br.ifba.edu.inf011.model.documentos.Documento;

public record DocumentoMemento(
        int index,
        Documento documentoRef,
        String conteudo,
        Boolean urgente
) {
}