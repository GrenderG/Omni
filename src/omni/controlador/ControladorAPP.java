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
public class ControladorAPP {
     
    public static void openApp(String url){
        Runtime app = Runtime.getRuntime();
        try{
            app.exec(url);
        }catch(Exception e){
            System.out.println("No se ha podido abrir la aplicacion");
            e.printStackTrace();
        }
    }
}
