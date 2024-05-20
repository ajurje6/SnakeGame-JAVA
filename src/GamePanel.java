import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{
    static final int SCREEN_WIDTH = 800;
    static final int SCREEN_HEIGHT = 800;
    static final int UNIT_SIZE = 30;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 75;
    final int xCoordinate[] = new int[GAME_UNITS];
    final int yCoordinate[] = new int[GAME_UNITS];
    int bodyParts = 6;
    int fruitEaten;
    int fruitXCoordinate;
    int fruitYCoordinate;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel(){
        random=new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.darkGray);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame() {
        newFruit();
        running=true;
        timer=new Timer(DELAY,this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
    	if(running) {
        g.setColor(Color.cyan);
        g.fillOval(fruitXCoordinate,fruitYCoordinate,UNIT_SIZE,UNIT_SIZE);

        for(int i=0;i<bodyParts;i++) {
            if(i==0) {
                g.setColor(Color.red);
                g.fillRect(xCoordinate[i],yCoordinate[i],UNIT_SIZE,UNIT_SIZE);
            }
            else {
                g.setColor(Color.yellow);
                g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));    
                g.fillRect(xCoordinate[i],yCoordinate[i],UNIT_SIZE,UNIT_SIZE);
            }
        }
        g.setColor(Color.BLUE);
    	g.setFont(new Font("Ink Free",Font.BOLD,35));
    	FontMetrics metrics=getFontMetrics(g.getFont());
    	g.drawString("Score: "+fruitEaten,(SCREEN_WIDTH - metrics.stringWidth("Score: "+fruitEaten)) / 2,g.getFont().getSize());
    	}
    	else {
    		gameOver(g);
    	}
    }

    public void newFruit() {
    	//Creates new fruit
        fruitXCoordinate=random.nextInt((int)SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE;
        fruitYCoordinate=random.nextInt((int)SCREEN_HEIGHT/UNIT_SIZE)*UNIT_SIZE;
    }

    public void move() {
        for(int i=bodyParts;i>0;i--) {
            xCoordinate[i]=xCoordinate[i-1];
            yCoordinate[i]=yCoordinate[i-1];
        }
        switch(direction) {
            case 'R':
                xCoordinate[0]=xCoordinate[0]+UNIT_SIZE;
                break;
            case 'L':
                xCoordinate[0]=xCoordinate[0]-UNIT_SIZE;
                break;
            case 'U':
                yCoordinate[0]=yCoordinate[0]-UNIT_SIZE;
                break;
            case 'D':
                yCoordinate[0]=yCoordinate[0]+UNIT_SIZE;
                break;
        }
    }

    public void checkFruit() {
    	//Check if snake touches fruit
        if((xCoordinate[0]==fruitXCoordinate)&&(yCoordinate[0]==fruitYCoordinate)) {
        	bodyParts++;
        	fruitEaten++;
        	newFruit();
        }
    }

    public void checkCollision() {
    	//CHECK IF HEAD COLLIDES WITH BODY
       for(int i=bodyParts;i>0;i--) {
    	   if((xCoordinate[0]==xCoordinate[i])&&(yCoordinate[0]==yCoordinate[i])) {
    		   running=false;
    	   }
       }
       //CHECK IF IT COLLIDES WITH BORDER
       if(xCoordinate[0]<0) {
    	   running=false;
       }
       if(xCoordinate[0]>SCREEN_WIDTH) {
    	   running=false;
       }
       if(yCoordinate[0]<0) {
    	   running=false;
       }
       if(yCoordinate[0]>SCREEN_HEIGHT) {
    	   running=false;
       }
       if(!running) {
    	   timer.stop();
       }
    }

    public void gameOver(Graphics g) {
        // Implement game over message
    	g.setColor(Color.BLUE);
    	g.setFont(new Font("Ink Free",Font.BOLD,70));
    	FontMetrics metrics=getFontMetrics(g.getFont());
    	int messageWidth = metrics.stringWidth("GAME OVER, YOU LOST");
    	g.drawString("GAME OVER NOOB",(SCREEN_WIDTH - messageWidth) / 2,SCREEN_HEIGHT / 2);
    	 g.setColor(Color.BLUE);
     	g.setFont(new Font("Ink Free",Font.BOLD,35));
     	FontMetrics metrics2=getFontMetrics(g.getFont());
     	g.drawString("Score: "+fruitEaten,(SCREEN_WIDTH - metrics.stringWidth("Score: "+fruitEaten)) / 2,g.getFont().getSize());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running) {
            move();
            checkFruit();
            checkCollision();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }

    
}

