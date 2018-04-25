package algorithm.sort;

import edu.emory.mathcs.backport.java.util.Arrays;

//算法思想：
//先随机找一个数，作为起点，然后从右边开始遍历寻找比基准数字小的点，找到去填
//然后从左向右遍历，找到比i大的数，就放入前面j的空位，以此类推，直到找到i==j的位置

public class QuickSort extends AbstractSort{

	@Override
	protected int[] sortInner(int[] s) {
		quicksort(s, 0, s.length-1);
		return s;
	}
	
	
	private void quicksort(int[] s,int left,int right){
		
		if(right<left) return;
		
		//先选点
		int privot = s[left];
		int i = left;
		int j = right;
		
		//这里准备循环
		
		while(true){
			
			while(s[j] > privot&&j>left) j--;
			
			if(i < j) s[i] = s[j];
			
			while(s[i] < privot&&i<right) i++;
			
			if(i < j) s[j] = s[i];
			
			System.out.println("i="+i+"\tj"+j);
			
			if(i>=j) {
				s[i] = privot;
				break;
			}
		}
		
		quicksort(s, left, i-1);
		
		quicksort(s, i+1, right);
		
	}
	
	public static void main(String[] args) {
		
		QuickSort insertSort = new QuickSort();
		/*int[] generateSort = insertSort.generateSort(100,10);
		System.out.println(Arrays.toString(generateSort));*/
		//[66, 54, 54, 83, 31, 96, 32, 40, 6, 41]
		int[] ss = {66, 54, 54, 83, 31, 96, 32, 40, 6, 41};
		
		int[] sort = insertSort.sort(ss);
		
		System.out.println(Arrays.toString(sort));
		
	}

}
