package src;
 import javax.swing.JFrame;

 public class Frame extends JFrame{
    static final long version= 1L;

    Frame(){
        Panel _panel = new Panel();
        this.add(_panel);
        this.setTitle("Snake");
        this.setIconImage(getIconImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}

