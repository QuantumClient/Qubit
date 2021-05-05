package org.quantumclient.qubit.utils.annotations;

import java.lang.annotation.*;

/**
 * Will not send chat message when toggled
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Silent {
}
