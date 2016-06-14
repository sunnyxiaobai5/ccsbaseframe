package cn.ccsgroup.ccsframework.utils;


/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.utils.UUID.java]  
 * @ClassName:    [UUID]   
 * @Description:  [返回32位字符串UUID]   
 * @Author:       [weiwen ]   
 * @CreateDate:   [2013-12-2 下午5:20:01]   
 * @UpdateUser:   [weiwen(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-12-2 下午5:20:01，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
public class UUID {
	
	public static String create()
	{
		String uuid=java.util.UUID.randomUUID().toString();
		StringBuilder bf=new StringBuilder(32);
		for(int i=0;i<uuid.length();++i){
			char c=uuid.charAt(i);
			if(c!='-'&&c!='_'){
				bf.append(c);
			}
		}
		return bf.toString();
	}
	
	public static void main(String[] args) {
		Integer a = 10;
		int ab = 10;
		System.out.println( a == ab);
	}
}
