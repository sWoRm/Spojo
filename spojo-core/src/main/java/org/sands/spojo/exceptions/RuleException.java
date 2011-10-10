/**
 *
 */
package org.sands.spojo.exceptions;

/**
 * @author Vincent Palau
 * @Since Feb 27, 2011
 * 
 */
public class RuleException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6706010699182962172L;

	/**
	 * @param description
	 */
	public RuleException(final String description) {
		super(description);
	}

	/**
	 * @param description
	 * @param cause
	 */
	public RuleException(final String description, final Throwable cause) {
		super(description, cause);
	}

	/**
	 * @param cause
	 */
	public RuleException(final Throwable cause) {
		super(cause);
	}

}
