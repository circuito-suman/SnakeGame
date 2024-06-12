package src;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.Timer;
import javax.swing.*;

public class Panel extends JPanel implements ActionListener{
     static final long version= 1L;
     static final int _height=500;
     static final int _width=500;
     static final int _unit_size=20;
     static final int _number_of_game_units=(_width*_height)/(_unit_size*_unit_size);

     final int x[] = new int [_number_of_game_units]; // for x coordinates of body parts
     final int y[] = new int [_number_of_game_units]; // for y coordinates of body parts

     int _init_length =4;
     int _foodX , _foodY;
     int _food_eaten;
     char _direction ='D';
     boolean _game_status = false;
    
     Random _random;
     Timer _timer;

     Panel(){
          _random = new Random();
          this.setPreferredSize(new Dimension(_width, _height));
          this.setBackground(Color.black);
          this.addKeyListener(new MyKeyAdapter());
          this.setFocusable(true);
          play();

     }
     public void play(){
          // reset to default
          _init_length = 4;
          _food_eaten = 0;
          _direction = 'D';
          _game_status = true;
          for (int i = 0; i < _number_of_game_units; i++) {
               x[i] = 0;
               y[i] = 0;
          }
          x[0] = _unit_size;
          y[0] = _unit_size;

          //Remove components after replay
          this.removeAll();
          this.revalidate();
          this.repaint();


          addFood();
          _timer = new Timer(100, this);
          _timer.start();
     }

     @Override
     public void paintComponent(Graphics g){
          super.paintComponent(g);
          draw(g);
     }

     public void move(){
          for(int i=_init_length;i>0;i--){
               // shift the snake one unit to desired dirrection to create a move
               x[i]=x[i-1];
               y[i]=y[i-1];
          }
          // update head of snake

          if (_direction=='l'){
               x[0]=x[0]-_unit_size;
          }
          else if (_direction =='r'){
               x[0]=x[0]+_unit_size;
          }
          else if (_direction=='u'){
               y[0]=y[0]-_unit_size;
          }
          else {
               y[0]=y[0]+_unit_size;
          }
          
     }
     public void checkforFood(){
          if(x[0]==_foodX && y[0]==_foodY){
               _init_length++;
               _food_eaten++;
               addFood();

          }
     }

     public void draw(Graphics g){
          if(_game_status){
               g.setColor(Color.red);
               g.fillOval(_foodX,_foodY,_unit_size,_unit_size);
               g.setColor(Color.white);
               g.fillOval(x[0], y[0], _unit_size, _unit_size);

               for(int i=1;i<_init_length;i++){
                    g.setColor(Color.GREEN);
                    g.fillOval(x[i], y[i], _unit_size, _unit_size);
               }

               g.setColor(Color.white);
               g.setFont( new Font("Sans serif", Font.ROMAN_BASELINE, 25));
               FontMetrics _metrics = getFontMetrics(g.getFont());
               g.drawString("Score: " + _food_eaten, (_width - _metrics.stringWidth("Score: " + _food_eaten))/2, g.getFont().getSize());
          }
          else {
               gameOver(g);
          }
     }

     public void addFood(){
          _foodX = _random.nextInt((int)_width/_unit_size)*_unit_size;
          _foodY = _random.nextInt((int)_height/_unit_size)*_unit_size;
     }

     public void checkforhit(){
          // Check if the head had run into its body
          for(int i=_init_length;i>0;i--){
               if(x[0]==x[i] && y[0]==y[i]){
                    _game_status = false;
               }
          }

          // Check if the head had run into the walls
          if(x[0]<0||x[0]>_width || y[0]<0||y[0]>_height){
               _game_status = false;
          }

          if(!_game_status){
               _timer.stop();
          }
     }

     public void gameOver(Graphics g){
          g.setColor(Color.red);
          g.setFont( new Font("Sans serif", Font.ROMAN_BASELINE, 50));
          FontMetrics _metrics = getFontMetrics(g.getFont());
          g.drawString(" Game Over ", (_width-_metrics.stringWidth(" Game Over "))/2, _height/2);

          g.setColor(Color.white);
          g.setFont( new Font("Sans serif", Font.ROMAN_BASELINE, 25));
          _metrics = getFontMetrics(g.getFont());
          g.drawString("Score: " + _food_eaten, (_width - _metrics.stringWidth("Score: " + _food_eaten))/2, g.getFont().getSize());

          // replay options
          JButton replayButton = new JButton("Replay");
          replayButton.setForeground(Color.BLUE);
          replayButton.setBounds((_width - 100) / 2, _height / 2 + 50, 100, 50); 
          replayButton.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                    play(); 
               }
          });
          this.setLayout(null);
          this.add(replayButton);
          this.revalidate();
          this.repaint();
     }

     @Override
     public void actionPerformed(ActionEvent arg0){
          if(_game_status){
               move();
               checkforFood();
               checkforhit();
          }
          repaint();
     }

     public class MyKeyAdapter extends KeyAdapter {
          @Override
          public void keyPressed(KeyEvent e){
               switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_A:
                    if(_direction!= 'r'){
                         _direction = 'l';
                    }
                    break;
                    case KeyEvent.VK_RIGHT:
                    case KeyEvent.VK_D:
                    if(_direction!= 'l'){
                         _direction = 'r';
                    }
                    break;
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_W:
                    if(_direction!= 'd'){
                         _direction = 'u';
                    }
                    break;
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_S:
                    if(_direction!= 'u'){
                         _direction = 'd';
                    }
                    break;
               }     
          }
     
     } 
}