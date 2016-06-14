package cn.ccsgroup.ccsframework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.annotation.TokenAnnotation.java]  
 * @ClassName:    [TokenAnnotation]   
 * @Description:  [防止表单重复提交注解类]   
 * @Author:       [weiwen ]   
 * @CreateDate:   [2014-1-23 下午5:26:38]   
 * @UpdateUser:   [weiwen(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2014-1-23 下午5:26:38，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TokenAnnotation {
 
    boolean save() default false;
 
    boolean remove() default false;
}