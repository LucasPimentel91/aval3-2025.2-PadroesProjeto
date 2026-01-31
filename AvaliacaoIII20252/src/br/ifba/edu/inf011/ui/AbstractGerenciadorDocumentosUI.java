package br.ifba.edu.inf011.ui;
import java.awt.BorderLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import br.ifba.edu.inf011.af.DocumentOperatorFactory;
import br.ifba.edu.inf011.model.GerenciadorDocumentoModel;
import br.ifba.edu.inf011.model.documentos.Documento;
public abstract class AbstractGerenciadorDocumentosUI extends JFrame implements ListSelectionListener {
    protected GerenciadorDocumentoModel controller;
    protected JPanelBarraSuperior<String> barraSuperior;
    protected JPanelAreaEdicao areaEdicao;
    protected JPanelListaDocumentos<Documento> barraDocs;
    protected String[] tipos = { "Criminal", "Pessoal", "Exportação", "Confidencial" };
    protected Documento atual;
    protected DefaultListModel<Documento> listDocs;
    public AbstractGerenciadorDocumentosUI(DocumentOperatorFactory factory) {
        this.controller = new GerenciadorDocumentoModel(factory);
        this.listDocs = new DefaultListModel<>();
        this.barraSuperior = new JPanelBarraSuperior<>(tipos);
        this.areaEdicao = new JPanelAreaEdicao();
        this.barraDocs = new JPanelListaDocumentos<>(this.listDocs, this);
        this.montarAparencia();
    }
    protected abstract JPanelOperacoes montarMenuOperacoes();
    public void montarAparencia() {
        this.setTitle("Sistema de Gestão de Documentos - INF011");
        this.setSize(800, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.add(this.barraSuperior, BorderLayout.NORTH);
        this.add(this.areaEdicao, BorderLayout.CENTER);
        this.add(this.barraDocs, BorderLayout.WEST);
        this.add(this.montarMenuOperacoes(), BorderLayout.EAST);
    }
    protected void refreshUI() {
        try {
            String texto = (this.atual == null) ? "" : this.atual.getConteudo();
            this.areaEdicao.atualizar(texto == null ? "" : texto);
        } catch (Exception e) {
            this.areaEdicao.atualizar("");
            JOptionPane.showMessageDialog(this, "Erro ao Carregar : " + e.getMessage());
        }
        int idx = this.barraDocs.getIndiceDocSelecionado();
        if (idx >= 0 && this.atual != null) {
            this.barraDocs.updateDoc(idx, this.atual);
        }
    }
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int index = this.barraDocs.getIndiceDocSelecionado();
            if (index != -1) {
                this.atual = controller.getRepositorio().get(index);
                this.refreshUI();
            }
        }
    }
}