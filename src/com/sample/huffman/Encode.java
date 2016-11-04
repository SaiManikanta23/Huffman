/* Name : Deepika Joseph
 * Wsu ID: R382U353
 * Assignment-3 Part-2
 */

package com.sample.huffman;

import java.util.ArrayList;
import java.util.Map;

public class Encode {

	private String sourceFileLocation;
	private String targetFileLocation;

	public Encode(String sourceFileLocation, String targetFileLocation) {
		this.sourceFileLocation = sourceFileLocation;
		this.targetFileLocation = targetFileLocation;
	}

	private void encodingString() {
		System.out.println("<-Starting File Encoding ->");
		Map<Character, Integer> map = huffmanImp.createMapFromFile(this.sourceFileLocation);
		Node rootNode = huffmanImp.huffman(map);
		ArrayList<huffmanTple> encodVals = huffmanImp.canonizeHuffmanTree(rootNode);
		System.out.println("<-Applying Algorithm->");
		System.out.println(encodVals);
		// 4. Write the file based on the encoding
		System.out.println("<-Writing Encoded File to -Huffman/src/" + this.targetFileLocation + " ->");
		huffmanImp.writeEncodedFile(this.sourceFileLocation, this.targetFileLocation, encodVals);
		System.out.println("<-Completed Encoding->");
	}


public void Decode(String sourceFileLocation, String targetFileLocation) {
	this.sourceFileLocation = sourceFileLocation;
	this.targetFileLocation = targetFileLocation;
}


public static void main(String[] args) {
	Encode encode;

	if (args.length == 2) {
		encode = new Encode(args[0], args[1]);
	} else {
// To change the source of output file or input file, please change it in the below line 
		encode = new Encode("sample1.txt", "Sample1_Huffman.hpc");
	}

	encode.encodingString();
	Decode decode;

	if (args.length == 2) {
		decode = new Decode(args[0], args[1]);
	} else {
		decode = new Decode("Sample1_Huffman.hpc", "Sample1_final_Recheck.txt");
	}

	decode.performDecodingString();
}
}

