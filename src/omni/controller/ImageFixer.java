/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package omni.controller;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author Grender
 */
public class ImageFixer {

    /*Arregla que no se pueda visualizar si contiene espacios.*/
    public File spacePathFixer(File f) {

        if (f.toString().contains("%20")) {
            String replacedPath = (f.toString().replace("%20", " "));
            return new File(replacedPath);
        } else {
            return f;
        }
    }
    
    /*Escala la imagen*/
    public Image scaleImage(File f, int width, int height) {
        BufferedImage img = null;
        Image scaledImage;

        try {
            img = ImageIO.read(f.toURI().toURL());
        } catch (Exception e) {
            e.printStackTrace();
        }

        scaledImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        return scaledImage;

    }

}
