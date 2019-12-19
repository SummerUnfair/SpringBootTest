package worm;

public class SplitTest {


//    public static boolean isNumber(String string) {
//        if (string == null)
//            return false;
//        Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");
//        return pattern.matcher(string).matches();
//    }

    public static void main(String[] args) {



//        String AddressName="12弄12支弄12号";
////        int addressINDEX = AddressName.indexOf("弄");
////        String N = AddressName.substring(0, addressINDEX); //获取弄级地址
////        System.out.println(N);
////        int addressINDEX0=AddressName.indexOf("支弄");
////        String N1 = AddressName.substring(addressINDEX, addressINDEX0); //获取弄级地址
////        System.out.println(N1);
//        int addressINDEX = AddressName.indexOf("弄");
//
//        String a=AddressName.substring(addressINDEX);
//        System.out.println(a);


//        String str = "(  ( GLDWH = '14000' )  )";
//
//        Pattern pattern = Pattern.compile("([\'\"])(.*?)\\1");
//        Matcher matcher = pattern.matcher(str );
//        if (matcher.find()) {
//            String collegeId = matcher.group(2);
//
//            System.out.println(collegeId);//14000
//        }
//        String str = "12弄12支弄12号";
//
//        Pattern pattern = Pattern.compile(".+弄(.+支弄)*(.+号)*(.+)*");
//        Matcher matcher = pattern.matcher(str);
//        if (matcher.find()) {
//            String collegeId = matcher.group(1);
//
//            System.out.println(collegeId);//14000
//        }

//// TODO Auto-generated method stub
//        String str = "Hello,World! in Java.";
//        Pattern pattern = Pattern.compile("W(or)(ld!)");
//        Matcher matcher = pattern.matcher(str);
//        while(matcher.find()) {
//            System.out.println("Group 0:" + matcher.group(0));//得到第0组——整个匹配
//            System.out.println("Group 1:" + matcher.group(1));//得到第一组匹配——与(or)匹配的
//            System.out.println("Group 2:" + matcher.group(2));//得到第二组匹配——与(ld!)匹配的，组也就是子表达式
//            System.out.println("Start 0:" + matcher.start(0) + " End 0:" + matcher.end(0));//总匹配的索引
//            System.out.println("Start 1:" + matcher.start(1) + " End 1:" + matcher.end(1));//第一组匹配的索引
//            System.out.println("Start 2:" + matcher.start(2) + " End 2:" + matcher.end(2));//第二组匹配的索引
//            System.out.println(str.substring(matcher.start(0), matcher.end(1)));//从总匹配开始索引到第1组匹配的结束索引之间子串——Wor

//        String Building="123弄";
//        boolean isMatch= Pattern.matches("\\d+(支弄[0-9]+号|[弄号]|[甲乙丙丁]号|识别号)",Building);
//        System.out.println(isMatch);

//        String budings="1019弄42号后门";
//        int a =budings.indexOf("号");
//        String name=budings.substring(0,a+1);
//        System.out.println(budings+"\t"+name);


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
