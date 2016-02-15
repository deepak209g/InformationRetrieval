import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;


public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Trie engine = new Trie();
//		engine.insert("abc", "first");
//		engine.insert("abcd", "first");
//		engine.insert("abcde", "first");
//		engine.insert("abc", "second");
//		engine.insert("abcd", "second");
//		engine.insert("abcd", "second");
//		engine.insert("abcdef", "second");
//		engine.Query("abcdef abc");
		if(args.length > 0){
			traverse(args[0], engine);
			Scanner in = new Scanner(System.in);
			while(true){
				System.out.println("Enter q to query e to exit");
				String s = in.next();
				if(s.equals("q")){
					System.out.println("Enter query");
					String query = in.next();
					query = query.toLowerCase();
					query = query.replaceAll("[^a-zA-Z]","");
					engine.Query(query);
				}else if(s.equals("e")){
					in.close();
					break;
				}
			}	
		}
		
		
	}
	
	public static void traverse(String file, Trie t){
		File temp = new File(file);
		if(temp.isFile()){
			// read contents
			System.out.println("reading file");
			BufferedReader br = null;

			try {

				String sCurrentLine;

				br = new BufferedReader(new FileReader(file));

				StringTokenizer tok ;
				while ((sCurrentLine = br.readLine()) != null) {
					
					// got a line from file
					// add it to the engine
					tok = new StringTokenizer(sCurrentLine);
					while(tok.hasMoreTokens()){
						String token = tok.nextToken();
						token = token.toLowerCase();
						token = token.replaceAll("[^a-zA-Z]","");
						t.insert(token, file);
					}
					
					
					
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (br != null)br.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}else{
			for(String files : temp.list()){
				//System.out.println(files);
				traverse(file + "\\" +files, t);
			}
		}
	}

}
