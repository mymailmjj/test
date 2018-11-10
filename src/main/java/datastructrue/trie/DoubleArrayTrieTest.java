package datastructrue.trie;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 测试DoubleArrayTrie的性能
 * @author az6367
 * http://www.hankcs.com/program/java/双数组trie树doublearraytriejava实现.html
 */
public class DoubleArrayTrieTest {

	public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\AZ6367\\Desktop\\0000\\技术储备\\搜索任务\\导入数据.txt"));
        String line;
        List<String> words = new ArrayList<String>();
        Set<Character> charset = new HashSet<Character>();
        while ((line = reader.readLine()) != null)
        {
        	System.out.println("导入："+line);
            words.add(line);
            // 制作一份码表debug
            for (char c : line.toCharArray())
            {
                charset.add(c);
            }
        }
        reader.close();
        // 这个字典如果要加入新词必须按字典序，参考下面的代码
        Collections.sort(words);
//        BufferedWriter writer = new BufferedWriter(new FileWriter("./data/sorted.dic", false));
//        for (String w : words)
//        {
//            writer.write(w);
//            writer.newLine();
//        }
        System.out.println("字典词条：" + words.size());
 
        String infoCharsetValue = "";
        String infoCharsetCode = "";
        for (Character c : charset)
        {
            infoCharsetValue += c.charValue() + "    ";
            infoCharsetCode += (int)c.charValue() + " ";
        }
        infoCharsetValue += '\n';
        infoCharsetCode += '\n';
        System.out.print(infoCharsetValue);
        System.out.print(infoCharsetCode);
 
        DoubleArrayTrie dat = new DoubleArrayTrie();
        System.out.println("是否错误: " + dat.build(words));
        System.out.println(dat);
        List<Integer> integerList = dat.commonPrefixSearch("北京王家");
        for (int index : integerList)
        {
            System.out.println(words.get(index));
        }
        
        System.out.println("--------------------------------------");
        
        int index = dat.exactMatchSearch("北京隆格酒店");
        System.out.println("index:"+index);
        System.out.println(words.get(index));
    }

}
