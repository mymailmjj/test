/**
 * 
 */
package es.lucene;

import java.io.IOException;

import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.IntsRef;
import org.apache.lucene.util.IntsRefBuilder;
import org.apache.lucene.util.fst.Builder;
import org.apache.lucene.util.fst.ByteSequenceOutputs;
import org.apache.lucene.util.fst.FST;

import com.sun.corba.se.impl.javax.rmi.CORBA.Util;
import com.sun.org.apache.bcel.internal.util.ByteSequence;

import junit.framework.TestCase;

/**
 * @author az6367
 *
 */
public class DataStructure extends TestCase {
	
	
	/**
	 * 模拟创建
	 * @throws IOException
	 */
	public void testCreateFST() throws IOException{
		
		String[] str = {"abc","acd","ace"};
		byte[][] bytes = {{1,2},{3,4},{5,6}};
		ByteSequenceOutputs singleton = ByteSequenceOutputs.getSingleton();
		Builder<BytesRef> builder = new Builder<>(FST.INPUT_TYPE.BYTE1, singleton);
		
		IntsRefBuilder intsRef = new IntsRefBuilder();
		for(int i = 0; i<str.length;i++){
			String string = str[i];
			byte[] bytes2 = string.getBytes();
			BytesRef bytesRef = new BytesRef(bytes2);
			byte[] bs = bytes[i];
			IntsRef intsRef2 = org.apache.lucene.util.fst.Util.toIntsRef(bytesRef, intsRef);
			builder.add(intsRef2, new BytesRef(bs));
		}
		
		FST<BytesRef> fst = builder.finish();
		
		BytesRef bytesRef = org.apache.lucene.util.fst.Util.get(fst, new BytesRef("acd".getBytes()));
		
		System.out.println(bytesRef);
		
	}
	

}
