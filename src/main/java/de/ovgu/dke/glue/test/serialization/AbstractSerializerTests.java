package de.ovgu.dke.glue.test.serialization;

import static org.junit.Assert.*;
import org.junit.Test;

import de.ovgu.dke.glue.api.serialization.SerializationException;
import de.ovgu.dke.glue.api.serialization.SerializationProvider;
import de.ovgu.dke.glue.api.serialization.Serializer;

/**
 * <p>
 * This test case checks whether an implementation of {@link Serializer} follows
 * the rules defined by the Glue-API or not. All you have to do is to extend
 * this test case and override the <code>getSerializer</code>-Method that
 * injects your serializer implementation into the test case.
 * </p>
 * <p>
 * It is highly recommended that you extend this basic test case according to
 * your implementation specific test purposes. For example not all
 * {@link SerializationException} can't be tested.
 * </p>
 * <p>
 * Of course you are free to extend this test case for your specific test
 * purposes.
 * </p>
 * 
 * @author Sebastian Dorok
 * 
 */
public abstract class AbstractSerializerTests {

	// builder to construct implementation specific serializers
	private Serializer serializer = null;

	/**
	 * Test case constructor.
	 * 
	 * @param serializer
	 *            under test
	 */
	public AbstractSerializerTests(Serializer serializer) {
		this.serializer = serializer;
	}

	/**
	 * <p>
	 * Test whether serializer returns correct format.
	 * </p>
	 */
	@Test
	public void T00_getFormat() {
		assertNotNull(
				"Serializer doesn't handle formats correctly - NULL format.",
				serializer.getFormat());
		assertTrue(
				"Serializer doesn't handle formats correctly - not allowed format",
				matchesAllowedFormats(serializer.getFormat()));
	}

	/**
	 * <p>
	 * This test checks the serialization and deserialization of characters.
	 * </p>
	 * <p>
	 * The test calls for serilizer of format
	 * <code>SerializationProvider.STRING</code>. Although the serializer under
	 * test doesn't support <code>SerializationProvider.STRING</code> it must
	 * handle string payloads in this test correctly, i.e. as objects or binary.
	 * </p>
	 */
	@Test
	public void T10_serializeAndDeserialize_Characters() {
		String payload = "thisIsADataPackage";
		Object o;
		try {
			o = serializer.serialize(payload);
			assertEquals("Deserialized payload differs from serialized one.",
					payload, serializer.deserialize(o));
		} catch (SerializationException e) {
			fail("Caught unexpected serialization exception!");
		}
	}

	/**
	 * <p>
	 * This test checks the serialization and deserialization of characters and
	 * whitespaces.
	 * </p>
	 * <p>
	 * The test calls for serilizer of format
	 * <code>SerializationProvider.STRING</code>. Although the serializer under
	 * test doesn't support <code>SerializationProvider.STRING</code> it must
	 * handle string payloads in this test correctly, i.e. as objects or binary.
	 * </p>
	 */
	@Test
	public void T11_serializeAndDeserialize_Whitespaces() {
		String payload = "this is a data package";
		Object o;
		try {
			o = serializer.serialize(payload);
			assertEquals("Deserialized payload differs from serialized one.",
					payload, serializer.deserialize(o));
		} catch (SerializationException e) {
			fail("Caught unexpected serialization exception!");
		}
	}

	/**
	 * <p>
	 * This test checks the serialization and deserialization of numbers.
	 * </p>
	 * <p>
	 * The test calls for serilizer of format
	 * <code>SerializationProvider.STRING</code>. Although the serializer under
	 * test doesn't support <code>SerializationProvider.STRING</code> it must
	 * handle string payloads in this test correctly, i.e. as objects or binary.
	 * </p>
	 */
	@Test
	public void T12_serializeAndDeserialize_Numbers() {
		String payload = "1234567890";
		Object o;
		try {
			o = serializer.serialize(payload);
			assertEquals("Deserialized payload differs from serialized one.",
					payload, serializer.deserialize(o));
		} catch (SerializationException e) {
			fail("Caught unexpected serialization exception!");
		}
	}

	/**
	 * <p>
	 * This test checks the serialization and deserialization of special
	 * characters.
	 * </p>
	 * <p>
	 * The test calls for serilizer of format
	 * <code>SerializationProvider.STRING</code>. Although the serializer under
	 * test doesn't support <code>SerializationProvider.STRING</code> it must
	 * handle string payloads in this test correctly, i.e. as objects or binary.
	 * </p>
	 */
	@Test
	public void T13_serializeAndDeserialize_SpecialCharacters() {
		String payload = "°^!\"§$%&/()=?`´*+~#'-_:.;,<>|'{[]}\\";
		Object o;
		try {
			o = serializer.serialize(payload);
			assertEquals("Deserialized payload differs from serialized one.",
					payload, serializer.deserialize(o));
		} catch (SerializationException e) {
			fail("Caught unexpected serialization exception!");
		}
	}

	/**
	 * <p>
	 * API claims to throw Null Pointer Exception if argument is NULL for
	 * serialization.
	 * </p>
	 * <p>
	 * The test calls for serilizer of format
	 * <code>SerializationProvider.STRING</code>. Although the serializer under
	 * test doesn't support <code>SerializationProvider.STRING</code> it must
	 * handle NULL correctly.
	 * </p>
	 */
	@Test
	public void T14_serialize_NullArgument() {
		try {
			serializer.serialize(null);
			fail("Didn't catch expected null pointer exception!");
		} catch (SerializationException e) {
			fail("Caught serialization exception - did not expect that!");
		} catch (NullPointerException e) {
			assertTrue("Caught expected NPE exception!", true);
		} catch (Exception e) {
			fail("Caught non expected exception!");
		}
	}

	/**
	 * <p>
	 * API claims to throw Null Pointer Exception if argument is NULL for
	 * deserialization.
	 * </p>
	 * <p>
	 * The test calls for serilizer of format
	 * <code>SerializationProvider.STRING</code>. Although the serializer under
	 * test doesn't support <code>SerializationProvider.STRING</code> it must
	 * handle NULL correctly.
	 * </p>
	 */
	@Test
	public void T15_deserialize_NullArgument() {
		try {
			serializer.deserialize(null);
			fail("Didn't catch expected null pointer exception!");
		} catch (SerializationException e) {
			fail("Caught serialization exception - did not expect that!");
		} catch (NullPointerException e) {
			assertTrue("Caught expected NPE exception!", true);
		} catch (Exception e) {
			fail("Caught non expected exception!");
		}
	}

	// helper method that checks if format is allowed
	private boolean matchesAllowedFormats(String format) {
		if (SerializationProvider.BINARY.equals(format)) {
			return true;
		}
		if (SerializationProvider.JAVA.equals(format)) {
			return true;
		}
		if (SerializationProvider.STRING.equals(format)) {
			return true;
		}
		if (SerializationProvider.XML.equals(format)) {
			return true;
		}
		if (SerializationProvider.SERIALIZABLE.equals(format)) {
			return true;
		}
		return false;
	}

}
