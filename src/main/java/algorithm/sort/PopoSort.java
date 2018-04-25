/**
 * 
 */
package algorithm.sort;

import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * 冒泡排序
 * @author mymai
 *
 */
public class PopoSort extends AbstractSort {

	/* (non-Javadoc)
	 * @see algorithm.sort.AbstractSort#sortInner(int[])
	 */
	@Override
	protected int[] sortInner(int[] s) {
		
		for(int i = 0; i < s.length-1;i++){
			for(int j = 0; j < s.length-1-i ;j++){
				if(s[j] > s[j+1]){
					swap(s[j], s[j+1]);
				}
			}
		}
		
		
		
		
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		InsertSort insertSort = new InsertSort();
		int[] generateSort = insertSort.generateSort(1000,200);
		System.out.println(Arrays.toString(generateSort));
		
		
		int[] sort = insertSort.sort(generateSort);
		
		System.out.println(Arrays.toString(sort));
	}

}
