package com.twinleaves.ims.annotation;

import com.twinleaves.ims.utils.CustomDateStringValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Target({ FIELD, ANNOTATION_TYPE, TYPE_USE })
@Constraint(validatedBy = CustomDateStringValidator.class)
public @interface ValidCustomDateString {
 String message() default "Invalid date format passed";

 Class<?>[] groups() default {};

 Class<? extends Payload>[] payload() default {};

 String dateFormat() default "";

 boolean isMandatory() default false;
}