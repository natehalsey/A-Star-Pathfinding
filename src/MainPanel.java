import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
public class MainPanel extends JPanel{


    private final int RECTANGLE_WIDTH = 10;
    private final int RECTANGLE_HEIGHT = 10;
    private final int RECTANGLE_COUNT_X = 60;
    private final int RECTANGLE_COUNT_Y = 60;

    private final int width = RECTANGLE_COUNT_X*RECTANGLE_WIDTH;
    private final int height = RECTANGLE_COUNT_Y*RECTANGLE_HEIGHT;

    private ArrayList<Shape> grid;
    private ArrayList<Shape> lines;
    private ArrayList<Shape> points;
    private ArrayList<Shape> closedNodes;
    private ArrayList<Shape> openNodes;
    private ArrayList<Shape> pathNodes;

    private Point start;
    private Point end;

    public MainPanel(int width, int height){
        setSize(width,height);
        setFocusable(true);
        setLayout(null);
        setVisible(true);
        init_arrays();
        mouseListeners();

    }
    private void init_arrays(){
        grid  = new ArrayList<>(RECTANGLE_COUNT_X*RECTANGLE_COUNT_Y);
        lines = new ArrayList<>(RECTANGLE_COUNT_X*RECTANGLE_COUNT_Y);
        points = new ArrayList<>(RECTANGLE_COUNT_X*RECTANGLE_COUNT_Y);
        openNodes = new ArrayList<>(RECTANGLE_COUNT_X*RECTANGLE_COUNT_Y);
        closedNodes = new ArrayList<>(RECTANGLE_COUNT_X*RECTANGLE_COUNT_Y);
        pathNodes = new ArrayList<>(RECTANGLE_COUNT_X*RECTANGLE_COUNT_Y);
        for (int i = 0; i < RECTANGLE_COUNT_X; i++){
            for (int j = 0; j < RECTANGLE_COUNT_Y; j++){
                grid.add(new Rectangle(i*RECTANGLE_WIDTH, j*RECTANGLE_HEIGHT, RECTANGLE_WIDTH, RECTANGLE_HEIGHT));
            }
        }
    }
    public void updatePath(Stack<Point> path){
        while (!path.empty()){
            int coordinate = (path.peek().getY()*RECTANGLE_COUNT_X) + path.peek().getX();
            pathNodes.add(grid.get(coordinate));
            path.pop();
        }
        repaint();
    }
    public void updateClosed(Point point){
        int coordinate = (point.getY()*RECTANGLE_COUNT_X) + point.getX();
        closedNodes.add(grid.get(coordinate));
        repaint();
    }
    public void updateOpen(Point point){
        int coordinate = (point.getY()*RECTANGLE_COUNT_X) + point.getX();
        openNodes.add(grid.get(coordinate));
        repaint();
        
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
    private void mouseListeners(){
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
                }
            }
        });
        addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent me){
                if (points.size() < 2){
                    for (int i = 0; i < grid.size(); i++){
                        Shape shape = grid.get(i);
                        if (shape.contains(me.getPoint())){
                            if (!points.contains(shape)){
                                if (points.size() == 0){
                                    start = new Point(me.getX()/RECTANGLE_WIDTH,me.getY()/RECTANGLE_HEIGHT);
                                } else {
                                    end = new Point(me.getX()/RECTANGLE_WIDTH,me.getY()/RECTANGLE_HEIGHT);
                                }
                                points.add(shape);
                                repaint();
                            }
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
        g2d.setColor(Color.green);
        for (Shape cell : points){
            g2d.fill(cell);
        }
        g2d.setColor(Color.red);
        for (Shape cell : closedNodes){
            g2d.fill(cell);
        }
        g2d.setColor(Color.blue);
        for (Shape cell : openNodes){
            g2d.fill(cell);
        }
        g2d.setColor(Color.green);
        for (Shape cell : pathNodes){
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
