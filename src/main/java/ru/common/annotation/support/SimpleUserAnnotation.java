package ru.common.annotation.support;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value = RetentionPolicy.RUNTIME) //используем при исполнении кода
public @interface SimpleUserAnnotation
{
}
