package br.ifba.edu.inf011.ui;
import javax.swing.JOptionPane;
import br.ifba.edu.inf011.af.DocumentOperatorFactory;
import br.ifba.edu.inf011.model.FWDocumentException;
import br.ifba.edu.inf011.model.documentos.Privacidade;
public class MyGerenciadorDocumentoUI extends AbstractGerenciadorDocumentosUI {
    public MyGerenciadorDocumentoUI(DocumentOperatorFactory factory) {
        super(factory);
    }

    @Override
    protected JPanelOperacoes montarMenuOperacoes() {
        JPanelOperacoes comandos = new JPanelOperacoes();

        comandos.addOperacao("➕ Criar Publico", e -> this.criarDocumento(Privacidade.PUBLICO));
        comandos.addOperacao("➕ Criar Privado", e -> this.criarDocumento(Privacidade.SIGILOSO));
        comandos.addOperacao("💾 Salvar", e -> this.salvarConteudo());
        comandos.addOperacao("Salvar e Assinar", e -> this.salvarEAssinar());
        comandos.addOperacao("🔑 Proteger", e -> this.protegerDocumento());
        comandos.addOperacao("✍️ Assinar", e -> this.assinarDocumento());
        comandos.addOperacao("⏰ Priorizar", e -> this.priorizarDocumento());
        comandos.addOperacao("↩ Desfazer", e -> this.desfazer());
        comandos.addOperacao("↪ Refazer", e -> this.refazer());
        comandos.addOperacao("🧹 Consolidar", e -> this.consolidar());
        return comandos;
    }

    private void criarDocumento(Privacidade privacidade) {
        try {
            int tipoIndex = this.barraSuperior.getTipoSelecionadoIndice();
            this.atual = this.controller.criarDocumento(tipoIndex, privacidade);

            try {
                this.controller.salvarDocumento(this.atual, "");
                this.atual = this.controller.getDocumentoAtual();
            } catch (Exception ignored) {
            }

            this.barraDocs.addDoc(this.atual);
            this.refreshUI();
        } catch (FWDocumentException e) {
            JOptionPane.showMessageDialog(this, "Erro ao criar documento: " + e.getMessage());
        }
    }

    protected void salvarConteudo() {
        try {
            this.controller.salvarDocumento(this.atual, this.areaEdicao.getConteudo());
            this.atual = this.controller.getDocumentoAtual();
            this.refreshUI();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage());
        }
    }

    protected void salvarEAssinar(){
        try{
            this.controller.macroAlterarEAssinar(this.atual, this.areaEdicao.getConteudo());
            this.atual = this.controller.getDocumentoAtual();
            this.refreshUI();
        } catch (Exception e){
            JOptionPane.showMessageDialog(this, "Erro ao salvar e assinar: " + e.getMessage());
        }
    }

    private void salvarRascunhoSeNecessario() {
        if (this.atual == null) return;

        String digitado = this.areaEdicao.getConteudo();
        if (digitado == null) digitado = "";

        try {
            String salvo = this.atual.getConteudo();
            if (salvo == null) salvo = "";

            if (!digitado.equals(salvo)) {
                this.controller.salvarDocumento(this.atual, digitado);
                this.atual = this.controller.getDocumentoAtual();
            }
        } catch (Exception e) {
           
            try {
                this.controller.salvarDocumento(this.atual, digitado);
                this.atual = this.controller.getDocumentoAtual();
            } catch (Exception ignored) {
            }
        }
    }

    protected void protegerDocumento() {
        try {
            salvarRascunhoSeNecessario();

            this.controller.protegerDocumento(this.atual);
            this.atual = this.controller.getDocumentoAtual();
            this.refreshUI();
        } catch (FWDocumentException e) {
            JOptionPane.showMessageDialog(this, "Erro ao proteger: " + e.getMessage());
        }
    }

    protected void assinarDocumento() {
        try {
            salvarRascunhoSeNecessario();

            this.controller.assinarDocumento(this.atual);
            this.atual = this.controller.getDocumentoAtual();
            this.refreshUI();
        } catch (FWDocumentException e) {
            JOptionPane.showMessageDialog(this, "Erro ao assinar: " + e.getMessage());
        }
    }
    protected void priorizarDocumento() {
        try {
            salvarRascunhoSeNecessario();

            this.controller.macroPriorizar(this.atual);
            this.atual = this.controller.getDocumentoAtual();
            this.refreshUI();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao priorizar: " + e.getMessage());
        }
    }
    protected void desfazer() {
        try {
            this.controller.undo();
            this.atual = this.controller.getDocumentoAtual();
            this.refreshUI();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Não foi possível desfazer: " + e.getMessage());
        }
    }
    protected void refazer() {
        try {
            this.controller.redo();
            this.atual = this.controller.getDocumentoAtual();
            this.refreshUI();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Não foi possível refazer: " + e.getMessage());
        }
    }
    protected void consolidar() {
        this.controller.consolidate();
        this.atual = this.controller.getDocumentoAtual();
        this.refreshUI();
         JOptionPane.showMessageDialog(this, "Documento consolidado, não é possível realizar mais nenhuma alteração.");
    }
}