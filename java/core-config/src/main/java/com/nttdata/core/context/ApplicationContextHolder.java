/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Implements spring application context static access using {@link ApplicationContextAware}.<br>
 * Uses internal static classes with one instance class
 * of type {@link InnerContextResource} inside {@link SpringContextHolder} 
 * that allows get the context between static and instance variables.
 * 
 * @author NTT DATA
 * @since 0.0.1
 */
public final class ApplicationContextHolder implements ApplicationContextAware {

    /** Allow static access to {@link InnerContextResource} */
    private static class SpringContextHolder {

        /** Inner context resource containing application context */
        private static final InnerContextResource INNER_CONTEXT = new InnerContextResource();

        /** Prevent class instantiation */
        private SpringContextHolder() {
            super();
        }
    }
    
    /** Inner context resource containing application context instance */
    private static final class InnerContextResource {

        /** spring application context */
        private ApplicationContext context;
        
        /** Prevent class instantiation */
        private InnerContextResource() {
            super();
        }

        /**
         * Set the application context
         * @param context {@link ApplicationContext}
         */
        private void setContext(ApplicationContext context) {
            this.context = context;
        }
    }

    /**
     * Get the current application context
     * @return {@link ApplicationContext}
     */
    public static ApplicationContext getApplicationContext() {
        return SpringContextHolder.INNER_CONTEXT.context;
    }

    /**
     * Set the current application context
     * @param ac {@link ApplicationContext}
     */
    @Override
    public void setApplicationContext(ApplicationContext ac) {
    	SpringContextHolder.INNER_CONTEXT.setContext(ac);
    }
}