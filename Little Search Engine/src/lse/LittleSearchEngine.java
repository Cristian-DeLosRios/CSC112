package lse;

import java.io.*;
import java.util.*;

/**
 * This class builds an index of keywords. Each keyword maps to a set of pages in
 * which it occurs, with frequency of occurrence in each page.
 *
 */
public class LittleSearchEngine {
	
	/**
	 * This is a hash table of all keywords. The key is the actual keyword, and the associated value is
	 * an array list of all occurrences of the keyword in documents. The array list is maintained in 
	 * DESCENDING order of frequencies.
	 */
	HashMap<String,ArrayList<Occurrence>> keywordsIndex;
	
	/**
	 * The hash set of all noise words.
	 */
	HashSet<String> noiseWords;
	
	/**
	 * Creates the keyWordsIndex and noiseWords hash tables.
	 */
	public LittleSearchEngine() {
		keywordsIndex = new HashMap<String,ArrayList<Occurrence>>(1000,2.0f);
		noiseWords = new HashSet<String>(100,2.0f);
	}
	
	/**
	 * Scans a document, and loads all keywords found into a hash table of keyword occurrences
	 * in the document. Uses the getKeyWord method to separate keywords from other words.
	 * 
	 * @param docFile Name of the document file to be scanned and loaded
	 * @return Hash table of keywords in the given document, each associated with an Occurrence object
	 * @throws FileNotFoundException If the document file is not found on disk
	 */
	public HashMap<String,Occurrence> loadKeywordsFromDocument(String docFile) 
	throws FileNotFoundException {
		
		HashMap<String, Occurrence> keysFound = new HashMap<String, Occurrence>();
		int freq = 1;
		try {
			Scanner sc = new Scanner(new File(docFile));
			sc.close();
		} catch(FileNotFoundException e) {
			return keysFound;
		}
		
		Scanner docWords = new Scanner(new File(docFile));
	
		//occurrence(file docfile,int  frequency)
		while(docWords.hasNext()) {
			String word = docWords.next();
			
			if(getKeyword(word) != null) { //check if it is a keyword
				word = getKeyword(word);
				if(!keysFound.containsKey(word)) { //as long as the word isnt in the new map, add it
					Occurrence newOccurrence = new Occurrence(docFile,freq); //saves which document and how many times it was there
					keysFound.put(word, newOccurrence);
				}
				else
					keysFound.get(word).frequency += 1;
				
			}
		}
		return keysFound;
	}
	/**
	 * Merges the keywords for a single document into the master keywordsIndex
	 * hash table. For each keyword, its Occurrence in the current document
	 * must be inserted in the correct place (according to descending order of
	 * frequency) in the same keyword's Occurrence list in the master hash table. 
	 * This is done by calling the insertLastOccurrence method.
	 * 
	 * @param kws Keywords hash table for a document
	 */
	public void mergeKeywords(HashMap<String,Occurrence> kws) {
		for (String key : kws.keySet())
		{
			ArrayList<Occurrence> occurs = new ArrayList<Occurrence>();
			
			if (!keywordsIndex.containsKey(key)) {
				occurs.add(kws.get(key));
				keywordsIndex.put(key, occurs);
			}
			else {
				
				occurs = keywordsIndex.get(key);
				occurs.add(kws.get(key));
				insertLastOccurrence(occurs);
				keywordsIndex.put(key, occurs);
			}
		}
		
	}
	public String getKeyword(String word) {
		/** COMPLETE THIS METHOD **/
		
		word = word.trim();
		char end =  word.length() > 1 ? word.charAt(word.length()-1): word.charAt(0);
		
		
		while(!Character.isLetter(end)) {
			word = word.substring(0, word.length()-1);
			if(word.length()>1)
				end = word.charAt(word.length()-1);
			else
				break;
		}
		word = word.toLowerCase();
		
		for(String noiseWord : noiseWords) {
			noiseWord = noiseWord.toLowerCase();
			if(word.equals(noiseWord)) {
				return null;
			}
		}
		for(int i = 0; i < word.length();i++) {
			if(!Character.isLetter(word.charAt(i)))
				return null;
		}
		
		return word;
	}
		
		/*
		for((String key : map.keySet()) {
		for (Object value : map.values()) {
		for (Map.Entry<String, Object> entry : map.entrySet()) {
    	String key = entry.getKey();
    	Object value = entry.getValue();
		
		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
		return null;
	}
	*/
	private ArrayList<Integer> BinarySearch(ArrayList<Integer> arr, int target, int l, int r) {
		
		ArrayList<Integer> midPts = new ArrayList<Integer>();
		
		while (r >= l)
		  {
		      int m = (l + r) / 2;
		      midPts.add(m); 
		      if (arr.get(m) <  target)
		        r = m - 1;
		      else if (arr.get(m) > target )
		        l = m + 1;
		      else
		    	  break; 
		  }
		  return midPts; 
		}
	
	/**
	 * Inserts the last occurrence in the parameter list in the correct position in the
	 * list, based on ordering occurrences on descending frequencies. The elements
	 * 0..n-2 in the list are already in the correct order. Insertion is done by
	 * first finding the correct spot using binary search, then inserting at that spot.
	 * 
	 * @param occs List of Occurrences
	 * @return Sequence of mid point indexes in the input list checked by the binary search process,
	 *         null if the size of the input list is 1. This returned array list is only used to test
	 *         your code - it is not used elsewhere in the program.
	 */
	public ArrayList<Integer> insertLastOccurrence(ArrayList<Occurrence> occs) {
		/** COMPLETE THIS METHOD **/
		
		ArrayList<Integer> temp = new ArrayList<Integer>(); 
		for (int i = 0; i < occs.size()-1; i++)
		{
			temp.add(occs.get(i).frequency); 
		}
		int toInsert = occs.get(occs.size()-1).frequency; 
		ArrayList<Integer> midPts = BinarySearch(temp, toInsert, 0, temp.size()-1);
		
		if(midPts.size() < 2)
			return null;
		else
			return midPts;
		
	}
	
	/**
	 * This method indexes all keywords found in all the input documents. When this
	 * method is done, the keywordsIndex hash table will be filled with all keywords,
	 * each of which is associated with an array list of Occurrence objects, arranged
	 * in decreasing frequencies of occurrence.
	 * 
	 * @param docsFile Name of file that has a list of all the document file names, one name per line
	 * @param noiseWordsFile Name of file that has a list of noise words, one noise word per line
	 * @throws FileNotFoundException If there is a problem locating any of the input files on disk
	 */
	public void makeIndex(String docsFile, String noiseWordsFile) 
	throws FileNotFoundException {
		// load noise words to hash table
		Scanner sc = new Scanner(new File(noiseWordsFile));
		while (sc.hasNext()) {
			String word = sc.next();
			noiseWords.add(word);
		}
		
		// index all keywords
		sc = new Scanner(new File(docsFile));
		while (sc.hasNext()) {
			String docFile = sc.next();
			HashMap<String,Occurrence> kws = loadKeywordsFromDocument(docFile);
			mergeKeywords(kws);
		}
		sc.close();
	}
	private ArrayList<String> Sort(ArrayList<Occurrence> arr){
		
		ArrayList<Occurrence> temp = arr;
		ArrayList<String> sorted = new ArrayList<String>();
	
			int mIndex = 0;
			int max = 0;
			while(!temp.isEmpty()) {
				for(int i = 0; i < temp.size(); i++) {
					if (max < temp.get(i).frequency) {
						max = temp.get(i).frequency;
						mIndex = i;
					}
				}
				sorted.add(temp.get(mIndex).document);
				temp.remove(mIndex);
				mIndex = 0;
			}
				
				return sorted;
	}
	
	/**
	 * Search result for "kw1 or kw2". A document is in the result set if kw1 or kw2 occurs in that
	 * document. Result set is arranged in descending order of document frequencies. (Note that a
	 * matching document will only appear once in the result.) Ties in frequency values are broken
	 * in favor of the first keyword. (That is, if kw1 is in doc1 with frequency f1, and kw2 is in doc2
	 * also with the same frequency f1, then doc1 will take precedence over doc2 in the result. 
	 * The result set is limited to 5 entries. If there are no matches at all, result is null.
	 * 
	 * @param kw1 First keyword
	 * @param kw1 Second keyword
	 * @return List of documents in which either kw1 or kw2 occurs, arranged in descending order of
	 *         frequencies. The result size is limited to 5 documents. If there are no matches, returns null.
	 */
	public ArrayList<String> top5search(String kw1, String kw2) {
		HashMap<String, ArrayList<Occurrence>> docFreqs = keywordsIndex;
		//key/word        //document and freq

		kw1 = kw1.toLowerCase();
		kw2 = kw2.toLowerCase();
		ArrayList<String> documents = new ArrayList<String>();
		ArrayList<String> tempDocList = new ArrayList<String>();
		ArrayList<Occurrence> L1 = new ArrayList<Occurrence>();
		ArrayList<Occurrence> L2 = new ArrayList<Occurrence>();
		ArrayList<Occurrence> OrderedL1 = new ArrayList<Occurrence>();
		ArrayList<Occurrence> OrderedL2 = new ArrayList<Occurrence>();
		int freq1;
		int freq2;

		if(docFreqs.containsKey(kw1))
			L1 = docFreqs.get(kw1); //save arraylist off occurrences into l1
		if(docFreqs.containsKey(kw2))
			L2 = docFreqs.get(kw2); //save arraylist off occurrences into l2
		int count = 0;


		if(L1.isEmpty() && L2.isEmpty()) 
			return null;
		else if(L2.isEmpty()) 
		{
			tempDocList = Sort(L1);
			for(int i = 0; i < 5; i++) 
			{
				String doc1 = tempDocList.get(i);
				documents.add(doc1);	
			}
			return documents;
		}
		else  if(L1.isEmpty()) 
		{
			tempDocList = Sort(L2);
			for(int i = 0; i < 5; i++) 
			{
				String doc2 = tempDocList.get(i);
				documents.add(doc2);	
			}
		return documents;
		}


 
		int max = 0;
		int mIndex = 0;
		while(L1.size() >= 1) {
			for( int i = 0 ; i< L1.size();i++) {
				if(L1.get(i).frequency > max) {
					max = L1.get(i).frequency;
					mIndex = i;
				}
		 
			}
			OrderedL1.add(L1.get(mIndex));
			L1.remove(L1.get(mIndex));
			mIndex = 0;
		}
		while(L2.size() >= 1) {
			for( int j = 0 ; j< L1.size();j++) {
				if(L2.get(j).frequency > max) {
					max = L2.get(j).frequency;
					mIndex = j;
				}
		 
			}
			OrderedL2.add(L2.get(mIndex));
			L2.remove(L2.get(mIndex));
			mIndex = 0;
		}
		for( int k = 0; k < OrderedL1.size();k++) 
		{
			freq1 = OrderedL1.get(k).frequency;
			String doc1 = OrderedL1.get(k).document;
			for( int l = 0; l < OrderedL2.size();l++) 
			{
				freq2 = OrderedL2.get(l).frequency;
				String doc2 = OrderedL2.get(l).document;
				if ( freq1 >= freq2) 
				{
					if(documents.size() < 5 && !documents.contains(doc1))
					{
						documents.add(doc1);
					}
				}
				else if(freq1 < freq2) 
				{
					if(documents.size() < 5 && !documents.contains(doc2))
					{
						documents.add(doc2);
					}
				}
			} 
		}
		System.out.println();
		System.out.print(kw1 + ": "); 
		for(int i = 0; i < OrderedL1.size(); i++) {
			if(i+1 == OrderedL1.size()) { 
				System.out.print(OrderedL1.get(i)); 
			}
			else 
				System.out.print(OrderedL1.get(i) + ", "); 
			}
		System.out.println();
		System.out.print(kw2 + ": "); 
		for(int i = 0; i < OrderedL2.size(); i++) {
			if(i+1 == OrderedL2.size()) { 
				System.out.print(OrderedL2.get(i)); 
			}
			else 
				System.out.print(OrderedL2.get(i) + ", "); 
		}
		System.out.println();


		if (documents.size() == 0)
			return null;
		else 
			return documents;
	}
}
