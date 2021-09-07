import java.util.*;
public class Solve {
    private int width;
    private int height;
    
    private ArrayList<Point> points;
    private ArrayList<Point> openSet;
    private ArrayList<Point> closedSet;
    private ArrayList<Point> path;

    private ArrayList<Integer> blockers;

    private Point endNode;
    private Point startNode;

    public Solve(Point start, Point end, ArrayList<Integer> blockers, int width, int height){

        this.width = width;
        this.height = height;
        this.blockers = blockers;


        openSet = new ArrayList<>();
        closedSet = new ArrayList<>();
        points = new ArrayList<>();
        path = new ArrayList<>();
        blockers = new ArrayList<>();


        init(start,end);

    }
    private void init(Point start, Point end){

        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                Point point = new Point((i*width)+j,i,j);
                point.setHScore(euclideanDistance(point, end));
                point.setGScore(width*height);
                points.add(point);
            }
        }

        startNode = points.get(start.getIndex());
        endNode = points.get(end.getIndex());

        openSet.add(startNode);
        startNode.setGScore(0);
        startNode.setFScore(startNode.getHScore());
        startNode.setPrevious(null);

    }
    public boolean makeMove(){
        if (openSet.isEmpty()){
            return false;
        }
        Point current = lowestFScore();
        if (current.equals(endNode)){
            reconstructPath(current);
            return false;
        }
        openSet.remove(current);
        if (current.getGScore() != 0){
            closedSet.add(current);
        }

        ArrayList<Point> neighbours = getNeighbours(current);
        if (neighbours.size() == 0){
            return false;
        }
        for (Point point : neighbours){
            double tentative_gScore = current.getGScore()+point.getDScore();
            if (tentative_gScore < point.getGScore()){
                point.setPrevious(current);
                point.setGScore(tentative_gScore);
                point.setFScore(point.getGScore()+point.getHScore());

                if (!openSet.contains(point)){
                    openSet.add(point);
                }
            }
        }

        return true;
    }
    private double euclideanDistance(Point from, Point to){
        return Math.sqrt(Math.pow(from.getX()-to.getX(),2)+Math.pow(from.getY()-to.getY(),2));
    }
    private Point lowestFScore(){
        double smallest = width*height;
        int index = 0;
        for (int i = 0; i < openSet.size(); i++){
            Point current = openSet.get(i);
            if (current.getFScore() < smallest){
                smallest = current.getFScore();
                index = i;
            }
        }
        return openSet.get(index);
    }
    private void reconstructPath(Point current){
        if (current == null){
            return;
        }
        path.add(current);
        current = current.getPrevious();
        reconstructPath(current);
    }
    private ArrayList<Point> getNeighbours(Point point){
        ArrayList<Point> neighbours = new ArrayList<>();
        for (int i = point.getY()-1; i <= point.getY()+1; i++){
            for (int j = point.getX()-1; j <= point.getX()+1; j++){
                if ((j != -1) && j != width && i != -1 && i != height){
                    Point current = points.get((j*width)+i);
                    if(!current.equals(point) && !blockers.contains(current.getIndex())){
                        current.setDScore(euclideanDistance(point, current));
                        neighbours.add(current);
                    }
                }
            }
        }
        return neighbours;
    }
    public ArrayList<Point> getPath(){
        return path;
    }
    public ArrayList<Point> getClosed(){
        return closedSet;
    }
    public ArrayList<Point> getOpen(){
        return openSet;
    }
}
