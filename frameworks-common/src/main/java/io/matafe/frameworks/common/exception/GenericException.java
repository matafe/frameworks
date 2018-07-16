package io.matafe.frameworks.common.exception;

/**
 * Generic Exception
 * 
 * @author matafe@gmail.com
 */
public class GenericException extends ApplicationException {

  private static final long serialVersionUID = 1L;

  public GenericException(final int errorCode) {
    super(DEFAULT_ERROR_FILE, errorCode);
  }

  public GenericException(final int errorCode, final Object... args) {
    super(DEFAULT_ERROR_FILE, errorCode, args);
  }

  public GenericException(final int errorCode, Throwable throwable) {
    super(DEFAULT_ERROR_FILE, errorCode, throwable);
  }

  public GenericException(final int errorCode, Throwable throwable, final Object... args) {
    super(DEFAULT_ERROR_FILE, errorCode, throwable, args);
  }

}
