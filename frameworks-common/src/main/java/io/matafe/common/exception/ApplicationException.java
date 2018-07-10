package io.matafe.common.exception;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.matafe.common.util.MessageUtils;

/**
 * Abstract Base Application Exception
 * 
 * @author matafe@gmail.com
 */
public abstract class ApplicationException extends RuntimeException {

    protected final static String DEFAULT_ERROR_FILE = "error_messages";

    private static final Logger LOGGER = Logger.getLogger(ApplicationException.class.getName());

    private static final long serialVersionUID = 1L;

    private static final AtomicLong nextUniqueId = new AtomicLong(System.currentTimeMillis());

    /** Bundle for messages */
    private String bundle;

    /** Error code */
    protected int errorCode;

    /** Arguments */
    private Object[] arguments;

    /** Unique identifier for this message */
    private final String uniqueId;

    /** The resolved message from bundle */
    private final String exceptionMessage;

    /** The message that will be logged */
    private final String logExceptionMessage;

    public ApplicationException(final String bundleName, final int errorCode) {
	this(bundleName, errorCode, null, new Object[0]);
    }

    public ApplicationException(final String bundleName, final int errorCode, final Object... args) {
	this(bundleName, errorCode, null, args);
    }

    public ApplicationException(final String bundleName, final int errorCode, final Throwable throwable,
	    final Object... args) {
	super(throwable);

	this.arguments = args;
	this.bundle = bundleName;
	this.errorCode = errorCode;
	this.uniqueId = this.generateUniqueId();
	this.exceptionMessage = this.generateExceptionMessage();
	this.logExceptionMessage = uniqueId + " - " + exceptionMessage;

	if (!(this instanceof IQuiteException)) {
	    this.doLog();
	}

    }

    protected void doLog() {
	if (LOGGER.isLoggable(Level.SEVERE)) {
	    Exception ex = new Exception(logExceptionMessage, getCause());
	    LOGGER.log(Level.SEVERE, logExceptionMessage, ex);
	}
    }

    public Object[] getArguments() {
	return arguments;
    }

    @Override
    public String getMessage() {
	return this.exceptionMessage;
    }

    public String getBundle() {
	return this.bundle;
    }

    public int getErrorCode() {
	return this.errorCode;
    }

    public String getUniqueId() {
	return this.uniqueId;
    }

    private String generateExceptionMessage() {
	final Locale locale = Locale.getDefault();
	String exceptionMessage;
	try {
	    final ResourceBundle bundle = MessageUtils.getInstance().getBundle(this.bundle, locale);
	    final String pattern = bundle.getString(Integer.toString(errorCode));
	    final MessageFormat format = new MessageFormat(pattern, locale);
	    exceptionMessage = format.format(arguments);
	} catch (Exception e) {
	    exceptionMessage = e.getMessage();
	}
	return exceptionMessage;
    }

    private String generateUniqueId() {
	return new StringBuilder(getHostId()).append(" - ").append(Long.toHexString(nextUniqueId.incrementAndGet()))
		.toString().toUpperCase();
    }

    private String getHostId() {
	String hostId;
	try {
	    final InetAddress local = InetAddress.getLocalHost();
	    hostId = local.getHostName();
	} catch (final UnknownHostException e) {
	    hostId = "local";
	}
	return hostId;
    }

}
