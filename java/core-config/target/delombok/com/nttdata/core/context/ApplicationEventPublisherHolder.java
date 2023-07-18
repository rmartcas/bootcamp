/*******************************************************************************
 * Copyright (C) 2023 NTT DATA, All Rights Reserved
 *******************************************************************************/
package com.nttdata.core.context;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

public final class ApplicationEventPublisherHolder implements ApplicationEventPublisherAware {
	
	/** Allow static access to {@link InnerContextResource} */
    private static class SpringContextHolder {

        /** Inner context resource containing application publisher */
        private static final InnerContextResource INNER_CONTEXT = new InnerContextResource();

        /** Prevent class instantiation */
        private SpringContextHolder() {
            super();
        }
    }
    
    /** Inner context resource containing application publisher instance */
    private static final class InnerContextResource {

        /** spring application event publisher */
        private ApplicationEventPublisher publisher;
        
        /** Prevent class instantiation */
        private InnerContextResource() {
            super();
        }

        /**
         * Set the application publisher
         * @param publisher {@link ApplicationEventPublisher}
         */
        private void setPublisher(ApplicationEventPublisher publisher) {
            this.publisher = publisher;
        }
    }

    /**
     * Get the current application publisher
     * @return {@link ApplicationEventPublisher}
     */
    public static ApplicationEventPublisher getEventPublisher() {
        return SpringContextHolder.INNER_CONTEXT.publisher;
    }

    /**
     * Set the current application publisher
     * @param applicationEventPublisher {@link ApplicationEventPublisher}
     */
	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		SpringContextHolder.INNER_CONTEXT.setPublisher(applicationEventPublisher);
	}

}
