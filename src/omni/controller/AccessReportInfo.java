/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package omni.controller;

/**
 *
 * @author Grender
 */
public class AccessReportInfo {
    
    private String name;
    private int times;
    
    public AccessReportInfo(String name, int times){
        this.name = name;
        this.times = times;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }
    
}
