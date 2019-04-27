/**
 * 版权所有(C)，上海***股份有限公司，2018，所有权利保留。
 * 
 * 项目名：	springboot
 * 文件名：	DataSourceConfig.java
 * 模块说明：	
 * 修改历史：
 * 2018年10月21日 - Administrator - 创建。
 */
package com.example.springbootstage.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据源配置
 * 
 * @author Administrator
 *
 */
@Configuration
public class DataSourceConfig {

  public final static String WRITE_DATASOURCE_KEY = "writeDruidDataSource";
  public final static String READ_DATASOURCE_KEY = "readDruidDataSource";

  @ConfigurationProperties(prefix = "spring.datasource.druid.read")
  @Bean(name = READ_DATASOURCE_KEY)
  public DataSource readDruidDataSource() {
    return new DruidDataSource();
  }

  @ConfigurationProperties(prefix = "spring.datasource.druid.write")
  @Bean(name = WRITE_DATASOURCE_KEY)
  @Primary
  public DataSource writeDruidDataSource() {
    return new DruidDataSource();
  }

  /**
   * 注入AbstractRoutingDataSource
   * 
   * @param readDruidDataSource
   * @param writeDruidDataSource
   * @return
   * @throws Exception
   */
  @Bean
  public AbstractRoutingDataSource routingDataSource(
      @Qualifier(READ_DATASOURCE_KEY) DataSource readDruidDataSource,
      @Qualifier(WRITE_DATASOURCE_KEY) DataSource writeDruidDataSource) throws Exception {
    DynamicDataSource dataSource = new DynamicDataSource();
    Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
    targetDataSources.put(WRITE_DATASOURCE_KEY, writeDruidDataSource);
    targetDataSources.put(READ_DATASOURCE_KEY, readDruidDataSource);
    dataSource.setTargetDataSources(targetDataSources);// 配置数据源
    dataSource.setDefaultTargetDataSource(writeDruidDataSource);// 默认为主库用于写数据
    return dataSource;
  }
}
