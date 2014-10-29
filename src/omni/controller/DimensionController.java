/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package omni.controller;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 *
 * @author grender
 */
public class DimensionController {
    
    private final Dimension tam = Toolkit.getDefaultToolkit().getScreenSize();
    
    public double getScreenWidth(){
        return this.tam.getWidth();
    }
    
    public double getScreenHeight(){
        return this.tam.getHeight();
    }
           
    public double getMainWidth(){
        return this.getScreenWidth() * 0.4;
    }
    
    public double getMainHeight(){
        return this.getScreenHeight() * 0.8;
    }
    
}
