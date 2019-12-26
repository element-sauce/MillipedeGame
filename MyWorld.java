import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
import java.awt.GraphicsEnvironment;

/**
 * This is my World class that extends World. 
 * 
 * I put a few comments in all the classes so that the reader will have an 
 * easier time understanding my code.
 * 
 * @author Bill Xiang
 * @version 2.0
 */
public class MyWorld extends World
{
/**  The first set of private final variables represent the game states. */
    private static final int MENU = 0;
    private static final int PLAYING = 1;
    private static final int SUSPENDED = 2;
    private static final int NEXTLEVEL = 3;
    private static final int GAMEOVER = 4;
    private static final int WIN = 5;
    
/**  The second set of private variables represent the the direction the text class should lock
 *  meaning which way the text class should grow if it gets bigger (so that it doesn't go off-screen,
 *  as it would extend in both directions by default). [EXTRA]
 */
    private int NO_LOCK = 0;
    private int LOCK_LEFT = 1;
    private int LOCK_RIGHT = 2;
    
/**  The private static level variable helps me keep track of what level I'm on. */
    private static int level = 0;

/**  The private variable state represents the current game state. */
    private int state = MENU;

/**  Adjustable game settings [EXTRA]   */
    public static int NUM_CENTIPEDES = 11;
    public static final int NUM_MUSHROOMS = 40;
    public static final int NUM_CENTIPEDEMUSHROOMS = 5;
    public static final int NUM_MILLIPEDEMUSHROOMS = 3;
    public static final int GRID_SIZE = 20;
    //public static final int WORLD_W = 480;
    //public static final int WORLD_H = 580;
    public static final int WORLD_W = 400;
    public static final int WORLD_H = 600;
    
/**  Allows you to adjust how far the mushrooms can spawn away the bottom
 *   Note that the offset from the top is always one row [EXTRA] */
    public static final int NUM_ROWS_SPAWN_ABOVE_BOTTOM = 2;

/** Variables that will hold the class containing the Title and Enter images */    
    private Title title;
    private Enter enter;

/** Pressing enter after game is over to play again */
    private boolean enterPressed = false;
    //Centipede[] centipedes = new Centipede[NUM_CENTIPEDES + NUM_CENTIPEDEMUSHROOMS];
/** All centipedes stored in an ArrayList */
    ArrayList<Centipede> centipedes = new ArrayList<Centipede>();
    ArrayList<Mushroom> mushrooms = new ArrayList<Mushroom>();
    
/** Score global variable */
    Score score;
    
    Player player;
    
    Timer timer;
    
    int levelDelay = 0;

    public MyWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        /** Title and enter initalized and added */
        
        super(WORLD_W, WORLD_H, 1, false); 
        title = new Title();
        title.getImage().scale(WORLD_W, WORLD_H);
        addObject(title, WORLD_W/2, WORLD_H/2);
        enter = new Enter();
        enter.getImage().scale(WORLD_W/2, WORLD_H/12);
        addObject(enter, WORLD_W*9/13, WORLD_H*13/18);
        
        level = 0;
        NUM_CENTIPEDES = 10;
        
        timer = new Timer(1000, true);
        /*
        String fonts[] = 
        GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    
        for ( int i = 0; i < fonts.length; i++ )
        {
          System.out.println(fonts[i]);
        }
        */

    }
    
    public void act() {
        
        if (state == MENU) {
            if (Greenfoot.isKeyDown("enter")) {
                state = PLAYING;
                removeObject(title);
                removeObject(enter);
                // Change state to PLAYING and set up the World by
                // removing the Title and adding a Player,              
                //centipede, and mushrooms 
                score = new Score("0", 30, Color.RED, Color.BLACK, LOCK_LEFT);
                addObject(score, 10, 10);
                player = new Player();
                GreenfootImage playerImage = player.getImage();
                playerImage.scale(20, 20);
                addObject(player, getWidth()/2, getHeight() - playerImage.getHeight()/2);
                
                /** centipedes adding */
                for (int i = 0; i < NUM_CENTIPEDES; i++) {
                    Centipede cent = new Centipede();
                    centipedes.add(cent);
                    GreenfootImage img = cent.getImage(); 
                    img.scale(GRID_SIZE, GRID_SIZE);
                    addObject(cent, img.getWidth()/2 + i * img.getWidth(), img.getHeight()/2);
                    //addObject(cent, img.getWidth() * 17 + i * img.getWidth(), img.getHeight()/2);
                }
                
                /** Normal mushroom adding */
                for (int i = 0; i < NUM_MUSHROOMS; i++) {
                    Mushroom mush = new Mushroom();
                    GreenfootImage mushImage = mush.getImage();
                    mushImage.scale(GRID_SIZE, GRID_SIZE);
                    /** Random number from x from 0 inclusive to world width / the grid width EXclusive, multiplied by the grid width to fit the world,
                     * and half width of mushroom added to get to center */
                    int x = Greenfoot.getRandomNumber((int)getWidth()/GRID_SIZE) * GRID_SIZE + mushImage.getWidth()/2;
                    /** Random number from 0 inclusive to the world width / the grid width EXclusive (modified based on the NUM_ROWS_SPAWN_ABOVE_BOTTOM 
                      * variable) plus one to include the excluded value and additionally make sure that no mushroom spawns on first row,
                      * multipled by the grid width to fit the world, and half width of mushroom added to get to center
                      */
                    int y = (Greenfoot.getRandomNumber((int)(getHeight()/GRID_SIZE-(1+NUM_ROWS_SPAWN_ABOVE_BOTTOM)))+1) * GRID_SIZE + mushImage.getHeight()/2;//- mushImage.getHeight()*(NUM_ROWS_SPAWN_ABOVE_BOTTOM);
                    /** loops until it spawns at an area not already taken by another mushroom */
                    while (getObjectsAt(x, y, Mushroom.class).size() != 0) {
                        x = Greenfoot.getRandomNumber((int)getWidth()/GRID_SIZE) * GRID_SIZE + mushImage.getWidth()/2;
                        y = (Greenfoot.getRandomNumber((int)(getHeight()/GRID_SIZE-(1+NUM_ROWS_SPAWN_ABOVE_BOTTOM)))+1) * GRID_SIZE + mushImage.getHeight()/2;//- mushImage.getHeight()*(NUM_ROWS_SPAWN_ABOVE_BOTTOM);
                    }
                    mushrooms.add(mush);
                    addObject(mush, x, y);
                    //System.out.println(x + " " + y);
                }
                
                /** Same logic as normal mushroom */
                for (int i = 0; i < NUM_CENTIPEDEMUSHROOMS; i++) {
                    CentipedeMushroom cmush = new CentipedeMushroom();
                    GreenfootImage mushImage = cmush.getImage();
                    mushImage.scale(GRID_SIZE, GRID_SIZE);
                    int x = Greenfoot.getRandomNumber((int)getWidth()/GRID_SIZE) * GRID_SIZE + mushImage.getWidth()/2;
                    int y = (Greenfoot.getRandomNumber((int)(getHeight()/GRID_SIZE-(1+NUM_ROWS_SPAWN_ABOVE_BOTTOM)))+1) * GRID_SIZE + mushImage.getHeight()/2;
                    while (getObjectsAt(x, y, Mushroom.class).size() != 0) {
                        x = Greenfoot.getRandomNumber((int)getWidth()/GRID_SIZE) * GRID_SIZE + mushImage.getWidth()/2;
                        y = (Greenfoot.getRandomNumber((int)(getHeight()/GRID_SIZE-(1+NUM_ROWS_SPAWN_ABOVE_BOTTOM)))+1) * GRID_SIZE + mushImage.getHeight()/2;
                    }
                    addObject(cmush, x, y);
                }
                
                for (int i = 0; i < NUM_MILLIPEDEMUSHROOMS; i++) {
                    MillipedeMushroom mmush = new MillipedeMushroom();
                    GreenfootImage mushImage = mmush.getImage();
                    mushImage.scale(GRID_SIZE, GRID_SIZE);
                    int x = Greenfoot.getRandomNumber((int)getWidth()/GRID_SIZE) * GRID_SIZE + mushImage.getWidth()/2;
                    int y = (Greenfoot.getRandomNumber((int)(getHeight()/GRID_SIZE-(1+NUM_ROWS_SPAWN_ABOVE_BOTTOM)))+1) * GRID_SIZE + mushImage.getHeight()/2;
                    while (getObjectsAt(x, y, Mushroom.class).size() != 0) {
                        x = Greenfoot.getRandomNumber((int)getWidth()/GRID_SIZE) * GRID_SIZE + mushImage.getWidth()/2;
                        y = (Greenfoot.getRandomNumber((int)(getHeight()/GRID_SIZE-(1+NUM_ROWS_SPAWN_ABOVE_BOTTOM)))+1) * GRID_SIZE + mushImage.getHeight()/2;
                    }
                    addObject(mmush, x, y);
                }

            }
        } 
        else if (state == GAMEOVER) {
            /** Displays game over, and user can press enter to restart */
            
            GameOver gameOver = new GameOver();
            gameOver.getImage().scale(WORLD_W/2, WORLD_H/8);
            addObject(gameOver, WORLD_W/2, WORLD_H*7/8);
            
            if (Greenfoot.isKeyDown("enter")) { 
                enterPressed = true;
            } else if (enterPressed) {
                enterPressed = false;
                removeObjects(getObjects(Centipede.class));
                removeObjects(getObjects(Mushroom.class));
                removeObjects(getObjects(Player.class));
                removeObjects(getObjects(GameOver.class));
                removeObjects(getObjects(Score.class));
                removeObjects(getObjects(Millipede.class));
                level = 0;
                NUM_CENTIPEDES = 10;
                state = MENU;                
                
            }
       } else if (state == WIN) {
           /** Displays you win, and user can press enter to restart */
           YouWin youWin = new YouWin();
            youWin.getImage().scale(WORLD_W/2, WORLD_H/8);
            addObject(youWin, WORLD_W/2, WORLD_H*7/8);
            
            if (Greenfoot.isKeyDown("enter")) { 
                enterPressed = true;
                    // Reset the game variables
                    // Reset state to MENU
            } else if (enterPressed) {
                enterPressed = false;
                removeObjects(getObjects(Centipede.class));
                removeObjects(getObjects(Mushroom.class));
                removeObjects(getObjects(Player.class));
                removeObjects(getObjects(YouWin.class));
                removeObjects(getObjects(Score.class));
                removeObjects(getObjects(Millipede.class));
                level = 0;
                NUM_CENTIPEDES = 10;
                
                state = MENU;
            }
    
       } else if (state == NEXTLEVEL) {
                    /**delay between levels*/
                    levelDelay++;
                    
                    /** During part of that delay, extra mushrooms spawn */
                    if (levelDelay % 20 == 0 && levelDelay <= 120) {
                        Mushroom mush = new Mushroom();
                        
                        /** creating image to get access to width method of image*/
                        GreenfootImage mushImage = new GreenfootImage((level+1) + "Mushroom_001.png");
                        mushImage.scale(GRID_SIZE, GRID_SIZE);
                        
                        mush.level = level;
                        
                        int x = Greenfoot.getRandomNumber((int)getWidth()/GRID_SIZE) * GRID_SIZE + mushImage.getWidth()/2;
                       
                        int y = (Greenfoot.getRandomNumber((int)(getHeight()/GRID_SIZE-(1+NUM_ROWS_SPAWN_ABOVE_BOTTOM)))+1) * GRID_SIZE + mushImage.getHeight()/2;//- mushImage.getHeight()*(NUM_ROWS_SPAWN_ABOVE_BOTTOM);
                        
                        while (getObjectsAt(x, y, Mushroom.class).size() != 0) {
                            x = Greenfoot.getRandomNumber((int)getWidth()/GRID_SIZE) * GRID_SIZE + mushImage.getWidth()/2;
                            y = (Greenfoot.getRandomNumber((int)(getHeight()/GRID_SIZE-(1+NUM_ROWS_SPAWN_ABOVE_BOTTOM)))+1) * GRID_SIZE + mushImage.getHeight()/2;//- mushImage.getHeight()*(NUM_ROWS_SPAWN_ABOVE_BOTTOM);
                        }
                        mushrooms.add(mush);
                        addObject(mush, x, y);
                    }
                    
                    if (levelDelay == 130 || levelDelay == 150) {
                        CentipedeMushroom cmush = new CentipedeMushroom();
                        
                        /** creating image to get access to width method of image*/
                        GreenfootImage mushImage = new GreenfootImage("centMushroom_001.png");
                        mushImage.scale(GRID_SIZE, GRID_SIZE);
                        
                        cmush.level = level;
                        cmush.setImage(mushImage);
                        
                        int x = Greenfoot.getRandomNumber((int)getWidth()/GRID_SIZE) * GRID_SIZE + mushImage.getWidth()/2;
                       
                        int y = (Greenfoot.getRandomNumber((int)(getHeight()/GRID_SIZE-(1+NUM_ROWS_SPAWN_ABOVE_BOTTOM)))+1) * GRID_SIZE + mushImage.getHeight()/2;
                        
                        while (getObjectsAt(x, y, Mushroom.class).size() != 0) {
                            x = Greenfoot.getRandomNumber((int)getWidth()/GRID_SIZE) * GRID_SIZE + mushImage.getWidth()/2;
                            y = (Greenfoot.getRandomNumber((int)(getHeight()/GRID_SIZE-(1+NUM_ROWS_SPAWN_ABOVE_BOTTOM)))+1) * GRID_SIZE + mushImage.getHeight()/2;
                        }
                        mushrooms.add(cmush);
                        addObject(cmush, x, y);
                    }
                    
                    if (levelDelay == 170) {
                        MillipedeMushroom mmush = new MillipedeMushroom();
                        
                        GreenfootImage mushImage = new GreenfootImage("millMushroom_001.png");
                        mushImage.scale(GRID_SIZE, GRID_SIZE);
                        
                        mmush.level = level;
                        mmush.setImage(mushImage);
                        
                        int x = Greenfoot.getRandomNumber((int)getWidth()/GRID_SIZE) * GRID_SIZE + mushImage.getWidth()/2;
                       
                        int y = (Greenfoot.getRandomNumber((int)(getHeight()/GRID_SIZE-(1+NUM_ROWS_SPAWN_ABOVE_BOTTOM)))+1) * GRID_SIZE + mushImage.getHeight()/2;
                        
                        while (getObjectsAt(x, y, Mushroom.class).size() != 0) {
                            x = Greenfoot.getRandomNumber((int)getWidth()/GRID_SIZE) * GRID_SIZE + mushImage.getWidth()/2;
                            y = (Greenfoot.getRandomNumber((int)(getHeight()/GRID_SIZE-(1+NUM_ROWS_SPAWN_ABOVE_BOTTOM)))+1) * GRID_SIZE + mushImage.getHeight()/2;
                        }
                        mushrooms.add(mmush);
                        addObject(mmush, x, y);
                    }
                    
                    /** after delay is over, the level updates */
                    if (levelDelay > 200) {
                        level++;
                        NUM_CENTIPEDES++;
                  
                        centipedes.clear();
                        if (level == 1) {
                            for (int i = 0; i < NUM_CENTIPEDES; i++) {
                                Centipede cent = new Centipede();
                                centipedes.add(cent);
                                GreenfootImage img = cent.getImage(); 
                                img.scale(GRID_SIZE, GRID_SIZE);
                                cent.level = 1;
                                addObject(cent, img.getWidth()/2 + i * img.getWidth(), img.getHeight()/2);
                                cent.dx = 2;
                            }
                            GreenfootImage image = new GreenfootImage("player2.png");
                            image.scale(20, 20);
                            player.setImage(image);
                            
                            for (Mushroom mush : mushrooms) {
                                mush.level = 1;
                                mush.setScoreValue(mush.getScoreValue() + 4);
                            }
                        }
                        
                        if (level == 2) {
                            for (int i = 0; i < NUM_CENTIPEDES; i++) {
                                Centipede cent = new Centipede();
                                centipedes.add(cent);
                                GreenfootImage img = cent.getImage(); 
                                img.scale(GRID_SIZE, GRID_SIZE);
                                cent.level = 2;
                                addObject(cent, img.getWidth()/2 + i * img.getWidth(), img.getHeight()/2);
                                cent.dx = 4;
                            }
                            GreenfootImage image = new GreenfootImage("player3.png");
                            image.scale(20, 20);
                            player.setImage(image);
                            
                            for (Mushroom mush : mushrooms) {
                                mush.level = 2;
                                mush.setScoreValue(mush.getScoreValue() + 5);
                            }
                        }
                        
                        if (level == 3) {
                            NUM_CENTIPEDES += 4;
                            for (int i = 0; i < NUM_CENTIPEDES; i++) {
                                Centipede cent = new Centipede();
                                centipedes.add(cent);
                                GreenfootImage img = cent.getImage(); 
                                img.scale(GRID_SIZE, GRID_SIZE);
                                cent.level = 3;
                                addObject(cent, img.getWidth()/2 + i * img.getWidth(), img.getHeight()/2);
                                cent.dx = 5;
                            }
                            GreenfootImage image = new GreenfootImage("player4.png");
                            image.scale(20, 20);
                            player.setImage(image);
                            
                            for (Mushroom mush : mushrooms) {
                                mush.level = 3;
                                mush.setScoreValue(mush.getScoreValue() + 5);
                            }
                        }
                        
                        if (level == 4) {
                            NUM_CENTIPEDES -= 3;
                            for (int i = 0; i < NUM_CENTIPEDES; i++) {
                                Centipede cent = new Centipede();
                                centipedes.add(cent);
                                GreenfootImage img = cent.getImage(); 
                                img.scale(GRID_SIZE, GRID_SIZE);
                                cent.level = 4;
                                addObject(cent, img.getWidth()/2 + i * img.getWidth(), img.getHeight()/2);
                                cent.dx = 6;
                            }
                            GreenfootImage image = new GreenfootImage("player5.png");
                            image.scale(20, 20);
                            player.setImage(image);
                            
                            for (Mushroom mush : mushrooms) {
                                mush.level = 4;
                                mush.setScoreValue(mush.getScoreValue() + 5);
                            }
                        }
                        
                        /** Reverts to PLAYING mode after level setup logic completed */
                        state = PLAYING;
                        
                        if (level == 5) {
                            state = WIN;
                        }
                    }
                    
                  
         
       } else if (state == PLAYING) {
           /** After centipedes are gone, the state switches to NEXTLEVEL mode*/
           
           if (getObjects(Centipede.class).isEmpty()) {
                    // Change state to NEXTLEVEL
                    state = NEXTLEVEL;
                    
           }
           levelDelay = 0; 
       }
       
       /** Pause and resume, using the p key */
       if (Greenfoot.isKeyDown("p") && state == PLAYING) {
            
            Paused pause = new Paused();
            pause.getImage().scale(GRID_SIZE*6, GRID_SIZE*6);
            addObject(pause, getWidth()/2, getHeight()/2);
            
            state = SUSPENDED;

            
       } else if (Greenfoot.isKeyDown("p") && state == SUSPENDED) {
           removeObjects(getObjects(Paused.class));
           state = PLAYING;
       }
       
       if (Greenfoot.isKeyDown("q") && state == PLAYING) {
                for (int i = 0; i < NUM_CENTIPEDES; i++) {
                    Centipede cent = new Centipede();
                    centipedes.add(cent);
                    GreenfootImage img = cent.getImage(); 
                    img.scale(GRID_SIZE, GRID_SIZE);
                    cent.level = 4;
                    addObject(cent, img.getWidth()/2 + i * img.getWidth(), img.getHeight()/2);
                    cent.dx = 10;
                }
               
       }
    }
    
    /** Several getter and setter methods */
    
    public int getState() {
        return state;
    }
    
    public void setState(int stateNum) {
        state = stateNum;
    }
    
    public int getLevel() {
        return level;
    }
    
    public void setLevel(int level) {
        this.level = level;
    }
    
    public int getTime() {
        return timer.getTime();
    }
    
    public void setTime(int time) {
        timer.setTime(time);
    }
} 
