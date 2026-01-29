package br.ifba.edu.inf011.command;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class RegistradorOperacoesArquivo {

    private final File file;
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public RegistradorOperacoesArquivo(String filename) {
        this.file = new File(filename);
    }

    public synchronized void log(String message) {
        String line = "[" + LocalDateTime.now().format(fmt) + "] " + message;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(line);
            writer.newLine();
        } catch (Exception e) {
           
            System.err.println("Falha ao escrever log: " + e.getMessage());
        }
    }
}
