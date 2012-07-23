package de.ovgu.dke.glue.test.serialization;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.ovgu.dke.glue.api.serialization.SerializationException;
import de.ovgu.dke.glue.api.serialization.SerializationProvider;
import de.ovgu.dke.glue.api.serialization.Serializer;

/**
 * <p>
 * This test case checks whether an implementation of
 * {@link SerializationProvider} follows the rules defined by the Glue-API or
 * not. All you have to do is to extend this test case and override the
 * <code>getSerializationProvider</code>- and
 * <code>getNumberOfSerializers</code>-Method that injects your provider
 * implementation into the test case.
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
public abstract class AbstractSerializationProviderTests {

	// used to store mock serializers during test
	private ArrayList<Serializer> availableSerializers = null;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		availableSerializers = null;
	}

	/**
	 * <p>
	 * Providers can provide different serializers at runtime. The number of
	 * provided serializers is important for testing purposes. Via overriding
	 * this method the number of possibly provided serializers should be
	 * returned.
	 * </p>
	 * 
	 * @return maximum number of provided serializers
	 */
	public abstract int getNumberOfSerializers();

	/**
	 * <p>
	 * This method is used to create an implementation specific
	 * {@link SerializationProvider} providing the serializers determined by a
	 * serializers list.
	 * </p>
	 * 
	 * @param serializers
	 *            that should be provided by the provider
	 * @return provider instance under test
	 */
	public abstract SerializationProvider getSerializationProvider(
			List<Serializer> serializers);

	/**
	 * <p>
	 * Tests the number of registered serializers. Furthermore it checks the
	 * order of registered serializers (preferred items at first).
	 * </p>
	 */
	@Test
	public void T00_AvailableFormats() {
		// get SerializationProvider for test
		SerializationProvider provider = getSerializationProvider(getSerializers());
		// check count
		assertEquals(
				"Number of returned serializers doesn't match expected number.",
				Math.min(availableSerializers.size(), getNumberOfSerializers()),
				provider.availableFormats().size());
		// check order
		for (int i = 0; i < availableSerializers.size(); i++)
			assertEquals("Order of serializers differs from expected.",
					availableSerializers.get(i).getFormat(), provider
							.availableFormats().get(i));
	}

	/**
	 * <p>
	 * Tests if serialization provider can hanlde null arguments. Therefore the
	 * serializer list is NULL. Additionally if the provider can provide more
	 * than one serializer a non NULL list is used that contains a NULL value.
	 * </p>
	 */
	@Test
	public void T01_AvailableFormats_NullSerializer() {
		int num = getNumberOfSerializers();
		switch (num) {
		case Integer.MAX_VALUE:
			// null serializer
			try {
				List<Serializer> list = getSerializers();
				list.add(null);
				getSerializationProvider(list);
				fail("No exception - expected NullPointerException.");
			} catch (NullPointerException e) {
				assertTrue(true);
			}
		case 1:
			// null serializer list
			try {
				getSerializationProvider(null);
				fail("No exception - expected NullPointerException.");
			} catch (NullPointerException e) {
				assertTrue(true);
			}
			break;
		}
	}

	/**
	 * <p>
	 * Get the most preferred serializer.
	 * </p>
	 */
	@Test
	public void T10_GetSerializer() {
		SerializationProvider provider = getSerializationProvider(getSerializers());
		// get the serializer
		try {
			assertEquals("Returned first serializer doesn't equal expected.",
					availableSerializers.get(0),
					provider.getSerializer(provider.availableFormats().get(0)));
		} catch (SerializationException e) {
			fail("Unexpected exception thrown.");
		}
	}

	/**
	 * <p>
	 * Test if SerializationException is thrown when format is unknown.
	 * </p>
	 */
	@Test
	public void T11_GetSerializers_NoSuitableSerializer() {
		SerializationProvider provider = getSerializationProvider(getSerializers());
		// get the serializer
		try {
			provider.getSerializer(SerializationProvider.JAVA);
			fail("No exception - expected SerializationException.");
		} catch (SerializationException e) {
			assertTrue(true);
		}
	}

	/**
	 * <p>
	 * Test if NullPointerException is thrown when format is NULL.
	 * </p>
	 */
	@Test
	public void T12_GetSerializers_NullArgument() {
		SerializationProvider provider = getSerializationProvider(getSerializers());
		// get the serializer
		try {
			provider.getSerializer(null);
			fail("No exception - expected SerializationException.");
		} catch (SerializationException e) {
			fail("Unexpected exception thrown.");
		} catch (NullPointerException e) {
			assertTrue(true);
		}
	}

	// helper method to create mocked serializers for test
	private List<Serializer> getSerializers() {
		availableSerializers = new ArrayList<Serializer>();

		Serializer binSerializer = EasyMock.createMock(Serializer.class);
		Serializer stringSerializer = EasyMock.createMock(Serializer.class);

		EasyMock.expect(binSerializer.getFormat())
				.andReturn(SerializationProvider.BINARY).anyTimes();
		EasyMock.expect(stringSerializer.getFormat())
				.andReturn(SerializationProvider.STRING).anyTimes();

		EasyMock.replay(binSerializer);
		EasyMock.replay(stringSerializer);

		int num = getNumberOfSerializers();
		switch (num) {
		case 1:
			availableSerializers.add(binSerializer);
			break;
		default:
			availableSerializers.add(binSerializer);
			availableSerializers.add(stringSerializer);
			break;
		}
		return availableSerializers;
	}

}
