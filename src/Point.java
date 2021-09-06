public class Point {
    private int x;
    private int y;
    private boolean marked;
    private Point previous;
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void setMark(boolean mark){
        marked = mark;
    }
    public boolean getMark(){
        return marked;
    }
    public void setPrevious(Point previous){
        this.previous = previous;
    }
    public Point getPrevious(){
        return previous;
    }
    public boolean equals(Point point){
        if (this.getX() == point.getX() && this.getY() == point.getY()){
            return true;
        }
        return false;
    }
    
}
