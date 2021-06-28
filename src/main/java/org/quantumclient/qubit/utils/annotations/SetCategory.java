package org.quantumclient.qubit.utils.annotations;

import org.quantumclient.qubit.module.Category;
import org.quantumclient.qubit.utils.Bind;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface SetCategory {
    Category value();
}
