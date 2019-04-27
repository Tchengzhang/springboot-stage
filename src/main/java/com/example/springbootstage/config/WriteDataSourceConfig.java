/**
 * 版权所有(C)，上海***股份有限公司，2018，所有权利保留。
 * 
 * 项目名：	springboot
 * 文件名：	WriteDataSourceConfig.java
 * 模块说明：	
 * 修改历史：
 * 2018年10月21日 - Administrator - 创建。
 */
package com.example.springbootstage.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * @author Administrator
 *
 */
@Configuration
@EnableJpaRepositories(value = "com.example.springbootstage.dao.*",
    entityManagerFactoryRef = "writeEntityManagerFactory",
    transactionManagerRef = "writeTransactionManager")
public class WriteDataSourceConfig {

  @Autowired
  JpaProperties jpaProperties;
  @Autowired
  @Qualifier("writeDruidDataSource")
  private DataSource writeDruidDataSource;

  /**
   * 我们通过LocalContainerEntityManagerFactoryBean来获取EntityManagerFactory实例
   * 
   * @return
   */
  @Bean(name = "writeEntityManagerFactoryBean")
  public LocalContainerEntityManagerFactoryBean writeEntityManagerFactoryBean(
      EntityManagerFactoryBuilder builder) {
    return builder.dataSource(writeDruidDataSource).properties(jpaProperties.getProperties())
        .packages("com.example.springbootstage.entity") // 设置实体类所在位置
        .persistenceUnit("writePersistenceUnit").build();
  }

  /**
   * EntityManagerFactory类似于Hibernate的SessionFactory，mybatis的SqlSessionFactory
   * 总之在执行操作之前我们总要获取一个EntityManager，这就类似于Hibernate的Session，mybatis的sqlSession。
   * 
   * @param builder
   * @return
   */
  @Bean(name = "writeEntityManagerFactory")
  @Primary
  public EntityManagerFactory writeEntityManagerFactory(EntityManagerFactoryBuilder builder) {
    return this.writeEntityManagerFactoryBean(builder).getObject();
  }

  /**
   * 配置事物管理器
   * 
   * @return
   */
  @Bean(name = "writeTransactionManager")
  @Primary
  public PlatformTransactionManager writeTransactionManager(EntityManagerFactoryBuilder builder) {
    return new JpaTransactionManager(writeEntityManagerFactory(builder));
  }
}
