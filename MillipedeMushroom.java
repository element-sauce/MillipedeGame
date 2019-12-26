import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Extends the Mushroom class.
 * Overrides the specialEndBehavior method and the changeImage method.
 * 
 * When destroyed, the MillipedeMushroom spawns a millipede.
 * See the Millipede class for a description of the millipede.
 * 
 * @author Bill Xiang
 * @version 1.0
 */
public class MillipedeMushroom extends Mushroom
{
    
    public MillipedeMushroom() {
        super.scoreValue = 100;
    }
    
    @Override
    public void specialEndBehavior() {
        setImage(new GreenfootImage("clear.png"));
        int x = this.getX();
        int y = this.getY();
        
        for(int i = 0; i < 5; i++) {
            if (true) {
                Millipede mill = new Millipede();
                //mill.level = ((MyWorld)getWorld()).getLevel();
                mill.dx = 4;
                GreenfootImage img = mill.getImage(); 
                img.scale(MyWorld.GRID_SIZE, MyWorld.GRID_SIZE);
                
                /** if to-be-spawned millipede is on a location whose x-coordinate can be obtained by the
                   rule x = f(m) = 10 + 40m, where x is the coordinate, multiple which can be any positive integer
                   , then do this*/
                if ((this.getX() - MyWorld.GRID_SIZE/2) % 40 == 0) {
                    mill.dx *= 1;
                    
                } else if ((this.getX() - MyWorld.GRID_SIZE*3/2) % 40 == 0) {
                    /** if to-be-spawned millipede is on a location whose x-coordinate can be obtained by the
                   rule x = f(m) = 30 + 40m, where x is the coordinate, multiple which can be any positive integer
                   , then do this*/
                    
                   /** I multiply dx by -1 before spawning it, because there is a chance that it goes the opposite
                      direction than another millipedes, and collides
                      with them, causing a collision where neither can move because at the time of the writing of
                      this comment, I have not written code to deal with this scenario in the Millipede class, in 
                      the case that I choose to spawn millipedes in the future in a way that may conflict with 
                      the direction of this millipede*/
                   mill.dx *= -1;
                }
                            
                getWorld().addObject(mill, x, y);
                if (getWorld().getWidth() - getX() < MyWorld.GRID_SIZE * 4) {
                    x -= MyWorld.GRID_SIZE;
                } else {
                    x += MyWorld.GRID_SIZE;
                }
            }
            
        }
        
        getWorld().removeObject(this);
    }
    
    @Override 
    public void specialActiveBehavior() {
        
    }
    
    @Override
    public void changeImage() {
        GreenfootImage image = null;
        if (numHits == 1) {
            image = new GreenfootImage("millMushroom_002.png");
        } else if (numHits == 2) {
            image = new GreenfootImage("millMushroom_003.png");
        } else if (numHits == 3) {
            image = new GreenfootImage("millMushroom_004.png");
        }
        int gridSize = ((MyWorld)getWorld()).GRID_SIZE;
        image.scale(gridSize, gridSize);
        setImage(image);
    }
}
