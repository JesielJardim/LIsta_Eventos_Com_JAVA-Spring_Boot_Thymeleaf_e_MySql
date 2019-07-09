package com.eventoapp;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

//classe de configuração do banco de dados
@Configuration
public class DataConfiguration {

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/eventosapp");
		dataSource.setUsername("root");
		dataSource.setPassword("jesiel123");
		return dataSource;
	}
	
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter(); //cria conecçao com o hibernet
		adapter.setDatabase(Database.MYSQL); //informa qual database que estamos utilizando
		adapter.setShowSql(true); // informa em todas as etapas de inserir ou deleter aparece os passo a passo no console
		adapter.setGenerateDdl(true); //permite que o hibernet crie as tabelas automaticamente
		adapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect"); //informa o dialeto que vai ser utilizado
		adapter.setPrepareConnection(true); // para o hibernete se conectar
		return adapter;
	}
}
