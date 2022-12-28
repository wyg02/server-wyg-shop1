package com.bwie.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;

import java.util.HashSet;
import java.util.Set;

/**
 * @author 军哥
 * @version 1.0
 * @description: 如果原来是null值，则不进行复制
 * @date 2022/7/4 17:24
 */

public class CopyBeanUtils extends BeanUtils {
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set emptyNames = new HashSet();
        for(java.beans.PropertyDescriptor pd : pds) {
            //check if value of this property is null then add it to the collection
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return (String[]) emptyNames.toArray(result);
    }

    public static void copy(Object source, Object target) throws BeansException {
        copyProperties(source, target, getNullPropertyNames(source));
    }
}
