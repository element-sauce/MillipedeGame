import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Subclass of Text designed to handle time
 * 
 * Timer is not added as a text in the world therefore in
 * this case, it would not be necessary to extend the Text class
 * 
 * @author Bill Xiang 
 * @version 2.0
 */
public class Timer extends Actor
{
    int THRESHOLD = 1000;
    int speed = 0;
    boolean isIncreasing = false;
    
    private int time = 0;
    private int count = 0;
    
    public Timer(int speed, boolean isIncreasing) {
        this.speed = speed;
        this.isIncreasing = isIncreasing;
    }
    
    /**
     * Act - do whatever the Timer wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (isIncreasing) {
            if (count % (THRESHOLD - speed) == 0){
                time++;
            }
            
            
        } else {
            if (count % (THRESHOLD - speed) == 0) {
                time--;
            }
        }
        
        if ((time < Integer.MIN_VALUE + 1) || (time > Integer.MAX_VALUE - 1)) {
            time = 0;
        }
        
        count++;
    }    
    
    public void setTime(int time) {
        this.time = time;
    }
    
    public int getTime() {
        return time;
    }
}
