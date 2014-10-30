/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package omni.model;

import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author campus
 */
public class GestionAccesosModel  extends AbstractTableModel {
    ArrayList<Object[]> datos = new ArrayList<>();
    String [] columns = {"Nombre", "Ruta", "Imagen"};
    
    Class[] types = new Class[] {java.lang.String.class, java.lang.String.class, 
            java.lang.Integer.class};
    
    public GestionAccesosModel(){
        Object[] row = new Object[3];
        
        this.datos.add(row);
    }
    
    public GestionAccesosModel (String[] info){
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
    public Class getColumnClass(int col){
        return this.types[col];
    }
    
    public void removeRow(int row){
        datos.remove(row);
        fireTableDataChanged();
    }
    
    public void addRow(){
        Object[] info = new Object[3];
        JFileChooser jf = new JFileChooser();
        
        info[0] = JOptionPane.showInputDialog(null, "Introduce nombre de la aplicación", "Selecciona nombre", 
                JOptionPane.QUESTION_MESSAGE);
        
        jf.showOpenDialog(null);
        info[1] = jf.getSelectedFile().getPath();
        
        jf.showOpenDialog(null);

        info[2] = jf.getSelectedFile().getPath();
        
        datos.add(info);
        fireTableDataChanged();
    }
    
    @Override
    public void setValueAt(Object value, int row, int col){
        this.datos.get(row)[col] = value;
    }
    
    @Override
    public boolean isCellEditable(int row, int col){
        return false;
    }
    
}
