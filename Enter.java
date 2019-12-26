import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Class holding the Enter image.
 * 
 * @author Bill Xiang
 * @version 2.0
 */
public class Enter extends Actor
{
    private int tick = 0;
    
    public Enter() {
        
    }
    
    public void act() 
    {
        if (tick > 40) {
            GreenfootImage img = new GreenfootImage("clear.png");
            setImage(img);
        } else if (tick == 1) {
            GreenfootImage img = new GreenfootImage("ENTERTOSTART.png");
            img.scale(((MyWorld)getWorld()).WORLD_W/2, ((MyWorld)getWorld()).WORLD_H/12);
            setImage(img);
        }
        
        if (tick > 80) {
            tick = 0;
        }
        
        tick++;
    }    
}
