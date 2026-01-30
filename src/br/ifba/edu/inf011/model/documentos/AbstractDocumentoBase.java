package br.ifba.edu.inf011.model.documentos;
import java.time.LocalDateTime;
import java.util.Set;
import br.ifba.edu.inf011.model.Assinatura;
import br.ifba.edu.inf011.model.FWDocumentException;
import br.ifba.edu.inf011.model.operador.Operador;
public abstract class AbstractDocumentoBase implements Documento {

    protected String numero;
    protected String conteudo;
    protected Operador proprietario;
    protected LocalDateTime dataCriacao;
    protected Privacidade privacidade;

   
    protected boolean urgente;

    public AbstractDocumentoBase(String conteudo, Operador proprietario,
                                 LocalDateTime dataCriacao, Privacidade privacidade, Set<Assinatura> assinaturas) {
        this.conteudo = (conteudo != null) ? conteudo : "";
        this.proprietario = proprietario;
        this.dataCriacao = dataCriacao;
        this.privacidade = privacidade;
        this.urgente = false; 
    }

    public AbstractDocumentoBase(Operador proprietario, Privacidade privacidade) {
        this(null, proprietario, LocalDateTime.now(), privacidade, null);
    }

    public AbstractDocumentoBase() {
        this(null, null, LocalDateTime.now(), null, null);
    }

    @Override
    public void inicializar(Operador proprietario, Privacidade privacidade) {
        this.proprietario = proprietario;
        this.privacidade = privacidade;

        if (this.conteudo == null) {
            this.conteudo = "";
        }
    }

    @Override
    public String getNumero() {
        return this.numero;
    }

    @Override
    public String getConteudo() throws FWDocumentException {
        return this.conteudo;
    }

    @Override
    public Operador getProprietario() {
        return this.proprietario;
    }

    @Override
    public Privacidade getPrivacidade() {
        return this.privacidade;
    }

    @Override
    public void setConteudo(String conteudo) {
        this.conteudo = (conteudo != null) ? conteudo : "";
    }

    @Override
    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public boolean isUrgente() {
        return this.urgente;
    }

   
    @Override
    public void setUrgente(boolean urgente) {
        this.urgente = urgente;
    }

    @Override
    public String toString() {
        String tag = this.urgente ? "[URGENTE] " : "";
        return tag + this.getClass().getSimpleName() + "{" +
                "numero='" + numero + '\'' +
                ", proprietario='" + proprietario + '\'' +
                ", dataCriacao=" + dataCriacao +
                '}';
    }
}
