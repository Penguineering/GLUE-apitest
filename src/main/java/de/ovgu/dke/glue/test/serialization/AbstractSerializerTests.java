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
		assertNotNull("Serializer returns NULL as format.",
				serializer.getFormat());
		assertTrue("Serializer doesn't return allowed format.",
				matchesAllowedFormats(serializer.getFormat()));
	}

	/**
	 * <p>
	 * The test tries to serialize NULL. If an exception is thrown it must be a
	 * NPE any other exception leads to a failure. If no exception is thrown the
	 * test will pass because serializer can be able to handle NULL.
	 * </p>
	 */
	@Test
	public void T10_serialize_NullArgument() {
		try {
			serializer.serialize(null);
		} catch (SerializationException e) {
			fail("Caught serialization exception - did not expect that!");
		} catch (NullPointerException e) {
			assertTrue("Caught expected NPE exception!", true);
		} catch (Exception e) {
			fail("Caught unexpected exception!");
		}
	}

	/**
	 * <p>
	 * The test tries to deserialize NULL. If an exception is thrown it must be
	 * a NPE any other exception leads to a failure. If no exception is thrown
	 * the test will pass because serializer can be able to handle NULL.
	 * </p>
	 */
	@Test
	public void T11_deserialize_NullArgument() {
		try {
			serializer.deserialize(null);
		} catch (SerializationException e) {
			fail("Caught serialization exception - did not expect that!");
		} catch (NullPointerException e) {
			assertTrue("Caught expected NPE exception!", true);
		} catch (Exception e) {
			fail("Caught unexpected exception!");
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
