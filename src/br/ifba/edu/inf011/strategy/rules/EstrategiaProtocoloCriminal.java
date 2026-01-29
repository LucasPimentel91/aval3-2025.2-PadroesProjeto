package br.ifba.edu.inf011.strategy.rules;

import java.time.LocalDate;

import br.ifba.edu.inf011.model.documentos.Documento;
import br.ifba.edu.inf011.strategy.EstrategiaNumeroProtocolo;

public class EstrategiaProtocoloCriminal implements EstrategiaNumeroProtocolo {

    @Override
    public String generate(Documento documento) {
        return "CRI-" + LocalDate.now().getYear() + "-" + documento.hashCode();
    }
}
