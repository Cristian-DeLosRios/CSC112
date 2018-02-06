package poly;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients and
	 *         degrees read from scanner
	 */
	public static Node read(Scanner sc) 
	throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}
	private static Node Delete(Node poly, int k){
		
		Node ptr = poly;
		 
	 	for( int i= 0; i<k; i++){
	 		ptr=ptr.next;
	 		if( i+1 == k){
	 			poly.next = poly.next.next;
	 		}
	 	}
	 	
	 	return poly;
	 }
		
		
	
	private static Node Sort(Node poly){
		
		
		Node polyCopy = null;
		Node Highest = null;
		int largest;
		int k = 0;
		int i = 0;
		
		while(poly != null){
			largest = Integer.MIN_VALUE;
			for(Node current = poly; current != null; current = poly.next){
				i++;
				 if(current.term.degree > largest){
					 Highest = current;
					 largest = current.term.degree; 
					 k = i;
				 }
				
			}
			
			polyCopy = new Node(Highest.term.coeff,Highest.term.degree,null);//maybe set these too 0,0, null and then fill with .next
			//poly = poly.next;
			//polycopy.next = new Node();
		}
		Delete(poly,k);//delete highest node
		Sort(poly); //grab highest degree > add to node list > delete said node > repeat until no more nodes left in poly.
		
		return polyCopy;
	}
	
	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node add(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		Node sortedP1 = Sort(poly1);
		Node sortedP2 = Sort(poly2);
		Node Head = null; //Pointer for third node to save sum into
		
		//Node temp = new Node(0, 0, null);
		//to add, the degree must be the same.
		while(sortedP1 != null && sortedP2 != null){ // as long as it is not empty.

	       	if(sortedP1.term.degree <sortedP2.term.degree)
	       		sortedP1 = sortedP1.next; //movement of the pointer
	        else if(sortedP2.term.degree <sortedP1.term.degree)
	        	sortedP2 = sortedP2.next;
	        else {//their degrees are the same
	        	
	        	Node Temp = new Node(sortedP1.term.coeff + sortedP2.term.coeff, sortedP1.term.degree, null);
	        	//Head of new node, Set to Null starting value. Initialize new node to begin saving sum into.
	        	
	        	if(Head == null){
	        		Head = Temp; //copy over the node into Head.
	        	}
	     //before while loop ends move both poly and head pointers to the next value
	        	Head = Head.next;
	        	sortedP1 = sortedP1.next;
	        	sortedP2 = sortedP2.next;
	        	
	        }//End of while loop
	       	return Head; //returns sorted nodes
		}
			
			
		return Head;// if empty will return null node, else will return the sorted and added temp poly
			
			
		/*
		for (Node list1 = poly1; list1 != null; list1 = poly1.next) {
			for (Node list2 = poly2; list2 != null; list2 = poly2.next) {
				
				if(list1.term.degree == list2.term.degree){
					
					float tempCo =	list1.term.coeff + list2.term.coeff;  //check if 0
					int tempDegree = list1.term.degree;
					poly1 = poly1.next;
					poly2 = poly2.next;
					
					if (tempCo == 0){
						//remove, then continue
						continue;
					}
					
					 //temp.term = new Term(tempCo, tempDegree);
					 temp.next = new Node(tempCo, tempDegree,null);
					 length +=1;
				}
			}
		}*/
	}
	
	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		return null;
	}
		
	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		return 0;
	}
	
	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}	
}
