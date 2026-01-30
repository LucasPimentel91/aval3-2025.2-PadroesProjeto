package br.ifba.edu.inf011.command;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class ComandoMacro implements DocumentoCommand {
    private final String description;
    private final List<DocumentoCommand> commands;

    public ComandoMacro(String description, List<DocumentoCommand> commands) {
        this.description = description;
        this.commands = (commands != null) ? new ArrayList<>(commands) : new ArrayList<>();
    }
    @Override
    public void execute() throws Exception {
        for (DocumentoCommand cmd : commands) {
            cmd.execute();
        }
    }
    @Override
    public void undo() throws Exception {
        List<DocumentoCommand> reverse = new ArrayList<>(commands);
        Collections.reverse(reverse);
        for (DocumentoCommand cmd : reverse) {
            cmd.undo();
        }
    }
    @Override
    public void redo() throws Exception {
        for (DocumentoCommand cmd : commands) {
            cmd.redo();
        }
    }
    @Override
    public String getDescription() {
        return description;
    }
}