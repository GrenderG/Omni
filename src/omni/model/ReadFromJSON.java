/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package omni.model;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import omni.controller.User;

/**
 *
 * @author Grender
 */
public class ReadFromJSON {

    private Gson gson = new Gson();
    private BufferedReader br;
    private BufferedReader tmpBr;
    private User user;
    private static final File tmpUsers = new File("tmpUsers.json");
    private static final File users = new File("users.json");
    private static FileWriter fw;

    public User readUser(String nombre, String pass) {

        try {
            
            if (!tmpUsers.exists())
                tmpUsers.createNewFile();
            if (!users.exists())
                users.createNewFile();
            
            fw = new FileWriter(tmpUsers);
            br = new BufferedReader(new FileReader(users));
            tmpBr = new BufferedReader(new FileReader(tmpUsers));

            String line = br.readLine();

            while (line != null) {

                fw.write(line);
                user = gson.fromJson(line, User.class);

                if (user.getNombre().equals(nombre)
                        && user.getPass().equals(pass)) {
                    
                    fw.close();
                    br.close();
                    tmpBr.close();
                    
                    tmpUsers.delete();
                    
                    return user;
                } else {
                    line = br.readLine();
                }

            }
            

            fw.close();
            br.close();
            tmpBr.close();
            
            tmpUsers.delete();
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public User readUser(String nombre) {

        try {
            
            if (!tmpUsers.exists())
                tmpUsers.createNewFile();
            if (!users.exists())
                users.createNewFile();
            
            fw = new FileWriter(tmpUsers);
            br = new BufferedReader(new FileReader(users));
            tmpBr = new BufferedReader(new FileReader(tmpUsers));

            String line = br.readLine();

            while (line != null) {

                fw.write(line);
                user = gson.fromJson(line, User.class);

                if (user.getNombre().equals(nombre)) {
                    
                    fw.close();
                    br.close();
                    tmpBr.close();
                    
                    tmpUsers.delete();
                    
                    return user;
                } else {
                    line = br.readLine();
                }

            }
            

            fw.close();
            br.close();
            tmpBr.close();
            
            tmpUsers.delete();
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
}
