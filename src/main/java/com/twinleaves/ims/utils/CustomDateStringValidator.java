package com.twinleaves.ims.utils;

import com.twinleaves.ims.annotation.ValidCustomDateString;
import com.twinleaves.ims.constants.IMSConstants;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

public class CustomDateStringValidator implements ConstraintValidator<ValidCustomDateString, String> {

    private String dateFormat = null;
    private boolean isMandatory;

    @Override
    public void initialize(final ValidCustomDateString constraintAnnotation) {
        dateFormat = constraintAnnotation.dateFormat();
        isMandatory = constraintAnnotation.isMandatory();
        if (StringUtils.isEmpty(dateFormat)) {
            dateFormat = IMSConstants.IMS_STD_DATE_TIME_FMT;
        }
    }

    @Override
    public boolean isValid(String dateStr, ConstraintValidatorContext context) {
        boolean isValid = false;
        if (StringUtils.isNotEmpty(dateStr)) {
            try {
                Date date = CommonUtil.getDateFromString(dateStr, dateFormat);
                if (date != null) {
                    isValid = true;
                }
            } catch (Exception e) {
                isValid = false;
            }
        } else if (!isMandatory) {
            isValid = true;
        }
        return isValid;
    }

}