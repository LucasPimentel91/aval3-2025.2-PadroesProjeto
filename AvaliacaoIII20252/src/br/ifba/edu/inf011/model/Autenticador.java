package br.ifba.edu.inf011.model;
import java.util.HashMap;
import java.util.Map;
import br.ifba.edu.inf011.model.documentos.Documento;
import br.ifba.edu.inf011.strategy.EstrategiaNumeroProtocolo;
import br.ifba.edu.inf011.strategy.rules.EstrategiaProtocoloCriminal;
import br.ifba.edu.inf011.strategy.rules.EstrategiaProtocoloPadrao;
import br.ifba.edu.inf011.strategy.rules.EstrategiaProtocoloExportacao;
import br.ifba.edu.inf011.strategy.rules.EstrategiaProtocoloPessoal;
public class Autenticador {
    private final Map<Integer, EstrategiaNumeroProtocolo> strategies;
    private EstrategiaNumeroProtocolo defaultStrategy;
    public Autenticador() {
        this.strategies = new HashMap<>();
        this.defaultStrategy = new EstrategiaProtocoloPadrao();
        this.registerStrategy(0, new EstrategiaProtocoloCriminal());
        this.registerStrategy(1, new EstrategiaProtocoloPessoal());
        this.registerStrategy(2, new EstrategiaProtocoloExportacao());
    }
    public void registerStrategy(Integer code, EstrategiaNumeroProtocolo strategy) {
        if (code == null || strategy == null) {
            return;
        }
        this.strategies.put(code, strategy);
    }
    public void setDefaultStrategy(EstrategiaNumeroProtocolo strategy) {
        if (strategy != null) {
            this.defaultStrategy = strategy;
        }
    }
    public void autenticar(Integer tipo, Documento documento) {
        if (documento == null) {
            return;
        }
        EstrategiaNumeroProtocolo strategy = this.strategies.get(tipo);
        if (strategy == null) {
            strategy = this.defaultStrategy;
        }
        String numero = strategy.generate(documento);
        documento.setNumero(numero);
    }
}