package com.pandroid.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
//tag::PandroidBindLifeCycleDelegateAnnotation[]
public @interface BindLifeCycleDelegate {

    String BINDER_PREFIX = "_LifecycleAutoBinder";

}
//end::PandroidBindLifeCycleDelegateAnnotation[]
