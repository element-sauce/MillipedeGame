import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Subclass of Text designed to handle the score.
 * 
 * @author Bill Xiang
 * @version 2.0
 */
public class Score extends Text
{
    /**
     * Act - do whatever the Score wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int score = 0;
    
    public Score(String text, int size, Color foreground, Color background, int lock) {
        super(text, size, foreground, background, lock);
    }
    
    public Score(int score) {
        this.score = score;
    }
    
    public int getScore() {
        return score;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
    
    public void act() 
    {
        super.setText("" + score);
    }    
}
