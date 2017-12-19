package com.example.goran.mvpdemo.dagger;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Goran on 19.12.2017..
 */

@Scope
@Retention(value= RetentionPolicy.RUNTIME)
@interface ActivityScope {
}
