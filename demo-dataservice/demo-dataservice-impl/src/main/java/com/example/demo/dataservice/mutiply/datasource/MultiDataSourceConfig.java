package com.example.demo.dataservice.mutiply.datasource;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.ClassPathMapperScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.example.demo.dataservice.model.DataSourceBeanConfig;

/**
 *   多数据源配置
 * @author Administrator
 *
 */
@Component
public class MultiDataSourceConfig implements BeanDefinitionRegistryPostProcessor, EnvironmentAware {

	private Logger logger = LoggerFactory.getLogger(MultiDataSourceConfig.class);

	private Map<String, DataSourceBeanConfig> dataSourceBeanConfigMap = new ConcurrentHashMap<>();

	private BeanDefinitionRegistry registry;

	private static final String mapperLocation = "classpath:com/example/demo/dataservice/${schema}/mapper/*.xml";

	private static final String basePackages = "com.example.demo.dataservice.${schema}.dao";

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanfactory) throws BeansException {
		if (CollectionUtils.isEmpty(dataSourceBeanConfigMap)) {
			return;
		}

		logger.info("dataSource schema: {}", dataSourceBeanConfigMap.keySet());

		dataSourceBeanConfigMap.forEach((schema, config) -> {
			String dataSourceName = schema + "DataSource";
			String sqlSessionFactoryName = schema + "SqlSessionFactory";
			String dataSourceTransactionManagerName = schema + "TransactionManager";

			try {

				DataSource dataSource = createDataSource(config);
				beanfactory.registerSingleton(dataSourceName, dataSource);

				SqlSessionFactory sqlSessionFactory = createSqlSessionFactory(dataSource, schema);
				beanfactory.registerSingleton(sqlSessionFactoryName, sqlSessionFactory);

				DataSourceTransactionManager manager = new DataSourceTransactionManager();
				manager.setDataSource(dataSource);
				beanfactory.registerSingleton(dataSourceTransactionManagerName,manager);

				ClassPathMapperScanner scanner = new ClassPathMapperScanner(registry);
				scanner.setSqlSessionFactory(sqlSessionFactory);
				scanner.registerFilters();
				scanner.doScan(replacePlace(basePackages, schema));

			} catch (Exception e) {
				logger.error("registry multiDataSource failed: ", e);
			}

		});

	}

	private DataSource createDataSource(DataSourceBeanConfig config) throws Exception {
		Properties properties = new Properties();
		properties.setProperty("driverName", config.getDriverName());
		properties.setProperty("url", config.getUrl());
		properties.setProperty("username", config.getUsername());
		properties.setProperty("password", config.getPassword());
		return DruidDataSourceFactory.createDataSource(properties);
	}

	private SqlSessionFactory createSqlSessionFactory(DataSource dataSource, String schema) throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		bean.setMapperLocations(resourcePatternResolver.getResources(replacePlace(mapperLocation, schema)));
		return bean.getObject();
	}

	private String replacePlace(String location, String schema) {
		return location.replaceAll("\\$\\{schema\\}", schema);
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		this.registry = registry;
	}

	@Override
	public void setEnvironment(Environment env) {
		ConfigurableEnvironment envionment = (ConfigurableEnvironment) env;
		MutablePropertySources mutablePropSources = envionment.getPropertySources();
		for (PropertySource<?> propertySource : mutablePropSources) {
			if (propertySource instanceof EnumerablePropertySource) {
				String[] nameArr = ((EnumerablePropertySource<?>) propertySource).getPropertyNames();
				for (String name : nameArr) {
					if (name.startsWith("datasource.")) {
						int index = name.indexOf(".");
						String subName = name.substring(index + 1);
						String[] arr = subName.split("\\.");
						if (!dataSourceBeanConfigMap.containsKey(arr[0])) {
							dataSourceBeanConfigMap.put(arr[0], new DataSourceBeanConfig());
						}
						DataSourceBeanConfig config = dataSourceBeanConfigMap.get(arr[0]);
						setConfigProperty(config, arr[1], propertySource.getProperty(name));
					}
				}
			}

		}
	}

	private void setConfigProperty(DataSourceBeanConfig config, String property, Object value) {
		Class<?> clazz = config.getClass();
		String methodName = "set" + property.substring(0, 1).toUpperCase() + property.substring(1);
		try {
			Method method = clazz.getDeclaredMethod(methodName, String.class);
			method.invoke(config, new Object[] { value });
		} catch (Exception e) {

		}

	}

}
