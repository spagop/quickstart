jboss-as-helloworld-ejb-timer Example
=====================================

What is it?
-----------

This quickstart demonstrates the use of *EJB 3.1 Timer Service* in JBoss AS 7.1.0 or JBoss EAP 6.

System requirements
-------------------

All you need to build this project is Java 6.0 (Java SDK 1.6) or better, Maven 3.0 or better.

The application this project produces is designed to be run on a JBoss AS 7.1.0 or EAP 6. 
 
With the prerequisites out of the way, you're ready to build and deploy.


Deploying the application
-------------------------

First of all you need to enable the "admin" user from $JBOSS_HOME/standalone/configuration/mgmt-users.properties file, and then start JBoss AS 7 (or EAP 6). To do this, run this script
  
    $JBOSS_HOME/bin/standalone.sh
  
or if you are using windows
 
    $JBOSS_HOME/bin/standalone.bat

To deploy the application, you first need to produce the archive to deploy using
the following Maven goal:

    mvn package

You can now deploy the artifact to JBoss AS by executing the following command:

    mvn jboss-as:deploy

This will deploy `target/jboss-as-helloworld-ejb-timer`.
 
After successfull deployment the timer is activited and starts to check the availability of the url. In the JBoss Application Server Log you will see something like this:

23:10:06,491 INFO  [org.jboss.as.server.deployment] (MSC service thread 1-1) Starting deployment of "jboss-as-helloworld-ejb-timer.war"
23:10:06,513 INFO  [org.jboss.as.ejb3.deployment.processors.EjbJndiBindingsDeploymentUnitProcessor] (MSC service thread 1-4) JNDI bindings for session bean named MyTimerService in deployment unit deployment "jboss-as-helloworld-ejb-timer.war" are as follows:

	java:global/jboss-as-helloworld-ejb-timer/MyTimerService!org.jboss.as.quickstarts.ejbtimer.MyTimerService
	java:app/jboss-as-helloworld-ejb-timer/MyTimerService!org.jboss.as.quickstarts.ejbtimer.MyTimerService
	java:module/MyTimerService!org.jboss.as.quickstarts.ejbtimer.MyTimerService
	java:global/jboss-as-helloworld-ejb-timer/MyTimerService
	java:app/jboss-as-helloworld-ejb-timer/MyTimerService
	java:module/MyTimerService

23:10:06,562 INFO  [org.jboss.web] (MSC service thread 1-1) registering web context: /jboss-as-helloworld-ejb-timer
23:10:06,573 INFO  [org.jboss.as.server.controller] (pool-1-thread-11) Deployed "jboss-as-helloworld-ejb-timer.war"
23:10:10,002 INFO  [] (pool-6-thread-6) 
23:10:10,007 INFO  [] (pool-6-thread-6) HTTP Request to http://localhost:8080 has a HTTP Reponse with this status code HTTP/1.0 200
23:10:10,008 INFO  [] (pool-6-thread-6) 
23:10:15,001 INFO  [] (pool-6-thread-7) 
23:10:15,003 INFO  [] (pool-6-thread-7) HTTP Request to http://localhost:8080 has a HTTP Reponse with this status code HTTP/1.0 200
23:10:15,053 INFO  [] (pool-6-thread-7) 
23:10:20,001 INFO  [] (pool-6-thread-8) 
23:10:20,002 INFO  [] (pool-6-thread-8) HTTP Request to http://localhost:8080 has a HTTP Reponse with this status code HTTP/1.0 200
23:10:20,003 INFO  [] (pool-6-thread-8) 
23:10:25,001 INFO  [] (pool-6-thread-9) 
23:10:25,002 INFO  [] (pool-6-thread-9) HTTP Request to http://localhost:8080 has a HTTP Reponse with this status code HTTP/1.0 200
23:10:25,002 INFO  [] (pool-6-thread-9) 
23:10:30,002 INFO  [] (pool-6-thread-10) 
23:10:30,054 INFO  [] (pool-6-thread-10) HTTP Request to http://localhost:8080 has a HTTP Reponse with this status code HTTP/1.0 200
23:10:30,055 INFO  [] (pool-6-thread-10) 

To undeploy from JBoss AS, run this command:

    mvn jboss-as:undeploy

You can also start JBoss AS 7.1.0 or JBoss EAP 6 and deploy the project using Eclipse. See the JBoss AS 7
Getting Started Guide for Developers for more information.

