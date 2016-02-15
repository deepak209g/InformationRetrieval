import java.util.*;


public class TrieNode {
	Map<String,Integer> map;
	TrieNode nodes[];
	public TrieNode() {
		super();
		this.map = null;
		this.nodes = new TrieNode[26];
		for(int i=0; i<26; i++){
			nodes[i] = null;
		}
	}
}
