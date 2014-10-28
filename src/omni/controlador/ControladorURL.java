/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package omni.controlador;

/**
 *
 * @author grender
 */
public class ControladorURL {

    public static void openUrl(String url) {

        String osName = System.getProperty("os.name");
        System.out.println(osName);

        try {

            if (osName.equals("Windows")) {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
            } else if (osName.equals("Linux")) {
                Runtime.getRuntime().exec("/usr/bin/firefox -new-window " + url);
            }

        } catch (Exception e) {
            System.out.println("No se ha podido abrir el navegador");
        }
    }
}
