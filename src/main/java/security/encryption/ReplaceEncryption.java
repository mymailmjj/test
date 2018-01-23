/**
 * 
 */
package security.encryption;

import java.nio.ByteBuffer;

/**
 * 置换加密方法
 * @author cango
 *
 */
public class ReplaceEncryption {
    
    private int maxwidth;
    
    private byte[] keyArray(){
        
        byte[] bytes = new byte[maxwidth];
        
        for(int i = 0; i< maxwidth; i++){
            bytes[i] = (byte) (maxwidth-i-1);
        }
        
        return bytes;
    }
    
    private int findIndexForElement(int findEle,byte[] bytes,int fromindex,int endindex){
        
        int middleindex = (fromindex+endindex)/2;
        int middlevalue = bytes[middleindex];
        
        if(middlevalue > findEle){
            endindex = middleindex;
            findIndexForElement(findEle, bytes,fromindex,endindex);
        }else if(middlevalue < findEle){
            fromindex = middleindex;
            findIndexForElement(findEle, bytes,fromindex,endindex);
        }else{
            return middlevalue;
        }

        return -1;
        
    }
    
    /**
     * 加密明文
     * 主方法
     * @param bytes
     */
    public byte[] encrypt(byte[] bytes){
        
      byte[][] convert = convert(bytes); 
      
      byte[][] exChangeColumn = encryptChangeColumn(convert);
        
      byte[] convert2 = convert(exChangeColumn);
      
      return convert2;
      
    }
    
    /**
     * 解密
     * 主方法
     * @param bytes
     * @return
     */
    public byte[] decrypt(byte[] bytes){
        byte[][] convert = convert(bytes); 
        byte[][] decryptColumn = decryptColumn(convert);
        byte[] convert2 = convert(decryptColumn);
        return convert2;
    }
    
    /**
     *一维数组转换成二维数组
     * @param bytes
     */
    private byte[][] convert(byte[] bytes){
        
        double w = Math.sqrt(bytes.length);
        
        int width = (int) Math.ceil(w);
        
        maxwidth = width;
        
        byte[][] nbytes = new byte[width][width];
        
        int m = 0;
        
        for(int i = 0; i < width;i++){
            for(int j = 0; j < width;j++){
                if(m < bytes.length){
                    nbytes[i][j] = bytes[m++]; 
                }
            }
        }
        
        return nbytes;
    }
    
    /**
     * 二维转换成一维
     * @param bytes
     * @return
     */
    private byte[] convert(byte[][] bytes){
        int w = bytes.length*bytes.length;
        byte[] rbytes = new byte[w];
        int n = 0;
        for(int i = 0; i < bytes.length;i++){
            for(int j = 0; j < bytes.length;j++){
                rbytes[n++] = bytes[i][j]; 
            }
        }
        return rbytes;
    }
    
    
    /**
     * 进行加密转换
     * @param bytes
     * @return
     */
    private byte[][] encryptChangeColumn(byte[][] bytes){
        byte[][] tempArray = createArray(bytes.length);
        
        byte[] keyArray = keyArray();
        
        for(int i = 0; i < bytes.length;i++){
            for(int j = 0; j < bytes.length;j++){
                tempArray[i][j] = bytes[i][keyArray[j]]; 
            }
        }
        
        return tempArray;
        
    }
    
    
    /**
     * 进行解密转换
     * @param bytes
     * @return
     */
    private byte[][] decryptColumn(byte[][] bytes){
        byte[][] tempArray = createArray(bytes.length);
        
        byte[] keyArray = keyArray();
        
        for(int i = 0; i < bytes.length;i++){
            for(int j = 0; j < bytes.length;j++){
                int index = findIndexForElement(j, keyArray, 0, keyArray.length);
                tempArray[i][j] = bytes[i][index]; 
            }
        }
        
        return tempArray;
        
    }
    
    
    
    private byte[][] createArray(int length){
        return new byte[length][length];
    }
    
    
    public static void main(String[] args) {
        
        ReplaceEncryption encryption = new ReplaceEncryption();
        
        ByteBuffer buffer = ByteBuffer.wrap("我和我的祖国，一刻也不能分割！".getBytes());
        
        byte[] array = buffer.array();
        
        byte[] encrypt = encryption.encrypt(array);
        
        for (int i = 0; i < encrypt.length; i++) {
            char c = (char)i;
        }
        
    }

}
