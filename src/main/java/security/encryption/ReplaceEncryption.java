/**
 * 
 */
package security.encryption;


/**
 * 置换加密方法
 * 加密效率
 * 加密文本长度：2558
    加密次数：10000
   finishtime:344毫秒
 * 
 * 
 * @author cango
 * 
 */
public class ReplaceEncryption {

    private int maxwidth;

    private byte[] keyArray() {

        byte[] bytes = new byte[maxwidth];

        for (int i = 0; i < maxwidth; i++) {
            bytes[i] = (byte) (maxwidth - i - 1);
        }

        return bytes;
    }

   /* private int findIndexForElement(int findEle, byte[] bytes, int fromindex, int endindex) {

        int middleindex = (fromindex + endindex) / 2;
        int middlevalue = bytes[middleindex];

        if (middlevalue > findEle) {
            endindex = middleindex;
            findIndexForElement(findEle, bytes, fromindex, endindex);
        } else if (middlevalue < findEle) {
            fromindex = middleindex;
            findIndexForElement(findEle, bytes, fromindex, endindex);
        } else {
            return middlevalue;
        }

        return -1;

    }*/
    
    
    private int findIndexForElement(int findEle, byte[] bytes){
        
        for (int i = 0; i < bytes.length; i++) {
            int value = bytes[i];
            if(value == findEle){
                return i;
            }
            
        }
        
        return -1;
    }
    
    

    /**
     * 加密明文 主方法
     * 
     * @param bytes
     */
    public byte[] encrypt(byte[] bytes) {

        byte[][] convert = convert(bytes);

        byte[][] exChangeColumn = encryptChangeColumn(convert);

        byte[] convert2 = convert(exChangeColumn);

        return convert2;

    }

    /**
     * 解密 主方法
     * 
     * @param bytes
     * @return
     */
    public byte[] decrypt(byte[] bytes) {
        byte[][] convert = convert(bytes);
        byte[][] decryptColumn = decryptColumn(convert);
        byte[] convert2 = convert(decryptColumn);
        return convert2;
    }

    /**
     * 一维数组转换成二维数组
     * 
     * @param bytes
     */
    private byte[][] convert(byte[] bytes) {

        double w = Math.sqrt(bytes.length);

        int width = (int) Math.ceil(w);

        maxwidth = width;
        
//        System.out.println("加密转换由一维转换成二维大小:"+maxwidth);

        byte[][] nbytes = new byte[width][width];

        int m = 0;

//        System.out.println("*******************一维转二维*****************************");
        
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                if (m < bytes.length) {
                    nbytes[i][j] = bytes[m++];
//                    System.out.print(nbytes[i][j]+"\t");
                }
            }
            
//            System.out.println();
            
        }

//        System.out.println("************************************************");
        
        return nbytes;
    }

    /**
     * 二维转换成一维
     * 
     * @param bytes
     * @return
     */
    private byte[] convert(byte[][] bytes) {
        int w = bytes.length * bytes.length;
        byte[] rbytes = new byte[w];
        int n = 0;
        for (int i = 0; i < bytes.length; i++) {
            for (int j = 0; j < bytes.length; j++) {
                rbytes[n++] = bytes[i][j];
            }
        }
        return rbytes;
    }

    /**
     * 进行加密转换
     * 
     * @param bytes
     * @return
     */
    private byte[][] encryptChangeColumn(byte[][] bytes) {
        byte[][] tempArray = createArray(bytes.length);

        byte[] keyArray = keyArray();
        
//        System.out.println("*******************加密交换次序*****************************");

        for (int i = 0; i < bytes.length; i++) {
            for (int j = 0; j < bytes.length; j++) {
                tempArray[i][j] = bytes[i][keyArray[j]];
//                System.out.print(tempArray[i][j]+"\t");
            }
            
//            System.out.println();
            
        }

//        System.out.println("************************************************");
        
        return tempArray;

    }

    /**
     * 进行解密转换
     * 
     * @param bytes
     * @return
     */
    private byte[][] decryptColumn(byte[][] bytes) {
        byte[][] tempArray = createArray(bytes.length);

        byte[] keyArray = keyArray();
        
//        System.out.println("*******************解密交换次序*****************************");

        for (int i = 0; i < bytes.length; i++) {
            for (int j = 0; j < bytes.length; j++) {
                int index = findIndexForElement(j, keyArray);
//                System.out.println("找第"+j+"列,对应的值:"+index);
                tempArray[i][j] = bytes[i][index];
                System.out.print(tempArray[i][j]+"\t");
            }
            
            System.out.println();
        }
        
//        System.out.println("************************************************");

        return tempArray;

    }

    private byte[][] createArray(int length) {
        return new byte[length][length];
    }

    public static void main(String[] args) {
/*
        ReplaceEncryption encryption = new ReplaceEncryption();

        byte[] array = "Hi,GoodMorning!".getBytes();
        
        System.out.println(Arrays.toString(array));

        byte[] encrypt = encryption.encrypt(array);
        
        System.out.println(new String(encrypt));

        System.out.println(Arrays.toString(encrypt));
        
        byte[] decrypt = encryption.decrypt(encrypt);
        
        System.out.println(Arrays.toString(decrypt));
        
        System.out.println(new String(decrypt));*/
        
        //效率测试
        
      /*  ReplaceEncryption encryption = new ReplaceEncryption();
        
        final StringBuffer buffer;
        
        String str;
        
        buffer = new StringBuffer();
        
        buffer.append("中共中央总书记、国家主席、中央军委主席、中央全面深化改革领导小组组长习近平1月23日下午主持召开中央全面深化改革领导小组第二次会议并发表重要讲话。他强调，2018年是贯彻党的十九大精神的开局之年，也是改革开放40周年，做好改革工作意义重大。要弘扬改革创新精神，推动思想再解放改革再深入工作再抓实，凝聚起全面深化改革的强大力量，在新起点上实现新突破。 李克强、张高丽、汪洋、王沪宁出席会议。 会议审议通过了《中央有关部门贯彻实施党的十九大〈报告〉重要改革举措分工方案》、《中央全面深化改革领导小组2018年工作要点》、《中央全面深化改革领导小组2017年工作总结报告》。 会议审议通过了《关于推进社会公益事业建设领域政府信息公开的意见》、《关于提高技术工人待遇的意见》、《关于建立城乡居民基本养老保险待遇确定和基础养老金正常调整机制的指导意见》、《积极牵头组织国际大科学计划和大科学工程方案》、《关于推进孔子学院改革发展的指导意见》、《关于建立“一带一路”争端解决机制和机构的意见》、《关于改革完善仿制药供应保障及使用政策的若干意见》、《科学数据管理办法》、《知识产权对外转让有关工作办法（试行）》、《地方党政领导干部安全生产责任制规定》。会议还审议了《浙江省“最多跑一次”改革调研报告》。 会议指出，推进社会公益事业建设领域政府信息公开，要准确把握社会公益事业建设规律和特点，加大信息公开力度，明确公开重点，细化公开内容，增强公开实效，提升社会公益事业透明度，推动社会公益资源配置更加公平公正，确保社会公益事业公益属性，维护社会公益事业公信力。 会议强调，提高技术工人待遇，要坚持全心全意依靠工人阶级的方针，发挥政府、企业、社会协同作用，完善技术工人培养、评价、使用、激励、保障等措施，实现技高者多得、多劳者多得，增强技术工人职业荣誉感、自豪感、获得感，激发技术工人积极性、主动性、创造性。 会议指出，建立城乡居民基本养老保险待遇确定和基础养老金正常调整机制，要按照兜底线、织密网、建机制的要求，建立激励约束有效、筹资权责清晰、保障水平适度的待遇确定和基础养老金正常调整机制，推动城乡居民基本养老保险待遇水平随经济发展逐步提高，确保参保居民共享经济社会发展成果。 会议强调，牵头组织国际大科学计划和大科学工程，要按照国家创新驱动发展战略要求，以全球视野谋划科技开放合作，聚焦国际科技界普遍关注、对人类社会发展和科技进步影响深远的研究领域，集聚国内外优秀科技力量，量力而行、分步推进，形成一批具有国际影响力的标志性科研成果，提升我国战略前沿领域创新能力和国际影响力。 会议指出，推进孔子学院改革发展，要围绕建设中国特色社会主义文化强国，服务中国特色大国外交，深化改革创新，完善体制机制，优化分布结构，加强力量建设，提高办学质量，使之成为中外人文交流的重要力量。 会议强调，建立“一带一路”争端解决机制和机构，要坚持共商共建共享原则，依托我国现有司法、仲裁和调解机构，吸收、整合国内外法律服务资源，建立诉讼、调解、仲裁有效衔接的多元化纠纷解决机制，依法妥善化解“一带一路”商贸和投资争端，平等保护中外当事人合法权益，营造稳定、公平、透明的法治化营商环境。 会议指出，改革完善仿制药供应保障及使用政策，要从群众需求出发，把临床必需、疗效确切、供应短缺、防治重大传染病和罕见病、处置突发公共卫生事件、儿童用药等作为重点，促进仿制药研发创新，提升质量疗效，提高药品供应保障能力，更好保障广大人民群众用药需求。 会议强调，加强和规范科学数据管理，要适应大数据发展形势，积极推进科学数据资源开发利用和开放共享，加强重要数据基础设施安全保护，依法确定数据安全等级和开放条件，建立数据共享和对外交流的安全审查机制，为政府决策、公共安全、国防建设、科学研究提供有力支撑。 会议指出，知识产权对外转让，要坚持总体国家安全观，依据现有法律法规和工作机制，对单位或者个人将其境内知识产权转让给外国企业、个人或者其他组织，严格审查范围、审查内容、审查机制，加强对涉及国家安全的知识产权对外转让行为的严格管理。 会议强调，实行地方党政领导干部安全生产责任制，要坚持党政同责、一岗双责、齐抓共管、失职追责，牢固树立发展决不能以牺牲安全为代价的红线意识，明确地方党政领导干部主要安全生产职责，综合运用巡查督查、考核考察、激励惩戒等措施，强化地方各级党政领导干部“促一方发展、保一方平安”的政治责任。 会议指出，党的十八大以来，浙江等地针对群众反映突出的办事难、投诉举报难等问题，从优化审批流程入手，推动实施“最多跑一次”改革，取得积极成效。各地区要结合实际，善于从基层和群众关心的问题上找出路、找办法，加大体制机制创新，以实际行动增强群众对改革的获得感。 会议强调，2018年是站在新的历史起点上接力探索、接续奋进的关键之年，要全面贯彻党的十九大精神，以习近平新时代中国特色社会主义思想为指导，统筹推进党的十八大以来部署的改革举措和党的十九大部署的改革任务，更加注重改革的系统性、整体性、协同性，着力补齐重大制度短板，着力抓好改革任务落实，着力巩固拓展改革成果，着力提升人民群众获得感，不断将改革推深做实，推进基础性关键领域改革取得实质性成果。 改革要突出重点，攻克难点，在破除各方面体制机制弊端、调整深层次利益格局上再拿下一些硬任务，重点推进国企国资、垄断行业、产权保护、财税金融、乡村振兴、社会保障、对外开放、生态文明等关键领域改革。要提高政治站位，勇于推进改革，敢于自我革命。要结合实际，实事求是，多从基层和群众关心的问题上找突破口，多推有地方特点的改革。要鼓励基层创新，继续发扬敢闯敢试、敢为人先的精神，推动形成更加浓厚、更有活力的改革创新氛围。 要拿出实实在在的举措克服形式主义问题。主要负责同志要带好头，把责任和工作抓实，越是难度大、见效慢的越要抓在手上，不弃微末，不舍寸功。要把调查研究突出出来，把存在的矛盾和困难摸清摸透，把工作做实做深做好。改革督察要扩点拓面、究根探底，既要听其言，也要观其行、查其果。对不作为的，要抓住典型，严肃问责。 中央全面深化改革领导小组成员出席，有关中央领导同志以及中央和国家机关有关部门负责同志列席会议。");
        
        str = buffer.toString();
        
        int encynum = 10000;
        
        System.out.println("加密文本长度："+str.length());
        
        System.out.println("加密次数："+encynum);
        
        long current = System.currentTimeMillis();
        
        
        for(int i = 0; i < encynum; i++){
            encryption.encrypt(str.getBytes());
        }
        
        long now = System.currentTimeMillis();
        
        System.out.println("finishtime:"+(now-current)+"毫秒");*/
        

    }

}
