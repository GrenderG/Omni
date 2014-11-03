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
    
    private final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    
    public double getScreenWidth(){
        return this.dim.getWidth();
    }
    
    public double getScreenHeight(){
        return this.dim.getHeight();
    }
           
    public double getMainWidth(){
        // Si hay mas de un monitor, entraremos en el primer if.
        if (getScreenWidth() > (getScreenHeight() * 2))
            return this.getScreenWidth() * 0.3;
        else
            return this.getScreenWidth() * 0.6;
    }
    
    public double getMainHeight(){
        return this.getScreenHeight() * 0.6;
    }
    
}
