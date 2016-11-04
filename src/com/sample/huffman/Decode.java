/* Name : Deepika Joseph
 * Wsu ID: R382U353
 * Assignment-3 Part-2
 */

package com.sample.huffman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Decode {
	private String sourceFileLocation;
	private String targetFileLocation;

	public Decode(String sourceFileLocation, String targetFileLocation) {
		this.sourceFileLocation = sourceFileLocation;
		this.targetFileLocation = targetFileLocation;
	}

	
	void performDecodingString() {
		System.out.println("<-Starting File Decoding->");
		huffmanImp.writeDecodedFile(this.sourceFileLocation, this.targetFileLocation);
		System.out.println("<- Decoded file updated to -Huffman/src/" + this.targetFileLocation +"->");
		System.out.println("<-Completed Decoding->");
	}

	protected static Map<String, Character> convertTuplesToLookupMap(ArrayList<huffmanTple> tuples) {
		Map<String, Character> map = new HashMap<>();
		for (huffmanTple t : tuples) {
			map.put(t.showCase, t.ltr);
		}
		return map;
	}

	protected static ArrayList<huffmanTple> convertMapToTuples(Map<Character, Integer> map) {
		ArrayList<huffmanTple> listVals = new ArrayList<>();
		for (Map.Entry<Character, Integer> entVal : map.entrySet()) {
			StringBuilder strBuld = new StringBuilder();
			for (int i = 0; i < entVal.getValue(); i++) {
				strBuld.append("1");
			}
			listVals.add(new huffmanTple(entVal.getKey(), strBuld.toString()));
		}
		return listVals;
	}

	public static void main(String[] args) {
		Decode decodeStringInput;

		if (args.length == 2) {
			decodeStringInput = new Decode(args[0], args[1]);
		} else {
// To change the source of output file or input file, please change it in the below line 
			decodeStringInput = new Decode("output1.hpc", "output1.txt");
		}

		decodeStringInput.performDecodingString();
	}
}
