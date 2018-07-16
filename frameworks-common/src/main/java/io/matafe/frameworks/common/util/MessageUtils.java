package io.matafe.frameworks.common.util;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Message Utility.
 * 
 * @author matafe@gmail.com
 */
public class MessageUtils {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Instance
     */
    private static MessageUtils instance = new MessageUtils();

    /**
     * Cache by Locale
     */
    private final Map<Locale, Map<String, ResourceBundle>> cacheMessages;

    /**
     * Private Constructor
     */
    private MessageUtils() {
	this.cacheMessages = new HashMap<>();
    }

    /**
     * Singleton of this class
     * 
     * @return The instance of this class
     */
    public static MessageUtils getInstance() {
	return instance;
    }

    /**
     * Get the Resource Bundle using caching for increase performance.
     * 
     * @param bundleName
     *            The bundle name
     * @param locale
     *            The locale
     * 
     * @return The Resource Bundle.
     */
    public ResourceBundle getBundle(final String bundleName, final Locale locale) {
	try {
	    Map<String, ResourceBundle> map = this.cacheMessages.get(locale);

	    if (map == null) {
		this.cacheMessages.put(locale, map = new HashMap<>());
	    }

	    // let's see if the bundle exists in the cache
	    ResourceBundle rb = map.get(bundleName);

	    if (rb == null) {
		// Nop, we have to get it from the file system
		rb = ResourceBundle.getBundle(bundleName, locale);
		map.put(bundleName, rb);
	    }

	    return rb;

	} catch (MissingResourceException ex) {
	    logger.error("Bundle not found {}", bundleName, ex);
	    throw new RuntimeException(
		    "Cannot find the message from bundle = " + bundleName + " and locate = " + locale.toString());
	}
    }

    @Override
    public String toString() {
	return this.cacheMessages.toString();
    }

    public static void main(String[] args) {
	MessageUtils messageUtils = MessageUtils.getInstance();

	// Example 1
	ResourceBundle bundle = messageUtils.getBundle("message", Locale.US);
	System.out.println("Application Name is = " + bundle.getString("application.name"));

	System.out.println();
    }
}
