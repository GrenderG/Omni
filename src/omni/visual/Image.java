/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package omni.visual;

import java.awt.Dimension;
import java.awt.Graphics;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 *
 * @author Grender
 */
public class Image extends javax.swing.JPanel {
        
        private final int width;
        private final int height;
        
        private final URL url;
    
        public Image(int width, int height, URL url) {
            this.setSize(width, height);
            this.width = width;
            this.height = height;
            this.url = url;
        }

        @Override
        public void paint(Graphics grafico) {
            Dimension size = getSize();
            grafico = grafico.create();
            ImageIcon Img = new ImageIcon(url);

            grafico.drawImage(Img.getImage(), 0, 0, size.width, size.height, null);

            setOpaque(false);
            super.paintComponent(grafico);
        }
    }
