import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Player logic is within this class
 * Minimal comments as code is fairly self-explanatory
 * 
 * @author Bill Xiang 
 * @version 2.0 
 */
public class Player extends Actor
{

    private static final int MENU = 0;
    private static final int PLAYING = 1;
    private static final int SUSPENDED = 2;
    private static final int NEXTLEVEL = 3;
    private static final int GAMEOVER = 4;
    private static final int WIN = 5;
 
    int[] xPos = new int[1000000];
    int[] yPos = new int[1000000];
    int i = 0;
    
    int dx = 6;
    int dy = 5;
//    int timer = 0;
    
    public void act() 
    {
        /*
        if (i < 1000) {
            xPos[i] = getX();
            xPos[i]= getY();
            System.out.println(""+ xPos[i] + " " + yPos[i]);
            i++;
        } else {
            setLocation(xPos[i-900], yPos[i-900]);
            i++;
        }
        */
         MyWorld myWorld = (MyWorld)(getWorld());
        if (myWorld.getState() == PLAYING || myWorld.getState() == NEXTLEVEL) {
            
            /** movement keys*/
            if (Greenfoot.isKeyDown("left")) {
                if (getWorld().getObjectsAt(getX() - getImage().getWidth()/2-1, getY(), Mushroom.class).size() == 0){
                    setLocation(getX() - dx, getY());
                }
           } else if (Greenfoot.isKeyDown("right")) {
               if (getWorld().getObjectsAt(getX() + getImage().getWidth()/2+1, getY(), Mushroom.class).size() == 0){
                    setLocation(getX() + dx, getY());
               } 
            } else if (Greenfoot.isKeyDown("up")) {
                if (getWorld().getObjectsAt(getX(), getY() - getImage().getHeight()/2-1, Mushroom.class).size() == 0){
                    setLocation(getX(), getY() - dy);
                } 
                
            } else if (Greenfoot.isKeyDown("down")) {
                if (getWorld().getObjectsAt(getX(), getY() + getImage().getHeight()/2+1, Mushroom.class).size() == 0){
                    setLocation(getX(), getY() + dy);
                } 
                
            }
            
            GreenfootImage img = getImage();
            
            /** movement restrictions */
            if (getX() + img.getWidth()/2 > MyWorld.WORLD_W) {
                setLocation(MyWorld.WORLD_W - img.getWidth()/2, getY());
            }
            
            else if (getX() - img.getWidth()/2 < 0) {
                setLocation(img.getWidth()/2, getY());
            }
            
            else if (getY() + img.getHeight()/2 > MyWorld.WORLD_H) {
                setLocation(getX(), MyWorld.WORLD_H - img.getHeight()/2);
            }
            
            else if (getY() - img.getHeight()/2 < MyWorld.WORLD_H * 0.8) {
                setLocation(getX(), (int)(MyWorld.WORLD_H * 0.8) + img.getHeight()/2);
            }
            
    /*        
            if (timer % Laser.COOLDOWN_TIME == 0) {
                if (Greenfoot.isKeyDown("space")) {
                    Laser laser = new Laser();
                    laser.getImage().scale(20, 20);
                    laser.getImage().rotate(270);
                    getWorld().addObject(laser, getX(), getY());
                }
            }
    */        
            /** Spawns a laser.*/
            if (myWorld.getState() == PLAYING) {
                if (Greenfoot.isKeyDown("space")){
                    List lasersList = getWorld().getObjects(Laser.class);
                    if (lasersList.isEmpty()) {
                        Laser laser = new Laser();
                        laser.getImage().scale(20, 20);
                        //laser.getImage().rotate(270);
                        getWorld().addObject(laser, getX(), getY() - laser.getImage().getHeight()* 3/4);
                    }
                    //check there is any laser in custom world
                    //if there is no laser, then create an instance of laser
                    //add to the custom world above the top edge of the player      
                }
            }   
            
    //        timer++;
        }
   
    }    
}
