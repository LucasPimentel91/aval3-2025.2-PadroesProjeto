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
        String conteudo = super.getConteudo();
        if (conteudo == null) {
            conteudo = "";
        }

        if (conteudo.startsWith("[URGENTE]")) {
            return conteudo;
        }

        return "[URGENTE]\n" + conteudo;
    }
}
