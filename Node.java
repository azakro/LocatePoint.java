public class Node<T> {
	
	
	public Point p1, p2;
	public Node<T> leftChild;
	public Node<T> rightChild;
	public Node<T> parent;
	
    public Node(Point p1, Point p2){
      this.p1 = p1;
      this.p2 = p2;
   }
    
    public String toString(){
    	return p1 + " & "+ p2;
    }
}