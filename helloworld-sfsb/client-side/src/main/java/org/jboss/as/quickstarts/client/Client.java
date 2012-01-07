package org.jboss.as.quickstarts.client;

import java.util.HashMap;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.as.quickstarts.sfsb.ShoppingCart;
import org.jboss.as.quickstarts.sfsb.ShoppingCartBean;

@SuppressWarnings("unchecked")
public class Client {

	public static void main(String[] args) throws NamingException {
		final Hashtable jndiProperties = new Hashtable();
		jndiProperties.put(Context.URL_PKG_PREFIXES,
				"org.jboss.ejb.client.naming");
		final Context context = new InitialContext(jndiProperties);
		// The app name is the application name of the deployed EJBs. This is
		// typically the ear name
		// without the .ear suffix. However, the application name could be
		// overridden in the application.xml of the
		// EJB deployment on the server.
		// Since we haven't deployed the application as a .ear, the app name for
		// us will be an empty string
		final String appName = "";
		// This is the module name of the deployed EJBs on the server. This is
		// typically the jar name of the
		// EJB deployment, without the .jar suffix, but can be overridden via
		// the ejb-jar.xml
		// In this example, we have deployed the EJBs in a
		// jboss-as-helloworld-sfsb.jar, so the module name is
		// jboss-as-helloworld-sfsb
		final String moduleName = "jboss-as-helloworld-sfsb";
		// AS7 allows each deployment to have an (optional) distinct name. We
		// haven't specified a distinct name for
		// our EJB deployment, so this is an empty string
		final String distinctName = "";
		// The EJB name which by default is the simple class name of the bean
		// implementation class
		final String beanName = ShoppingCartBean.class.getSimpleName();
		// the remote view fully qualified class name
		final String viewClassName = ShoppingCart.class.getName();
		// Let's lookup the remote stateful
		final ShoppingCart cart = (ShoppingCart) context.lookup("ejb:"+ appName + "/" + moduleName + "/" + distinctName + "/"+ beanName + "!" + viewClassName + "?stateful");
		System.out.println();
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
		System.out.println("Obtained a remote stateful counter for invocation");
		// invoke on the remote counter bean
		System.out.println("Buying 1 memory stick");
		cart.buy("Memory stick", 1);
		System.out.println("Buying another memory stick");
		cart.buy("Memory stick", 1);

		System.out.println("Buying a laptop");
		cart.buy("Laptop", 1);
		System.out.println();
		System.out.println("Print cart:");
		HashMap<String, Integer> fullCart = cart.getCartContents();
		for (String product : fullCart.keySet()) {
			System.out.println(fullCart.get(product) + "     " + product);
		}
		System.out.println();
		System.out.println("Checkout");
		cart.checkout();
		System.out
				.println("Should throw an object not found exception by invoking on cart after @Remove method");
		try {
			cart.getCartContents();
		} catch (RuntimeException e) {
			System.out.println("Successfully caught no such object exception.");
		}
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
		System.out.println();
	}
}
