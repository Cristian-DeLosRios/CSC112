package trie;

import java.util.ArrayList;

/**
 * This class implements a Trie. 
 * 
 * @author Sesh Venugopal
 *
 */
public class Trie {
	
	// prevent instantiation
	private Trie() { }
	
	/**
	 * Builds a trie by inserting all words in the input array, one at a time,
	 * in sequence FROM FIRST TO LAST. (The sequence is IMPORTANT!)
	 * The words in the input array are all lower case.
	 * 
	 * @param allWords Input array of words (lowercase) to be inserted.
	 * @return Root of trie with all words inserted from the input array
	 */
	private static void insertR(TrieNode curr, String[] allWords, int indexWordToAdd,int cIndex, TrieNode parent) {
		
		String wordToAdd = allWords[indexWordToAdd];
		String currWord = allWords[curr.substr.wordIndex];
		
		boolean newChars = false;
		
		//check how many characters are the same, both words must be at least cIndex length
		while((cIndex <= curr.substr.endIndex)  &&  (cIndex < wordToAdd.length()) &&
				(currWord.charAt(cIndex) == wordToAdd.charAt(cIndex)))
		{
			newChars = true;
			cIndex++;
		}
		if(newChars == false) {
			if(curr.sibling != null)
				insertR(curr.sibling, allWords, indexWordToAdd, cIndex, parent);
			else {
				Indexes tIndex = new Indexes(indexWordToAdd, (short)cIndex, 
						(short)(wordToAdd.length() - 1));
				curr.sibling = new TrieNode(tIndex, null, null);
				return;
			}	
		}
		
		//else if they share characters
		else {
			if((cIndex - 1 < curr.substr.endIndex) && curr.firstChild != null) {
				Indexes tempIndex = new Indexes(curr.substr.wordIndex, curr.substr.startIndex,(short)(cIndex - 1));
				TrieNode tempNode = new TrieNode(tempIndex, curr, curr.sibling);
				curr.substr.startIndex = (short)(tempNode.substr.endIndex + 1);
				
				if(parent.firstChild == curr) 
					parent.firstChild = tempNode;
				else 
				{
					TrieNode tempPtr = parent.firstChild;
					while((tempPtr.sibling != curr) && (tempPtr.sibling != null)) 
					{
						tempPtr = tempPtr.sibling;
					}				
					tempPtr.sibling = tempNode;
				}		
				Indexes wordToAddIndex = new Indexes(indexWordToAdd, (short)cIndex, (short) (wordToAdd.length() - 1));
				curr.sibling = new TrieNode(wordToAddIndex, null, null);
				return;
			}
			
			if(curr.firstChild == null) {
				short tempEndIndex = curr.substr.endIndex;
				curr.substr.endIndex = (short) (cIndex-1);
				Indexes indexOfOldChild = new Indexes(curr.substr.wordIndex, (short)cIndex, tempEndIndex);
				
				short endOfNewWord = (short)(wordToAdd.length() - 1);
				Indexes indexOfNewChild = new Indexes(indexWordToAdd, (short)cIndex, endOfNewWord);
				TrieNode child1 = new TrieNode(indexOfOldChild, null, null);
				TrieNode child2 = new TrieNode(indexOfNewChild, null, null);
				child1.sibling = child2;
				curr.firstChild = child1;
				return;
			}
			else 
				insertR(curr.firstChild, allWords, indexWordToAdd, cIndex, curr);
		}
	}
	public static TrieNode buildTrie(String[] allWords) {
		/** COMPLETE THIS METHOD **/
		
		//make empty root node
		TrieNode root = new TrieNode(null, null, null);
		//if word list is empty return empty root
		
		if(allWords.length == 0)
			return root;
		
		//create a temp index node, indices contain wordIndex,startIndex,endIndex
		Indexes temp = new Indexes(0, (short)0, (short)(allWords[0].length() - 1));
														//first word
		//the roots first child, each Trie node must have (substring, first child, sibling)
		root.firstChild = new TrieNode(temp, null , null);
		
		//first word is child of root so start after.
		for(int wordToAdd = 1; wordToAdd < allWords.length; wordToAdd++) {
			insertR(root.firstChild, allWords, wordToAdd, 0, root);
		}
		return root;
	}
	
	
	/**
	 * Given a trie, returns the "completion list" for a prefix, i.e. all the leaf nodes in the 
	 * trie whose words start with this prefix. 
	 * For instance, if the trie had the words "bear", "bull", "stock", and "bell",
	 * the completion list for prefix "b" would be the leaf nodes that hold "bear", "bull", and "bell"; 
	 * for prefix "be", the completion would be the leaf nodes that hold "bear" and "bell", 
	 * and for prefix "bell", completion would be the leaf node that holds "bell". 
	 * (The last example shows that an input prefix can be an entire word.) 
	 * The order of returned leaf nodes DOES NOT MATTER. So, for prefix "be",
	 * the returned list of leaf nodes can be either hold [bear,bell] or [bell,bear].
	 *
	 * @param root Root of Trie that stores all words to search on for completion lists
	 * @param allWords Array of words that have been inserted into the trie
	 * @param prefix Prefix to be completed with words in trie
	 * @return List of all leaf nodes in trie that hold words that start with the prefix, 
	 * 			order of leaf nodes does not matter.
	 *         If there is no word in the tree that has this prefix, null is returned.
	 */
	public static ArrayList<TrieNode> completionList(TrieNode root,
										String[] allWords, String prefix) {
		/** COMPLETE THIS METHOD **/
		if(root == null)
			return null;
		
		ArrayList<TrieNode> matches = new ArrayList<>();
		TrieNode ptr = root;
		
		while(ptr != null) {
			//Get the substring at this node
			if(ptr.substr == null) //Possible that we're checking on root
				ptr = ptr.firstChild;
			
			String s = allWords[ptr.substr.wordIndex];
			String a = s.substring(0, ptr.substr.endIndex+1);
			if(s.startsWith(prefix) || prefix.startsWith(a)) {
				if(ptr.firstChild != null) { //this is not a full word, go to children
					matches.addAll(completionList(ptr.firstChild, allWords, prefix));
					ptr = ptr.sibling;
				} else { //Otherwise this is a full string node
					matches.add(ptr);
					ptr = ptr.sibling;
				}
			} else {
				ptr = ptr.sibling;
			}
		} 
		if(matches.isEmpty())
			return null;
		return matches;
	}
	public static void print(TrieNode root, String[] allWords) {
		System.out.println("\nTRIE\n");
		print(root, 1, allWords);
	}
	
	private static void print(TrieNode root, int indent, String[] words) {
		if (root == null) {
			return;
		}
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		
		if (root.substr != null) {
			String pre = words[root.substr.wordIndex]
							.substring(0, root.substr.endIndex+1);
			System.out.println("      " + pre);
		}
		
		for (int i=0; i < indent-1; i++) {
			System.out.print("    ");
		}
		System.out.print(" ---");
		if (root.substr == null) {
			System.out.println("root");
		} else {
			System.out.println(root.substr);
		}
		
		for (TrieNode ptr=root.firstChild; ptr != null; ptr=ptr.sibling) {
			for (int i=0; i < indent-1; i++) {
				System.out.print("    ");
			}
			System.out.println("     |");
			print(ptr, indent+1, words);
		}
	}
 }
