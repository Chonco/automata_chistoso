import table.ResultsTable;
import validation.Validation;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main extends JPanel {
    private final JFileChooser fileChooser;
    private final ResultsTable resultsTable;
    private File file;

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
        processButton.setEnabled(false);
        processButton.addActionListener(e -> {
            if (file == null) return;

            String fileContent = readFile(file);
            Validation validation = new Validation(fileContent);
            resultsTable.setResults(validation.validate());

            processButton.setEnabled(false);
        });

        JButton selectFileButton = new JButton("Elegir Archivo");
        selectFileButton.addActionListener(e -> {
            int returnVal = fileChooser.showOpenDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file = fileChooser.getSelectedFile();
                selectFileButton.setText(file.getName());
                processButton.setEnabled(true);
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

    private String readFile(File file) {
        StringBuilder sb = new StringBuilder();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine());
                sb.append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return sb.toString();
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
