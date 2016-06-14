package cn.ccsgroup.ccsframework.base.dao;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.sql.DataSource;

public class MultiDataSource implements DataSource{

	//当前线程对应的实际DataSource  
	private ThreadLocal<DataSource> currentDataSourceHolder = new ThreadLocal<DataSource>();   
	private static DataSource defaultTargetDataSource = null;
	//使用Key-Value映射的DataSource   
	private static Map<String , DataSource> mappedDataSources;   
	public ThreadLocal<DataSource> getCurrentDataSourceHolder() {
		return currentDataSourceHolder;
	}
	public void setCurrentDataSourceHolder(
			ThreadLocal<DataSource> currentDataSourceHolder) {
		this.currentDataSourceHolder = currentDataSourceHolder;
	}
	public DataSource getDefaultTargetDataSource() {
		return defaultTargetDataSource;
	}
	public void setDefaultTargetDataSource(DataSource targetDataSource) {
		defaultTargetDataSource = targetDataSource;
	}
	public MultiDataSource(){   
		mappedDataSources = new HashMap<String , DataSource>(); 
	} 
	
	public static boolean defaultDataSource(){
		return defaultTargetDataSource != null ;
	}
	
	public static DataSource getDefaultDataSource(){
		return defaultTargetDataSource ;
	}
	
	/**  
	 * 获取当前线程绑定的DataSource  :
	 * @return  
	 */  
	public DataSource getCurrentDataSource() {   
		return currentDataSourceHolder.get()==null? defaultTargetDataSource:currentDataSourceHolder.get();   
	}   

	public Map<String, DataSource> getMappedDataSources() { 
		return mappedDataSources;   
	}  

	public void setMappedDataSources(Map<String, DataSource> mappedDataSources) {   
		MultiDataSource.mappedDataSources = mappedDataSources;
	}   
	
	public static DataSource getDateSource(String name){
		return mappedDataSources.get(name);
	}

	/**   
	 * 使用Key选择当前的数据源  
	 * @param key 
	 */ 
//	public void choiceMappedDataSources(String key){   
//		DataSource ds = this.mappedDataSources.get(key);   
//		if(ds == null){   
//			throw new IllegalStateException("No Mapped DataSources Exist!");   
//		} 
//		this.currentDataSourceHolder.set(ds);  
//	}  


	/* (non-Javadoc)  
	 * @see javax.sql.DataSource#getConnection()  
	 */  
	public Connection getConnection() throws SQLException {  
		if(getCurrentDataSource() != null){ 
			return getCurrentDataSource().getConnection();  
		}   
		return null;   
	} 

	/* (non-Javadoc)  
	 * @see javax.sql.DataSource#getConnection(java.lang.String, java.lang.String)  
	 */  
	public Connection getConnection(String username, String password)  
			throws SQLException {  
		if(getCurrentDataSource() != null){
			return getCurrentDataSource().getConnection(username , password);   
		}   
		return null;   
	}  

	/* (non-Javadoc)  
	 * @see javax.sql.CommonDataSource#getLogWriter()  
	 */ 
	public PrintWriter getLogWriter() throws SQLException { 
		if(getCurrentDataSource() != null){ 
			return getCurrentDataSource().getLogWriter();   
		}  
		return null;   
	}   

	/* (non-Javadoc)
	 * @see javax.sql.CommonDataSource#getLoginTimeout()  3 ]) 
	 */ 
	public int getLoginTimeout() throws SQLException {   
		if(getCurrentDataSource() != null){   
			return getCurrentDataSource().getLoginTimeout(); 
		}  
		return 0;   
	}  

	/* (non-Javadoc) 
	 * @see javax.sql.CommonDataSource#setLogWriter(java.io.PrintWriter) 
	 */  
	public void setLogWriter(PrintWriter out) throws SQLException { 
		if(getCurrentDataSource() != null){  
			getCurrentDataSource().setLogWriter(out);   
		} 
	}  

	/* (non-Javadoc)  
	 * @see javax.sql.CommonDataSource#setLoginTimeout(int)  
	 */  
	public void setLoginTimeout(int seconds) throws SQLException {  
		if(getCurrentDataSource() != null){   
			getCurrentDataSource().setLoginTimeout(seconds);   
		} 
	}   

	/* (non-Javadoc)  
	 * 该接口方法since 1.6 
	 * 不是所有的DataSource都实现有这个方法  
	 * @see java.sql.Wrapper#isWrapperFor(java.lang.Class)  
	 */ 
	public boolean isWrapperFor(Class<?> iface) throws SQLException {  

		//      if(getCurrentDataSource() != null){   
		//          return getCurrentDataSource().isWrapperFor(iface); 
		//      }    
		return false;   
	}   

	/* (non-Javadoc)  " 
	 * 该接口方法since 1.6  
	 * 不是所有的DataSource都实现有这个方法  
	 * @see java.sql.Wrapper#unwrap(java.lang.Class)  
	 */  
	public <T> T unwrap(Class<T> iface) throws SQLException {  
		//      if(getCurrentDataSource() != null){   
		//          return getCurrentDataSource().unwrap(iface);   
		//      }  
		return null;   
	}
	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

}
