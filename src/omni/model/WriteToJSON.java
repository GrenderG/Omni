/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package omni.model;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import omni.controller.User;

/**
 *
 * @author Campus
 */
public class WriteToJSON {
    
    private static final File users = new File("users.json");
    private static FileWriter fw;
    private static final Gson gson = new Gson();
    
    public void writeElement(User user){
        
        try {
            fw = new FileWriter(users, true);
            fw.write(gson.toJson(user));
            
            fw.flush();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
}
