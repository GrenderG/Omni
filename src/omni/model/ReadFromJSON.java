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


/**
 *
 * @author Grender
 */
public class ReadFromJSON {

    private static final Gson gson = new Gson();
    private BufferedReader br;
    private User user;
    private static final File users = new File("users.json");

    public User readUser(String nombre, String pass) {

        try {

            if (!users.exists()) {
                users.createNewFile();
            }

            br = new BufferedReader(new FileReader(users));

            String line = br.readLine();

            while (line != null) {

                user = gson.fromJson(line, User.class);

                if (user.getNombre().equals(nombre)
                        && user.getPass().equals(pass)) {

                    br.close();

                    return user;
                } else {
                    line = br.readLine();
                }

            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public User readUser(String nombre) {

        try {

            if (!users.exists()) {
                users.createNewFile();
            }

            br = new BufferedReader(new FileReader(users));

            String line = br.readLine();

            while (line != null) {

                user = gson.fromJson(line, User.class);

                if (user.getNombre().equals(nombre)) {

                    br.close();

                    return user;
                } else {
                    line = br.readLine();
                }

            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
