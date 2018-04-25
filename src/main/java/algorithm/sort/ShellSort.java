/**
 * 
 */
package algorithm.sort;

import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * 希尔排序
 * @author mymai
 *
 */
public class ShellSort extends AbstractSort {

	/*
	 * (non-Javadoc)
	 * 
	 * @see algorithm.sort.AbstractSort#sortInner(int[])
	 */
	@Override
	protected int[] sortInner(int[] s) {

		int gap = s.length/2;
		
		do{
			
			System.out.println("选的序列："+gap);
			
			int j = 0;
			
			for (int i = gap; i < s.length; i+=gap) {

				int temp = s[i];

				for (j = i; j-gap >= 0 && s[j - gap] > temp; j-=gap) {
					s[j] = s[j - gap];
				}

				s[j] = temp;

			}
			
			System.out.println(Arrays.toString(s));
			
			if(gap>1) gap/=2; else break;
			
			
		}while(gap>0);


		return s;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ShellSort insertSort = new ShellSort();
		int[] generateSort = insertSort.generateSort(2000,100);
		System.out.println(Arrays.toString(generateSort));
		
		int[] sort = insertSort.sort(generateSort);
		
		System.out.println(Arrays.toString(sort));

	}

}
