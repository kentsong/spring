package com.kentsong.spring.mybatis.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.Assert;

public class SqlSessionService implements InitializingBean {
	
	private SqlSessionTemplate sqlSession;
	
	private DataSourceTransactionManager transactionManager;
	
	public <T> T getMapper(Class<T> clazz) {
		return sqlSession.getMapper(clazz);
	}

	public SqlSessionTemplate getSqlSession() {
		return sqlSession;
	}

	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}

	public TransactionStatus getTransactionStatus() {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		
		return transactionManager.getTransaction(def);
	}
	
	public void commitTransaction(TransactionStatus status) {
		transactionManager.commit(status);
	}
	
	public void rollbackTransaction(TransactionStatus status) {
		transactionManager.rollback(status);
	}

	public DataSourceTransactionManager getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(DataSourceTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(sqlSession, "sqlSession bean is required.");
		Assert.notNull(transactionManager, "transactionManager bean is required.");
	}
	
}
