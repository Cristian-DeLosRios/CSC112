package structures;

import java.util.*;

/**
 * This class implements an HTML DOM Tree. Each node of the tree is a TagNode, with fields for
 * tag/text, first child and sibling.
 * 
 */
public class Tree {
	
	/**
	 * Root node
	 */
	TagNode root=null;
	
	/**
	 * Scanner used to read input HTML file when building the tree
	 */
	Scanner sc;
	
	/**
	 * Initializes this tree object with scanner for input HTML file
	 * 
	 * @param sc Scanner for input HTML file
	 */
	public Tree(Scanner sc) {
		this.sc = sc;
		root = null;
	}
	
	/**
	 * Builds the DOM tree from input HTML file, through scanner passed
	 * in to the constructor and stored in the sc field of this object. 
	 * 
	 * The root of the tree that is built is referenced by the root field of this object.
	 */
	public void build() {
		/** COMPLETE THIS METHOD **/
		
		Stack<TagNode> nodes = new Stack();
		TagNode ptr = root;
		sc.useDelimiter("\\S\\n");
		
		
		while (sc.hasNext()) {
			String current = sc.nextLine();
		
			if (ptr == null) {
				TagNode Tag = new TagNode(current.substring(1, current.length() - 1), null, null);
				root = Tag;
				ptr = root;
				nodes.push(ptr);
				
				continue;
			}
			if (current.contains("<") && !current.contains("</")) {
				TagNode Tag = new TagNode(current.substring(1, current.length() - 1), null, null);
				if (nodes.peek().firstChild == null) {
					ptr.firstChild = Tag;
					ptr = ptr.firstChild;
					nodes.push(ptr);
					
				} else {
					ptr.sibling = Tag;
					ptr = ptr.sibling;
					nodes.push(ptr);
					
				}
			} else if (current.contains("<") == false && current.contains("</") == false) {
				TagNode Tag = new TagNode(current, null, null);
				if (ptr.firstChild == null) {
					ptr.firstChild = Tag;
					ptr = ptr.firstChild;
				} else {
					ptr.sibling = Tag;
					ptr = ptr.sibling;
				}
			} else {
				ptr = nodes.pop();
			
			}
		}
		
	}
	
	/**
	 * Replaces all occurrences of an old tag in the DOM tree with a new tag
	 * 
	 * @param oldTag Old tag
	 * @param newTag Replacement tag
	 */
	
	private static void RreplaceTag(TagNode root, String oldTag, String newTag) {
		
		TagNode current = root;
		
		
		if (current == null)
			return;
		
		
		if (current.tag.equals(oldTag)) {
			current.tag = newTag;
		}
		else if ((oldTag.equals("ol") || oldTag.equals("ul") 
				|| oldTag.equals("p") || oldTag.equals("em")
				|| oldTag.equals("b"))
				&& 
				(newTag.equals("ol") || newTag.equals("ul") ||newTag.equals("em") || newTag.equals("b"))  
				&& 
				current.tag.equals(oldTag))
		{
			current.tag = newTag;
		}
		
		RreplaceTag(current.firstChild, oldTag, newTag);
		RreplaceTag(current.sibling, oldTag, newTag);
	}
		
	public void replaceTag(String oldTag, String newTag) {
		/** COMPLETE THIS METHOD **/
		
		RreplaceTag(root,oldTag,newTag);
		
		
	}
	

	public void boldRow(int row) { 
	
		if(row >= 0)
			this.findTable(row, this.root);
		else
			return;
	}
	
	
	private void findTable(int row, TagNode root) { 
		if (root.firstChild != null)
			this.findTable(row, root.firstChild);
		
		if (root.sibling != null)
			this.findTable(row, root.sibling);
		
		if (root.tag.equals("table"))
			this.BoldTable(row, root);
	}
	private void BoldTable(int row, TagNode root)
	{
		
		if (root == null)
			return;
		TagNode ptr = root;
		TagNode tr = ptr.firstChild;
		
		
		for(int r=1; r != row; r++) { 
			tr = tr.sibling;
		}
		for(TagNode td = tr.firstChild; td != null; td = td.sibling) { 
			TagNode b = new TagNode("b", td.firstChild, null);
			td.firstChild = b;
		}
		
		
		

	}
	
	/**
	 * Remove all occurrences of a tag from the DOM tree. If the tag is p, em, or b, all occurrences of the tag
	 * are removed. If the tag is ol or ul, then All occurrences of such a tag are removed from the tree, and, 
	 * in addition, all the li tags immediately under the removed tag are converted to p tags. 
	 * 
	 * @param tag Tag to be removed, can be p, em, b, ol, or ul
	 */
	
	private static void replaceWP(String tag, TagNode prev){
									
		
		if(prev == null)
			return;
	
		 if(prev.tag.equals(tag) &&  prev !=null){
			if(tag.equals("ol") ||tag.equals("ul") ){
				prev.tag = "p";
				TagNode ptr = null;
				 
				for(ptr = prev.firstChild; ptr.sibling != null; ptr = ptr.sibling){ 
					ptr.tag = "p";
				}
				ptr.tag = "p";
				ptr.sibling = prev.sibling;
				prev.sibling = prev.firstChild.sibling;
				prev.firstChild = prev.firstChild.firstChild;
				}
		 }				
					
		 replaceWP(tag, prev.firstChild);
		 replaceWP(tag, prev.sibling);	

			
	}
	private void removeTag(String tag, TagNode prev) { 
		if(prev == null) 
			return;
		if(prev.tag.equals(tag) && prev.firstChild != null) {
			prev.tag = prev.firstChild.tag;
			
			if(prev.firstChild.sibling != null) {
				TagNode ptr = null;
				for(ptr = prev.firstChild; ptr.sibling != null; ptr = ptr.sibling); 
				ptr.sibling = prev.sibling;
				prev.sibling = prev.firstChild.sibling;
			}
			prev.firstChild = prev.firstChild.firstChild;
		}

	}

	public void removeTag(String tag) {
		
		/** COMPLETE THIS METHOD **/
		tag = tag.toLowerCase();
		if(tag.equals("ol") || tag.equals("ul"))
			replaceWP(tag, root);
		else if(tag.equals("p") || tag.equals("em") || tag.equals("b"))
				removeTag(tag,root);
				
	}

	public void addTag(String word, String tag) {
		/** COMPLETE THIS METHOD **/
 
		addTag(root,word,tag);
	}
	private void addTag(TagNode root, String word, String tag) {
		word = word.toLowerCase();
		if(root == null) 
			return; 
		addTag(root.firstChild, word, tag);
		addTag(root.sibling, word, tag);
		
		if(root.firstChild == null) {//null below means current is text
			while(root.tag.toLowerCase().contains(word)) {
				String[] text = root.tag.split(" ");
				Boolean found = false;
				String TempWord = "";
				StringBuilder sb = new StringBuilder(root.tag.length());
				int i = 0;
				for(i=0; i < text.length; i++) {
					if(text[i].toLowerCase().matches(word+"[.?!,]?")) {
						found = true;
						TempWord = text[i];
						for(int x=i+1; x<text.length; x++) {
							sb.append(text[x]+" ");
						}
						break;
					}
				}
				if(!found)
					return;
				
				String restOfTag = sb.toString().trim(); 
				if(i == 0) { 
					root.firstChild = new TagNode(TempWord, null, null);
					root.tag = tag;
					
					if(!restOfTag.equals("")) { 
						root.sibling = new TagNode(restOfTag, null, root.sibling);
						root = root.sibling;
					}
				} else { 
					TagNode taggedWordNode = new TagNode(TempWord, null, null);
					TagNode newTag = new TagNode(tag, taggedWordNode, root.sibling);
					root.sibling = newTag;
					root.tag = root.tag.replaceFirst(" "+TempWord, "");
					if(!restOfTag.equals("")) { // word is in middle of text
						root.tag = root.tag.replace(restOfTag, "");
						newTag.sibling = new TagNode(restOfTag, null, newTag.sibling);
						root = newTag.sibling;
					}
				}
			} 
		}
	}
	
	/**
	 * Gets the HTML represented by this DOM tree. The returned string includes
	 * new lines, so that when it is printed, it will be identical to the
	 * input file from which the DOM tree was built.
	 * 
	 * @return HTML string, including new lines. 
	 */
	public String getHTML() {
		StringBuilder sb = new StringBuilder();
		getHTML(root, sb);
		return sb.toString();
	}
	
	private void getHTML(TagNode root, StringBuilder sb) {
		for (TagNode ptr=root; ptr != null;ptr=ptr.sibling) {
			if (ptr.firstChild == null) {
				sb.append(ptr.tag);
				sb.append("\n");
			} else {
				sb.append("<");
				sb.append(ptr.tag);
				sb.append(">\n");
				getHTML(ptr.firstChild, sb);
				sb.append("</");
				sb.append(ptr.tag);
				sb.append(">\n");	
			}
		}
	}
	
	/**
	 * Prints the DOM tree. 
	 *
	 */
	public void print() {
		print(root, 1);
	}
	
	private void print(TagNode root, int level) {
		for (TagNode ptr=root; ptr != null;ptr=ptr.sibling) {
			for (int i=0; i < level-1; i++) {
				System.out.print("      ");
			};
			if (root != this.root) {
				System.out.print("|---- ");
			} else {
				System.out.print("      ");
			}
			System.out.println(ptr.tag);
			if (ptr.firstChild != null) {
				print(ptr.firstChild, level+1);
			}
		}
	}
}
