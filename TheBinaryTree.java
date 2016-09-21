
public class TheBinaryTree{
	
	Node root;

	public void insertLeft(Point p1, Point p2) {
		
		Node newNode = new Node(p1, p2); //creating the new node
		
		if(root == null){ //checking if the root node is already full
			root = newNode; //because tree is empty, newNode is therefore the root
		
		}else{
			
			Node currentNode = root; //starting with root node to traverse
			Node parent; //future parent for the node
			
			while(true){
				parent = currentNode; //starting at top of tree (root)
				
				currentNode = currentNode.leftChild; //switch current node to the left
			 
					if(currentNode == null){ //if left child has no children
						parent.leftChild = newNode; // place new node on left
						return;
					}
			}
		}
	}
					
	public void insertRight(Point p1, Point p2){
		Node newNode = new Node(p1, p2); //creating the new node
			
		if(root == null){ //checking if the root node is already full
			root = newNode; //because tree is empty, newNode is therefore the root
			
		}else{
				
			Node currentNode = root;
			Node parent;
				
			while(true){
				parent = currentNode;
					
				currentNode = currentNode.rightChild;
					
				if(currentNode == null){ //if right child has no children
					parent.rightChild = newNode; //place the new node on the right of it
					return;
				}
			}
		}
	}
	
	public int ccw(Point p0, Point p1, Point p2) {
		 double dx1 = p1.x - p0.x;
		 double dy1 = p1.y - p0.y;
		 double dx2 = p2.x - p0.x;
		 double dy2 = p2.y - p0.y;
		 
		 if (dx1*dy2 > dy1*dx2){
			 return -1;
		 }else if (dx1*dy2 < dy1*dx2){
			 return 1;
		 }else if ((dx1*dx2 < 0) || (dy1*dy2 < 0)){
			 return 1;
			 
		 }else if ((dx1*dx1+dy1*dy1) < (dx2*dx2+dy2*dy2)){
			 return -1;
		 }else{
			 return 0;
		 }
	}
	
	public Node search(Node node, Point p1, Point p2) {
		
		int u = ccw(p1, node.p1, node.p2); 
		int p = ccw(p2, node.p1, node.p2);
		
		if (u != p) 
			return node;
		if (u == -1) {
			if (node.rightChild == null)
				return null;
			else
				search(node.rightChild, p1, p2);
		}
		if (u == 1) {
			if (node.leftChild==null)
				return null;
			else
				search(node.leftChild, p1, p2);
			}
		return null;
	}

	public void printPreOrderHelper(Node currentNode){
		if(currentNode == null){ //if tree is or is not empty
			return;
		}
		System.out.print(currentNode.p1 + " " + currentNode.p2);; //printing parent
		printPreOrderHelper(currentNode.leftChild); //calling method for the left child
		printPreOrderHelper(currentNode.rightChild); //calling method for the right child
	}
	
	public void printPreOrder() {
		printPreOrderHelper(root);
	}
	
	public void printInOrderHelper(Node currentNode){
		
		if(currentNode == null){ //making sure the tree isn't empty
			return;
		}
		printInOrderHelper(currentNode.leftChild); //calling method on right subtree
		System.out.print(currentNode.p1 + " " + currentNode.p2); //printing parent 
		printInOrderHelper(currentNode.rightChild); //calling method on left subtree
	}

	public void printInOrder() {
		printInOrderHelper(root);
	}

	public void printPostOrderHelper(Node currentNode){
		if(currentNode == null){
			return;
		}
		printPostOrderHelper(currentNode.leftChild); //starting at the left most node
		printPostOrderHelper(currentNode.rightChild); //calling the method on the right branch
		System.out.print(currentNode.p1 + " " + currentNode.p2);
	}
	
	public void printPostOrder() {	
		printPostOrderHelper(root);
	}
}
