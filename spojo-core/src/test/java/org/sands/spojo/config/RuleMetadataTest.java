/**
 * 
 */
package org.sands.spojo.config;

import static org.junit.Assert.*;

import org.junit.Test;
import org.sands.spojo.BaseTestCase;
import org.sands.spojo.enums.RuleType;

/**
 * @author albert
 * 
 */
public class RuleMetadataTest extends BaseTestCase {

	@Test
	public void ruleEmpty() {
		String name = "probe1";
		String[] properties = new String[] {};
		String[] extendsFrom = new String[] {};
		RuleType type = null;

		RuleMetadataImpl wm = new RuleMetadataImpl(name, type, extendsFrom, properties);

		assertEquals(name, wm.getName());
		assertEquals(type, wm.getType());
		assertArrayEquals(extendsFrom, wm.getExtendsFrom().toArray());
		assertFalse(wm.hasInheritance());
		assertNotNull(wm.getProperty());
		assertTrue(wm.getProperty().isEmpty());
	}

	@Test
	public void ruleProperties() {
		String name = "probe1";
		String[] properties = new String[] { "prop1", "prop2" };
		String[] extendsFrom = new String[] {};
		RuleType type = null;

		RuleMetadataImpl wm = new RuleMetadataImpl(name, type, extendsFrom, properties);

		assertEquals(name, wm.getName());
		assertEquals(type, wm.getType());
		assertArrayEquals(extendsFrom, wm.getExtendsFrom().toArray());
		assertFalse(wm.hasInheritance());
		assertNotNull(wm.getProperty());
		assertTrue(wm.getProperty().getProperties().size() == properties.length);
	}

	@Test
	public void ruleExtends() {
		String name = "probe1";
		String[] properties = new String[] { "prop1", "prop2" };
		String[] extendsFrom = new String[] { "parent1,parent2" };
		RuleType type = null;

		RuleMetadataImpl wm = new RuleMetadataImpl(name, type, extendsFrom, properties);

		assertEquals(name, wm.getName());
		assertEquals(type, wm.getType());
		assertArrayEquals(extendsFrom, wm.getExtendsFrom().toArray());
		assertTrue(wm.hasInheritance());
		assertNotNull(wm.getProperty());
		assertTrue(wm.getProperty().getProperties().size() == properties.length);
	}

	@Test
	public void ruleType() {
		String name = "probe1";
		String[] properties = new String[] { "prop1", "prop2" };
		String[] extendsFrom = new String[] {};
		RuleType type = RuleType.INCLUDE;

		RuleMetadataImpl wm = new RuleMetadataImpl(name, type, extendsFrom, properties);

		assertEquals(name, wm.getName());
		assertEquals(type, wm.getType());
		assertArrayEquals(extendsFrom, wm.getExtendsFrom().toArray());
		assertFalse(wm.hasInheritance());
		assertNotNull(wm.getProperty());
		assertTrue(wm.getProperty().getProperties().size() == properties.length);
	}

	@Test
	public void extendFromMetadata() {
		String name = "probe1";
		String[] properties = new String[] { "prop1", "prop2" };
		String[] extendsFrom = new String[] {};
		RuleType type = RuleType.INCLUDE;

		RuleMetadataImpl wm1 = new RuleMetadataImpl(name, type, extendsFrom, properties);

		String name2 = "probe2";
		String[] properties2 = new String[] { "prop21", "prop2" };
		String[] extendsFrom2 = new String[] { "probe1" };
		RuleType type2 = RuleType.INCLUDE;

		RuleMetadataImpl wm2 = new RuleMetadataImpl(name2, type2, extendsFrom2, properties2);

		wm2.extendFromMetadata(wm1);

		assertEquals(name2, wm2.getName());
		assertEquals(type2, wm2.getType());
		assertArrayEquals(extendsFrom2, wm2.getExtendsFrom().toArray());
		assertTrue(wm2.hasInheritance());
		assertNotNull(wm2.getProperty());
		assertTrue(wm2.getProperty().getProperties().size() == 3);

	}

	@Test
	public void metadataToString() {
		String name = "probe1";
		String[] properties = new String[] { "prop1", "prop2" };
		String[] extendsFrom = new String[] {};
		RuleType type = RuleType.INCLUDE;

		RuleMetadataImpl wm1 = new RuleMetadataImpl(name, type, extendsFrom, properties);

		assertEquals(
				"RuleMetadataImpl [name=probe1, type=INCLUDE, extendsFrom=[], property=probe1@probe1{[prop1,prop2],parent=null}]",
				wm1.toString());

	}
}
