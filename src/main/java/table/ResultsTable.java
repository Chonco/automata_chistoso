package table;

import validation.Token;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultsTable implements TableModel {

    private Map<Token, Integer> results;
    private final List<TableModelListener> listeners;

    public ResultsTable() {
        this.listeners = new ArrayList<>();
        this.results = new HashMap<>();
    }

    public void setResults(Map<Token, Integer> results) {
        this.results = results;

        TableModelEvent event = new TableModelEvent(
                this,
                0,
                getRowCount() - 1,
                1,
                TableModelEvent.UPDATE
        );

        listeners.forEach(listener -> listener.tableChanged(event));
    }

    @Override
    public int getRowCount() {
        return Token.values().length;
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> "Tipo de Token";
            case 1 -> "Cantidad";
            default -> "";
        };
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> String.class;
            case 1 -> Integer.class;
            default -> Object.class;
        };
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        final Token token = Token.values()[rowIndex];

        return switch (columnIndex) {
            case 0 -> token.getTagName();
            case 1 -> this.results.get(token);
            default -> "";
        };
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        // Since this table won't be editable, this method doesn't require to be implemented
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        listeners.add(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        listeners.remove(l);
    }
}
