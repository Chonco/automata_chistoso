import table.ResultsTable;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Main extends JPanel {
    private final JFileChooser fileChooser;
    private final ResultsTable resultsTable;

    Main() {
        fileChooser = new JFileChooser();
        resultsTable = new ResultsTable();

        setLayout(new BorderLayout());
        add(getTablePanel(), BorderLayout.CENTER);
        add(getButtonsPanel(), BorderLayout.SOUTH);
    }

    private JPanel getTablePanel() {
        JTable table = new JTable(resultsTable);
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
