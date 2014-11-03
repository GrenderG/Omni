/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package omni.controller;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author grender
 */
public class ButtonHandler extends JButton{
    
    private final int panelWidth;
    
    private final int XSpace;
    private final int YSpace;
    private final int buttonSize = 128;
    
    private static int rowCount = 1;
    private static int columnCount = 1;
    
    public ButtonHandler(String info, int width, int height){
        this.panelWidth = width;
        this.XSpace = panelWidth / 8;
        this.YSpace = XSpace;
        this.setSize(buttonSize, buttonSize);
        this.setLocation(columnCount * XSpace+(int)panelWidth/10, rowCount * YSpace);
            
        if (info.contains("/res/"))
            this.setIcon(new ImageIcon(info));
        else
            this.setText(info);
        
        columnCount++;
        
        if (columnCount > 4){
            columnCount = 1;
            rowCount++;
        }
        
    }
        
}
