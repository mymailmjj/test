/**
 * 
 */
package datastructrue.trie;

import java.util.ArrayList;
import java.util.List;

/**
 * @author az6367
 *
 */
public class TripleTrieTree {

	private Node root = new Node();

	static class Node {

		private char name;

		private Node son;

		private Node leftSon;

		private Node rightSon;

		private boolean isWord = false;

		public boolean isWord() {
			return isWord;
		}

		public void setWord(boolean isWord) {
			this.isWord = isWord;
		}

		public Node(char name) {
			this.name = name;
		}

		public Node() {
		}

		public Node(char name, boolean isWord) {
			this.name = name;
			this.isWord = isWord;
		}

		public void insert(char c, boolean isword) {

			if (this.son == null) {
				son = new Node(c, isword);
			}

			if (son.getName() == c)
				return;

			// 左边插入
			if (son.getName() > c) {
				if (leftSon == null) {
					leftSon = new Node(c, isword);
				} else {
					leftSon.insert(c, isword);
				}
			}

			// 右边插入
			if (son.getName() < c) {
				if (rightSon == null) {
					rightSon = new Node(c, isword);
				} else {
					rightSon.insert(c, isword);
				}
			}

		}

		public char getName() {
			return name;
		}

		public void setName(char name) {
			this.name = name;
		}

		public Node getSon() {
			return son;
		}

		public void setSon(Node son) {
			this.son = son;
		}

		public Node getLeftSon() {
			return leftSon;
		}

		public void setLeftSon(Node leftSon) {
			this.leftSon = leftSon;
		}

		public Node getRightSon() {
			return rightSon;
		}

		public void setRightSon(Node rightSon) {
			this.rightSon = rightSon;
		}

	}

	public void insert(String name) {
		if (name == null)
			throw new IllegalArgumentException();

		char[] charArray = name.toCharArray();

		int i = 0;

		Node temp = root;

		while (i < charArray.length) {

			char c = charArray[i];

			while (temp != null) {

				Node son = temp.getSon();
				
				boolean isword = i == charArray.length-1;

				if (son == null) {
					Node node = new Node(c,isword);
					temp.setSon(node);
					temp = node;
					i++;
					break;
				} else {
					if (son.name == c) {
						i++;
						temp = son;
						break;
					} else if (son.name > c) {
						if (temp.leftSon == null) {
							temp = temp.leftSon = new Node(c,isword);
							i++;
							break;
						} else {
							if (temp.leftSon.name == c) {
								i++;
								temp = temp.leftSon;
								break;
							}else{
								temp = temp.leftSon;
							}
						}
					} else if (son.name < c) {
						if (temp.rightSon == null) {
							temp = temp.rightSon = new Node(c,isword);
							i++;
							break;
						} else {
							if (temp.rightSon.name == c) {
								i++;
								temp = temp.rightSon;
								break;
							}else{
								temp = temp.rightSon;
							}
						}
					}
				}
			}

		}

	}

	public List<String> query(String name) {

		if (name == null)
			throw new IllegalArgumentException();

		char[] charArray = name.toCharArray();

		int i = 0;

		Node temp = root;
		
		boolean flag = true;

		while (i < charArray.length) {

			char c = charArray[i];

			while (temp != null) {

				Node son = temp.getSon();

				char name2 = son.name;

				if (name2 == c) {
					i++;
					temp = son;
					break;
				}

				else if (name2 > c) {
					
					if(temp.leftSon==null){
						flag = false;
						break;
					}else if(temp.leftSon.name==c){
						i++;
						temp = temp.leftSon;
						break;
					}else{
						temp = temp.leftSon;
					}
				}

				else if (name2 < c) {
					if(temp.rightSon==null){
						flag = false;
						break;
					}else if(temp.rightSon.name==c){
						i++;
						temp = temp.rightSon;
						break;
					}else{
						temp = temp.rightSon;
					}
				}
			}
			
			if(!flag) break;

			if (temp == null && i < charArray.length)
				break;

		}

		List<String> lists = new ArrayList<String>();

		printAllStr(temp, name, lists);

		return lists;

	}

	private void printAllStr(Node node, String str, List<String> lists) {
		Node son = node.getSon();

		if (son != null) {
			char name = son.getName();
			String sonStr = str + name;

			if (son.isWord) {
				lists.add(sonStr);
			}

			printAllStr(son, sonStr, lists);
		}

		if (node.leftSon != null) {
			char name = node.leftSon.getName();
			String leftStr = str + name;

			if (node.leftSon.isWord) {
				lists.add(leftStr);
			}

			printAllStr(node.leftSon, leftStr, lists);
		}

		if (node.rightSon != null) {
			char name = node.rightSon.getName();
			String rightStr = str + name;

			if (node.rightSon.isWord) {
				lists.add(rightStr);
			}

			printAllStr(node.rightSon, rightStr, lists);
		}

	}

	public static void main(String[] args) {
		TripleTrieTree tree = new TripleTrieTree();
		tree.insert("北京恋都商旅酒店（国贸店）");
		tree.insert("北京瑞兆快捷酒店(国贸分店)");
		tree.insert("北京易豪酒店");
		tree.insert("北京隆格酒店");
		tree.insert("北京都市花雨商务酒店");
		tree.insert("北京东四饭店");
		tree.insert("北京国泰饭店");
		tree.insert("北京意大利农场");
		tree.insert("北京前门建国饭店");
		tree.insert("北京瑞海国际商务酒店");
		tree.insert("北京欣燕都连锁酒店(朝阳路店)");
		tree.insert("北京国瑞百捷酒店");
		tree.insert("桔子酒店（北京官园桥店）");
		tree.insert("北京王家客栈");
		tree.insert("北京麦豪斯酒店");
		tree.insert("北京京林大厦");
		tree.insert("北京轻联富润饭店(东四店)");
		tree.insert("北京上东国际酒店");
		tree.insert("北京阅微庄四合院宾馆");
		tree.insert("北京前门富力智选假日酒店");
		tree.insert("速8酒店北京聚丰店(内宾)");
		/*tree.insert("桔子酒店（北京中关村店）");
		tree.insert("北京宣武门速8酒店（原捷捷酒店）");
		tree.insert("北京诚泰商务酒店");
		tree.insert("汉庭酒店(北京燕莎霄云桥店)");
		tree.insert("北京青年假日酒店(亦庄店)(大兴-亦庄经济开发区)");
		tree.insert("雅悦酒店(北京崇文门店)");
		tree.insert("如家快捷酒店（北京安德路店）");
		tree.insert("莫泰268(北京王府井步行街店)");
		tree.insert("北京空港（精品）快捷酒店");
		tree.insert("北京东四中航快捷酒店");
		tree.insert("中安之家酒店连锁(北京安定门店)");
		tree.insert("北京博雅国际酒店");
		tree.insert("北京竺航速8酒店（原竺航宾馆）");
		tree.insert("北京锐思特汽车连锁旅店(和平里店)");
		tree.insert("北京安怡之家宾馆(原春禾酒店)");
		tree.insert("格林豪泰（北京清河店）");
		tree.insert("城市客栈(北京欢乐谷店)");
		tree.insert("锦江之星（北京亦庄店）");
		tree.insert("汉庭快捷酒店（北京五棵松店）");
		tree.insert("北京都市之星快捷酒店");
		tree.insert("北京龙福宫宾馆(西直门店)");
		tree.insert("北京龙福宫宾馆(复兴路翠微店)");
		tree.insert("北京龙福宫宾馆（团结湖店）");
		tree.insert("北京龙福宫宾馆(大望路店)");
		tree.insert("北京龙福宫宾馆(洋桥店)");
		tree.insert("速8酒店(北京国展和平里店)");
		tree.insert("北京春禾园宾馆");
		tree.insert("锦江之星（北京后海店）");
		tree.insert("锦江之星(北京南站店)(内宾)");
		tree.insert("汉庭全季酒店北京王府井店（原汉庭酒店北京王府井店）");
		tree.insert("如家快捷酒店（北京小西天店）");
		tree.insert("格林豪泰（北京方庄商务酒店）");
		tree.insert("锦江之星（北京西直门店）");
		tree.insert("如家快捷酒店（北京紫竹桥店）");
		tree.insert("格林豪泰（北京安贞鸟巢店）");
		tree.insert("北京新航岸酒店");
		tree.insert("北京新宇桥宾馆");
		tree.insert("北京欣燕都连锁酒店(菜市口店)");
		tree.insert("格林豪泰（北京潘家园店）");
		tree.insert("如家快捷酒店（北京安贞店）");
		tree.insert("如家快捷酒店(北京国展左家庄店)(原左家庄店)");
		tree.insert("如家快捷酒店（北京和平西桥店）");
		tree.insert("如家快捷酒店(北京青年路大悦城店)");
		tree.insert("北京优优客酒店");
		tree.insert("北京京裕宾馆（清华店）");
		tree.insert("如家快捷酒店（北京广渠门店）（内宾）");
		tree.insert("速8酒店北京鸟巢店");
		tree.insert("汉庭酒店(北京南锣鼓巷店)");
		tree.insert("北京临空皇冠假日酒店");
		tree.insert("锦江之星（北京广安门店）");
		tree.insert("如家快捷酒店（北京朝阳公园店）（内宾）");
		tree.insert("如家快捷酒店（北京大北窑店）");
		tree.insert("如家快捷酒店（北京四惠店）（内宾）");
		tree.insert("如家快捷酒店(北京什刹海鼓楼交道口店)(原雍和宫)");
		tree.insert("北京空港天缘大酒店");
		tree.insert("如家快捷酒店（北京农展长虹桥店）");
		tree.insert("北京全季酒店");
		tree.insert("桔子酒店（北京劲松桥东店）");
		tree.insert("北京金菠萝国际青年酒店");
		tree.insert("桔子酒店（北京望京店）");
		tree.insert("汉庭全季酒店（北京东直门店）原汉庭快捷");
		tree.insert("汉庭全季酒店北京东单店（原汉庭酒店北京东单店）");
		tree.insert("北京民族园智选假日酒店");
		tree.insert("北京空港奥竺宾馆");
		tree.insert("汉庭酒店(北京望京店)");
		tree.insert("北京色彩连锁酒店（国展店）");
		tree.insert("锦江之星（北京马甸桥店）");
		tree.insert("桔子酒店（北京劲松桥西店）");
		tree.insert("飘HOME-北京东四店");
		tree.insert("北京望京华彩智选假日酒店");
		tree.insert("北京三元桥宜必思酒店");
		tree.insert("北京福泰酒店公寓");
		tree.insert("北京金地公寓(首都机场T3航站楼店)");
		tree.insert("北京都季商务快捷酒店");
		tree.insert("北京银马酒店");
		tree.insert("如家快捷酒店（北京通州八里桥店）（内宾）");
		tree.insert("北京方圆四季宾馆");
		tree.insert("汉庭酒店(北京国展店)");
		tree.insert("如家快捷酒店（北京亦庄天华西路店）");
		tree.insert("北京北辰公寓经营管理分公司贵宾楼");
		tree.insert("北京锦绣山泉宾馆");
		tree.insert("锦江之星（北京奥运村大屯路店）");
		tree.insert("如家快捷酒店(北京鸟巢店)(原北京亚运村店)");
		tree.insert("北京恩德民酒店");
		tree.insert("北京上地速8酒店");
		tree.insert("锦江之星（北京酒仙桥店）");
		tree.insert("如家快捷酒店（北京景泰桥珐琅厂店）（内宾）");
		tree.insert("北京御林汤泉度假村");
		tree.insert("布丁酒店(北京中关村店)");
		tree.insert("汉庭酒店(北京亚运村店)");
		tree.insert("北京怡尔国际商务会馆");
		tree.insert("中国气象局招待所(北京)");
		tree.insert("速8酒店北京通州新华大街店");
		tree.insert("北京东方溢洋宾馆");
		tree.insert("北京欣燕都连锁酒店(天坛东门店)");
		tree.insert("汉庭酒店(北京马甸桥店)(内宾)");
		tree.insert("如家快捷酒店（北京首都博物馆店）（内宾）");
		tree.insert("如家快捷酒店（北京珠市口店）（内宾）");
		tree.insert("如家快捷酒店（积水潭桥0无效");
		tree.insert("格林豪泰（北京西客站北广场店）");
		tree.insert("金泰之家(北京四季青店)");
		tree.insert("格林豪泰（北京传媒大学西门店）");
		tree.insert("北京翠微速8酒店");
		tree.insert("北京升港快捷酒店（军祥聚店）原升港快捷宾馆");
		tree.insert("汉庭快捷酒店（北京国贸店）");
		tree.insert("如家快捷酒店(北京牡丹园店)");
		tree.insert("北京上佳宾馆");
		tree.insert("速8酒店北京怀柔开放路店");
		tree.insert("南院悦居(北京雍和宫店)(原海纳宾馆)");
		tree.insert("如家快捷酒店（北京宋家庄店）");
		tree.insert("北京欣燕都连锁酒店(万丰路店)");
		tree.insert("岭南佳园连锁酒店(北京西单店)");
		tree.insert("汉庭酒店(北京燕莎新源里店)");
		tree.insert("金泰之家（北京金玉元店）");
		tree.insert("北京禧龙宾馆(北三环店)");
		tree.insert("格林联盟(北京天坛东门店)(原天坛红桥店)");
		tree.insert("如家快捷酒店(北京芍药居对外经贸大学店)");
		tree.insert("如家快捷酒店（北京燕莎新源里店）");
		tree.insert("如家快捷酒店(北京酒仙桥798艺术区店)");
		tree.insert("汉庭酒店(北京三元西桥店)");
		tree.insert("汉庭快捷酒店（北京农展馆店）-原汉庭三里屯酒吧街店");
		tree.insert("汉庭快捷酒店（北京国贸二店）");
		tree.insert("北京西单古德豪斯酒店");
		tree.insert("北京青年假日酒店（良乡店）");
		tree.insert("北京青年假日酒店(民大店)");
		tree.insert("汉庭酒店(北京中关村四通桥店)");
		tree.insert("北京洲洋宾馆(魏公村店)");
		tree.insert("锦江之星（北京大兴开发区店）");
		tree.insert("北京洲洋宾馆(知春路店)");
		tree.insert("如家快捷酒店(北京奥林匹克公园店)");
		tree.insert("北京和敬府宾馆");
		tree.insert("汉庭快捷酒店（北京天坛店）");
		tree.insert("北京京友快捷酒店");
		tree.insert("北京乐家连锁酒店（北京建外店）");
		tree.insert("北京伯鑫宾馆(护国寺店)");
		tree.insert("如家快捷酒店（北京京广桥店）");
		tree.insert("北京前门速8酒店");
		tree.insert("格林豪泰（格林联盟北京亚运村店）");
		tree.insert("格林豪泰（北京学清路店）");
		tree.insert("北京心动空间商务酒店");
		tree.insert("北京市静春园宾馆");
		tree.insert("汉庭酒店(北京酒仙桥798店)");
		tree.insert("速8酒店北京顺义博联店");
		tree.insert("北京丽恩酒店");
		tree.insert("如家快捷酒店（北京亚运村小营店）");
		tree.insert("北京西直门速8酒店");
		tree.insert("北京东大桥宜必思酒店");
		tree.insert("北京天缘府宾馆");
		tree.insert("速8酒店北京北七家店(内宾)");
		tree.insert("锦江之星（北京通州新华东街古运河店）");
		tree.insert("汉庭快捷酒店（北京上地环岛店）");
		tree.insert("如家快捷酒店(北京苏州桥店)");
		tree.insert("北京欣燕都连锁酒店(永定门店)");
		tree.insert("沐阳时尚连锁酒店(北京国贸店)(原铂丽商务酒店)");
		tree.insert("沐阳时尚连锁酒店（北京建国门店）");
		tree.insert("沐阳时尚连锁酒店（北京赵公口店）");
		tree.insert("汉庭快捷酒店（北京西客站南广场店）");
		tree.insert("汉庭快捷酒店（北京军博店）");
		tree.insert("汉庭快捷酒店（北京中关村学院桥店）");
		tree.insert("如家快捷酒店（北京万丰路店）（内宾）");
		tree.insert("北京三泰之星宾馆");
		tree.insert("格林豪泰（北京学院路商务酒店）");
		tree.insert("北京玉蜓桥酒店");
		tree.insert("北京紫地客栈");
		tree.insert("速8酒店北京右安门（高铁站）店");
		tree.insert("速8酒店北京大兴黄村店（内宾）");
		tree.insert("北京云彩快捷酒店");
		tree.insert("汉庭快捷酒店（北京广渠门店）");
		tree.insert("北京荣即达连锁酒店");
		tree.insert("北京科通酒店");
		tree.insert("北京烽火联发宾馆");
		tree.insert("如家快捷酒店(北京平安里地铁站店)");
		tree.insert("北京逸旅阳光主题酒店");
		tree.insert("北京兰亭汇酒店");
		tree.insert("北京家的家快捷酒店");
		tree.insert("北京度假屋时尚酒店(南锣鼓巷店)");
		tree.insert("汉庭快捷酒店（北京航天桥店）");
		tree.insert("北京百分百酒店公寓(中关村店)");
		tree.insert("北京百分百酒店公寓（知春路店）");
		tree.insert("北京欣燕都连锁酒店(珠市口二店)");
		tree.insert("北京欣燕都连锁酒店(珠市口店)原(棕树斜街店)");
		tree.insert("北京炮局工厂国际青年旅舍");
		tree.insert("北京华凯宾馆");
		tree.insert("北京市东外宾馆");
		tree.insert("北京御苑会议中心");
		tree.insert("北京欣燕都连锁酒店(刘家窑店)");
		tree.insert("速8酒店（北京马家堡店）");
		tree.insert("北京龙马商务酒店");
		tree.insert("北京优舍酒店");
		tree.insert("北京嘉利华连锁酒店(刘家窑店)");
		tree.insert("北京嘉利华连锁酒店（传媒大学东门店）");
		tree.insert("北京嘉利华连锁酒店(双桥店)");
		tree.insert("北京嘉利华连锁酒店(定福庄分店)");
		tree.insert("北京实华饭店");
		tree.insert("北京嘉利华连锁酒店（玉泉路店）");
		tree.insert("如家快捷酒店（北京华贸店）");
		tree.insert("如家快捷酒店（北京奥运村国家会议中心店）");
		tree.insert("北京如意商务酒店");
		tree.insert("北京金泰绿洲大酒店");
		tree.insert("如家快捷酒店（北京德外店）");
		tree.insert("如家快捷酒店（北京惠新东桥店）");
		tree.insert("北京阳光嘉誉金灿酒店");
		tree.insert("如家快捷酒店(北京望京花家地店)");
		tree.insert("汉庭全季酒店（北京西直门店）");
		tree.insert("北京石景山玖玖源速8酒店");
		tree.insert("桔子酒店（北京天宁寺店）");
		tree.insert("如家快捷酒店（北京西直门展览馆店）");
		tree.insert("汉庭快捷酒店（北京大观园店）");
		tree.insert("如家快捷酒店(北京陶然亭店）");
		tree.insert("如家快捷酒店（北京新街口地铁站店）");
		tree.insert("锦江之星（北京西单店）");
		tree.insert("飘HOME连锁酒店-北京五棵松店(原北京定慧桥店)");
		tree.insert("北京龙泉湖酒店");
		tree.insert("如家快捷酒店（北京广安门店）（内宾）");
		tree.insert("如家快捷酒店（北京天坛南门店）");
		tree.insert("万商如一酒店（北京六铺炕店）");
		tree.insert("南苑e家商务连锁酒店（北京牛街店）");
		tree.insert("如家快捷酒店（北京安华桥地铁站店）");
		tree.insert("如家快捷酒店（北京广渠门富力城店）");
		tree.insert("北京红都实佳宾馆");
		tree.insert("如家快捷酒店（北京木樨园店）（内宾）");
		tree.insert("南苑e家商务连锁酒店（北京王府井店）");
		tree.insert("北京欣燕都连锁酒店(西安门店)");
		tree.insert("万商如一酒店（北京玉泉路店）");
		tree.insert("莫泰168（北京西客站店）");
		tree.insert("北京唐人街商务酒店");
		tree.insert("北京东航之星鼓楼酒店");
		tree.insert("北京西单速8酒店");
		tree.insert("汉庭快捷酒店（北京紫竹桥店）");
		tree.insert("北京事路通宾馆");
		tree.insert("如家快捷酒店（北京中关村南大街店）");
		tree.insert("飘HOME-北京前门店");
		tree.insert("飘HOME-北京建国门店");
		tree.insert("飘HOME连锁酒店-北京南站店(原大观园店)");
		tree.insert("如家快捷酒店(北京前门店)");
		tree.insert("北方朗悦酒店(北京紫竹桥店)");
		tree.insert("如家快捷酒店(北京上地店)");
		tree.insert("北京旅居快捷西便门店");
		tree.insert("格林豪泰（北京天坛赵公口桥快捷酒店）");
		tree.insert("速8酒店北京天坛南门店");
		tree.insert("如家快捷酒店（北京天坛店）（内宾）");
		tree.insert("北京瑞洁宾舍酒店");
		tree.insert("北京欣燕都连锁酒店（广安门店）");
		tree.insert("如家快捷酒店（北京赵公口店）（内宾）");
		tree.insert("北京小窝酒店(原易程小窝酒店)");
		tree.insert("格林联盟(北京地坛酒店)");
		tree.insert("北京乐游酒店");
		tree.insert("格林豪泰(北京市亦庄万源街地铁站商务酒店)");
		tree.insert("锦江之星（北京方庄成寿寺地铁站店）");
		tree.insert("北京侣松园宾馆");
		tree.insert("汉庭酒店(北京鸟巢店)");
		tree.insert("北京国际航空俱乐部");
		tree.insert("锦江之星（北京奥林匹克公园店）");
		tree.insert("飘HOME-北京国贸东店");
		tree.insert("北京欣燕都连锁酒店(光明桥店)");
		tree.insert("如家快捷酒店（北京劲松店）");
		tree.insert("如家快捷酒店（北京国展三元桥店）");
		tree.insert("如家快捷酒店（北京刘家窑店）");
		tree.insert("如家快捷酒店（北京双井店）");
		tree.insert("北京叠玉宾馆-原（志新宾馆）");
		tree.insert("飘HOME-北京酒仙桥店");
		tree.insert("汉庭酒店(北京华贸二店)");
		tree.insert("北京轻联富润饭店(北京站店)");
		tree.insert("北京清泽酒店(内蒙会馆)");
		tree.insert("格林豪泰（北京石景山店）");
		tree.insert("北京161酒店");
		tree.insert("北京煤科宾馆");
		tree.insert("汉庭快捷酒店（北京华贸店）");
		tree.insert("格林豪泰（北京团结湖温泉店）");
		tree.insert("飘HOME-北京王府井店");
		tree.insert("全季酒店(北京朝阳门店)");
		tree.insert("北京王府井春豪宾馆");
		tree.insert("锦江之星（北京慈云寺店）");
		tree.insert("飘HOME-北京西客站店");
		tree.insert("北京慧廷商务酒店(魏公村店)");
		tree.insert("如家快捷酒店（北京丽泽桥店）");
		tree.insert("北京京都苑宾馆");
		tree.insert("北京奥友宾馆");
		tree.insert("北京富伦德酒店");
		tree.insert("北京国盛饭店");
		tree.insert("北京枫林商务酒店（岳各庄店）");
		tree.insert("北京太阳岛宾馆");
		tree.insert("北京西华京兆饭店");
		tree.insert("汉庭酒店(北京德胜门店)");
		tree.insert("北京枣园居宾馆");
		tree.insert("北京富润酒店-菜市口店(原河东宾馆)");
		tree.insert("北京赢家商务酒店");
		tree.insert("北京时腾商务酒店");
		tree.insert("北京五棵松奥世速8酒店");
		tree.insert("北京顺祥家园酒店");
		tree.insert("北京升港快捷宾馆(首都机场店)");
		tree.insert("北京中洋宾馆");
		tree.insert("北京红驿栈酒店");
		tree.insert("北方朗悦酒店(北京金融街店)");
		tree.insert("北京燕岭宾馆");
		tree.insert("北京飞月楼宾馆");
		tree.insert("北京和平里旅居酒店");
		tree.insert("北京哈特商务酒店");
		tree.insert("北京神舟商旅酒店(芍药居店)");
		tree.insert("北京大东方宾馆");
		tree.insert("北京可丽亚商务酒店");
		tree.insert("如家快捷酒店（北京昌平体育馆店）");
		tree.insert("北京智胜快捷酒店");
		tree.insert("北京富润连锁酒店(富润宾馆)");
		tree.insert("北京兰溪宾馆");
		tree.insert("北京滨海明珠宾馆");
		tree.insert("北京正义路华纺商务酒店(正义路酒店)");
		tree.insert("北京意发宾馆北楼");
		tree.insert("北京长白山国际酒店(原吉林大厦)");
		tree.insert("北京天天家酒店");
		tree.insert("北京社科博源宾馆");
		tree.insert("北京兴化苑宾馆");
		tree.insert("北京益百诚花园酒店");
		tree.insert("北京赵家楼饭店");
		tree.insert("北京江海金苑商务酒店");
		tree.insert("北京虹昱铭宾馆");
		tree.insert("北京嘉琳苑宾馆");
		tree.insert("北京丽豪酒店（首都机场航站楼店）-原北京丽豪快捷酒店");
		tree.insert("北京欣燕都连锁酒店(丽泽桥店)");
		tree.insert("北京鸿盛宾馆");
		tree.insert("北京胡同印文化酒店（锣鼓巷店）");
		tree.insert("北京什刹海国际公寓");
		tree.insert("北京成铭宾馆");
		tree.insert("星程酒店（北京西站店）");
		tree.insert("北京惠翔园渡假村");
		tree.insert("北京龙源宾馆");
		tree.insert("北京海特饭店");
		tree.insert("格林豪泰（北京东坝商务酒店）");
		tree.insert("如家快捷酒店(北京北太平庄店)");
		tree.insert("格林豪泰（北京清河桥店）");
		tree.insert("北京每日快捷酒店");
		tree.insert("北京中油宾馆");
		tree.insert("北京海银都宾馆");
		tree.insert("北京锦绣山庄");
		tree.insert("北京天和晟玉泉路速8酒店");
		tree.insert("北京尊泰酒店");
		tree.insert("北京瑞兆快捷酒店(西单店)");
		tree.insert("北京京岭酒店");
		tree.insert("北京德胜饭店");
		tree.insert("北京新月天宾馆（原北京顺天港宾馆）");
		tree.insert("北京永兴花园商务酒店");
		tree.insert("格林豪泰（北京西客站南广场店）");
		tree.insert("北京凯美佳商旅酒店(北京紫英阁宾馆)");
		tree.insert("北京市交通饭店");
		tree.insert("格林豪泰（北京传媒大学双桥店）");
		tree.insert("北京合家立四合院旅馆");
		tree.insert("北京山泉锦绣宾馆");
		tree.insert("北京秦都饭店");
		tree.insert("北京苹果快捷酒店");
		tree.insert("北京金三环宾馆");
		tree.insert("北京航美之家酒店");
		tree.insert("北京都市商务酒店");
		tree.insert("北京雍汇雅居四合院宾馆");
		tree.insert("北京什刹海福禄四合院宾馆");
		tree.insert("北京广运饭店");
		tree.insert("北京中联鑫华酒店(南站店)-原马家堡分店");
		tree.insert("北京红楼宴酒店");
		tree.insert("北京隆泽园大酒店");
		tree.insert("北京太行之星商务酒店");
		tree.insert("如家快捷酒店(北京长椿街店)");
		tree.insert("北京天衢航空商务酒店");
		tree.insert("北京易尚诺林大酒店");
		tree.insert("中国政法大学国际交流中心（北京昌平）");
		tree.insert("北京天海汇金宾馆");
		tree.insert("北京双阳宾馆（怀柔）");
		tree.insert("北京山东宾馆");
		tree.insert("北京五棵松饭店");
		tree.insert("北京学府宾馆");
		tree.insert("北京华扬新星酒店");
		tree.insert("北京空港新悦商务酒店");
		tree.insert("北京盛厦宾馆");
		tree.insert("北京华客山庄");
		tree.insert("北京南苑亿丰商务酒店");
		tree.insert("北京金桥国际公寓酒店（房山区）");
		tree.insert("北京陶然花园酒店");
		tree.insert("北京碧水云天宾馆");
		tree.insert("北京展航商务宾馆");
		tree.insert("北京凯协宾馆");
		tree.insert("北京凯悦莱温泉会议中心");
		tree.insert("北京汇海航宾馆");
		tree.insert("北京嘉洁酒店");
		tree.insert("富驿商旅酒店(北京西三旗店)");
		tree.insert("北京国门商务酒店");
		tree.insert("北京金都天弘宾馆");
		tree.insert("北京益泉花园酒店(原益泉休闲会所)");
		tree.insert("北京青云阁酒店");
		tree.insert("北京山水人间快捷酒店");
		tree.insert("北京万盛吉宾馆");
		tree.insert("北京华商酒店(三元桥店)");
		tree.insert("北京德奇宾馆");
		tree.insert("北京圈子国际青年旅舍");
		tree.insert("北京爱特家酒店公寓");
		tree.insert("北京市蓟明快捷酒店");
		tree.insert("北京欣得酒店（霄云路店）（原宝华宾馆）");
		tree.insert("北京紫金宫饭店");
		tree.insert("北京京铁大酒店(原北京京铁饭店)");
		tree.insert("如家快捷酒店（北京和平里国家林业局店）");
		tree.insert("北京盛鸿宾馆");
		tree.insert("北京富乐山酒店");
		tree.insert("北京中监宾馆");
		tree.insert("北京竹园宾馆");
		tree.insert("阳光短租服务式公寓（北京中关村店）");
		tree.insert("阳光短租服务式公寓（北京苏州桥店）");
		tree.insert("北京凯旋星快捷酒店");
		tree.insert("北京资源燕园宾馆");
		tree.insert("北京蝴蝶泉宾馆");
		tree.insert("北京金紫银商务酒店");
		tree.insert("北京喜马拉雅宾馆");
		tree.insert("北京悦都大酒店");
		tree.insert("北京愉程轩酒店");
		tree.insert("北京牡丹宾馆");
		tree.insert("北京吉龙宾馆(原祈年饭店)");
		tree.insert("北京欣得酒店公寓(上地店)");
		tree.insert("北京嘉鑫快捷酒店(欢乐谷店)");
		tree.insert("北京维海纳酒店(西三旗海诚店)");
		tree.insert("北京便宜居连锁酒店(西直门店)");
		tree.insert("北京博大永康商务酒店(亦庄经济开发区)");
		tree.insert("北京国安宾馆");
		tree.insert("北京海南大厦");
		tree.insert("北京护国寺宾馆");
		tree.insert("北京华通新饭店");
		tree.insert("北京华康宾馆");
		tree.insert("北京豪威大厦");
		tree.insert("北京上园饭店");
		tree.insert("北京欣燕都连锁酒店(建国门店)");
		tree.insert("北京国展宾馆");
		tree.insert("北京爱华宾馆");
		tree.insert("北京欣燕都连锁酒店(原新街口饭店)");
		tree.insert("北京紫龙宾馆");
		tree.insert("北京时代假日商务酒店");
		tree.insert("北京王府井大万酒店");
		tree.insert("北京中煤宾馆");
		tree.insert("北京东方和平宾馆");
		tree.insert("好家得快捷连锁酒店(北京天缘惠达店)");
		tree.insert("北京瑞鑫宾馆");
		tree.insert("北京博泰酒店（原都市新明基宾馆）");
		tree.insert("北京呼家楼力行宾馆");
		tree.insert("北京阳光饭店(原北京京川阳光饭店)");
		tree.insert("北京航旅大酒店");
		tree.insert("北京西华智德宾馆(原智德宾馆)");
		tree.insert("北京大北宾馆");
		tree.insert("北京城市青年酒店");
		tree.insert("北京桂京宾馆");
		tree.insert("北京西翠之旅广安门店(广安门铁路宾馆)");
		tree.insert("北京西翠之旅连锁宾馆白纸坊店(天健宾馆)");
		tree.insert("北京福星假日宾馆(原金梦家苑宾馆)");
		tree.insert("北京圣天使酒店");
		tree.insert("速8酒店北京东四店");
		tree.insert("北京唐府酒店(张自忠路店)");
		tree.insert("如家快捷酒店（北京农展馆店）");
		tree.insert("锦江之星（北京北太平庄店）");
		tree.insert("锦江之星（北京玉泉路店）");
		tree.insert("如家快捷酒店（北京燕莎使馆区店）");
		tree.insert("北京宜兰宾馆");
		tree.insert("如家快捷酒店（北京方庄店）");
		tree.insert("北京速8金宝街店（原北京华福新龙宾馆）");
		tree.insert("北京京海饭店");
		tree.insert("速8酒店北京国贸店");
		tree.insert("北京森林大地酒店");
		tree.insert("北京亦庄天宝速8酒店（大兴-亦庄经济开发区）");
		tree.insert("如家快捷酒店(北京玉泉路店)");
		tree.insert("锦江之星（北京长椿街店）");
		tree.insert("锦江之星（北京安贞里店）");
		tree.insert("如家快捷酒店（北京青塔店）");
		tree.insert("北京大唐科苑宾馆");
		tree.insert("北京上地智选假日酒店");
		tree.insert("北京门头沟百花速8酒店（门头沟-县城）");
		tree.insert("北京方庄银马速8酒店");
		tree.insert("莫泰168（北京安贞桥店）");
		tree.insert("北京齐鲁饭店");
		tree.insert("北京万里路青年酒店(东四九条店)");
		tree.insert("北京鸿润商务酒店");
		tree.insert("161连锁酒店(北京京州店)");
		tree.insert("北京学院路速8酒店");
		tree.insert("北京国都鼎盛大酒店");
		tree.insert("北京且亭山水酒店(原北京富豪花园度假酒店)");
		tree.insert("锦江之星（北京天坛公园店）");
		tree.insert("北京青海循化商务酒店(原北京新视线便捷酒店)");
		tree.insert("北京西华饭店(月坛分店)");
		tree.insert("桔子酒店（北京五棵松店）");
		tree.insert("如家快捷酒店（北京六里桥店）");
		tree.insert("北京市前门京顺宾馆");
		tree.insert("汉庭商务酒店（北京西单店）");
		tree.insert("莫泰168（北京南站店）-原北京右安门店");
		tree.insert("北京瑞嘉宾馆");
		tree.insert("北京圣豪酒店");
		tree.insert("金泰之家(北京劲松店)");
		tree.insert("北京乾隆宾馆");
		tree.insert("北京神舟商旅酒店（奥运村店）");
		tree.insert("北京罗马阳光酒店");
		tree.insert("北京永正商务酒店(苏州街店)");
		tree.insert("锦江之星（北京刘家窑店）");
		tree.insert("汉庭酒店(北京中关村南店)");
		tree.insert("雅悦酒店(北京西直门店)");
		tree.insert("桔子酒店（北京亚运村店）");
		tree.insert("中青旅山水时尚酒店(北京芍药居店)");
		tree.insert("北京冠军苑宾馆（怀柔）");
		tree.insert("北京应物会议中心");
		tree.insert("山水时尚酒店(北京西客站店)");
		tree.insert("北京神舟商旅酒店(航天城店)");
		tree.insert("桔子酒店（北京西直门店）");
		tree.insert("北京京新旅酒店");
		tree.insert("汉庭快捷酒店（北京长虹桥店）");
		tree.insert("北京恋都商旅酒店(百万庄店)");
		tree.insert("北京七台河宾馆");
		tree.insert("北京农军宾馆");
		tree.insert("北京永定门饭店");
		tree.insert("北京中宇饭店");
		tree.insert("北京建通鸿雁宾馆");
		tree.insert("锦江之星（北京天桥店）");
		tree.insert("北京佳号宾馆");
		tree.insert("北京恒兴商务酒店");
		tree.insert("北京源泉宾馆(西单店)");
		tree.insert("北京春豪宾馆前门店(原公交招待所)");
		tree.insert("北京鑫园客栈");
		tree.insert("北京运来宾馆");
		tree.insert("第五季商旅酒店(中关村店)");
		tree.insert("北京密云云佛山旅游度假村(云佛山-密云水库)");
		tree.insert("金泰之家(北京西直门)");
		tree.insert("北京前门京顺宾馆（第一分店）");
		tree.insert("北京雁门汇丰宾馆");
		tree.insert("维也纳酒店（北京花园店）");
		tree.insert("北京金昌佳园宾馆");
		tree.insert("万通驿馆(北京中关村店)(原北京苏州街店)");
		tree.insert("北京奥香阁宾馆");
		tree.insert("便宜居连锁酒店(北京人民大学店)");
		tree.insert("北京辰茂鸿翔酒店(原北京鸿翔大厦)");
		tree.insert("北京金泰之家盛达园饭店");
		tree.insert("北京玉都饭店");
		tree.insert("北京二十一世纪饭店");
		tree.insert("北京永安宾馆");
		tree.insert("北京市中国科技会堂宾馆");
		tree.insert("北京重庆饭店");
		tree.insert("北京裕龙大酒店");
		tree.insert("北京华美伦酒店");
		tree.insert("北京梅地亚中心");
		tree.insert("北京阳光温特莱酒店");
		tree.insert("北京王府井远洋酒店");
		tree.insert("北京华泰饭店");
		tree.insert("北京华都饭店");
		tree.insert("北京亚洲大酒店(锦江集团)");
		tree.insert("北京金谷琪珑大酒店(原北京天堂阳光大酒店)");
		tree.insert("北京天伦松鹤大饭店");
		tree.insert("北京崇文门饭店");
		tree.insert("北京天瑞酒店(原天瑞泓胤酒店)");
		tree.insert("北京贵州大厦");
		tree.insert("北京青蓝大厦酒店");
		tree.insert("首旅集团北京展览馆宾馆");
		tree.insert("北京和平里大酒店");
		tree.insert("北京新兴宾馆");
		tree.insert("北京国谊宾馆");
		tree.insert("北京富豪宾馆");
		tree.insert("北京大地花园酒店");
		tree.insert("北京东长安饭店");
		tree.insert("北京凯迪克格兰云天大酒店");
		tree.insert("北京银都酒店");
		tree.insert("北京西藏大厦");
		tree.insert("北京湖北大厦");
		tree.insert("北京中国职工之家饭店");
		tree.insert("北京宣武门商务酒店(原越秀大饭店)");
		tree.insert("北京国宏宾馆");
		tree.insert("北京凤展酒店(原金太阳酒店)");
		tree.insert("北京通联太和大酒店");
		tree.insert("北京东方宫霄酒店");
		tree.insert("北京平安府宾馆");
		tree.insert("北京商务会馆");
		tree.insert("北京南航明珠商务酒店");
		tree.insert("北京玉华宫宾馆");
		tree.insert("北京旅居华侨饭店");
		tree.insert("北京假日花园精品酒店");
		tree.insert("玉渊潭集团北京瑞成大酒店");
		tree.insert("北京弘利苑大厦");
		tree.insert("北京天坛饭店");
		tree.insert("北京建银饭店");
		tree.insert("北京广安宾馆");
		tree.insert("北京紫玉饭店");
		tree.insert("北京京民大厦");
		tree.insert("北京圆山大酒店");
		tree.insert("北京西郊宾馆");
		tree.insert("北京亚运村宾馆");
		tree.insert("北京国统宾馆");
		tree.insert("北京首都机场宾馆");
		tree.insert("北京市政协会议中心");
		tree.insert("北京深能万寿商务酒店（原深能万寿宫酒店）");
		tree.insert("北京北邮科技酒店");
		tree.insert("北京嘉苑饭店");
		tree.insert("北京佳龙阳光酒店(朝阳门店)");
		tree.insert("北京银丰商务酒店");
		tree.insert("北京东方饭店");
		tree.insert("北京金唐酒店");
		tree.insert("北京西翠之旅连锁宾馆西翠路店(西翠宾馆)");
		tree.insert("北京贵国酒店");
		tree.insert("北京玛依塔柯酒店");
		tree.insert("南京大饭店(北京)");
		tree.insert("北京红墙酒店");
		tree.insert("北京洋桥大厦");
		tree.insert("中协宾馆(北京)");
		tree.insert("北京东长安商务酒店");
		tree.insert("北京天天假日饭店");
		tree.insert("北京欣燕都连锁酒店（北京南站店）（原洋桥店）");
		tree.insert("北京顺义宾馆(顺义)");
		tree.insert("中安之家酒店连锁(北京中安宾馆)");
		tree.insert("北京翠明庄宾馆");
		tree.insert("北京中青记者之家饭店");
		tree.insert("锦江之星（北京马家堡店）");
		tree.insert("锦江之星（北京西客站店）");
		tree.insert("锦江之星（北京广渠门店）");
		tree.insert("北京贯通现代酒店(前门店)");
		tree.insert("速8酒店北京国展店");
		tree.insert("北京瑞尔威连锁饭店");
		tree.insert("北京梦溪宾馆");
		tree.insert("中国红十字会宾馆(北京)");
		tree.insert("北京国林宾馆");
		tree.insert("北京皇城工商宾馆(原工商宾馆)");
		tree.insert("北京王府井银地宾馆");
		tree.insert("北京京滨饭店");
		tree.insert("北京伊仕登商务酒店");
		tree.insert("速8酒店(北京中关村永正店)");
		tree.insert("北京松麓圣方假日饭店");
		tree.insert("北京龙轩宾馆");
		tree.insert("北京东晓新越酒店(平谷)");
		tree.insert("逸羽连锁酒店(北京太平桥店)");
		tree.insert("北京银洋酒店(首都机场新国展店)");
		tree.insert("北京中欣戴斯酒店");
		tree.insert("北京贯通现代酒店(国展店)");
		tree.insert("如家快捷酒店（北京新兴桥店）");
		tree.insert("如家快捷酒店（北京清华大学东门店）");
		tree.insert("北京中欧宾馆");
		tree.insert("北京春晖园温泉度假酒店（顺义-高丽营）");
		tree.insert("北京上康城酒店");
		tree.insert("北京瑞兆快捷酒店");
		tree.insert("北京广东大厦(原岭南饭店)");
		tree.insert("北京王府井新天地宾馆(原新天地宾馆)");
		tree.insert("北京培新宾馆");
		tree.insert("北京蟹岛绿色生态度假村");
		tree.insert("北京唐悦酒店");
		tree.insert("锦江之星（北京苏州桥店）");
		tree.insert("北京八闽宾馆");
		tree.insert("莫泰168（北京中关村店）");
		tree.insert("中安之家酒店连锁(北京东单宾馆)");
		tree.insert("北京博大万源公寓(大兴-亦庄经济开发区)");
		tree.insert("北京枫林时尚酒店(酒仙桥店)");
		tree.insert("北京昊天假日酒店(房山-良乡)");
		tree.insert("北京故宫角楼商务酒店");
		tree.insert("北京雅悦酒店红庙店");
		tree.insert("北京平谷渔阳酒店(平谷)");
		tree.insert("北京神农庄园大酒店（石景山-八大处风景区）");
		tree.insert("北京万年青宾馆");
		tree.insert("北京荣宝宾馆");
		tree.insert("北方朗悦酒店(北京甘家口店)");
		tree.insert("北京轻联富润饭店(前门店)");
		tree.insert("格林豪泰（北京雍和宫店）");
		tree.insert("北京都海宾馆（原北京都海长安招待所）");
		tree.insert("格林豪泰（北京光明桥店）");
		tree.insert("北京九州家园酒店");
		tree.insert("北京雍圣府酒店");
		tree.insert("北京侨园饭店");
		tree.insert("北京中邮苑宾馆");
		tree.insert("北京君安宾馆");
		tree.insert("北京首都机场宜必思酒店");
		tree.insert("北京鸿瑞阁酒店");
		tree.insert("北京星海琪假日酒店");
		tree.insert("北京呼家楼宾馆");
		tree.insert("汉庭酒店(北京欢乐谷店)");
		tree.insert("北京安徽大厦");
		tree.insert("北京荣丰假日酒店（王府井店）");
		tree.insert("北京荣丰之星酒店");
		tree.insert("北京星河楼宾馆");
		tree.insert("北京义竺家园宾馆");
		tree.insert("北京唐廊.中堂四合院精品酒店（原中堂客栈）");
		tree.insert("北京裕龙花园大酒店(怀柔分店)");
		tree.insert("北京裕龙花园大酒店");
		tree.insert("北京北斗星酒店");
		tree.insert("北京鹰城商务酒店");
		tree.insert("北京国宾大厦");
		tree.insert("北京江苏大厦");
		tree.insert("北京惠侨饭店");
		tree.insert("北京荣丰之旅宾馆(万寿路店)");
		tree.insert("北京北外宾馆");
		tree.insert("北京珀丽酒店");
		tree.insert("北京昆仑饭店");
		tree.insert("锦江集团北京东方花园饭店");
		tree.insert("北京京伦饭店");
		tree.insert("北京凤凰苏源大厦(原苏源锦江大厦)");
		tree.insert("首旅集团北京西苑饭店");
		tree.insert("北京友谊宾馆");
		tree.insert("北京新侨诺富特饭店");
		tree.insert("北京国际饭店");
		tree.insert("北京建国饭店");
		tree.insert("北京亮马河大厦");
		tree.insert("北京兆龙饭店");
		tree.insert("北京诺富特和平宾馆");
		tree.insert("北京王府井大饭店");
		tree.insert("北京渔阳饭店");
		tree.insert("北京国贸饭店");
		tree.insert("北京新大都饭店");
		tree.insert("北京五洲大酒店");
		tree.insert("北京贝尔特酒店");
		tree.insert("北京燕山大酒店");
		tree.insert("北京丽都维景酒店(原北京丽都假日酒店)");
		tree.insert("首旅集团北京好苑建国酒店");
		tree.insert("北京皇家大饭店");
		tree.insert("北京首都大酒店");
		tree.insert("北京保利大厦酒店");
		tree.insert("北京民族饭店");
		tree.insert("北京宝辰饭店");
		tree.insert("北京国都大饭店");
		tree.insert("北京深圳大厦");
		tree.insert("北京赛特饭店");
		tree.insert("北京中苑宾馆");
		tree.insert("北京国际艺苑皇冠假日酒店");
		tree.insert("北京天伦王朝酒店");
		tree.insert("北京中旅大厦");
		tree.insert("北京金龙建国温泉酒店(原金龙温泉酒店式公寓)");
		tree.insert("北京东方文化酒店");
		tree.insert("北京国贸世纪公寓");
		tree.insert("北京广州大厦");
		tree.insert("北京河南大厦");
		tree.insert("北京港中旅维景国际大酒店");
		tree.insert("北京长峰假日酒店");
		tree.insert("北京金都假日饭店");
		tree.insert("北京东交民巷饭店");
		tree.insert("北京德宝饭店");
		tree.insert("北京中裕世纪大酒店");
		tree.insert("北京广西大厦");
		tree.insert("北京丽亭华苑酒店");
		tree.insert("北京艾维克酒店");
		tree.insert("北京凯富酒店");
		tree.insert("北京中民大厦（原中民建国商务酒店）");
		tree.insert("北京金泰海博大酒店");
		tree.insert("北京建设大厦");
		tree.insert("北京船舶重工酒店");
		tree.insert("北京永兴花园饭店");
		tree.insert("北京西单美爵酒店");
		tree.insert("北京福建大厦");
		tree.insert("北京北方佳苑饭店");
		tree.insert("北京五环大酒店（原世纪远洋宾馆）");
		tree.insert("北京华融大厦");
		tree.insert("北京奥加美术馆酒店(原北京奥加饭店)");
		tree.insert("北京东航大酒店(原北京东航商务首都机场酒店)");
		tree.insert("北京北辰汇园酒店公寓");
		tree.insert("北京山水宾馆");
		tree.insert("北京外国专家大厦");
		tree.insert("北京西西友谊酒店");
		tree.insert("北京丽苑公寓");
		tree.insert("北京中土大厦");
		tree.insert("北京锦江富园大酒店(大兴-亦庄经济开发区)");
		tree.insert("北京安贞大厦");
		tree.insert("北京香青园商务会馆");
		tree.insert("北京中成天坛假日酒店");
		tree.insert("北京郡王府饭店");
		tree.insert("北京华威国际公寓（套房式酒店）");
		tree.insert("北京国玉大酒店");
		tree.insert("北京金码大酒店");
		tree.insert("北京中环假日酒店");
		tree.insert("北京亚太花园酒店(通州)");
		tree.insert("北京元辰鑫国际酒店(原国际会展酒店)");
		tree.insert("北京新闻大厦(酒店)");
		tree.insert("北京华威商务酒店");
		tree.insert("北京金桥国际公寓");
		tree.insert("北京京海大厦");
		tree.insert("北京紫玉渡假酒店");
		tree.insert("北京泰悦豪庭酒店");
		tree.insert("北京宁夏大厦");
		tree.insert("北京国际竹藤大厦");
		tree.insert("北京康铭大厦");
		tree.insert("北京新疆大厦嘉宾楼");
		tree.insert("北京香榭舍酒店公寓");
		tree.insert("北京万柳酒店式公寓");
		tree.insert("北京雅诗阁服务公寓");
		tree.insert("北京艾丽华丽舍服务公寓");
		tree.insert("北京皇苑大酒店");
		tree.insert("北京明苑酒店");
		tree.insert("北京雍景台酒店");
		tree.insert("北京万程华府国际酒店");
		tree.insert("北京亚丁湾商务酒店");
		tree.insert("北京星明湖度假村(大兴-庞各庄)");
		tree.insert("北京华欣绿都国际酒店(原平谷国际酒店)");
		tree.insert("北京星城亮马公寓");
		tree.insert("北京金梧桐宾馆");
		tree.insert("北京中工大厦");
		tree.insert("北京富顿中心公寓");
		tree.insert("富驿时尚酒店(北京中关村店)");
		tree.insert("北京国电接待中心");
		tree.insert("北京丽亭酒店");
		tree.insert("北京文华大厦");
		tree.insert("北京昆泰嘉禾酒店(原昆泰大酒店)(昌平-回龙观)");
		tree.insert("北京顺义区财会培训中心(顺义商务/会议)");
		tree.insert("北京城宝饭店");
		tree.insert("北京月亮河温泉度假酒店");
		tree.insert("北京南粤苑宾馆");
		tree.insert("北京国润商务酒店（丰台）");
		tree.insert("北京龙山渡假村(昌平-十三陵水库)");
		tree.insert("北京美豪富邦国际酒店");
		tree.insert("北京中基宾馆");
		tree.insert("北京乾元国际商务酒店");
		tree.insert("北京木棉花酒店");
		tree.insert("北京乔波国际会议中心(顺义-马坡)");
		tree.insert("北京萨尔利兹国际酒店");
		tree.insert("北京康福瑞假日酒店(北京学院南路店)");
		tree.insert("北京大兴龙凤轩大酒店（大兴-西红门）");
		tree.insert("速8酒店北京龙城店");
		tree.insert("山水时尚酒店(北京航天桥店)");
		tree.insert("北京天佑大厦");
		tree.insert("北京龙脉温泉度假村(昌平-小汤山)");
		tree.insert("北京新悦宏国际酒店");
		tree.insert("北京世纪兴融商务酒店");
		tree.insert("如家快捷酒店（北京中关村店）");
		tree.insert("北京金龙潭大饭店");
		tree.insert("北京大兴宾馆(大兴-黄村卫星城)");
		tree.insert("北京加码主题酒店(大兴-黄村卫星城)");
		tree.insert("北京江西大酒店");
		tree.insert("北京龙鼎华鼎云酒店");
		tree.insert("中国华电集团高级培训中心(北京密云-密云水库)");
		tree.insert("北京龙泉宾馆(门头沟-西山风景区)");
		tree.insert("北京北纬四十度大酒店");
		tree.insert("北京太姥山国际商务酒店");
		tree.insert("北京厦门大厦");
		tree.insert("北京德宝温泉会议中心(房山)");
		tree.insert("北京金宝花园酒店");
		tree.insert("北京华清温泉宾馆");
		tree.insert("北京外研社国际会议中心(大兴-芦城工业开发区)");
		tree.insert("北京唐山大厦");
		tree.insert("北京王府井东华饭店");
		tree.insert("北京神舟国际酒店");
		tree.insert("北京和平里宾馆");
		tree.insert("北京运河源酒店(通州-运河西大街)");
		tree.insert("汉庭快捷酒店（北京北海公园店）");
		tree.insert("北京亮马河酒店式服务公寓");
		tree.insert("北京亚奥国际酒店(原劳动大厦)");
		tree.insert("北京香江戴斯酒店");
		tree.insert("北京汇源宫酒店(顺义燕京桥)");
		tree.insert("北京天湖会议中心(房山)");
		tree.insert("北京文惠宝宾馆（昌平区商业中心）");
		tree.insert("北京翰洲商务酒店");
		tree.insert("汉庭商务酒店（北京宣武门店）");
		tree.insert("北京中都泰和酒店（原北京爱华大厦）");
		tree.insert("北京焦庄国际酒店");
		tree.insert("富驿时尚酒店(北京燕莎店)");
		tree.insert("北京天坛万程酒店");
		tree.insert("北京星程星月亮马酒店");
		tree.insert("富驿时尚酒店(北京首都国际机场店)");
		tree.insert("北京新族酒店");
		tree.insert("北京远望楼宾馆");
		tree.insert("北京万禧公寓");
		tree.insert("北京城市庭院客栈");
		tree.insert("北京翔达国际商务酒店");
		tree.insert("北京明都饭店");
		tree.insert("北京人济万怡酒店");
		tree.insert("北京锡华商务酒店");
		tree.insert("北京百环饭店");
		tree.insert("北京工大建国饭店");
		tree.insert("北京凯创金街商务酒店");
		tree.insert("北京晶都国际酒店");
		tree.insert("北京格纳斯主题酒店（原燕莎主题酒店）");
		tree.insert("北京大雨澳斯特酒店");
		tree.insert("北京张家口饭店");
		tree.insert("北京凤凰台饭店");
		tree.insert("北京阳光老宅院酒店");
		tree.insert("北京好特热温泉酒店");
		tree.insert("北京碧云天国际酒店");
		tree.insert("北京鄂尔多斯艾力酒店");
		tree.insert("北京丰荣君华酒店");
		tree.insert("北京广电国际酒店");
		tree.insert("北京共济国际酒店");
		tree.insert("北京望京智选假日酒店");
		tree.insert("北京法官之家酒店");
		tree.insert("北京中奥华美达大酒店");
		tree.insert("北京诺富特三元酒店");
		tree.insert("北京银枫戴斯商务酒店");
		tree.insert("北京上东盛贸饭店");
		tree.insert("北京东煌酒店(原北京东煌凯丽酒店)");
		tree.insert("北京新云南皇冠假日酒店");
		tree.insert("北京喜家红宾馆");
		tree.insert("北京上行格调酒店");
		tree.insert("北京兴基铂尔曼饭店(大兴-亦庄经济开发区)");
		tree.insert("北京万柳派顿酒店");
		tree.insert("北京首农香山会议中心(原三元香山商务会馆)");
		tree.insert("北京国二招宾馆");
		tree.insert("北京福缘花园商务酒店");
		tree.insert("北京白鹭园培训中心");
		tree.insert("北京中冀斯巴鲁宾馆");
		tree.insert("北京汤山假日会议中心");
		tree.insert("北京春秋园四合院宾馆(春园)");
		tree.insert("北京华坤庄园酒店");
		tree.insert("北京唐韵山庄");
		tree.insert("北京婧园雅筑宾馆");
		tree.insert("北京秦唐府客栈七号院");
		tree.insert("北京邮电会议中心");
		tree.insert("北京金台饭店");
		tree.insert("北京紫金丽亭酒店");
		tree.insert("北京月桂树酒店");
		tree.insert("北京深航精品酒店");
		tree.insert("北京中江商旅酒店");
		tree.insert("北京景明园宾馆");
		tree.insert("北京四合院宾馆");
		tree.insert("北京古巷贰拾号商务会所");
		tree.insert("北京中意鹏奥酒店");
		tree.insert("北京复地国际公寓酒店");
		tree.insert("北京地大国际会议中心");
		tree.insert("北京人济建国酒店");
		tree.insert("北京吉庆堂四合院宾馆");
		tree.insert("北京辉煌国际大酒店");
		tree.insert("北京院子酒店");
		tree.insert("一渡假日酒店（北京房山）");
		tree.insert("北京昆明大厦");
		tree.insert("北京大郊亭国际商务酒店");
		tree.insert("北京瑞德酒店");
		tree.insert("北京远洋国际酒店（原北京金色假日国际酒店）");
		tree.insert("北京兴海大厦");
		tree.insert("北京海淀花园饭店");
		tree.insert("北京方恒假日酒店");
		tree.insert("北京武青会议中心");
		tree.insert("北京静之湖度假酒店");
		tree.insert("北京明日五洲酒店");
		tree.insert("北京河南商务酒店");
		tree.insert("北京润泽嘉业大酒店");
		tree.insert("北京中关村公馆(北京凯菱国际酒店)");
		tree.insert("北京明豪戴斯酒店");
		tree.insert("北京千里之家商务酒店");
		tree.insert("北京三和概念酒店");
		tree.insert("北京和悦天成商务酒店");
		tree.insert("北京迈克之家精品酒店");
		tree.insert("北京徽都大酒店");
		tree.insert("北京月亮河庭院酒店(密云-密云水库)(原峡谷洲际)");
		tree.insert("北京花水湾磁化温泉度假村");
		tree.insert("北京金池蟒山会议中心(昌平-十三陵水库)");
		tree.insert("北京鲁弘宾馆");
		tree.insert("北京富尔顿酒店公寓");
		tree.insert("北京京燕饭店(石景山)");
		tree.insert("北京海润艾丽华酒店及服务公寓");
		tree.insert("北京万商花园酒店");
		tree.insert("北京大成路九号");
		tree.insert("北京融金中财大酒店");
		tree.insert("北京中奥凯富国际酒店");
		tree.insert("北京壹家壹快捷酒店");
		tree.insert("北京万寿宾馆");
		tree.insert("北京世纪华天大酒店");
		tree.insert("北京西国贸大酒店");
		tree.insert("北京贵都大酒店");
		tree.insert("北京海运国际酒店");
		tree.insert("宝林轩国际大酒店");
		tree.insert("北京日升昌酒店");
		tree.insert("北京龙健都商务酒店");
		tree.insert("北京光明饭店");
		tree.insert("北京德威国际酒店（原北京百名威国际酒店）");
		tree.insert("北京百纳烟台山商务酒店");
		tree.insert("北京金辉国际商务会议大酒店");
		tree.insert("北京毕节商务酒店");
		tree.insert("北京潇湘大厦");
		tree.insert("北京世纪黄山酒店");
		tree.insert("北京首都机场新国展和颐酒店(原空港金航酒店)");
		tree.insert("北京交通云蒙山庄");
		tree.insert("北京海淀雅乐轩酒店");
		tree.insert("北京中关村皇冠假日酒店");
		tree.insert("北京天方饭店");
		tree.insert("北京富来宫温泉山庄");
		tree.insert("北京浙江大厦");
		tree.insert("北京四川五粮液龙爪树宾馆");
		tree.insert("北京东方太阳城东方嘉宾国际酒店(原东方嘉宾国际)");
		tree.insert("北京奥北宝迪酒店");
		tree.insert("北京太阳花酒店");
		tree.insert("北京温都水城");
		tree.insert("北京德胜门华宇假日酒店");
		tree.insert("北京雷特思酒店");
		tree.insert("北京波菲特酒店");
		tree.insert("北京金航线国际商务酒店");
		tree.insert("北京工体A酒店");
		tree.insert("北京八达岭青龙泉休闲渡假村");
		tree.insert("北京净雅大酒店");
		tree.insert("北京京西酒店");
		tree.insert("北京香山饭店");
		tree.insert("北京文锦世博国际大酒店");
		tree.insert("北京广播大厦酒店");
		tree.insert("北京龙悦国际商务酒店");
		tree.insert("北京北发大酒店");
		tree.insert("北京万达嘉华酒店(原北京万达铂尔曼酒店)");
		tree.insert("北京毛林惠丰大酒店");
		tree.insert("北京贵宾楼饭店");*/
		 

		List<String> resuls = tree.query("北京");

		for (int i = 0; i < resuls.size(); i++) {
			String s = resuls.get(i);
			System.out.println(s);
		}

	}

}
