/**
 *
 */
package org.sands.spojo.exceptions;

/**
 * @author Vincent Palau
 * @Since Feb 27, 2011
 * 
 */
public class RuleNotFoundException extends RuleException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7352862258052973608L;

	/**
	 * @param clazz
	 * @param filterName
	 */
	public RuleNotFoundException(final String filterName) {
		super(String.format("the Rule definition is not found with filterName: [%s]", filterName));
	}

}
