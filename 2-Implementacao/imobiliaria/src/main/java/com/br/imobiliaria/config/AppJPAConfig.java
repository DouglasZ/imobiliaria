package com.br.imobiliaria.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.br.imobiliaria.model.Imovel;
import com.br.imobiliaria.repository.Imoveis;

@Configuration
@ComponentScan(basePackageClasses = { Imoveis.class })
@EnableTransactionManagement
public class AppJPAConfig {

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource("jdbc:postgresql://127.0.0.1:5432/imobiliaria");
		dataSource.setUsername("imobiliaria");
		dataSource.setPassword("imobiliaria");
		dataSource.setDriverClassName("org.postgresql.Driver");
		return dataSource;
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.POSTGRESQL);
		adapter.setShowSql(true);
		adapter.setGenerateDdl(false);
		adapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQLDialect");
		return adapter;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
			JpaVendorAdapter jpaVendorAdapter) {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(dataSource);
		entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter);
		entityManagerFactory.setPackagesToScan(Imovel.class.getPackage().getName());
		entityManagerFactory.getJpaPropertyMap().put("hibernate.hbm2ddl.auto", "update");
		return entityManagerFactory;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactoryBean.getObject());
		return transactionManager;
	}
	
}






