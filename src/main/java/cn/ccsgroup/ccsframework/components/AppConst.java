package cn.ccsgroup.ccsframework.components;

/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.components.AppConst.java]  
 * @ClassName:    [AppConst]   
 * @Description:  [公共常量类]   
 * @Author:       [weiwen ]   
 * @CreateDate:   [2013-12-2 下午4:49:07]   
 * @UpdateUser:   [weiwen(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-12-2 下午4:49:07，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
public class AppConst {

	/*用户seesion*/
	public static final String USER_SESSION = "userSession";
	
	// 系统编码11
	public static final String ENCODING = "UTF-8";
	
	// 默认显示日期格式
	public static final String DATE_PATTEN = "yyyy/MM/dd";
	
	// 默认显示日期格式（带年）
	public static final String YEAR_PATTEN = "yyyy";

	// 默认显示日期格式（带时分秒）
	public static final String TIME_PATTEN = "yyyy/MM/dd HH:mm:ss";
	
	// 显示日期格式（年月日） 
	public static final String ZH_CN_DATE_PATTEN = "yyyy年M月d日";

	// 禁用用户  
	public static final String DISABLE = "1";
	// 解禁用户  
	public static final String ENABLE = "0";
	
	// 页面操作状态 ---操作成功   
	public static final String SUCCESS = "success";
	// 页面操作状态 ---操作失败  
	public static final String FAIL = "error";
	// 页面操作状态
	public static final String STATUS = "status";
	// 页面操作状态  --- 返回页面
	public static final String FORWORD = "forword";
	
	//用户初始密码
	public static final String ORIGINAL_PWD = "aaaaaa";

	// 默认每页显示的最大记录数
	public static int PAGE_SIZE = 14;

	// 正确消息
	public static final String MESSAGES = "message";
	// 错误消息
	public static final String ERRORS = "errors";
	
	
	//单点登录KEY
	public static final String SESSION_KEY = "SESSION_KEY";
	
	//分页页面参数名称
	public static final String PAGER = "pager";
	//分页表单名称
	public static final String PAGER_OUTPUT = "output";
	
	//   注销  
	public static final Integer STATUS_DISABLE = -1;
	//   有效
	public static final Integer STATUS_ENABLE = 1;
	//   是否可修改
	public static final Integer CANMODIFY = 1;
	//   不能修改
	public static final Integer NOTMODIFY = 0;
	//   查询日志主键ID
	public static final String QUERY_ID = "query_id";

    // 操作类型
    public static final String OPT = "opt";
    // 添加
    public static final String ADD = "add";
    // 修改/编辑
    public static final String UPDATE = "update";
}
