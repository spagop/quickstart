package org.jboss.as.quickstarts.mgmt;

import java.net.InetAddress;
import java.util.List;

import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.dmr.ModelNode;

/**
 * <p>
 * This example shows you how you can use the JBoss Application Server Management API to get the
 * Status of a standalone server."
 * </p>
 * <p> The Model description via the CLI will look like this
 * <pre>
 * [standalone@localhost:9999 /] :read-attribute(name=server-state)
 * {
 *   "outcome" => "success",
 *   "result" => "running"
 * }
 * </pre>
 * </p>
 * 
 * @author Serge Pagop (spagop@redhat.com)
 * 
 */
public class Client {
	public static void main(String[] args) throws Exception {
		ModelControllerClient client = ModelControllerClient.Factory.create(
				InetAddress.getByName("localhost"), 9999);
		try {
			String dsname = "REPLACE_WITH_PROPERTY";
			ModelNode op = new ModelNode();
			op.get("operation").set("add");

            debug("1", op);

			ModelNode address = op.get("address");
			address.add("subsystem", "datasources");
			address.add("data-source", dsname);

            debug("2", address);

			op.get("jndi-name").set("java:jboss/datasources/" + dsname);
			op.get("driver-name").set("mysql");
			op.get("enabled").set(true);
			op.get("pool-name").set("TestDS");
			op.get("connection-url").set("jdbc:mysql://localhost/akdb");

			ModelNode result = client.execute(op);

            debug("3", result);
            System.out.println(result.toString());


			if (result.hasDefined("outcome")
					&& "success".equals(result.get("outcome").asString())) {
				if (result.hasDefined("result")) {

                    ModelNode readOp = new ModelNode();
                    readOp.get("operation").set("read-resource");
                    readOp.get("name").set("data-source");
                    readOp.get("recursive").set(true);

                    ModelNode localResult = client.execute(readOp);

                    System.out.println(localResult.toString());

                    List<ModelNode> results = localResult.get("result").asList();
                    for (ModelNode oneResult : results)
                    {
                        debug("4", oneResult);
                        System.out.println();
                        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                        System.out.println("&&&&&& Datasource: " + oneResult.toString());
                        System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                        System.out.println();

                    }
				}
			} else if (result.hasDefined("failure-description")) {
				throw new RuntimeException(result.get("failure-description")
						.toString());
			} else {
				throw new RuntimeException(
						"Operation not successful; outcome = "
								+ result.get("outcome"));
			}
		} finally {
			client.close();
		}
	}

    private static void debug(String identifier, ModelNode node)
    {
        System.out.println(identifier + " - ModelNodeType: " + node.getType());
    }
}
