 package apps;

import structures.*;
import java.util.ArrayList;
//TODO parto treelist methods also
////Kruskal's and Prim's 
//edges are arcs here
public class MST {
	
	/**
	 * Initializes the algorithm by building single-vertex partial trees
	 * 
	 * @param graph Graph for which the MST is to be found
	 * @return The initial partial tree list
	 */
//	private static PartialTreeList ordered(PartialTreeList T){
//		
//		
//		PartialTreeList temp = T;
//		ArrayList<PartialTree.Arc> arcList = new ArrayList<PartialTree.Arc>();
//		MinHeap<PartialTree.Arc> minHeap = temp .r.getArcs();
//		
//		while(temp.size()>1){
//			PartialTree min = temp.remove();
//			PartialTree.Arc arcs = min.getArcs().deleteMin();
//			int minWeight = arcs.weight; 
//	
//			
//		}
//		//temp.tree.getRoot();
//		
//		
//		return null;
//	}
	public static PartialTreeList initialize(Graph graph) {
	
		/* COMPLETE THIS METHOD */
		//initialize partial tree we will return 
		//and min heap we will use for storage
		PartialTreeList finalTreeList = new PartialTreeList();
		MinHeap<PartialTree.Arc> heap = new MinHeap<PartialTree.Arc>();
		
		  for(int i = 0; i < graph.vertices.length; i++){
			  PartialTree temp = new PartialTree(graph.vertices[i]);//tree used to traverse
			  //MinHeap<PartialTree.Arc> heap = new MinHeap<PartialTree.Arc>();
			  graph.vertices[i].parent = temp.getRoot();
			  heap = temp.getArcs();
			  Vertex currentVertex = graph.vertices[i]; //get current vertex
			  
			  while(currentVertex.neighbors != null){
				  PartialTree.Arc arc = new PartialTree.Arc(graph.vertices[i], currentVertex.neighbors.vertex,currentVertex.neighbors.weight);
				  heap.insert(arc);
				  currentVertex.neighbors = currentVertex.neighbors.next;
	
			  }
			  //temp.getArcs().merge(heap);
			 
		
			  finalTreeList.append(temp);
		  }
		  
		return finalTreeList;
	}

	/**
	 * Executes the algorithm on a graph, starting with the initial partial tree list
	 * 
	 * @param ptlist Initial partial tree list
	 * @return Array list of all arcs that are in the MST - sequence of arcs is irrelevant
	 */
	public static ArrayList<PartialTree.Arc> execute(PartialTreeList ptlist) {
		
		
		ArrayList<PartialTree.Arc> arcList = new ArrayList<PartialTree.Arc>();
		
		while(ptlist.size() > 1){
			
			PartialTree PTX = ptlist.remove();
			MinHeap<PartialTree.Arc> minHeap = PTX.getArcs();
			PartialTree.Arc arc= minHeap.deleteMin();
			
			Vertex v2 = arc.v2;
			while(!PTX.getArcs().isEmpty()){
				if (PTX.getRoot().equals(v2.getRoot()))
				{
					arc = PTX.getArcs().deleteMin();
					v2 = arc.v2;
				} 
				else if (!PTX.getRoot().equals(v2.getRoot()))
					break;
				
			}
			PartialTree PTY = ptlist.removeTreeContaining(v2);
			PTX.merge(PTY);
			ptlist.append(PTX);
			arcList.add(arc);
		}
		
		return arcList;
	}
}
