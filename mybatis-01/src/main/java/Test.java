

import com.zt.been.UserInfo;
import com.zt.mapper.UserInfoMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class Test {
    public static void main(String[] args) throws IOException {
        DBHelper.init("mybatis-config.xml");
        SqlSession session = DBHelper.openSession();

        UserInfoMapper mapper = session.getMapper(UserInfoMapper.class);
        List<UserInfo> all = mapper.findAll();
        all.forEach(System.out::println);
//        sqlSession.close();

//        SqlSessionFactory sqlSessionFactory=getSqlSessionFactory("mybatis-config.xml");
//        SqlSession session = sqlSessionFactory.openSession();
//        List<UserInfo> list = session.selectList("com.zt.UserInfoMapper.findAll");
//        list.forEach(System.out::println);
//        session.close();

    }




    private static SqlSessionFactory getSqlSessionFactory(String configLocation) throws IOException {
        InputStream is = Resources.getResourceAsStream(configLocation);
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory build = builder.build(is);
        return build;
    }

}
