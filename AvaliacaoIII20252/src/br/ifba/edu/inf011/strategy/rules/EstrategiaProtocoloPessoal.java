package br.ifba.edu.inf011.strategy.rules;

import java.time.LocalDate;

import br.ifba.edu.inf011.model.documentos.Documento;
import br.ifba.edu.inf011.model.operador.Operador;
import br.ifba.edu.inf011.strategy.EstrategiaNumeroProtocolo;

public class EstrategiaProtocoloPessoal implements EstrategiaNumeroProtocolo {

    @Override
    public String generate(Documento documento) {
        Operador owner = documento.getProprietario();
        int ownerHash = (owner != null) ? owner.hashCode() : 0;
        return "PES-" + LocalDate.now().getDayOfYear() + "-" + ownerHash;
    }
}
