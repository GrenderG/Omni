/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package omni.controller;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JOptionPane;

/**
 *
 * @author grender
 */
public class URLController {

    public static void openUrl(String url) {

        try {

            Desktop.getDesktop().browse(new URI(url));

        } catch (IOException | URISyntaxException ex) {
            JOptionPane.showMessageDialog(null, "Imposible abrir la URL, asegúrate de que está bien escrita.",
                    "Error al intentar abrir", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

}
