
	package lse;

	import java.io.FileNotFoundException;
	import java.io.BufferedReader;
	import java.io.IOException;
	import java.io.InputStreamReader;
	import java.util.HashMap;

	public class Driver
	{
	      static BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
	      public static void main(String args[]) throws IOException
	   {
	       String docsFile = "AliceCh1.txt";
	       String noiseWords = "noisewords.txt";
	          LittleSearchEngine google = new LittleSearchEngine();  
	          google.makeIndex(docsFile, noiseWords);
	          String kw1 = "world";
	          String kw2 = "deep";
	         
	      System.out.println(google.getKeyword("World!?"));
	      System.out.println(google.top5search(kw1, kw2));
	      //
	    
	   }
	}