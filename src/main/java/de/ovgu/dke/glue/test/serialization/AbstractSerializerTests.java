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

	/**
	 * <p>
	 * This method must be overridden by an implementation specific variant that
	 * returns the serializer implementation uner test.
	 * </p>
	 * 
	 * @param format
	 *            if needed for instantiation of serializer, else it can be
	 *            ignored
	 * @return serializer instance that will be tested
	 */
	public abstract Serializer getSerializer(String format);

	/**
	 * <p>
	 * Test whether serializer returns correct format. Therefore the allowed
	 * format <code>SerializationProvider.BINARY</code> is used to get a
	 * serializer.
	 * </p>
	 * <p>
	 * In case of serializers that don't support dynamic formats this test will
	 * always pass if the internal format is allowed.
	 * </p>
	 */
	@Test
	public void T00_getFormat() {
		Serializer ser = getSerializer(SerializationProvider.BINARY);
		assertNotNull(
				"Serializer doesn't handle formats correctly - NULL format.",
				ser.getFormat());
		assertTrue(
				"Serializer doesn't handle formats correctly - not allowed format",
				matchesAllowedFormats(ser.getFormat()));
	}

	/**
	 * <p>
	 * {@link Serializer} interface claims that the serializer have to take care
	 * that the format is one of the defined in {@link SerializationProvider}.
	 * It is not specified how this should be handled. Test case tests only
	 * return value. Test fails if not allowed format is returned. </p
	 * <p>
	 * In case of serializers that don't support dynamic formats this test will
	 * always pass if the internal format is allowed.
	 * </p>
	 */
	@Test
	public void T01_getFormat_UnknownFormat() {
		Serializer ser = getSerializer("WRONG_FORMAT");
		assertNotSame(
				"Serializer doesn't handle formats correctly - not allowed format",
				"WRONG_FORMAT", ser.getFormat());
		assertTrue(
				"Serializer doesn't handle formats correctly - not allowed format",
				matchesAllowedFormats(ser.getFormat()));
	}

	/**
	 * <p>
	 * {@link Serializer} interface claims that the serializer have to take care
	 * that the format is one of the defined in {@link SerializationProvider}.
	 * It is not specified how this should be handled. Test case tests if
	 * serializer implementation handles NULL values. Test fails if NULL or not
	 * allowed format is returned. </p
	 * <p>
	 * <p>
	 * In case of serializers that don't support dynamic formats this test will
	 * always pass if the internal format is allowed.
	 * </p>
	 */
	@Test
	public void T02_getFormat_NullFormat() {
		Serializer ser = getSerializer(null);
		assertNotNull(
				"Serializer doesn't handle formats correctly - NULL format.",
				ser.getFormat());
		assertTrue(
				"Serializer doesn't handle formats correctly - not allowed format",
				matchesAllowedFormats(ser.getFormat()));
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
		Serializer ser = getSerializer(SerializationProvider.STRING);
		Object o;
		try {
			o = ser.serialize(payload);
			assertEquals("Deserialized payload differs from serialized one.",
					payload, ser.deserialize(o));
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
		Serializer ser = getSerializer(SerializationProvider.STRING);
		Object o;
		try {
			o = ser.serialize(payload);
			assertEquals("Deserialized payload differs from serialized one.",
					payload, ser.deserialize(o));
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
		Serializer ser = getSerializer(SerializationProvider.STRING);
		Object o;
		try {
			o = ser.serialize(payload);
			assertEquals("Deserialized payload differs from serialized one.",
					payload, ser.deserialize(o));
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
		Serializer ser = getSerializer(SerializationProvider.STRING);
		Object o;
		try {
			o = ser.serialize(payload);
			assertEquals("Deserialized payload differs from serialized one.",
					payload, ser.deserialize(o));
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
		Serializer ser = getSerializer(SerializationProvider.STRING);
		try {
			ser.serialize(null);
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
		Serializer ser = getSerializer(SerializationProvider.STRING);
		try {
			ser.deserialize(null);
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
