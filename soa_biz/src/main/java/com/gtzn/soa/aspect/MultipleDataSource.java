package com.gtzn.soa.aspect;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @info 提供多数据源的支持
 * @authors 高峰 (562373460@qq.com)
 * @date    2017-05-02
 * @version 1.0.0
 */
public class MultipleDataSource extends AbstractRoutingDataSource {

	private static final ThreadLocal<String> dataSourceKey = new InheritableThreadLocal<String>();

    public static void setDataSourceKey(String dataSource) {
        dataSourceKey.set(dataSource);
    }
    
	@Override
	protected Object determineCurrentLookupKey() {
		return dataSourceKey.get();	
	}
	
	
}
