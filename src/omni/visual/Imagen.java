/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package omni.visual;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ImageIcon;

/**
 *
 * @author Grender
 */
public class Imagen extends javax.swing.JPanel {
        
        private int width;
        private int height;
        
        private String path;
    
        public Imagen(int width, int height, String path) {
            this.setSize(width, height);
            this.width = width;
            this.height = height;
            this.path = path;
        }

        @Override
        public void paint(Graphics grafico) {
            Dimension height = getSize();

            ImageIcon Img = new ImageIcon(getClass().getResource(path));

            grafico.drawImage(Img.getImage(), 0, 0, height.width, height.height, null);

            setOpaque(false);
            super.paintComponent(grafico);
        }
    }
