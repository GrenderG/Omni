/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package omni.controller;

import java.util.ArrayList;

/**
 *
 * @author Campus
 */
public class User {

    private String nombre;
    private String pass;
    private ArrayList<String[]> accesos = new ArrayList<>();
    private static User actualUser;
    
    public User(){
        
    }
    
    public User(String nombre, String pass) {
        this.nombre = nombre;
        this.pass = pass;
        actualUser = this;
    }

    public void setAcceso(String tipo, String nombre_acc, String path,
            String imgPath) {
        accesos.add(new String[]{tipo, nombre_acc, path, imgPath});
    }

    public ArrayList<String[]> getAcceso() {
        return this.accesos;
    }
    
    public String getNombre(){
        return this.nombre;
    }
    
    public String getPass(){
        return this.pass;
    }
    
    public User getUser(){
        return this.actualUser;
    }
}
