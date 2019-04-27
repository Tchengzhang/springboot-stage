package com.example.springbootstage.config;

import com.example.springbootstage.utils.DynamicDataSourceHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author Administrator
 *
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

  @Override
  protected Object determineCurrentLookupKey() {
    // 可以做一个简单的负载均衡策略
    String lookupKey = DynamicDataSourceHolder.getDataSource();
    System.out.println("------------lookupKey---------" + lookupKey);
    return lookupKey;
  }
}
