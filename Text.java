import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.util.*;
import java.awt.Font;

/**
 * Base text class
 * 
 * @author Bill Xiang 
 * @version 2.0
 */
public class Text extends Actor
{
    /**
     * Act - do whatever the Text wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int NO_LOCK = 0;
    private int LOCK_LEFT = 1;
    private int LOCK_RIGHT = 2;
    
    private String text = "";
    private int size = 10;
    private Color foreground = Color.BLACK;
    private Color background = Color.WHITE;
    private Color outline = Color.BLACK;
    private int lock = NO_LOCK;
    
    private GreenfootImage image;
    
    public Text() {
        image = new GreenfootImage(text, size, foreground, background, outline);
        setImage(image);
    }
    
    public Text(String text) {
        this.text = text;
        image = new GreenfootImage(text, size, foreground, background, outline);
        setImage(image);
    }
    
    public Text(String text, int size, int lock) {
        this.text = text;
        this.size = size;
        if (lock >= NO_LOCK && lock <= LOCK_RIGHT) {
            this.lock = lock;
        }
        image = new GreenfootImage(text, size, foreground, background, outline);
        setImage(image);
    }
    
    public Text(String text, int size, Color foreground, Color background, int lock) {
        this.text = text;
        this.size = size;
        this.foreground = foreground;
        this.background = background;
        if (lock >= NO_LOCK && lock <= LOCK_RIGHT) {
            this.lock = lock;
        }
        image = new GreenfootImage(text, size, foreground, background, outline);
        setImage(image);
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        /** Stores original text in a string, before updated by new text, and using
          * that with the new string to determine width of char.*/
        String prevText = this.text;
        this.text = text;
        int difference = text.length() - prevText.length();
        int charWidth = getImage().getWidth() / prevText.length();
       // getImage().scale(getImage().getWidth() + difference * charWidth, getImage().getHeight());
        image = new GreenfootImage(text, size, foreground, background, outline);
        setImage(image);
        /** Moving the image appropriately to avoid extension of text in locked direction */
        if (lock == LOCK_LEFT) {
            move((difference * charWidth)/2);
        } else if (lock == LOCK_RIGHT) {
            move(-(difference * charWidth)/2);
        }
        
    }
    
    public int getSize() {
        return size;
    }
    
    public void setSize(int size) {
        this.size = size;
        setText(text);
    }
    
    public Color getForeground() {
        return foreground;
    }
    
    public void setForeground(Color foreground) {
        this.foreground = foreground;
        setText(text);
    }
    
    public Color getBackground() {
        return background;
    }
    
    public void setBackground(Color background) {
        this.background = background;
        setText(text);
    }
    
    public Color getOutline() {
        return outline;
    }
    
    public void setOutline(Color outline) {
        this.outline = outline;
        setText(text);
    }
    
    public void act() 
    {
        // Add your action code here.
        
    }    
}

