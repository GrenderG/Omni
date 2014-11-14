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
 * @author Campus
 */
public class WriteToJSON {
    
    private static final File users = new File("users.json");
    private static final File tmpCopyUsers = new File("tmpCopyUsers.json");
    private static FileWriter fw;
    private static final Gson gson = new Gson();
    private BufferedReader br;

    public void writeElement(User user){
     
        try {
            
            if (!users.exists())
                users.createNewFile();
            fw = new FileWriter(users, true);            
            
            fw.write(gson.toJson(user)+"\n");
            
            fw.flush();
            fw.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }       
    }
    
    public void updateElement(User user){

        try {
            
            if (!users.exists())
                users.createNewFile();
            
            fw = new FileWriter(tmpCopyUsers, true);
            br = new BufferedReader(new FileReader(users));

            String line = br.readLine();

            while (line != null) {              

                if (line.contains(user.getNombre()) &&
                        line.contains(user.getPass())) {

                    fw.write(gson.toJson(user)+"\n");
                    
                } else {
                    fw.write(line+"\n");
                }
                
                line = br.readLine();
                
            }          
            
            br.close();
            fw.flush();
            fw.close();
            
            new File("users.json").delete();
            tmpCopyUsers.renameTo(new File("users.json"));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}