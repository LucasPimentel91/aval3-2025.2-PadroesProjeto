package br.ifba.edu.inf011.strategy.rules;

import br.ifba.edu.inf011.model.documentos.Documento;
import br.ifba.edu.inf011.model.documentos.Privacidade;
import br.ifba.edu.inf011.strategy.EstrategiaNumeroProtocolo;

public class EstrategiaProtocoloExportacao implements EstrategiaNumeroProtocolo {

    @Override
    public String generate(Documento documento) {
        if (documento.getPrivacidade() == Privacidade.SIGILOSO) {
            String base = documento.getNumero();
            int hash = (base != null) ? base.hashCode() : documento.hashCode();
            return "SECURE-" + hash;
        }
        return "PUB-" + documento.hashCode();
    }
}
