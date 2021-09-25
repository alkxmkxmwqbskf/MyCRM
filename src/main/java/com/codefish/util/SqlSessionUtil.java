package com.codefish.util;

import java.io.IOException;
import java.io.InputStream;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SqlSessionUtil {

	private SqlSessionUtil(){}

	private static SqlSessionFactory sqlSessionFactory;

	static{

		String resource = "mybatis-config.xml";
		InputStream inputStream = null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

	}

	private static ThreadLocal<SqlSession> sessionThreadLocal = new ThreadLocal<>();

	public static SqlSession getSqlSession(){

		SqlSession session = sessionThreadLocal.get();

		if(session==null){

			session = sqlSessionFactory.openSession();
			sessionThreadLocal.set(session);
		}

		return session;

	}

	public static void myClose(SqlSession session){

		if(session!=null){
			session.close();
			sessionThreadLocal.remove();
		}
	}
}
