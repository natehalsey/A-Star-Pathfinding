public class Point {
    private int x_coord;
    private int y_coord;
    private int index;
    private double hScore;
    private double gScore;
    private double fScore;
    private double dScore;
    private Point previous;
    public Point(int index, int x_coord, int y_coord){
        this.index = index;
        this.x_coord = x_coord;
        this.y_coord = y_coord;
    }
    public int getIndex(){
        return index;
    }
    public void setIndex(int index){
        this.index = index;
    }
    public int getX(){
        return x_coord;
    }
    public int getY(){
        return y_coord;
    }
    public void setX(int x_coord){
        this.x_coord = x_coord;
    }
    public void setY(int y_coord){
        this.y_coord = y_coord;
    }
    public void setHScore(double hScore){
        this.hScore = hScore;
    }
    public void setGScore(double gScore){
        this.gScore = gScore;
    }
    public void setFScore(double fScore){
        this.fScore = fScore;
    }
    public double getHScore(){
        return hScore;
    }
    public double getGScore(){
        return gScore;
    }
    public double getFScore(){
        return fScore;
    }
    public void setDScore(double dScore){
        this.dScore = dScore;
    }
    public double getDScore(){
        return dScore;
    }
    public void setPrevious(Point previous){
        this.previous = previous;
    }
    public Point getPrevious(){
        return previous;
    }
    public boolean equals(Point point){
        if (this.getIndex() == point.getIndex()){
            return true;
        }
        return false;
    }
    
    
}
