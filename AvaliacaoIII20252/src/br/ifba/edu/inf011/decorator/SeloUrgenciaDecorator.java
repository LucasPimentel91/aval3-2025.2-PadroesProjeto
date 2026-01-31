package br.ifba.edu.inf011.decorator;
import br.ifba.edu.inf011.model.FWDocumentException;
import br.ifba.edu.inf011.model.documentos.Documento;
public class SeloUrgenciaDecorator extends DocumentoDecorator {
    public SeloUrgenciaDecorator(Documento d) {
        super(d);
        if (d != null) {
            d.setUrgente(true);
        }
    }
    @Override
    public String getConteudo() throws FWDocumentException {
        String base = super.getConteudo();
        if (base == null) {
            base = "";
        }

        if (base.startsWith("[URGENTE]")) {
            return base;
        }

        return "[URGENTE]\n" + base;
    }
}