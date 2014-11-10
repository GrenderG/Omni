/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package omni.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author campus
 */
public class GestionWebModel extends AbstractTableModel {

    ArrayList<Object[]> datos = new ArrayList<>();
    String[] columns = {"Nombre", "URL", "Imagen"};

    Class[] types = new Class[]{java.lang.String.class, java.lang.String.class,
        java.lang.Integer.class};

    public GestionWebModel() {

    }

    public GestionWebModel(String[] info) {
        Object[] row = new Object[3];

        row[0] = info[0];
        row[1] = info[1];
        row[2] = info[2];

        this.datos.add(row);
    }

    @Override
    public int getRowCount() {
        return this.datos.size();
    }

    @Override
    public int getColumnCount() {
        return this.columns.length;
    }

    @Override
    public Object getValueAt(int arg0, int arg1) {
        Object fila[] = this.datos.get(arg0);
        return fila[arg1];
    }

    @Override
    public String getColumnName(int col) {
        return this.columns[col];
    }

    @Override
    public Class getColumnClass(int col) {
        return this.types[col];
    }

    public void removeRow(int row) {
        datos.remove(row);
        fireTableDataChanged();
    }

    public void addRow(String name, String webURL, String imagePath) {
        Object[] info = new Object[3];

        info[0] = name;
        info[1] = webURL;
        info[2] = imagePath;

        datos.add(info);
        fireTableDataChanged();
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        this.datos.get(row)[col] = value;
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

}
