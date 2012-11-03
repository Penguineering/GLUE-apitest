/*
 * Copyright 2012 Stefan Haun, Thomas Low, Sebastian Stober, Andreas Nürnberger
 * 
 *      Data and Knowledge Engineering Group, 
 * 		Faculty of Computer Science,
 *		Otto-von-Guericke University,
 *		Magdeburg, Germany
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.ovgu.dke.glue.test.serialization;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Test;

import de.ovgu.dke.glue.api.serialization.SerializationException;
import de.ovgu.dke.glue.api.serialization.SerializationProvider;
import de.ovgu.dke.glue.api.serialization.Serializer;

/**
 * <p>
 * This test case checks whether an implementation of
 * {@link SerializationProvider} follows the rules defined by the Glue-API. All
 * you have to do is to extend this test case and implement a constructor that
 * instantiates this abstract class with an {@link SerializationProviderBuilder}
 * that builds your provider implementation for the test case.
 * </p>
 * <p>
 * It is highly recommended that you extend this basic test case according to
 * your implementation specific test purposes. For example not all
 * {@link SerializationException} can be tested.
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
	// builder to construct implementation specific providers
	private SerializationProviderBuilder spbuilder = null;
	// maximum number of serializers provided by an provider instance
	private int numberOfSerializers = 0;

	/**
	 * Test case constructor.
	 * 
	 * @param builder
	 *            is used to create an implementation specific
	 *            {@link SerializationProvider} providing predefined serializers
	 * @param numberOfSerializers
	 *            that can be provided by a provider instance, must be an
	 *            integer greater than zero
	 * @throws NullPointerException
	 *             if the builder parameter is null.
	 * @throws IllegalArgumentException
	 *             if the numberOfSerializers is smaller than or equals zero
	 */
	public AbstractSerializationProviderTests(
			SerializationProviderBuilder builder, int numberOfSerializers) {
		if (builder == null)
			throw new NullPointerException("builder parameter may not be null!");
		if (numberOfSerializers <= 0)
			throw new IllegalArgumentException(
					"number of serializers is smaller than or equals zero!");

		this.spbuilder = builder;
		this.numberOfSerializers = numberOfSerializers;
	}

	@After
	public void tearDown() throws Exception {
		availableSerializers = null;
	}

	/**
	 * <p>
	 * Tests the number of registered serializers. Furthermore it checks the
	 * order of registered serializers (preferred items at first).
	 * </p>
	 */
	@Test
	public void T00_availableFormats() {
		// get SerializationProvider for test
		SerializationProvider provider = spbuilder.build(getSerializers());
		// check count
		assertEquals(
				"Number of returned serializers doesn't match expected number.",
				Math.min(availableSerializers.size(), numberOfSerializers),
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
	public void T01_availableFormats_NullSerializer() {
		// null serializer list
		try {
			spbuilder.build(null);
			fail("No exception - expected NullPointerException.");
		} catch (NullPointerException e) {
			assertTrue(true);
		}
	}

	/**
	 * <p>
	 * Get the most preferred serializer.
	 * </p>
	 */
	@Test
	public void T10_getSerializer() {
		SerializationProvider provider = spbuilder.build(getSerializers());
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
	public void T11_getSerializers_NoSuitableSerializer() {
		SerializationProvider provider = spbuilder.build(getSerializers());
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
	public void T12_getSerializers_NullArgument() {
		SerializationProvider provider = spbuilder.build(getSerializers());
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

	/**
	 * <p>
	 * Check the immutable requirement on the available format list.
	 * </p>
	 */
	@Test
	public void T20_immutableFormatList() {
		SerializationProvider provider = spbuilder.build(getSerializers());
		List<String> formats = provider.availableFormats();
		String newFormat = "A new format!";

		// if the modification throws an exception this is also okay
		try {
			formats.add(newFormat);
			// test sizes of lists
			assertTrue(
					"Available format list can be modified - breaks the immutable requirement.",
					provider.availableFormats().size() == formats.size() - 1);
		} catch (Exception e) {
			assertTrue(true);
		}
		// check if new entry isn't in format list
		assertTrue("Also size is okay, the new format was added to the list.",
				!provider.availableFormats().contains(newFormat));
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

		switch (numberOfSerializers) {
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
