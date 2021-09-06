import java.util.*;
public class Solve {
    private Point end;
    private Point[][] points;
    private Stack<Point> openSet;
    private Stack<Double> FScore;
    private MainPanel pane;

    public Solve(MainPanel pane, Point start, Point end, int width, int height){
        this.end = end;
        this.pane = pane;
        openSet = new Stack<>();
        FScore = new Stack<>();
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                points[i][j] = new Point(j,i);
            }
        }
        AStar(start,end);
    }
    private void AStar(Point start, Point end){
        openSet.push(start);
        FScore.push(heuristic(start));
        while (!openSet.empty()){
            Point current = openSet.peek();
            if (current.equals(end)){
                reconstructPath(start.getPrevious(), start);
            }
            if (!current.equals(start)){
                current.setMark(true);
                pane.updateClosed(current);
            }
            openSet.pop();
            ArrayList<Point> neighbours = getNeighbours(current);
            for (Point neighbour : neighbours){

            }
        }
    }
    private void reconstructPath(Point previous, Point current){

    }
    private ArrayList<Point> getNeighbours(Point point){
        // +1 x, -1 x, +1 y, -1y if not marked
        ArrayList<Point> neighbours = new ArrayList<>();


        return neighbours;
    }
    private double heuristic(Point point){
        return Math.sqrt(Math.pow((point.getX()-end.getX()),2)+Math.pow((point.getY()-end.getY()),2));
    }
    public static void main(String[] args) {
        new Solve(null,null,null,0,0);
    }

    
}
