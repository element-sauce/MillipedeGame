import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Extends the Mushroom class.
 * Overrides the specialEndBehavior method and the changeImage method.
 * 
 * When destroyed, the CentipedeMushroom spawns a centipede.
 * 
 * @author Bill Xiang
 * @version 2.0
 */
public class CentipedeMushroom extends Mushroom
{
    /**  This mushroom gives off a different point value than the base point 
       value of the normal mushroom*/
    public CentipedeMushroom() {
        super.scoreValue = 20;
        
    }
    
    @Override
    public void specialEndBehavior() {
        for(int i = 0; i < 1; i++) {
            Centipede cent = new Centipede();
            cent.level = ((MyWorld)getWorld()).getLevel();
            cent.dx = 3;
            GreenfootImage img = cent.getImage(); 
            img.scale(MyWorld.GRID_SIZE, MyWorld.GRID_SIZE);
            
            /** if to-be-spawned centipede is on a location whose x-coordinate can be obtained by the
               rule x = f(m) = 10 + 40m, where x is the coordinate, multiple which can be any positive integer
               , then do this*/
            if ((this.getX() - MyWorld.GRID_SIZE/2) % 40 == 0) {
                cent.dx *= 1;
                
            } else if ((this.getX() - MyWorld.GRID_SIZE*3/2) % 40 == 0) {
                /** if to-be-spawned centipede is on a location whose x-coordinate can be obtained by the
               rule x = f(m) = 30 + 40m, where x is the coordinate, multiple which can be any positive integer
               , then do this*/
                
               /** I multiply dx by -1 before spawning it, because there is a chance that it goes the opposite
                  direction of the rest of the centipedes spawned at the beginning of the level, and collides
                  with them, causing a collision where neither can move because at the time of the writing of
                  this comment, I have not written code to deal with this scenario in the Centipede class*/
               cent.dx *= -1;
            }
            
            getWorld().addObject(cent, this.getX(), this.getY());
        }
        
        getWorld().removeObject(this);
    }
    
    /** Overrides specialActiveBehavior to cancel out the logic that was written for the normal mushroom
     * class in this method.
     */
    @Override
    public void specialActiveBehavior() {
        
    }
    
    /** Overrides changeImage to use images meant for this mushroom.
     * 
     */
    @Override
    public void changeImage() {
        GreenfootImage image = null;
        if (numHits == 1) {
            image = new GreenfootImage("centMushroom_002.png");
        } else if (numHits == 2) {
            image = new GreenfootImage("centMushroom_003.png");
        } else if (numHits == 3) {
            image = new GreenfootImage("centMushroom_004.png");
        }
        int gridSize = ((MyWorld)getWorld()).GRID_SIZE;
        image.scale(gridSize, gridSize);
        setImage(image);
    }
    
}
