import org.apache.ibatis.binding.MapperProxy;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Proxy;
import java.util.List;

public class DBHelper {
    private static SqlSessionFactory sqlSessionFactory;
    /*SqlSessionFactory 一旦被创建就应该在应用的运行期间一直存在，没有任何理由丢弃它或重新创建另一个实例。 使用 SqlSessionFactory 的最佳实践是在应用运行期间不要重复创建多次，
    多次重建 SqlSessionFactory 被视为一种代码“坏习惯”。因此 SqlSessionFactory 的最佳作用域是应用作用域。*/

    public static final void init(String configLocation) throws IOException {
        InputStream is = Resources.getResourceAsStream(configLocation);
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        /*这个类可以被实例化、使用和丢弃，一旦创建了 SqlSessionFactory，就不再需要它了。
         因此 SqlSessionFactoryBuilder 实例的最佳作用域是方法作用域（也就是局部方法变量）。*/
        sqlSessionFactory = builder.build(is);

    }

    public static SqlSession openSession() {
        return sqlSessionFactory.openSession();
    }

    public static <T> T geyMapper(Class<T> clazz) {
        SqlSession session = openSession();
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, (proxy, method, args) -> {
            if (method.getDeclaringClass().isAssignableFrom(clazz)){
               String namespace=clazz.getName();
               String id=method.getName();
               Class<?> returnType=method.getReturnType();
               if (List.class.isAssignableFrom(returnType)){
                   return session.selectList(namespace+"."+id,args);
               }else {
                   return session.selectOne(namespace+"."+id,args);
               }
            }else {
               return null;
            }

        });
    }
}
