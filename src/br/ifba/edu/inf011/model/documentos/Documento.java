package br.ifba.edu.inf011.model.documentos;
import br.ifba.edu.inf011.model.FWDocumentException;
import br.ifba.edu.inf011.model.operador.Operador;
import br.ifba.edu.inf011.prototype.Prototipavel;
public interface Documento extends Prototipavel {
    void inicializar(Operador proprietario, Privacidade privacidade) throws FWDocumentException;
    void setConteudo(String conteudo);
    void setNumero(String numero);
    String getNumero();
    String getConteudo() throws FWDocumentException;
    Operador getProprietario();
    Privacidade getPrivacidade();
    boolean isUrgente();
    void setUrgente(boolean urgente);
}
