/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
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
package org.jboss.as.tests.deployment.classloading.ear;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class EjbJarCanAccessOtherEjbJarTestCase {

    @Deployment
    public static Archive<?> deploy() {

        EnterpriseArchive ear = ShrinkWrap.create(EnterpriseArchive.class);

        JavaArchive jar = ShrinkWrap.create(JavaArchive.class, "otherjar.jar");
        jar.addClass(WebInfLibClass.class);
        // TODO: non empty file
        jar.addResource(new StringAsset(""), "META-INF/ejb-jar.xml");

        ear.addModule(jar);
        jar = ShrinkWrap.create(JavaArchive.class, "testjar.jar");
        jar.addClass(EjbJarCanAccessOtherEjbJarTestCase.class);
        jar.addResource(new StringAsset(""), "META-INF/ejb-jar.xml");
        ear.addModule(jar);

        return ear;
    }

    @Test
    public void testEjbJarCanAccessOtherEjbJar() throws ClassNotFoundException {
        loadClass("org.jboss.as.tests.deployment.classloading.ear.WebInfLibClass");
    }


    private static Class<?> loadClass(String name) throws ClassNotFoundException {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        if (cl != null) {
            return Class.forName(name, false, cl);
        } else
            return Class.forName(name);
    }
}
