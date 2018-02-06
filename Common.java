publi class IntNode{
	public int data;
	public IntNode next;
	public IntNode(int data, IntNode next){
	
		this.data = data;
		this.next = next;
	}
	public String toString(){
		return data + "";
	}
	public IntNode commonElements(IntNode front1, IntNode front2){

	       IntNode first = null, last = null;
	       while(front1 != null && front2 !=null){
	       
	       	if(front1.data <front2.data)
	       		front = front.next; //movement of the pointer
	        else if(front2.data <front1.data)
	        	front2 = front2.next;
	        else //must be the same
	        	IntNode tmp = new IntNode(front1.data,null)
	        	if (first == null)
	        		first = temp;
	        	else {
	        		last.next = temp; //last node
	        	}
	        		last = temp;
	        		front1 =front1.next;
	        		front2=front2.next;
	        }
	        return first;
	       
	       }
	        
		//.next.next to delete over a node
		
		 public static int kthTolast(Node head, int k){
		 
		 	IntNode ptr = head;
		 
		 	for( int i= 0; i<k; i++){
		 		ptr=ptr.next;
		 	}
		 	IntNode target = head;
		 	
		 	while(ptr!=null){
		 		ptr = ptr.next;
		 		target = target.next;
		 	}
		 	
		 	return target.data;
		 }
		 
		 
		 
		 }
	
		
	
	
	}
	
	
	
	
	Node Ordered = new Node(data, next)	
	
	}
	
	}



}
