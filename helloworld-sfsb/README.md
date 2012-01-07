Jboss-as-helloworld EJB Stateful Session Bean Example
=====================================================

What is it?
===========

This example shows how to deploy a EJB 3.1 stateful session bean in JBoss AS7.1 or (EAP 6) and access that bean from a remote Java client program. The Java client uses the JNDI to lookup a proxy for the bean and invoke on that returned proxy.

There are two parts to the example: a server side component and a remote client program that accesses it. Each part is in its own standalone Maven module, however the quickstart does provide a top level module to simplify the packaging of the artifacts.

The server component is comprised of a stateful EJB. The client program looks up the stateful bean via JNDI and invokes methods on them.

System requirements
===================
All you need to build this project is Java 6.0 (Java SDK 1.6) or better, Maven 3.0 or better.

The application this project produces is designed to be run on a JBoss AS 7.1.0 or EAP 6. The following instructions target JBoss AS 7.1.0, but they also apply to JBoss EAP 6.

Building and deploying the application
======================================
Start JBoss AS 7.1 (or EAP 6):

    $JBOSS_HOME/bin/standalone.sh

To build both the server side component and a remote client program change into the examples quickstart directory and type:

    mvn clean install

The server side component is packaged as a jar and needs deploying to the AS you just started:

    cd server-side
    mvn jboss-as:deploy

This maven goal will deploy server-side/target/jboss-as-helloworld-sfsb.jar. You can check the AS console to see information messages regarding the deployment.

Note that you can also start JBoss AS 7.1.0 (or EAP 6) and deploy the project using Eclipse. See the JBoss AS 7 Getting Started Guide for Developers for more information.

Now start a client that will access the stateful bean you just deployed:

    cd ../client-side
    mvn exec:exec

You should see output showing:

   * the client sending a method invocation to the stateful bean to buy 3 products ( 2 Memory sticks & 1 Laptop) and then a second invocation to get the content of the shopping cart.
    
  and can looks lile this:

&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
Obtained a remote stateful counter for invocation
Buying 1 memory stick
Buying another memory stick
Buying a laptop

Print cart:
1     Laptop
2     Memory stick

Checkout
Should throw an object not found exception by invoking on cart after @Remove method
Jan 7, 2012 1:58:39 AM org.jboss.ejb.client.remoting.ChannelAssociation resultReady
INFO: Discarding result for invocation id 6 since no waiting context found
Successfully caught no such object exception.
&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&


To undeploy the server side component from JBoss AS:

    cd ../server-side
    mvn jboss-as:undeploy

