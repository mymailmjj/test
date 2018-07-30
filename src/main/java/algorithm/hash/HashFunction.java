package algorithm.hash;

public class HashFunction {

	public static int hashMultiply(String key, int primer) {
		int hash = 0;
		char[] charArray = key.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			hash = 33 * hash + (int) charArray[i];
		}
		return hash % primer;
	}

	public static int hashBitOp(String key, int primer) {
		int hash = 0;
		char[] charArray = key.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			hash = (hash << 4) ^ (hash >> 28) ^ charArray[i];
		}
		return hash % primer;
	}

	public static void main(String[] args) {
		String s = "abcdefghi";
		int hash = hashBitOp(s, 5);
		System.out.println(hash);
	}

}
