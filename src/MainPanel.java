import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
public class MainPanel extends JPanel{


    private final int RECTANGLE_WIDTH = 20;
    private final int RECTANGLE_HEIGHT = 20;
    private final int RECTANGLE_COUNT_X = 30;
    private final int RECTANGLE_COUNT_Y = 30;

    private ArrayList<Shape> grid;
    private ArrayList<Shape> lines;
    private ArrayList<Shape> points;
    private ArrayList<Shape> closedNodes;
    private ArrayList<Shape> openNodes;
    private ArrayList<Shape> pathNodes;

    private ArrayList<Integer> blockers;

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
        blockers = new ArrayList<>(RECTANGLE_COUNT_X*RECTANGLE_COUNT_Y);

        for (int i = 0; i < RECTANGLE_COUNT_X; i++){
            for (int j = 0; j < RECTANGLE_COUNT_Y; j++){
                grid.add(new Rectangle(i*RECTANGLE_WIDTH, j*RECTANGLE_HEIGHT, RECTANGLE_WIDTH, RECTANGLE_HEIGHT));
            }
        }
    }
    public void refreshBoard() {
        lines.clear();
        points.clear();
        pathNodes.clear();
        openNodes.clear();
        closedNodes.clear();
        blockers.clear();
        repaint();
    }
    public void removeLines() {
        blockers.clear();
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
                            if (end.getIndex() != i){
                                blockers.add(i);
                            }
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
                                    start = new Point(i,me.getX()/RECTANGLE_WIDTH,me.getY()/RECTANGLE_HEIGHT);
                                } else {
                                    end = new Point(i,me.getX()/RECTANGLE_WIDTH,me.getY()/RECTANGLE_HEIGHT);
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
    public void Solve(){
        Solve solver = new Solve(start,end,blockers,RECTANGLE_COUNT_X,RECTANGLE_COUNT_Y);
        (new Thread() {
            public void run(){
                while (true){
                    if(solver.makeMove()){
                        ArrayList<Point> openSet = solver.getOpen();
                        ArrayList<Point> closedSet = solver.getClosed();

                        closedNodes.clear();
                        openNodes.clear();

                        for (int i = 0; i < openSet.size(); i++){
                            openNodes.add(grid.get(openSet.get(i).getIndex()));
                        }
                        for (int i = 0; i < closedSet.size(); i++){
                            closedNodes.add(grid.get(closedSet.get(i).getIndex()));
                        }
                        repaint();
                        try {
                            Thread.sleep(5);
                        } 
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        ArrayList<Point> pathSet = solver.getPath();
                        for (int i = 0; i < pathSet.size(); i++){
                            pathNodes.add(grid.get(pathSet.get(i).getIndex()));
                        }
                        repaint();         
                        break;
                    }
                }
            }
        }).start();
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
