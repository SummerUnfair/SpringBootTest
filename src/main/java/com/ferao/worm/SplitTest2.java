package com.ferao.worm;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SplitTest2 {

    @Test
    public void filesTest() throws IOException {

        FileOutputStream li = new FileOutputStream("a.txt");
        li.write(97);
        li.close();

    }

    /**
     * 基本数值-->包装对象
     * 包装对象-->基本数值
     */
    @Test
    public void fileTest() {

        //路径分隔符 windows：分号 ； linux：冒号 ：
        String pathSeparator = File.pathSeparator;
        System.out.println(pathSeparator);

        //文件名称分隔符 windows:反斜杠 \  linux：正斜杠/
        String separator = File.separator;
        System.out.println(separator);

    }



//    public static boolean isNumber(String string) {
//        if (string == null)
//            return false;
//        Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");
//        return pattern.matcher(string).matches();
//    }

    @Test
    public void staticTest() throws ClassNotFoundException {
        Class.forName("A",true,this.getClass().getClassLoader());
    }

    public static void main(String[] args) {

//        String Building="12号12";
//        boolean isMatch= Pattern.matches(".*?(\\w+[-]?\\w+[临甲乙丙丁]?号).*?",Building);
//        System.out.println(isMatch);
//
//        Matcher matcher0=Pattern.compile(".*?(\\w+[-]?\\w+[临甲乙丙丁]?号).*?").matcher(Building);
//        if (matcher0.find()){
//            System.out.println(matcher0.group(1));
//        }

//      \w*[临甲乙丙丁]?[支]?弄$    .*?(\w+[-]?\w+[临甲乙丙丁]?号).*?    .*?(\w+[临甲乙丙丁]?[支]?弄).*?
//        String Building="12-23支弄一临号12";
//        boolean isMatch= Pattern.matches(".*?(\\w*[临甲乙丙丁]?[支]?弄).*?",Building);
//        System.out.println(isMatch);
//
//        Matcher matcher0= Pattern.compile(".*?(\\w+[-]?\\w+[临甲乙丙丁]?弄).*?").matcher(Building);
//        if (matcher0.find()){
//            System.out.println(matcher0.group(1));
//        }
//        Matcher matcher1=Pattern.compile(".*?(\\w+[-]?\\w+[临甲乙丙丁]?支弄).*?").matcher(Building);
//        if (matcher1.find()){
//            System.out.println(matcher1.group(1));
//        }

        //\w+([栋幢座]|单元|号楼)
//        String Building="弄A幢12";
//        boolean isMatch= Pattern.matches(".*?(\\w*[栋幢座]|单元|号楼)",Building);
//        System.out.println(isMatch);
//
//        Matcher matcher0= Pattern.compile(".*?(\\w+[-]?\\w*[栋幢座]|单元|号楼)").matcher(Building);
//        if (matcher0.find()){
//            System.out.println(matcher0.group(1));
//        }
/////////////////////////////
//.+?([一二三四五六七八九]+弄).*?
//        String Building="12弄22号";
//        boolean isMatch= Pattern.matches("(\\d[甲乙丙丁临]?号)",Building);
//        System.out.println(isMatch);
//
//        Matcher matcher0= Pattern.compile("(\\d[甲乙丙丁临]?号)").matcher(Building);
//        if (matcher0.find()){
//            System.out.println(matcher0.group(1));
//        }
        //////////////////
//        String addressName="123弄12号";
//
//        if (Pattern.matches("(\\w+弄)",addressName)){
//
//            Matcher matcher0= Pattern.compile("(123弄)").matcher(addressName);
//            if (matcher0.find()){
//                System.out.println(matcher0.group(1));
//            }
//        }

//            Set<String> set =new HashSet<>();
//            set.add("地址1");
//            set.add("地址2");
//            set.add("地址3");
//            set.add("地址4");
//            set.add("地址1");
//            set.add("地址2");
//            set.add("地址3");
//            set.add("地址4");
//
//            for (String str : set) {
//                System.out.println(str);
//            }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间




        /////////////////
//        String  A="797临27-2临";
//        String reBuilding=A.replace("临","");
//        System.out.println(reBuilding);
//



//        Matcher matcher1=Pattern.compile(".*?(\\w+[-]?\\w+[临甲乙丙丁]?支弄).*?").matcher(Building);
//        if (matcher1.find()){
//            System.out.println(matcher1.group(1));
//        }


//        String budings="1019弄42号后门";
//        int a =budings.indexOf("号");
//        String name=budings.substring(0,a+1);
//        System.out.println(budings+"\t"+name);

//        String S="2501弄301号301室";
//
//        if (Pattern.matches("\\d+弄\\d+号\\d+(室号|室)", S)) {  //2501弄301号301室
//            System.out.println(S + "\t");
//
//            int a = S.indexOf("号");
//            String reBuilding = S.substring(0, a + 1);
//            System.out.println(reBuilding);
//
//        }else
//                if (Pattern.matches("(\\d+弄)*\\d+号(后门|边门|[0-9]+室号)*", S)){  //1019弄42号后门 边门    9385号101室号  266号后门
//                System.out.println(S+"\t");
//                int a =S.indexOf("号");
//                String reBuilding=S.substring(0,a+1);
//
//                System.out.println(reBuilding);
//
//            }

//        String reBuilding=Building.replace("号","");
//       System.out.println(Building+"号");


        //Pattern p=Pattern.compile("\\w+");
        //p.pattern();//返回 \w+
        // 按指定模式在字符串查找
        //System.out.println(p.pattern());
        //Matcher m= p.matcher(Building);

        //第一个参数正则，第二个参数字符串，返回不布尔类型

        System.out.println("=====================");
//        String line = "This order was placed for QT3000! OK?";
//        // 创建 Pattern 对象
//        Pattern r = Pattern.compile("(\\D*)(\\d+)(.*)");
//        // 现在创建 matcher 对象
//        Matcher m = r.matcher(line);
//        if (m.find()) {
//            System.out.println("Found value: " + m.group(0) );
//            System.out.println("Found value: " + m.group(1) );
//            System.out.println("Found value: " + m.group(2) );
//            System.out.println("Found value: " + m.group(3) );
//        } else {
//            System.out.println("NO MATCH");
//        }
//       String str="wer1389980000ty1234564uiod234345675f";
//       String reg="\\d{5,}";
//       String arr="#";
//       String a=str.replaceAll(reg,arr);
//
//       System.out.println(a);

//        String str = "erkktyqqquizzzzzo";
//        String reg = "(.)\\1+";
//        String[] arr = str.split(reg);
//        System.out.println("切割得到的长度是:" + arr.length);
//        for(final String s : arr) {
//            System.out.println(s);
//        }


//        System.out.println(isNumber("580"));
//        System.out.println(isNumber("5234254125424584"));
//        System.out.println(isNumber("dfg15s4df5sd1fds"));

//        count++;
//        System.out.println(count);
//

//        int type=1;
//
//        if (type==1){
//
//            System.out.println("1");
//        }



//        String c="4269";
//        Object a=4269;
//        String b= a+"";
//        if (b.equals(c)){
//
//            System.out.println("相同");
//
//        }



//        String addressName="SaddrTermSaddrTerm";
//
//        int len =addressName.split("addrTerm").length;
//
//        System.out.println(len);
//        String []a=addressName.split("addrTerm");
//        for (String b: a){
//            System.out.println("b:"+b);
//        }

        //String addressName="\\|%\\|";
//        String name ="null";
//        String addressName="|@|"+name;
//        System.out.println(addressName);

//        int type=-1;
//        type=2;
//
//        if (type==1){
//            System.out.println("相同");
//        }else{
//            System.out.println("不相同");
//        }

    }
}
