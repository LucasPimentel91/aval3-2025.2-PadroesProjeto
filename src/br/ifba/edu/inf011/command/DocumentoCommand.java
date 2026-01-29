package br.ifba.edu.inf011.command;
public interface DocumentoCommand {

    void execute() throws Exception;

    void undo() throws Exception;

    void redo() throws Exception;

    String getDescription();
}
