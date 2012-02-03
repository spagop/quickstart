package org.jboss.as.quickstarts.mgmt;

import java.net.InetAddress;

import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.dmr.ModelNode;
import org.jboss.dmr.Property;

/**
 * <p>
 * This example shows you how you can use the JBoss Application Server Management API to create a
 * Datasource.
 * </p>
 *
 * @author Juergen Hoffmann (jhoffmann@redhat.com)
 */
public class Client
{
    public static void main(String[] args) throws Exception
    {
        ModelControllerClient client = ModelControllerClient.Factory.create(
                InetAddress.getByName("localhost"), 9999);
        try
        {
            String dsname = "REPLACE_WITH_PROPERTY";
            ModelNode op = new ModelNode();
            op.get("operation").set("add");

            ModelNode address = op.get("address");
            address.add("subsystem", "datasources");
            address.add("data-source", dsname);

            op.get("jndi-name").set("java:jboss/datasources/" + dsname);
            op.get("driver-name").set("mysql");
            op.get("enabled").set(true);
            op.get("pool-name").set("TestDS");
            op.get("connection-url").set("jdbc:mysql://localhost/akdb");

            ModelNode result = client.execute(op);

            if (result.hasDefined("outcome")
                    && "success".equals(result.get("outcome").asString()))
            {

                ModelNode readOp = new ModelNode();
                readOp.get("operation").set("read-resource");
                readOp.get("address").add("subsystem", "datasources");
                readOp.get("recursive").set(true);

                ModelNode localResult = client.execute(readOp);

                if (localResult.hasDefined("outcome") && "success".equals(localResult.get("outcome").asString()))
                {
                    System.out.println("SUCCESS!!!");
                    System.out.println("The following Datasources are now configured");

                    ModelNode dsList = localResult.get("result").get("data-source");
                    for (ModelNode ds : dsList.asList())
                    {
                        System.out.println("-------> Datasource: " + ds.asProperty().getName() + " <------");
                        for (Property prop : ds.asProperty().getValue().asPropertyList())
                        {
                            System.out.println(prop.getName() + "=" + prop.getValue());
                        }
                    }
                }
            } else if (result.hasDefined("failure-description"))
            {
                throw new RuntimeException(result.get("failure-description")
                        .toString());
            } else
            {
                throw new RuntimeException(
                        "Operation not successful; outcome = "
                                + result.get("outcome"));
            }
        } finally
        {
            client.close();
        }
    }

    private static void debug(String identifier, ModelNode node)
    {
        System.out.println(identifier + " - ModelNodeType: " + node.getType());
    }
}
