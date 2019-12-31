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
        Query();
    }

    public  static void Query() throws SQLException, IOException {

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
