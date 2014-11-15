/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package omni.model;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 *
 * @author Campus
 */
public class User {

    private String nombre;
    private String pass;
    private ArrayList<String[]> accesos = new ArrayList<>();
    private Hashtable<String, Integer> accesosCount = new Hashtable<String, Integer>();

    public User() {

    }

    public User(String nombre, String pass) {
        this.nombre = nombre;
        this.pass = pass;
    }

    public void setAcceso(String tipo, String nombre_acc, String path,
            String imgPath) {
        accesos.add(new String[]{tipo, nombre_acc, path, imgPath});
        accesosCount.put(nombre_acc + ": (" + path + ")", 0);
    }

    public void removeAcceso(String tipo, String nombre_acc, String path,
            String imgPath) {

        boolean found = false;

        for (int i = 0; i < this.accesos.size() && !found; i++) {
            if (this.accesos.get(i)[0].equals(tipo)
                    && this.accesos.get(i)[1].equals(nombre_acc)
                    && this.accesos.get(i)[2].equals(path)
                    && this.accesos.get(i)[3].equals(imgPath)) {
                found = true;
                accesos.remove(i);
                accesosCount.remove(nombre_acc + ": (" + path + ")");
            }

        }

    }

    public void setAccesosCount(String nombre_acc, String path) {
        accesosCount.put(nombre_acc + ": (" + path + ")", accesosCount.get(nombre_acc + ": (" + path + ")") + 1);
    }

    public Hashtable<String, Integer> getAccesosCount() {
        return this.accesosCount;
    }

    public ArrayList<String[]> getAcceso() {
        return this.accesos;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getPass() {
        return this.pass;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
