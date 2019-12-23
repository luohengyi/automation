package sample.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import sample.bean.Firewall;
import sample.mapper.TestMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created with IDEA
 * Author: LuoHengYi
 * Date: 2019/11/7
 * Time: 5:15 下午
 */
public class SqlSessionFactoryUtil {

    private static SqlSessionFactory sqlSessionFactory;

    //该sqlSession自动提交事物
    private static SqlSession sqlSession = null;

    public static SqlSession getSqlSession() {
        return sqlSession;
    }

    static {
        sqlSessionFactory = getSqlSessionFactoryBean();
        sqlSession = sqlSessionFactory.openSession(true);
    }

    public static void commit(){
        sqlSession.commit();
    }


    public static SqlSessionFactory getSqlSessionFactoryBean() {
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    public static <T> T getMapper(Class<T> var1) {

        return sqlSession.getMapper(var1);
    }

}
