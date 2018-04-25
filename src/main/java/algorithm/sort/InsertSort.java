package algorithm.sort;

import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * 插入排序
 * @author mymai
 *
 */
public class InsertSort extends AbstractSort{
	
	public int[] sortInner(int[] s){
		int j = 0;
		for(int i = 1; i<s.length;i++){
			int w = s[i];
			for(j=i; j>0&& w<s[j-1];j--){
				s[j] = s[j-1];
			}
			s[j] = w;
			
		}
		
		return s;
	}
	
	

	public static void main(String[] args) {
		InsertSort insertSort = new InsertSort();
		int[] generateSort = insertSort.generateSort(1000,200);
		System.out.println(Arrays.toString(generateSort));
		
		
		int[] sort = insertSort.sort(generateSort);
		
		System.out.println(Arrays.toString(sort));
		
	}

}
