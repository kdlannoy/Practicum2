/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eventbroker;

import alarmevent.*;

/**
 *
 * @author jrvdvyve
 */
public class Main {
    
    public static void main(String[] args){
        
        //als we een object hospital aanmaken, dan gebeurt in de constructor van hospital zelf een toewijzing (van luisteren), dus we moeten deze objecten niet meer bijhouden, enkel aanmaken
        new Hospital("UZ");
        new Hospital("AZ");
        new PoliceDepartment();
        new FireDepartment();
        
        EmergencyCallCenter callCenter = new EmergencyCallCenter("112");
        EmergencyCallCenter callCenterPolice = new EmergencyCallCenter("101");
        
        callCenter.incomingCall("crash", "Plateaustraat");
        callCenter.incomingCall("assault", "Veldstraat");
        callCenter.incomingCall("fire", "Zwijnaardse Steenweg");
        callCenter.incomingCall("alien attack", "Voskenslaan");
        callCenterPolice.incomingCall("dangerous people", "Langestraat");
        
        
    }
    
}
