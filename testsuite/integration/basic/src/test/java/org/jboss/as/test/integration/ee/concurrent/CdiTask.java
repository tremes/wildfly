package org.jboss.as.test.integration.ee.concurrent;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;

import org.junit.Assert;

public class CdiTask implements Runnable {

    @Override
    public void run() {
        BeanManager beanManager = CDI.current().getBeanManager();
        Assert.assertNotNull(beanManager);
        Assert.assertTrue(beanManager.getContext(RequestScoped.class).isActive());
    }
}
