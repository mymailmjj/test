package algorithm.heap;

import java.time.Duration;
import java.time.LocalTime;

import algorithm.heap.BinaryHeap.Node;
import algorithm.sort.InsertSort;
import edu.emory.mathcs.backport.java.util.Arrays;
import utils.NumberUtils;

/**
 * 测试binaryHead的排序性能
 * 比较堆排序和插入排序的性能后，10万数字的规模，堆排序的性能好于插入排序
 * @author mymai
 *
 */
public class BinaryHeapSort {

	public static void main(String[] args) {
		
		int number = 100000;
		
		BinaryHeap<Integer> bh = new BinaryHeap<>();
		
		LocalTime now = LocalTime.now();
		
		for(int i = 0; i < number;i ++){
			bh.insert(i);
		}
		
		for(int i = 0; i < number;i ++){
			Node removeHead = bh.removeHead();
			System.out.println("排序后:"+removeHead);
		}
		
		LocalTime now1 = LocalTime.now();
		
		Duration between = Duration.between(now, now1);
		
		System.out.println("堆排序耗时："+between.getSeconds()+"s");
		
		int[] ss = new int[number];
		
		for(int i = 0; i < number;i ++){
			ss[i] = number-i;
		}
		
		LocalTime now2 = LocalTime.now();
		
		InsertSort insertSort = new InsertSort();
		
		insertSort.sortInner(ss);
		
//		System.out.println(Arrays.toString(ss));
		
		LocalTime now3 = LocalTime.now();
		
		Duration between1 = Duration.between(now2,now3);
		
		System.out.println("插入排序耗时："+between1.getSeconds()+"s");
		
		

	}

}
