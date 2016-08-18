/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2011, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.as.test.integration.ee.injection.support.jpa.beanManager;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.naming.NamingException;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

public class TestEntityListener {

    @PrePersist
    public void prePersist(Object obj) throws NamingException {
        FooBean fooBean = obtainFoo();
        fooBean.ping();
    }

    @PreUpdate
    public void preUpdate(Object obj) {
        FooBean fooBean = obtainFoo();
        fooBean.ping();
    }

    @PreRemove
    public void preRemove(Object obj) {
        FooBean fooBean = obtainFoo();
        fooBean.ping();
    }

    @PostLoad
    public void postLoad(Object obj) {
        FooBean fooBean = obtainFoo();
        fooBean.ping();
    }

    @PostUpdate
    public void postUpdate(Object obj) {
        FooBean fooBean = obtainFoo();
        fooBean.ping();
    }

    @PostPersist
    public void postPersist(Object obj) {
        FooBean fooBean = obtainFoo();
        fooBean.ping();
    }

    @PostRemove
    public void postRemove(Object obj) {
        FooBean fooBean = obtainFoo();
        fooBean.ping();
    }

    private static FooBean obtainFoo() {
        BeanManager cdiCurrentBeanManager = CDI.current().getBeanManager();
        Bean<FooBean> bean = (Bean<FooBean>) cdiCurrentBeanManager.getBeans(FooBean.class).stream().findFirst().get();
        CreationalContext<FooBean> creationalContext = cdiCurrentBeanManager.createCreationalContext(bean);
        FooBean fooBean = (FooBean) cdiCurrentBeanManager.getReference(bean, FooBean.class, creationalContext);
        return fooBean;
    }

}
