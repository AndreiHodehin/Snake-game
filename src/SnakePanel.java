import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class SnakePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static  int DELAY ;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts;
    int applesEaten;
    int appleX;
    int appleY;
    char direction;
    boolean running;
    boolean stopped;
    boolean firstMenu = true;
    Random random;
    Timer timer;

    public SnakePanel() {

        setDELAY(100);
        timer = new Timer(DELAY,this);
        random = new Random();

        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.addKeyListener(new MyKeyListener());
        this.setFocusable(true);

    }

    public void init(){

        bodyParts = 6;
        applesEaten = 0;
        direction = 'R';
        for (int i = 0; i < bodyParts; i++) {
            x[i] = 0;
            y[i] = 0;
        }
    }

    public void startGame(){

        newApple();
        running = true;
        timer.start();
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {

        if(firstMenu) {
            g.setColor(Color.LIGHT_GRAY);
            g.setFont(new Font("TimesRoman",Font.PLAIN,50));
            FontMetrics metrics =  getFontMetrics(g.getFont());
            g.drawString("Choose speed an run ", (SCREEN_WIDTH - metrics.stringWidth("Choose speed an run"))/2,SCREEN_HEIGHT/2);
            firstMenu = false;
        } else {
            if (running) {
                for (int i = 0; i < SCREEN_WIDTH / UNIT_SIZE; i++) {
                    g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                    g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
                }

                g.setColor(Color.red);
                g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

                g.setColor(Color.GREEN);
                for (int i = 0; i < bodyParts; i++) {
                    if (i == 0) {
                        g.setColor(Color.GREEN);
                        g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    } else {
                        g.setColor(new Color(40, 133, 4));
                        g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                    }
                }

                g.setColor(new Color(87, 19, 68));
                g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
                FontMetrics metrics = getFontMetrics(g.getFont());
                g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
            } else {
                gameOver(g);
            }
        }
    }
    private void newApple(){

        appleX = (random.nextInt(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = (random.nextInt(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }

    private void move(){

        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
        }
    }

    private void checkApple() {

        if(x[0] == appleX && y[0] == appleY) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    private void checkCollision(){

        for (int i = bodyParts; i > 0; i--) {
            if(x[0] == x[i] && y[0] == y[i]){
                running = false;
            }
        }

        if(x[0] < 0){
            running = false;
        }
        if(x[0] >= SCREEN_WIDTH){
            running = false;
        }
        if(y[0] < 0){
            running = false;
        }
        if(y[0] >= SCREEN_HEIGHT){
            running = false;
        }
        if(!running) {
            timer.stop();
        }
    }

    private void gameOver(Graphics g) {

        g.setColor(new Color(133,16,45));
        g.setFont(new Font("TimesNewRoman",Font.PLAIN,40));
        FontMetrics metrics1 =  getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());

        g.setColor(Color.red);
        g.setFont(new Font("TimesNewRoman",Font.PLAIN,70));
        FontMetrics metrics2 =  getFontMetrics(g.getFont());
        g.drawString("Game over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(running) {
            move();
            checkApple();
            checkCollision();
        }
        repaint();
    }

    public void setDELAY(int delay) {

        DELAY = delay;
    }

    public  class MyKeyListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U') {
                        direction = 'D';
                    }
                    break;

                case KeyEvent.VK_SPACE:

                    if(stopped){
                        timer.start();
                        stopped = false;
                    } else {
                    timer.stop();
                    stopped = true;
                    }
                    break;
            }
        }
    }

}
