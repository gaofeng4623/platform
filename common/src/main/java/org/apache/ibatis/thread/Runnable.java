package org.apache.ibatis.thread;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.session.Configuration;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.NestedIOException;
import org.springframework.core.io.Resource;

/**
 * 刷新使用进程
 * 
 * @author liubaoquan
 * 
 */
public class Runnable implements java.lang.Runnable {

	public static Logger log = Logger.getLogger(Runnable.class);

	private Configuration configuration;

	private static boolean refresh = false; // 是否执行刷新

	private static int delaySeconds = 10;// 延迟刷新秒数
	private static int sleepSeconds = 1;// 休眠时间
	
	private static boolean enabled = false;

	private Resource[] mapperLocations;
	
	private HashMap<String, Long> fileMapping = new HashMap<String, Long>();// 记录文件是否变化
	
	static {
		delaySeconds = PropertiesUtil.getInt("delaySeconds");
		sleepSeconds = PropertiesUtil.getInt("sleepSeconds");
		enabled = "true".equals(PropertiesUtil.getString("enabled"));
		
		delaySeconds = delaySeconds == 0 ? 50 : delaySeconds;
		sleepSeconds = sleepSeconds == 0 ? 1 : sleepSeconds;

		log.debug("[delaySeconds] " + delaySeconds);
		log.debug("[sleepSeconds] " + sleepSeconds);

	}

	public static boolean isRefresh() {
		return refresh;
	}

	public Runnable(Configuration configuration, Resource[] mapperLocations) {
		this.configuration = configuration;
		this.mapperLocations = mapperLocations;
	}

	@Override
	public void run() {

		log.debug("[configuration] " + configuration);
		
		if (enabled){
			start(this);
		}
	}

	public void start(final Runnable runnable) {

		new Thread(new java.lang.Runnable() {

			@Override
			public void run() {

				try {
					Thread.sleep(delaySeconds * 1000);
				} catch (InterruptedException e2) {
					e2.printStackTrace();
				}
				refresh = true;

				System.out.println("========= Enabled refresh mybatis mapper =========");

				while (true) {
					try {
						runnable.refresh(mapperLocations);
					} catch (Exception e1) {
						e1.printStackTrace();
					}

					try {
						Thread.sleep(sleepSeconds * 1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}
		}).start();
	}

	/**
	 * 执行刷新
	 * 
	 * @param filePath
	 *            刷新目录
	 * @param beforeTime
	 *            上次刷新时间
	 * @throws NestedIOException
	 *             解析异常
	 * @throws FileNotFoundException
	 *             文件未找到
	 */
	public void refresh(Resource[] mapperLocations) throws Exception {


		//判断是否有文件发生了变化
		if (this.isChanged()) {
			System.out.println("==============刷新mapper中的内容===============");
			// 重新加载
			for (Resource configLocation : mapperLocations) {
				try {
					XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configLocation.getInputStream(),
							configuration, configLocation.toString(), configuration.getSqlFragments());
					xmlMapperBuilder.parse1();
					log.info("mapper文件[" + configLocation.getFilename() + "]加载成功");
				} catch (IOException e) {
					log.error("mapper文件[" + configLocation.getFilename() + "]不存在或内容格式不对");
					continue;
				}
			}
		}
	}
	
	
	/**
	 * 判断文件是否发生了变化
	 * 
	 * @param resource
	 * @return
	 * @throws IOException
	 */
	private boolean isChanged() throws IOException {
		boolean flag = false;
		for (Resource resource : mapperLocations) {
			String resourceName = resource.getFilename();

			boolean addFlag = !fileMapping.containsKey(resourceName);// 此为新增标识

			// 修改文件:判断文件内容是否有变化
			Long compareFrame = fileMapping.get(resourceName);
			long lastFrame = resource.contentLength() + resource.lastModified();
			boolean modifyFlag = null != compareFrame && compareFrame.longValue() != lastFrame;// 此为修改标识

			// 新增或是修改时,存储文件
			if (addFlag || modifyFlag) {
				fileMapping.put(resourceName, Long.valueOf(lastFrame));// 文件内容帧值
				flag = true;
			}
		}
		return flag;
	}

}
