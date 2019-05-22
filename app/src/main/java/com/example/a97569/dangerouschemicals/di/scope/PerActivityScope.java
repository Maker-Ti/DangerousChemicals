package com.example.a97569.dangerouschemicals.di.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 *******************************************************************************
 * @FileName:  PerActivityScope
 * @Package com.yidaoyun.newenergyvehicleandroid.module
 * @Description:  Activity生存周期作用域
 * @author: lpz
 * @date:   2018/10/8  14:16
 * @version V1.0
 *******************************************************************************
 */

@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivityScope {
}
