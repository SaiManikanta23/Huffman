/* Name : Deepika Joseph
 * Wsu ID: R382U353
 * Assignment-3 Part-2
 */


package com.sample.huffman;


import java.io.*;
import java.util.*;

public class huffmanImp {

	public static Node huffman(Map<Character, Integer> map) {
		PriorityQueue<Node> q = new PriorityQueue<>(convertMapToList(map));

		for (int i = 1; i < map.size(); i++) {
			Node z = new Node();
			Node x = q.poll();
			z.lft = x;
			Node y = q.poll();
			z.rgt = y;
			z.freq = x.freq + y.freq;
			z.letter = z.INTRNODE_CHAR;
			q.add(z);
		}
		return q.poll();
	}

	
	private static void performInorderTraversal(Node current, String representation, ArrayList<huffmanTple> list) {
		if (current == null) {
			return;
		}

		performInorderTraversal(current.lft, representation + "0", list);
		if (current.letter != (char) 0x01) {
			list.add(new huffmanTple(current.letter, representation));
		}
		performInorderTraversal(current.rgt, representation + "1", list);
	}

	private static ArrayList<Node> convertMapToList(Map<Character, Integer> map) {
		ArrayList<Node> list = new ArrayList<>();
		for (Map.Entry<Character, Integer> entry : map.entrySet()) {
			list.add(new Node(entry.getKey(), entry.getValue()));
		}
		return list;
	}


	public static Map<Character, Integer> createMapFromFile(String filePath) {
		FrequencyMeasure createFrequencyMapWorker = new FrequencyMeasure();
		createFrequencyMapWorker.map.put((char) 0x00, 1);
		huffmanImp.readFromFileAndAlgorithm(filePath, createFrequencyMapWorker);
		return createFrequencyMapWorker.map;
	}

	protected static ArrayList<huffmanTple> canonizeHuffmanTree(Node root) {
		ArrayList<huffmanTple> encodings = huffmanImp.extractEncodings(root);

		huffmanImp.sortHuffmanTuples(encodings);

		huffmanImp.canonizeEncodings(encodings);
		return encodings;
	}

	protected static void canonizeEncodings(ArrayList<huffmanTple> encodings) {
		int currentNum = 0;
		for (int i = encodings.size() - 1; i >= 0; i--) {
			huffmanTple currentTuple = encodings.get(i);
			currentTuple.showCase = huffmanImp.rightPadString(Integer.toBinaryString(currentNum), currentTuple.showCase.length());
			if (i > 0) {
				currentNum = (currentNum + 1) >> (currentTuple.showCase.length() - encodings.get(i - 1).showCase.length());
			}
		}
	}


	protected static void sortHuffmanTuples(ArrayList<huffmanTple> list) {
		Collections.sort(list, new Comparator<huffmanTple>() {
			@Override
			public int compare(huffmanTple o1, huffmanTple o2) {
				if (o1.showCase.length() > o2.showCase.length()) {
					return 1;
				} else if (o1.showCase.length() < o2.showCase.length()) {
					return -1;
				} else {
					if (o1.ltr < o2.ltr) {
						return 1;
					} else if (o1.ltr > o2.ltr) {
						return -1;
					}
				}
				return 0;
			}
		});
	}


	public static String generateLookupCode(ArrayList<huffmanTple> encodings) {
		StringBuilder builder = new StringBuilder();
		builder.append(huffmanImp.rightPadString(Integer.toHexString(encodings.size()), 2));
		Collections.sort(encodings, new Comparator<huffmanTple>() {
			@Override
			public int compare(huffmanTple o1, huffmanTple o2) {
				if (o1.ltr > o2.ltr) {
					return 1;
				} else if (o1.ltr < o2.ltr) {
					return -1;
				}
				return 0;
			}
		});
		for (huffmanTple tuple : encodings) {
			builder.append(huffmanImp.rightPadString(Integer.toHexString((int) tuple.ltr), 2));
			builder.append(huffmanImp.rightPadString(Integer.toHexString(tuple.showCase.length()), 2));
		}
		return builder.toString();
	}


	protected static void writeEncodedFile(String inputPath, String outputPath, ArrayList<huffmanTple> encodings) {
		encodeFile writeEncodedFile = new encodeFile(outputPath, encodings);
		writeEncodedFile.writeBeginningOfFile(huffmanImp.generateLookupCode(encodings));
		huffmanImp.readFromFileAndAlgorithm(inputPath, writeEncodedFile);
		writeEncodedFile.writeEndOfFile();
	}


	protected static void writeDecodedFile(String inputPath, String outputPath) {
		decodeFile writeDecodedFileWorker = new decodeFile(outputPath);
		huffmanImp.readFromBinFil(inputPath, writeDecodedFileWorker);
	}

	
	public static String rightPadString(String input, int length) {
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append("0");
		}
		try {
			return sb.toString().substring(input.length()) + input;
		} catch (StringIndexOutOfBoundsException e) {
			return input;
		}
	}

	public static String leftPadString(String input, int length) {
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append("0");
		}
		try {
			return input + sb.toString().substring(input.length());
		} catch (StringIndexOutOfBoundsException e) {
			return input;
		}
	}


	private static ArrayList<huffmanTple> extractEncodings(Node root) {
		ArrayList<huffmanTple> list = new ArrayList<>();
		huffmanImp.performInorderTraversal(root, "", list);
		return list;
	}


	private static void readFromFileAndAlgorithm(String filePath, fileReader handler) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"))) {
			int currentByte;
			while ((currentByte = br.read()) != -1) {
				handler.doWork(currentByte);
			}
		} catch (FileNotFoundException e) {
			System.err.println("Cannot Find the file in the path: " + filePath);
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	private static void readFromBinFil(String filePath, fileReader handler) {
		try (DataInputStream ds = new DataInputStream(new BufferedInputStream(new FileInputStream(filePath)))) {
			int currentByte;
			while((currentByte = ds.read()) != -1) {
				handler.doWork(currentByte);
			}
		} catch (FileNotFoundException e) {
			System.err.println("Cannot Find the file in the path: " + filePath);
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
