import javax.swing.*;
import javax.swing.event.*;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
public class MainFrame extends JFrame{
    private final int RECTANGLE_WIDTH = 10;
    private final int RECTANGLE_HEIGHT = 10;
    private final int RECTANGLE_COUNT_X = 60;
    private final int RECTANGLE_COUNT_Y = 60;

    private final int width = RECTANGLE_COUNT_X*RECTANGLE_WIDTH;
    private final int height = RECTANGLE_COUNT_Y*RECTANGLE_HEIGHT;

    private ArrayList<Shape> grid;
    private ArrayList<Shape> lines;
    private ArrayList<Shape> points;

    private MainPanel main;
    private JMenuBar menuBar;
    private JMenuItem newMenu;
    private JMenuItem clearLines;

    public MainFrame(){

        main = new MainPanel();
        menuBar = new MenuBar();

        setJMenuBar(menuBar);
        add(main);
        setSize(width,height);
        setLayout(null);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        repaint();
    }
    private class MainPanel extends JPanel{
        public MainPanel(){
            setSize(width,height);
            setFocusable(true);
            setLayout(null);
            setVisible(true);
            init_arrays();
            mouseListener();

        }
        private void init_arrays(){
            grid  = new ArrayList<>(RECTANGLE_COUNT_X*RECTANGLE_COUNT_Y);
            lines = new ArrayList<>(RECTANGLE_COUNT_X*RECTANGLE_COUNT_Y);
            points = new ArrayList<>(RECTANGLE_COUNT_X*RECTANGLE_COUNT_Y);
            for (int i = 0; i < RECTANGLE_COUNT_X; i++){
                for (int j = 0; j < RECTANGLE_COUNT_Y; j++){
                    grid.add(new Rectangle(i*RECTANGLE_WIDTH, j*RECTANGLE_HEIGHT, RECTANGLE_WIDTH, RECTANGLE_HEIGHT));
                }
            }
        }
        public void refreshBoard() {
            lines.clear();
            points.clear();
            repaint();
        }
        public void removeLines() {
            lines.clear();
            repaint();
        }
        // issues: can fill the same spot twice, mouse needs to be dragged to fill square current during start/end,
        // start / end not unique and we won't be able to differentiate during the solve process
        public void mouseListener(){
            addMouseMotionListener(new MouseInputAdapter(){
                public void mouseDragged(MouseEvent me){
                    if (points.size() == 2){
                        for (int i = 0; i < grid.size(); i++){
                            Shape shape = grid.get(i);
                            if (shape.contains(me.getPoint())){
                                lines.add(shape);
                                repaint();
                            }
                        }
                    } else {
                        for (int i = 0; i < grid.size(); i++){
                            Shape shape = grid.get(i);
                            if (shape.contains(me.getPoint())){
                                points.add(shape);
                                repaint();
                            }
                        }
                    }
                }
            });
        }
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.gray);
            for (Shape cell : lines){
                g2d.fill(cell);
            }
            g2d.setColor(Color.red);
            for (Shape cell : points){
                g2d.fill(cell);
            }
            g2d.setColor(Color.black);
            for (int i = 0; i < RECTANGLE_COUNT_X; i++){
                for (int j = 0; j < RECTANGLE_COUNT_Y; j++){
                    g2d.drawRect(i*RECTANGLE_WIDTH, j*RECTANGLE_HEIGHT, RECTANGLE_WIDTH, RECTANGLE_HEIGHT);
                }
            }
        }

    }
    // Menu items go here
    private class MenuBar extends JMenuBar{
        public MenuBar(){
            JMenu menu = new JMenu("Menu");
            add(menu);

            newMenu = new JMenuItem("New Game");
            clearLines = new JMenuItem("Clear Lines");

            menu.add(newMenu);
            menu.add(clearLines);

            menuListeners();
        }
        private void menuListeners(){
            newMenu.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    main.refreshBoard();
                }
            });
            clearLines.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    main.removeLines();
                }
            });
        }
    }
    public static void main(String[] args) {
        new MainFrame();
    }


}
