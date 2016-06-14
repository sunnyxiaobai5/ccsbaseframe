package cn.ccsgroup.ccsframework.annotation;

import java.lang.annotation.*;
/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.annotation.FieldDesc.java]  
 * @ClassName:    [FieldDesc]   
 * @Description:  [实体名称注解类]   
 * @Author:       [weiwen ]   
 * @CreateDate:   [2013-12-3 下午1:15:01]   
 * @UpdateUser:   [weiwen(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-12-3 下午1:15:01，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FieldDesc {
	String value() default "";
}
