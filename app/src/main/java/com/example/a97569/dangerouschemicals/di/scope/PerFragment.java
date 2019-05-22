package com.example.a97569.dangerouschemicals.di.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 *******************************************************************************
 * @FileName:  PerFragment
 * @Package com.yidaoyun.newenergyvehicleandroid.module
 * @Description:  fragment生命周期标识
 * @author: lpz
 * @date:   2018/10/9  17:30
 * @version V1.0
 *******************************************************************************
 */

@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerFragment {
}
