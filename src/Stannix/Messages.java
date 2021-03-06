package Stannix;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
/**
 * Provides access to messages.properties file
 * 
 * @author derekbarrera
 */
public class Messages {
	private static final String BUNDLE_NAME = "Stannix.messages"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	/**
	 * Basic Object Creation
	 * 
	 */
	private Messages() {
	}

	/**
	 * Retrives string from properties file
	 * 
	 * @param key keyID
	 * @return property string
	 */
	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
