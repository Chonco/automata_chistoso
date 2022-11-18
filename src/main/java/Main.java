import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Vector;

public class Main extends JPanel {
    private final JFileChooser fileChooser;

    Main() {
        fileChooser = new JFileChooser();

        setLayout(new BorderLayout());
        add(getTablePanel(), BorderLayout.CENTER);
        add(getButtonsPanel(), BorderLayout.SOUTH);
    }

    private JPanel getTablePanel() {
        Vector<String> columNames = new Vector<>();
        columNames.add("Tipo de Token");
        columNames.add("Cantidad");

        Vector<Vector<String>> data = new Vector<>();
        Vector<String> row = new Vector<>();
        row.add("Palabra reservada");
        row.add("");
        data.add(row);
        row = new Vector<>();
        row.add("Identificador");
        row.add("");
        data.add(row);
        row = new Vector<>();
        row.add("Operador Relacional");
        row.add("");
        data.add(row);
        row = new Vector<>();
        row.add("Operador Lógico");
        row.add("");
        data.add(row);
        row = new Vector<>();
        row.add("Operador Aritmético");
        row.add("");
        data.add(row);
        row = new Vector<>();
        row.add("Asignación");
        row.add("");
        data.add(row);
        row = new Vector<>();
        row.add("Número entero");
        row.add("");
        data.add(row);
        row = new Vector<>();
        row.add("Número decimal");
        row.add("");
        data.add(row);
        row = new Vector<>();
        row.add("Comentario");
        row.add("");
        data.add(row);
        row = new Vector<>();
        row.add("Paréntesis");
        row.add("");
        data.add(row);
        row = new Vector<>();
        row.add("Llaves");
        row.add("");
        data.add(row);

        JTable table = new JTable(data, columNames);
        JScrollPane jScrollPane = new JScrollPane(table);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 1));
        panel.add(jScrollPane);

        return panel;
    }

    private JPanel getButtonsPanel() {
        JButton processButton = new JButton("Procesar");

        JButton selectFileButton = new JButton("Elegir Archivo");
        selectFileButton.addActionListener(e -> {
            int returnVal = fileChooser.showOpenDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                selectFileButton.setText(file.getName());
            }
        });
        JButton exportButton = new JButton("Exportar");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        panel.add(processButton);
        panel.add(selectFileButton);
        panel.add(exportButton);

        return panel;
    }

    public static void main(String[] args) {
        JFrame jFrame = new JFrame("Autómata Chistoso");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.add(new Main());
        jFrame.setSize(300, 320);
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }
}
