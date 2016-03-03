package com.wealthsimple.wealthsimplecodingchallenge.component;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Activity scope
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
}
