import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Laser class
 * 
 * @author Bill Xiang 
 * @version 2.0
 */
public class Laser extends Actor
{
    /**
     * Act - do whatever the Laser wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    int dy = 13;
//    static final int COOLDOWN_TIME = 20;
    
    public void act() 
    {
        // Add your action code here.
        move();
        if (getY() - getImage().getHeight()/2 < 0) {
            getWorld().removeObject(this);
        }
        

    }    
    
    public void move() {
        setLocation(getX(), getY() - dy);
    }
    
}
