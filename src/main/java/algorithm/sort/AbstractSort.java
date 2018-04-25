/**
 * 
 */
package algorithm.sort;

import java.util.Random;

/**
 * @author mymai
 *
 */
public abstract class AbstractSort {
	
	protected int size = 20;
	
	protected int[] arrays;
	
	protected int[] generateSort(){
		return generateSort(1000, size);
	}
	
	protected int[] generateSort(Integer maxNum,Integer bsize){
		
		Random ran = new Random();
		
		if(bsize==null) bsize = size;
		
		arrays = new int[bsize];
		
		for (int i = 0; i < arrays.length; i++) {
			arrays[i] = ran.nextInt(maxNum);
		}
		
		return arrays;
	}
	
	protected void swap(int w1,int j1){
		int j = w1;
		w1 = j1;
		j1 = j;
	}
	
	
	protected int[] sort(int[] s){
		long now = System.currentTimeMillis();
		int[] sortInner = sortInner(s);
		long end = System.currentTimeMillis();
		System.out.println("排序耗時："+(end-now)+"毫秒");
		return sortInner;
	}
	
	protected abstract int[] sortInner(int[] s);
	
	

}
