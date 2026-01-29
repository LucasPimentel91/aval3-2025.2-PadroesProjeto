package br.ifba.edu.inf011.ui;
import javax.swing.JOptionPane;
import br.ifba.edu.inf011.af.DocumentOperatorFactory;
import br.ifba.edu.inf011.model.FWDocumentException;
import br.ifba.edu.inf011.model.documentos.Privacidade;
public class MyGerenciadorDocumentoUI extends AbstractGerenciadorDocumentosUI{
	 public MyGerenciadorDocumentoUI(DocumentOperatorFactory factory) {
		super(factory);
	}
	protected JPanelOperacoes montarMenuOperacoes() {
		JPanelOperacoes comandos = new JPanelOperacoes();
		comandos.addOperacao("➕ Criar Publico", e -> this.criarDocumentoPublico());
		comandos.addOperacao("➕ Criar Privado", e -> this.criarDocumentoPrivado());
		comandos.addOperacao("💾 Salvar", e-> this.salvarConteudo());
		comandos.addOperacao("🔑 Proteger", e->this.protegerDocumento());
		comandos.addOperacao("✍️ Assinar", e->this.assinarDocumento());
		comandos.addOperacao("⏰ Urgente", e->this.tornarUrgente());
		comandos.addOperacao("↩️ Desfazer", e->this.desfazer());
		comandos.addOperacao("↪️ Refazer", e->this.refazer());
		comandos.addOperacao("⚡ Alterar e Assinar", e->this.alterarEAssinar());
		comandos.addOperacao("🚩 Priorizar", e->this.priorizar());
		comandos.addOperacao("✅ Consolidar", e->this.consolidar());
		return comandos;
	 }
	protected void criarDocumentoPublico() {
		this.criarDocumento(Privacidade.PUBLICO);
	}
	protected void criarDocumentoPrivado() {
		this.criarDocumento(Privacidade.SIGILOSO);
	}
	protected void salvarConteudo() {
        try {
            this.controller.salvarDocumento(this.atual, this.areaEdicao.getConteudo());
            this.atual = this.controller.getDocumentoAtual();
        } catch (Exception e) {
        	JOptionPane.showMessageDialog(this, "Erro ao Salvar: " + e.getMessage());
        }
    }	
	protected void protegerDocumento() {
		try {
			this.controller.protegerDocumento(this.atual);
			this.atual = this.controller.getDocumentoAtual();
			this.refreshUI();
		} catch (FWDocumentException e) {
			JOptionPane.showMessageDialog(this, "Erro ao proteger: " + e.getMessage());
		}
	}
	protected void assinarDocumento() {
		try {
			this.controller.assinarDocumento(this.atual);
			this.atual = this.controller.getDocumentoAtual();
			this.refreshUI();
		} catch (FWDocumentException e) {
			JOptionPane.showMessageDialog(this, "Erro ao assinar: " + e.getMessage());
		}		
	}
	protected void tornarUrgente() {
		try {
			this.controller.tornarUrgente(this.atual);
			this.atual = this.controller.getDocumentoAtual();
			this.refreshUI();
		} catch (FWDocumentException e) {
			JOptionPane.showMessageDialog(this, "Erro ao tornar urgente: " + e.getMessage());
		}		
	}	
	protected void desfazer() {
		try {
			this.controller.undo();
			this.atual = this.controller.getDocumentoAtual();
			this.refreshUI();
		} catch (FWDocumentException e) {
			JOptionPane.showMessageDialog(this, "Não foi possível desfazer: " + e.getMessage());
		}
	}
	protected void refazer() {
		try {
			this.controller.redo();
			this.atual = this.controller.getDocumentoAtual();
			this.refreshUI();
		} catch (FWDocumentException e) {
			JOptionPane.showMessageDialog(this, "Não foi possível refazer: " + e.getMessage());
		}
	}
	protected void alterarEAssinar() {
		try {
			String texto = this.areaEdicao.getConteudo();
			this.controller.macroAlterarEAssinar(this.atual, texto);
			this.atual = this.controller.getDocumentoAtual();
			this.refreshUI();
		} catch (FWDocumentException e) {
			JOptionPane.showMessageDialog(this, "Erro na macro: " + e.getMessage());
		}
	}
	protected void priorizar() {
		try {
			this.controller.macroPriorizar(this.atual);
			this.atual = this.controller.getDocumentoAtual();
			this.refreshUI();
		} catch (FWDocumentException e) {
			JOptionPane.showMessageDialog(this, "Erro na macro: " + e.getMessage());
		}
	}
	protected void consolidar() {
		this.controller.consolidate();
		JOptionPane.showMessageDialog(this, "Alterações consolidadas. Pilhas de desfazer/refazer foram limpas.");
	}
	private void criarDocumento(Privacidade privacidade) {
        try {
            int tipoIndex = this.barraSuperior.getTipoSelecionadoIndice();
            this.atual = this.controller.criarDocumento(tipoIndex, privacidade);
            this.barraDocs.addDoc("[" + atual.getNumero() + "]");
            this.refreshUI();
        } catch (FWDocumentException e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }	
	
}