package com.neo.mybatis;

import com.neo.enums.UserSexEnum;
import com.neo.mapper.UserMapper;
import com.neo.model.User;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.Test;

import javax.sql.DataSource;

public class MybatisStarter {

    @Test
    public void fromXML() {

    }

    @Test
    public void fromJavaCode() {
        DataSource dataSource = new UnpooledDataSource("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost:3306/test?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=true", "root", "root");
        // factory pattern:
        // org.apache.ibatis.session.Configuration.newExecutor(org.apache.ibatis.transaction.Transaction, org.apache.ibatis.session.ExecutorType)
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        // builder pattern:
        // org.apache.ibatis.mapping.Environment.Builder.build
        // org.apache.ibatis.session.SqlSessionFactoryBuilder.build(org.apache.ibatis.session.Configuration)
        Environment environment = new Environment("dev", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(UserMapper.class);
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        SqlSession session = sessionFactory.openSession();
        // proxy pattern:
        // org.apache.ibatis.binding.MapperProxyFactory.newInstance(org.apache.ibatis.binding.MapperProxy<T>)
        UserMapper mapper = session.getMapper(UserMapper.class);
        // delegate pattern:
        // org.apache.ibatis.session.defaults.DefaultSqlSession.update(java.lang.String, java.lang.Object)
        // interceptor pattern: plugins
        // org.apache.ibatis.plugin.InterceptorChain
        mapper.insert(new User("hz", "hz", UserSexEnum.MAN));
        session.commit();
        session.close();
    }
}
