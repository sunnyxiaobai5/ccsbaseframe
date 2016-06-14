
package cn.ccsgroup.ccsframework.base.dao;

import java.io.BufferedInputStream;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import oracle.sql.BLOB;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.base.dao.BaseDaoImpl.java]  
 * @ClassName:    [BaseDaoImpl]   
 * @Description:  [一句话描述该类的功能]   
 * @Author:       [zhiqian.wang]   
 * @CreateDate:   [2013-11-15 上午10:58:24]   
 * @UpdateUser:   [zhiqian.wang(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-11-15 上午10:58:24，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
@SuppressWarnings("deprecation")
public class BaseDaoImpl extends SqlMapClientDaoSupport{
	@Resource(name="sqlMapClientTemplate")
	private SqlMapClientTemplate sqlTemplate;
	
	public List queryForList(String statementName, Object parameterObject){
		return this.sqlTemplate.queryForList(statementName, parameterObject);
	}
	public List queryForList(String statementName){
		return this.sqlTemplate.queryForList(statementName);
	}
	
	public Object queryForObject(String statementName, Object parameterObject){
		return this.sqlTemplate.queryForObject(statementName, parameterObject);
	}
	
	public int delete(String statementName, Object parameterObject){
		return this.sqlTemplate.delete(statementName, parameterObject);
	}
	
	public int update(String statementName, Object parameterObject){
		return this.sqlTemplate.update(statementName, parameterObject);
	}
	
	public Object insertForObject(String statementName, Object parameterObject){
		return this.sqlTemplate.insert(statementName, parameterObject);
	}
	
	public void insert(String statementName, Object parameterObject){
		this.sqlTemplate.insert(statementName, parameterObject);
	}
	
	/**
	 * @Title: BlobToBytes
	 * @Description: TODO(blob转byte)
	 * @param @param blob
	 * @param @return    设定文件
	 * @return byte[]    返回类型
	 * @throws
	 */
	public byte[] BlobToBytes(BLOB blob) {
		BufferedInputStream is = null;
		byte[] bytes = null;
		try {
			is = new BufferedInputStream(blob.getBinaryStream());
			bytes = new byte[(int) blob.length()];
			int len = bytes.length;
			int offset = 0;
			int read = 0;
			while (offset < len
					&& (read = is.read(bytes, offset, len - offset)) >= 0) {
				offset += read;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bytes;
	}


    public void batchInsert(final String statementName, final List list) {
        if (list != null) {
            sqlTemplate.execute(new SqlMapClientCallback() {
                public Object doInSqlMapClient(SqlMapExecutor executor)
                        throws SQLException {
                    executor.startBatch();
                    for (int i = 0, n = list.size(); i < n; i++) {
                        executor.insert(statementName, list.get(i));
                    }
                    executor.executeBatch();
                    return null;
                }
            });
        }
    }

    public void batchUpdate(final String statementName, final List list) {
        if (list != null) {
            sqlTemplate.execute(new SqlMapClientCallback() {
                public Object doInSqlMapClient(SqlMapExecutor executor)
                        throws SQLException {
                    executor.startBatch();
                    for (int i = 0, n = list.size(); i < n; i++) {
                        executor.update(statementName, list.get(i));
                    }
                    executor.executeBatch();
                    return null;
                }
            });
        }

    }


    public void batchDelete(final String statementName, final List list) {
        if (list != null) {
            sqlTemplate.execute(new SqlMapClientCallback() {
                public Object doInSqlMapClient(SqlMapExecutor executor)
                        throws SQLException {
                    executor.startBatch();
                    for (int i = 0, n = list.size(); i < n; i++) {
                        executor.delete(statementName, list.get(i));
                    }
                    executor.executeBatch();
                    return null;
                }
            });
        }

    }
}
