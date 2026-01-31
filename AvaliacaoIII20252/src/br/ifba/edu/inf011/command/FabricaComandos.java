package br.ifba.edu.inf011.command;

import br.ifba.edu.inf011.model.GerenciadorDocumentoModel;
import br.ifba.edu.inf011.model.documentos.Documento;

public class FabricaComandos {

    public static DocumentoCommand criarAlterarEAssinar(GerenciadorDocumentoModel model, Documento doc, String novoTexto) {
        ComandoMacro macro = new ComandoMacro("Alterar Conteúdo e Assinar");

        macro.addCommand(new ComandoEditarConteudo(model, doc, novoTexto));
        macro.addCommand(new ComandoAssinar(model, doc));

        return macro;
    }

    public static DocumentoCommand criarPriorizar(GerenciadorDocumentoModel model, Documento doc) {
        ComandoMacro macro = new ComandoMacro("Priorizar (Urgente + Assinar)");

        macro.addCommand(new ComandoMarcarUrgente(model, doc));
        macro.addCommand(new ComandoAssinar(model, doc));

        return macro;
    }
}