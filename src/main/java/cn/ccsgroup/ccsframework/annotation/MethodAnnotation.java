package cn.ccsgroup.ccsframework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.annotation.MethodAnnotation.java]  
 * @ClassName:    [MethodAnnotation]   
 * @Description:  [操作名称注解类]   
 * @Author:       [weiwen ]   
 * @CreateDate:   [2013-12-3 下午1:14:37]   
 * @UpdateUser:   [weiwen(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-12-3 下午1:14:37，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodAnnotation {
	public enum logType{query,normal};
	String value();
	logType type() default logType.normal;

}
