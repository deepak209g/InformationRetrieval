import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.StringTokenizer;
import java.util.TreeMap;


public class Trie {
	TrieNode root;
	Map<String, Integer> docs;
	public Trie(){
		this.root = new TrieNode();
		this.docs = new HashMap<String, Integer>();
	}
	public void insert(String str, String docId){
		TrieNode temp = root;
		str = str.toLowerCase();
		
		if(docs.containsKey(docId)){
			int totalwords = docs.get(docId) + 1;
			docs.put(docId, totalwords);
		}else{
			docs.put(docId, 1);
		}
		
		//traverse to the final node
		for(int i=0; i<str.length(); i++){
			int idx = (int)str.charAt(i) - 97;
			
			if(temp.nodes[idx] == null){
				temp.nodes[idx] = new TrieNode();
			}
			temp = temp.nodes[idx];
		}
		
		// temporary map variable
		Map<String, Integer> mapp;
		if(temp.map == null){
			temp.map = new HashMap<String, Integer>();
		}
		mapp = temp.map;
		
		// update listt
		if(mapp.containsKey(docId)){
			int freq = mapp.get(docId);
			mapp.put(docId, freq+1);
		}else{
			mapp.put(docId, 1);
		}
	}
	
	public ArrayList<DocFreqPair> search(String key){
		TrieNode temp = root;
		String str = key.toLowerCase();
		
		//traverse to the final node
		for(int i=0; i<str.length(); i++){
			int idx = (int)str.charAt(i) - 97;
			
			if(temp.nodes[idx] == null){
				System.out.println("No search resulte");
				return null;
			}
			temp = temp.nodes[idx];
		}
		
		Set<String> set = temp.map.keySet();
		ArrayList<DocFreqPair> toret = new ArrayList<DocFreqPair>();
		Iterator<String> itr = set.iterator();
		String docid;
		double idf = docs.size()/(double)set.size();
		idf = Math.log(idf);
		while(itr.hasNext()){
			docid = itr.next();
			double docsize = docs.get(docid);
			double tf = temp.map.get(docid)/docsize;
			toret.add(new DocFreqPair(docid, key, tf, idf));
		}
		return toret;
	}
	
	public void Query(String q){
		StringTokenizer tok = new StringTokenizer(q);
		SortedMap<String, Double> results = new TreeMap<String, Double>();
		while(tok.hasMoreTokens()){
			String term = tok.nextToken();
			ArrayList<DocFreqPair> res = search(term);
			for(DocFreqPair p : res){
				if(!results.containsKey(p.docid)){
					double tfIdf = p.tf*p.idf;
					results.put(p.docid, tfIdf);
				}else{
					double tfIdf = p.tf*p.idf;
					tfIdf += results.get(p.docid);
					results.put(p.docid, tfIdf);
				}
			}
		}
		
		List<Entry<String, Double>> res = entriesSortedByValues(results);
		for(Entry<String, Double> e : res){
			System.out.println(e.getKey());
		}
		
	}
	
	static <K,V extends Comparable<? super V>> 
	List<Entry<K, V>> entriesSortedByValues(Map<K,V> map) {

		List<Entry<K,V>> sortedEntries = new ArrayList<Entry<K,V>>(map.entrySet());

		Collections.sort(sortedEntries, 
				new Comparator<Entry<K,V>>() {
			@Override
			public int compare(Entry<K,V> e1, Entry<K,V> e2) {
				return e2.getValue().compareTo(e1.getValue());
			}
		}
				);

		return sortedEntries;
	}
	
}
