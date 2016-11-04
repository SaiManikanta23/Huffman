/* Name : Deepika Joseph
 * Wsu ID: R382U353
 * Assignment-3 Part-2
 */


package com.sample.huffman;

public class Node implements Comparable<Node> {
	public Node lft;
	public Node rgt;
	public int freq;
	public char letter;
	public final char INTRNODE_CHAR = (char) 0x01;

	public Node() {
	}

	public Node(int freq) {
		this.freq = freq;

		this.letter = INTRNODE_CHAR;
	}

	public Node(char letter, int freq) {
		this.lft = null;
		this.rgt = null;
		this.freq = freq;
		this.letter = letter;
	}
	
	@Override
	public String toString() {
		return this.letter + " => " + this.freq;
	}

	@Override
	public int compareTo(Node o) {
		if (this.freq > o.freq) {
			return 1;
		} else if (this.freq < o.freq) {
			return -1;
		}
		return 0;
	}


	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		Node o = (Node) obj;
		return (this.freq == o.freq) && (this.letter == o.letter);
	}
}
