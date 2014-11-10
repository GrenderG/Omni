/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package omni.visual;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author grender
 */
public class ColorRenderer extends JLabel implements TableCellRenderer {

    private static final Color BG_RESOURCE_LIGHT_BLUE = new Color(235, 248, 255);
    private static final Color BG_GENERAL_BLUE = new Color(202, 238, 255);

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        if (isSelected) {
            this.setBackground(BG_RESOURCE_LIGHT_BLUE);
            this.setForeground(Color.black);
        } else {
            this.setBackground(BG_GENERAL_BLUE);
            this.setForeground(Color.black);
        }

        this.setFont(new Font(this.getFont().getFontName(), Font.PLAIN, 14));
        this.setText(value.toString());
        this.setOpaque(true);

        return this;

    }

}
