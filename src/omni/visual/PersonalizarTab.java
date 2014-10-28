package omni.visual;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class PersonalizarTab extends javax.swing.plaf.basic.BasicTabbedPaneUI {

    @Override
    public void paintTab(Graphics g, int tabPlacement, Rectangle[] rects, 
               int tabIndex, Rectangle iconRect, Rectangle textRect) {
        Color savedColor = g.getColor();
        g.setColor(Color.PINK);
        g.fillRect(rects[tabIndex].x, rects[tabIndex].y, 
               rects[tabIndex].width, rects[tabIndex].height);
        g.setColor(Color.BLUE);
        g.drawRect(rects[tabIndex].x, rects[tabIndex].y, 
               rects[tabIndex].width, rects[tabIndex].height);
        g.setColor(savedColor);
    }
 }