/**
 * 
 */
package org.sands.spojo.model;

/**
 * @author Vincent Palau
 * @Since Feb 27, 2011
 * 
 */
public class Product extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3238967678162019281L;

	private Long id = null;
	private String key = null;
	private String value = null;
	private boolean available = false;
	private boolean discount = false;

	/**
	 * 
	 */
	public Product() {
	}

	/**
	 * @param id
	 * @param key
	 * @param value
	 * @param available
	 * @param discount
	 */
	public Product(final Long id, final String key, final String value, final boolean available, final boolean discount) {
		this.id = id;
		this.key = key;
		this.value = value;
		this.available = available;
		this.discount = discount;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(final String key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(final String value) {
		this.value = value;
	}

	/**
	 * @return the available
	 */
	public boolean isAvailable() {
		return available;
	}

	/**
	 * @param available
	 *            the available to set
	 */
	public void setAvailable(final boolean available) {
		this.available = available;
	}

	/**
	 * @return the discount
	 */
	public boolean isDiscount() {
		return discount;
	}

	/**
	 * @param discount
	 *            the discount to set
	 */
	public void setDiscount(final boolean discount) {
		this.discount = discount;
	}

	@Override
	public String toString() {
		return String.format("Product [id=%s, key=%s, value=%s, available=%s, discount=%s]", getId(), getKey(), getValue(),
				isAvailable(), isDiscount());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Product other = (Product) obj;
		if (available != other.available) {
			return false;
		}
		if (discount != other.discount) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (key == null) {
			if (other.key != null) {
				return false;
			}
		} else if (!key.equals(other.key)) {
			return false;
		}
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}

}
