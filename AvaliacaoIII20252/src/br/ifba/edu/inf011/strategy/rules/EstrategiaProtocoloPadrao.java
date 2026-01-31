package br.ifba.edu.inf011.strategy.rules;

import br.ifba.edu.inf011.model.documentos.Documento;
import br.ifba.edu.inf011.strategy.EstrategiaNumeroProtocolo;

public class EstrategiaProtocoloPadrao implements EstrategiaNumeroProtocolo {

    @Override
    public String generate(Documento documento) {
        return "DOC-" + System.currentTimeMillis();
    }
}
