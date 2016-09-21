import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.util.*;
import java.util.List;

import javax.swing.*;

public class PointLocation extends JPanel implements MouseListener{

	TheBinaryTree tree = new TheBinaryTree();
	String[] lineArray1, lineArray2, lineArray3, lineArray4, lineArray5;
	Line line1, line2, line3, line4, line5;
	Point userPoint1, userPoint2;
	int mouseIsClicked = 0;
	LinkedList<Line> unaddedLineList = new LinkedList<Line>(); // because not added to tree yet
	LinkedList<Line> addedLineList = new LinkedList<Line>(); //only one in array because it's already the root of the tree
	
	public PointLocation(){
		addMouseListener(this);
	}

	public void getPoints(){
		//getting all the points from the user input
		Scanner input = new Scanner(System.in);
		System.out.println("You will be entering five sets of four points.");
	
		System.out.println("Enter the first set of four points in the format 0, y1, 400, y2 to form one line (max point value of 400): ");
		lineArray1 = input.nextLine().split(", ");
		
		System.out.println("Enter the second set of four points in the format 0, y1, 400, y2 to form one line (max point value of 400): ");
		lineArray2 = input.nextLine().split(", ");

		System.out.println("Enter the third set of four points in the format 0, y1, 400, y2 to form one line (max point value of 400): ");
		lineArray3 = input.nextLine().split(", ");

		System.out.println("Enter the fourth set of four points in the format 0, y1, 400, y2 to form one line (max point value of 400): ");
		lineArray4 = input.nextLine().split(", ");
		
		System.out.println("Enter the fifth set of four points in the format 0, y1, 400, y2 to form one line (max point value of 400): ");
		lineArray5 = input.nextLine().split(", ");
		
		makeIntoLinesandTree();
	}
	
	public void makeIntoLinesandTree(){	
		//drawing the lines based on the array made from user input
		line1 = new Line(Integer.parseInt(lineArray1[0]), Integer.parseInt(lineArray1[1]), Integer.parseInt(lineArray1[2]), Integer.parseInt(lineArray1[3]));
		line2 = new Line(Integer.parseInt(lineArray2[0]), Integer.parseInt(lineArray2[1]), Integer.parseInt(lineArray2[2]), Integer.parseInt(lineArray2[3]));
		line3 = new Line(Integer.parseInt(lineArray3[0]), Integer.parseInt(lineArray3[1]), Integer.parseInt(lineArray3[2]), Integer.parseInt(lineArray3[3]));
		line4 = new Line(Integer.parseInt(lineArray4[0]), Integer.parseInt(lineArray4[1]), Integer.parseInt(lineArray4[2]), Integer.parseInt(lineArray4[3]));
		line5 = new Line(Integer.parseInt(lineArray5[0]), Integer.parseInt(lineArray5[1]), Integer.parseInt(lineArray5[2]), Integer.parseInt(lineArray5[3]));
		//adding the lines to the unaddedLineList, or the list of lines that have to be sorted into the tree
		unaddedLineList.addFirst(line2);
		unaddedLineList.addFirst(line3);
		unaddedLineList.addFirst(line4);
		unaddedLineList.addFirst(line5);
		
		Point firstLinePoint1 = new Point(line1.x1, line1.y1); //making two points from the first line the user input so that it can be put into the tree
		Point firstLinePoint2 = new Point(line1.x2, line1.y2);
		tree.insertLeft(firstLinePoint1, firstLinePoint2); //putting line1 at the root of the tree because it is the first line to compare the others to
		addedLineList.addFirst(line1); //adding the first line to the addedLineList because they have been added to the tree
		
		sort();
		sort();
		sort();
	}
	
	public void sort(){
		
		if(unaddedLineList.size() != 0){
			for(int i = 0; i < unaddedLineList.size(); i++){ //scanning through both line arrays
			for(int j = 0; j < addedLineList.size(); i++){
				
				Point linePoint1 = new Point(unaddedLineList.get(i).x1, unaddedLineList.get(i).y1); //making two points for the line in the array so that it can be inserted into tree
				Point linePoint2 = new Point(unaddedLineList.get(i).x2, unaddedLineList.get(i).y2);
				
				if( linesIntersect(unaddedLineList.get(i), addedLineList.get(i))){ //when lines intersect
					tree.insertLeft(linePoint1, linePoint2);
					tree.insertRight(linePoint1, linePoint2);
					unaddedLineList.remove(unaddedLineList.get(i));//removing the line from the unadded list because it has now just been added to the list of lines that are being checked to see if they intersect
					addedLineList.add(unaddedLineList.get(i)); //adding the same line to the added line list because it has just been checked
					return;
					
				}else if(lineOrientation(addedLineList.get(i), unaddedLineList.get(i)) == 1){ //when method returns clockwise
					tree.insertRight(linePoint1, linePoint2);
					unaddedLineList.remove(unaddedLineList.get(i));
					addedLineList.add(unaddedLineList.get(i)); 
					return;
					
				}else if(lineOrientation(addedLineList.get(i), unaddedLineList.get(i)) == -1){ //when method returns counterclockwise
					tree.insertLeft(linePoint1, linePoint2);
					unaddedLineList.remove(unaddedLineList.get(i));
					addedLineList.add(unaddedLineList.get(i)); 
					return;
				}
			}
			}
		}else{
			return;
		}
	}
	
	public void compareRegions(){
		
		if( tree.search(tree.root, userPoint1, userPoint2) == null ){ //using search method to compare the regions of the points
			System.out.println("Points are in the same region");
		}else{
			System.out.println("Points are not in the same region");
		}	
	}
	
	@Override
	public void paintComponent(Graphics g){
		//drawing all the lines based on user input
		g.drawLine(Integer.parseInt(lineArray1[0]), Integer.parseInt(lineArray1[1]), Integer.parseInt(lineArray1[2]), Integer.parseInt(lineArray1[3]) );
		g.drawLine(Integer.parseInt(lineArray2[0]), Integer.parseInt(lineArray2[1]), Integer.parseInt(lineArray2[2]), Integer.parseInt(lineArray2[3]) );
		g.drawLine(Integer.parseInt(lineArray3[0]), Integer.parseInt(lineArray3[1]), Integer.parseInt(lineArray3[2]), Integer.parseInt(lineArray3[3]) );
		g.drawLine(Integer.parseInt(lineArray4[0]), Integer.parseInt(lineArray4[1]), Integer.parseInt(lineArray4[2]), Integer.parseInt(lineArray4[3]) );
		g.drawLine(Integer.parseInt(lineArray5[0]), Integer.parseInt(lineArray5[1]), Integer.parseInt(lineArray5[2]), Integer.parseInt(lineArray5[3]) );
	}
	
	public int ccw(Point p0, Point p1, Point p2) { //function given in project guidlines sheet
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
	
	public int lineOrientation(Line lineOne, Line lineTwo){ //calculating if two lines are clockwise, counterclockwise, or colinear to each other
		 double dx1 = lineTwo.x1 - lineOne.x1;
		 double dy1 = lineTwo.y1 - lineOne.y1;
		 double dx2 = lineTwo.x2 - lineOne.x1;
		 double dy2 = lineTwo.y2 - lineOne.y1;
		 
		 if (dx1*dy2 > dy1*dx2){
			 return -1; // means it is counterclockwise
			 
		 }else if (dx1*dy2 < dy1*dx2){
			 return 1; //means it is clockwise
			 
		 }else if ((dx1*dx2 < 0) || (dy1*dy2 < 0)){
			 return 1;
			 
		 }else if ((dx1*dx1+dy1*dy1) < (dx2*dx2+dy2*dy2)){
			 return -1;
			 
		 }else{
			 return 0;
		 }
	}
	
	public boolean linesIntersect(Line thisLine, Line thatLine){ //calculating if lines intersect with Line2D class
		
		Line2D firstLine = new Line2D.Float(thisLine.x1, thisLine.y1, thisLine.x2, thisLine.y2);
		Line2D secondLine = new Line2D.Float(thatLine.x1, thatLine.y1, thatLine.x2, thatLine.y2);
	
		if(secondLine.intersectsLine(firstLine)){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		mouseIsClicked++;
		
		if(mouseIsClicked == 1){
			int pointX = e.getX(); //getting the points of the line that the user chose
			int pointY = e.getY();
			userPoint1 = new Point(pointX, pointY); //making it into a defined point
			Graphics g = getGraphics();
			g.fillOval(userPoint1.x, userPoint1.y, 5, 5); //drawing an circle where the mouse was pressed
			
		}else if(mouseIsClicked == 2){
			int pointX = e.getX();
			int pointY = e.getY();
			userPoint2 = new Point(pointX, pointY);
			Graphics g = getGraphics();
			g.fillOval(userPoint2.x, userPoint2.y, 5, 5);
			compareRegions(); //calculating if theyre in the same region after the second point is chosen
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
	
	public static void main(String[] args){	
		PointLocation pl = new PointLocation();
		pl.getPoints();
		
		JFrame frame = new JFrame();
		frame.add(pl);
		frame.setVisible(true);
		frame.setSize(400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("kewl line point stuff");
	}
}
