import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SnakeFrame extends JFrame implements ActionListener {

    String[] speed = new String[]{"fast","extra","med","slow"};
    JComboBox<String> comboBox;
    SnakePanel snakePanel ;;
    JButton newGameButton ;
    JPanel menuPanel;

    public SnakeFrame() {
        init();
    }
    private void init() {

        initButton();
        initComboBox();

        menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(1,2));
        menuPanel.add(newGameButton);
        menuPanel.add(comboBox);

        snakePanel = new SnakePanel();

        this.setTitle("Snake");
        this.setLayout(new BorderLayout());

        this.add(snakePanel,BorderLayout.NORTH);
        this.add(menuPanel,BorderLayout.SOUTH);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    private void initComboBox() {

        comboBox = new JComboBox<>(speed);
        comboBox.setFont(new Font("TimesNewRoman", Font.BOLD,20));
        comboBox.addActionListener(this);
    }

    private void initButton() {

        newGameButton = new JButton("new Game");
        newGameButton.setFont(new Font("TimesNewRoman", Font.BOLD,20));
        newGameButton.addActionListener(this);
    }



    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == newGameButton) {
            snakePanel.init();
            snakePanel.startGame();
            snakePanel.stopped = false;
            snakePanel.requestFocus();
        }

        if(e.getSource() == comboBox) {
            int speed = comboBox.getSelectedIndex();
            switch (speed){
                case 0 :
                    snakePanel.timer.setDelay(100);
                    break;
                case 1 :
                    snakePanel.timer.setDelay(50);
                    break;
                case 2 :
                    snakePanel.timer.setDelay(175);
                    break;
                case 3 :
                    snakePanel.timer.setDelay(225);
                    break;
            }
            snakePanel.requestFocus();
        }
    }
}
