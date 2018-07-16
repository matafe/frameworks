
package io.matafe.frameworks.common.util;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unit Test for <code>MessageUtils</code>.
 * 
 * @author matafe@gmail.com
 */
public class MessageUtilsTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private MessageUtils messageUtils;

    @Before
    public void setUp() throws Exception {
	this.messageUtils = MessageUtils.getInstance();
    }

    @Test
    public void testGetMessage() {

	// Example 1
	ResourceBundle bundle = messageUtils.getBundle("message", Locale.US);
	String applicationName = bundle.getString("application.name");

	logger.info("Application Name is: " + applicationName);

	assertThat("frameworks", equalTo(applicationName));
    }

}
