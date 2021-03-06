/** This code is licenced under the GPL version 2. */
package pcap.spi.exception.error;

/**
 * This device doesn't support setting the time stamp type ({@code 10}).
 *
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.0.0
 */
public class InterfaceNotSupportTimestampTypeException extends Exception {

  public InterfaceNotSupportTimestampTypeException(String message) {
    super(message);
  }
}
