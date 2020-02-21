package apps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import structures.Graph;
import structures.Vertex;
import structures.MinHeap;
//TODO parto treelist methods also
////Kruskal's and Prim's 
//edges are arcs here

public class Driver {
	private static void printList(PartialTreeList a){
		Iterator<PartialTree> iter = a.iterator();
		   while (iter.hasNext()) {
		       PartialTree pt = iter.next();
		       System.out.println(pt.toString());
		   }
	}

	public static void main(String[] args) {
		Graph graph = null;
		try {
			 graph = new Graph("graph2.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PartialTreeList TreeList = MST.initialize(graph);
		printList(TreeList);
		ArrayList<PartialTree.Arc> arcArrayList = MST.execute(TreeList);
	    System.out.println(arcArrayList);
	      

	}
}