package br.ifba.edu.inf011.ui;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import br.ifba.edu.inf011.model.documentos.Documento;
public class JPanelListaDocumentos<T> extends JPanel {

    private final JList<T> listDocumentos;

    public JPanelListaDocumentos(DefaultListModel<T> listModel, ListSelectionListener listener) {
        super(new BorderLayout());

        this.listDocumentos = new JList<>(listModel);
        this.listDocumentos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.listDocumentos.setPreferredSize(new Dimension(200, 0));
        this.listDocumentos.setBorder(BorderFactory.createTitledBorder("Documentos"));
        this.listDocumentos.addListSelectionListener(listener);
        this.listDocumentos.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

                String texto;
                if (value instanceof Documento doc) {
                    String numero = doc.getNumero();
                    texto = "[" + (numero == null ? "SEM-NUMERO" : numero) + "]";
                    if (doc.isUrgente()) {
                        texto = "[URGENTE] " + texto;
                    }
                } else {
                    texto = String.valueOf(value);
                }

                return super.getListCellRendererComponent(list, texto, index, isSelected, cellHasFocus);
            }
        });

        this.add(this.listDocumentos, BorderLayout.CENTER);
    }
    public void addDoc(T doc) {
        DefaultListModel<T> model = (DefaultListModel<T>) this.listDocumentos.getModel();
        model.addElement(doc);
        this.listDocumentos.setSelectedIndex(model.size() - 1);
        this.repaint();
    }

    public void updateDoc(int index, T doc) {
        DefaultListModel<T> model = (DefaultListModel<T>) this.listDocumentos.getModel();

        if (index >= 0 && index < model.size()) {
            model.set(index, doc);
            this.listDocumentos.setSelectedIndex(index);
            this.repaint();
        }
    }

    public int getIndiceDocSelecionado() {
        return this.listDocumentos.getSelectedIndex();
    }
}
