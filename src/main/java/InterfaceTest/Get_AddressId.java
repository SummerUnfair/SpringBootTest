package InterfaceTest;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

/**
 * Information sources：地址文本
 * Output source：文本
 * function:根据地址名文本获取ID
 * Created by FH on 20190910.
 *
 */
public class Get_AddressId {

    public static void main(String[] args) throws SQLException, IOException {

        //String path1="C:\\Users\\user\\Desktop\\地址全称.txt";
        //String path2="C:\\Users\\user\\Desktop\\R.txt";

//        String path1=args[0];
//        String path2=args[1];
//
        Query();
    }

    public  static void Query() throws SQLException, IOException {

        //设置打印路径
//        PrintStream out=new PrintStream(new File(path2));
//        System.setOut(out);
//
//        Reader in = new FileReader(new File(path1));
//        BufferedReader reader = new BufferedReader(in);
//
//        String s = "";

        AddressMassegeMapper mapper=null;

        String resource = "addr-mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession session = sqlSessionFactory.openSession();
        mapper = session.getMapper(AddressMassegeMapper.class);
        String result=mapper.getAddressId();
        System.out.println(result);


            System.out.println("SUCCESS!!!");

    }

}
