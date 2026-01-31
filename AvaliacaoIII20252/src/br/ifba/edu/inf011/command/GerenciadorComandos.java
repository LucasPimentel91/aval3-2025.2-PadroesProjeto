package br.ifba.edu.inf011.command;
import java.util.ArrayDeque;
import java.util.Deque;
public class GerenciadorComandos {
    private final Deque<DocumentoCommand> undoStack;
    private final Deque<DocumentoCommand> redoStack;
    private final RegistradorOperacoesArquivo logger;
    public GerenciadorComandos(RegistradorOperacoesArquivo logger) {
        this.undoStack = new ArrayDeque<>();
        this.redoStack = new ArrayDeque<>();
        this.logger = logger;
    }
    public void execute(DocumentoCommand command) throws Exception {
        if (command == null) {
            return;
        }

        command.execute();
        this.undoStack.push(command);
        this.redoStack.clear();
        log("EXECUTE - " + command.getDescription());
    }
    public boolean canUndo() {
        return !this.undoStack.isEmpty();
    }
    public boolean canRedo() {
        return !this.redoStack.isEmpty();
    }
    public void undo() throws Exception {
        if (!canUndo()) {
            return;
        }
        DocumentoCommand command = this.undoStack.pop();
        command.undo();
        this.redoStack.push(command);
        log("UNDO - " + command.getDescription());
    }
    public void redo() throws Exception {
        if (!canRedo()) {
            return;
        }
        DocumentoCommand command = this.redoStack.pop();
        command.redo();
        this.undoStack.push(command);
        log("REDO - " + command.getDescription());
    }
    public void consolidate() {
        this.undoStack.clear();
        this.redoStack.clear();
        log("CONSOLIDATE - pilhas de desfazer/refazer limpas");
    }
    private void log(String msg) {
        if (this.logger != null) {
            this.logger.log(msg);
        }
    }
}