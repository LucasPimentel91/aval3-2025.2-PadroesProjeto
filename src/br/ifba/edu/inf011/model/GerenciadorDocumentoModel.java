package br.ifba.edu.inf011.model;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import br.ifba.edu.inf011.af.DocumentOperatorFactory;
import br.ifba.edu.inf011.command.ComandoAssinar;
import br.ifba.edu.inf011.command.ComandoEditarConteudo;
import br.ifba.edu.inf011.command.ComandoMacro;
import br.ifba.edu.inf011.command.ComandoMarcarUrgente;
import br.ifba.edu.inf011.command.ComandoProteger;
import br.ifba.edu.inf011.command.ComandoPriorizar;
import br.ifba.edu.inf011.command.GerenciadorComandos;
import br.ifba.edu.inf011.command.RegistradorOperacoesArquivo;
import br.ifba.edu.inf011.memento.DocumentoMemento;
import br.ifba.edu.inf011.model.documentos.Documento;
import br.ifba.edu.inf011.model.documentos.Privacidade;
import br.ifba.edu.inf011.model.operador.Operador;
@SuppressWarnings("unused")
public class GerenciadorDocumentoModel {
    private List<Documento> repositorio;
    private DocumentOperatorFactory factory;
    private Autenticador autenticador;
    private GestorDocumento gestor;
    private Documento atual;
    private GerenciadorComandos commandManager;
    public GerenciadorDocumentoModel(DocumentOperatorFactory factory) {
        this.repositorio = new ArrayList<>();
        this.factory = factory;
        this.autenticador = new Autenticador();
        this.gestor = new GestorDocumento();
        this.atual = null;
        this.commandManager = new GerenciadorComandos(new RegistradorOperacoesArquivo("operations.log"));
    }
    public Documento criarDocumento(int tipoAutenticadorIndex, Privacidade privacidade) throws FWDocumentException {
        Operador operador = factory.getOperador();
        Documento documento = factory.getDocumento();

        operador.inicializar("jdc", "João das Couves");
        documento.inicializar(operador, privacidade);

        this.autenticador.autenticar(tipoAutenticadorIndex, documento);
        this.repositorio.add(documento);
        this.atual = documento;
        return documento;
    }
    public void salvarDocumento(Documento doc, String conteudo) throws Exception {
        if (doc == null) return;
        this.commandManager.execute(new ComandoEditarConteudo(this, doc, conteudo));
        this.atual = this.getDocumentoAtual();
    }

    public List<Documento> getRepositorio() {
        return repositorio;
    }
    public Documento assinarDocumento(Documento doc) throws FWDocumentException {
        if (doc == null) return null;
        try {
            this.commandManager.execute(new ComandoAssinar(this, doc));
            this.atual = this.getDocumentoAtual();
            return this.atual;
        } catch (FWDocumentException e) {
            throw e;
        } catch (Exception e) {
            throw new FWDocumentException(e.getMessage());
        }
    }
    public Documento protegerDocumento(Documento doc) throws FWDocumentException {
        if (doc == null) return null;
        try {
            this.commandManager.execute(new ComandoProteger(this, doc));
            this.atual = this.getDocumentoAtual();
            return this.atual;
        } catch (FWDocumentException e) {
            throw e;
        } catch (Exception e) {
            throw new FWDocumentException(e.getMessage());
        }
    }
    public Documento tornarUrgente(Documento doc) throws FWDocumentException {
        if (doc == null) return null;
        try {
            this.commandManager.execute(new ComandoMarcarUrgente(this, doc));
            this.atual = this.getDocumentoAtual();
            return this.atual;
        } catch (FWDocumentException e) {
            throw e;
        } catch (Exception e) {
            throw new FWDocumentException(e.getMessage());
        }
    }
    public void undo() throws FWDocumentException {
        try {
            this.commandManager.undo();
        } catch (Exception e) {
            throw new FWDocumentException(e.getMessage());
        }
    }
    public void redo() throws FWDocumentException {
        try {
            this.commandManager.redo();
        } catch (Exception e) {
            throw new FWDocumentException(e.getMessage());
        }
    }
    public void consolidate() {
        this.commandManager.consolidate();
    }
    public void macroAlterarEAssinar(Documento doc, String conteudo) throws FWDocumentException {
        if (doc == null) return;
        try {
            ComandoMacro macro = new ComandoMacro(
                    "Macro: Alterar e Assinar",
                    List.of(
                            new ComandoEditarConteudo(this, doc, conteudo),
                            new ComandoAssinar(this, doc)
                    )
            );
            this.commandManager.execute(macro);
        } catch (FWDocumentException e) {
            throw e;
        } catch (Exception e) {
            throw new FWDocumentException(e.getMessage());
        }
    }
    public void macroPriorizar(Documento doc) throws FWDocumentException {
        if (doc == null) return;

        try {
            this.commandManager.execute(new ComandoPriorizar(this, doc));
            this.atual = this.getDocumentoAtual();
        } catch (FWDocumentException e) {
            throw e;
        } catch (Exception e) {
            throw new FWDocumentException(e.getMessage());
        }
    }
    public void atualizarRepositorio(Documento antigo, Documento novo) {
        int index = repositorio.indexOf(antigo);
        if (index != -1) {
            repositorio.set(index, novo);
        }
    }
    public Documento getDocumentoAtual() {
        return this.atual;
    }
    public void setDocumentoAtual(Documento doc) {
        this.atual = doc;
    }
    public DocumentoMemento createMemento(Documento doc) throws FWDocumentException {
        if (doc == null) {
            return new DocumentoMemento(-1, null, null, null);
        }

        int idx = repositorio.indexOf(doc);
        String conteudo = snapshotConteudo(doc);
        Boolean urgente = snapshotUrgente(doc);
        return new DocumentoMemento(idx, doc, conteudo, urgente);
    }
    public void restore(DocumentoMemento memento) throws FWDocumentException {
        if (memento == null) return;
        Documento doc = memento.getDocumentoRef();
        if (doc == null) return;
        int idx = memento.getIndex();
        if (idx >= 0 && idx < repositorio.size()) {
            repositorio.set(idx, doc);
        }
        if (memento.getConteudo() != null) {
            doc.setConteudo(memento.getConteudo());
        }
        if (memento.getUrgente() != null) {
            restoreUrgente(doc, memento.getUrgente());
        }
        this.atual = doc;
    }
    private Documento unwrapDocumento(Documento doc) {
        Documento atual = doc;
        while (atual != null) {
            try {
                Field f = findField(atual.getClass(), "wrappeeDocumento");
                if (f == null) {
                    break;
                }
                f.setAccessible(true);
                Object inner = f.get(atual);
                if (inner instanceof Documento d) {
                    atual = d;
                    continue;
                }
                break;
            } catch (Exception e) {
                break;
            }
        }
        return atual;
    }
    private String snapshotConteudo(Documento doc) {
        if (doc == null) return null;
        try {
            return doc.getConteudo();
        } catch (Exception ignored) {    
        }
        Documento base = unwrapDocumento(doc);
        try {
            return base.getConteudo();
        } catch (Exception ignored) {
        }
        try {
            Field f = findField(base.getClass(), "conteudo");
            if (f != null) {
                f.setAccessible(true);
                Object val = f.get(base);
                return (val != null) ? String.valueOf(val) : null;
            }
        } catch (Exception ignored) {
        }
        return null;
    }
    private Boolean snapshotUrgente(Documento doc) {
        try {
            return (doc != null) ? doc.isUrgente() : null;
        } catch (Exception ignored) {
            return null;
        }
    }
    private void restoreUrgente(Documento doc, boolean urgente) {
        if (doc == null) return;
        try {
            doc.setUrgente(urgente);
        } catch (Exception ignored) {
        }
    }
    private Field findField(Class<?> type, String name) {
        Class<?> cur = type;
        while (cur != null && cur != Object.class) {
            try {
                return cur.getDeclaredField(name);
            } catch (NoSuchFieldException e) {
                cur = cur.getSuperclass();
            }
        }
        return null;
    }
    public Documento applySetContent(Documento doc, String conteudo) {
        doc.setConteudo(conteudo);
        this.atual = doc;
        return doc;
    }
    public Documento applySign(Documento doc) throws FWDocumentException {
        Operador operador = factory.getOperador();
        operador.inicializar("jdc", "João das Couves");

        Documento assinado = gestor.assinar(doc, operador);
        this.atualizarRepositorio(doc, assinado);
        this.atual = assinado;
        return assinado;
    }
    public Documento applyProtect(Documento doc) throws FWDocumentException {
        Documento protegido = gestor.proteger(doc);
        this.atualizarRepositorio(doc, protegido);
        this.atual = protegido;
        return protegido;
    }
    public Documento applyUrgent(Documento doc) throws FWDocumentException {
        Documento urgente = gestor.tornarUrgente(doc);
        this.atualizarRepositorio(doc, urgente);
        this.atual = urgente;
        return urgente;
    }
}