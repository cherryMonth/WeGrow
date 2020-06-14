package com.wegrow.wegrow.demo.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 尽管Bean Validation API定义了一大堆标准的约束条件, 但是肯定还是有这些约束不能满足我们需求的时候, 在这种情况下, 你可以根据你的特定的校验需求来创建自己的约束条件.
 * 用户验证状态是否在指定范围内的注解
 * https://docs.jboss.org/hibernate/validator/4.2/reference/zh-CN/html/validator-customconstraints.html#validator-customconstraints-constraintannotation
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = FlagValidatorClass.class)
public @interface FlagValidator {
    String[] value() default {};

    String message() default "flag is not found";  // 这个属性被用来定义默认得消息模版, 当这个约束条件被验证失败的时候,通过此属性来输出错误信息.

    Class<?>[] groups() default {};  // 用于指定这个约束条件属于哪(些)个校验组(请参考第 2.3 节 “校验组”). 这个的默认值必须是Class<?>类型到空到数组.

    Class<? extends Payload>[] payload() default {};  // Bean Validation API 的使用者可以通过此属性来给约束条件指定严重级别. 这个属性并不被API自身所使用.
}
