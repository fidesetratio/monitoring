package com.app.conf;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfiguration extends DefaultBatchConfigurer   {
	/*
	 * @Autowired(required=true) protected BatchConfiguration(BatchProperties
	 * properties, @Qualifier("monitoringdataSource") DataSource dataSource,
	 * TransactionManagerCustomizers transactionManagerCustomizers) {
	 * super(properties, dataSource, transactionManagerCustomizers); // TODO
	 * Auto-generated constructor stub }
	 */
	
	  @Qualifier("monitoringdataSource")
	  @Autowired
	  private DataSource monitoringDataSource;
	  
	  @Qualifier("monitoringtransactionManager")
	  @Autowired
	  private PlatformTransactionManager monitoringtransactionManager;
	
	@Bean
	 public TaskExecutor threadPoolTaskExecutor(){
	  ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	        executor.setMaxPoolSize(12);
	        executor.setCorePoolSize(8);
	        executor.setQueueCapacity(15);	   return executor;
	 }
	
	 @Override
	    public JobLauncher createJobLauncher() throws Exception {
	        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
	        jobLauncher.setJobRepository(getJobRepository());
	        jobLauncher.setTaskExecutor(threadPoolTaskExecutor());
	        jobLauncher.afterPropertiesSet();
	        return jobLauncher;
	    }
	 
	 @Override
	    public JobRepository createJobRepository() throws Exception {
	        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
	        factory.setDataSource(monitoringDataSource);
	        factory.setTransactionManager(monitoringtransactionManager);
	        factory.setIsolationLevelForCreate("ISOLATION_SERIALIZABLE");
	        factory.setTablePrefix("BATCH_");
	        factory.setMaxVarCharLength(1000);
	        return factory.getObject();
	    }
	 
	  @Override
	    public JobExplorer createJobExplorer() throws Exception {
	        JobExplorerFactoryBean jobExplorerFactoryBean = new JobExplorerFactoryBean();
	        jobExplorerFactoryBean.setDataSource(monitoringDataSource);
	        jobExplorerFactoryBean.afterPropertiesSet();
	        return jobExplorerFactoryBean.getObject();
	    }
	  
	  
	/*
	 * @Bean public JobRepository jobRepository(@Qualifier("monitoringdataSource")
	 * DataSource dataSource, @Qualifier("monitoringtransactionManager")
	 * DataSourceTransactionManager transactionManager) throws Exception {
	 * JobRepositoryFactoryBean jobRepositoryFactoryBean = new
	 * JobRepositoryFactoryBean();
	 * jobRepositoryFactoryBean.setDataSource(dataSource);
	 * jobRepositoryFactoryBean.setTransactionManager(transactionManager); return
	 * jobRepositoryFactoryBean.getObject(); }
	 * 
	 * @Bean(name="async") public SimpleJobLauncher jobLauncher(JobRepository
	 * jobRepository) { SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
	 * jobLauncher.setTaskExecutor(threadPoolTaskExecutor());
	 * jobLauncher.setJobRepository(jobRepository); return jobLauncher; }
	 */
 	
}
