package de.ovgu.dke.glue.test.serialization;

import java.util.List;

import de.ovgu.dke.glue.api.serialization.SerializationProvider;
import de.ovgu.dke.glue.api.serialization.Serializer;

/**
 * This interface provides a builder to construct {@link SerializationProvider}
 * instances.
 * 
 * @author Sebastian Dorok
 * 
 */
public interface SerializationProviderBuilder {

	/**
	 * <p>
	 * Builds the {@link SerializationProvider}.
	 * </p>
	 * 
	 * @param serializers
	 *            could be provided by the provider.
	 * @return instance of provider
	 */
	public SerializationProvider build(List<Serializer> serializers);

}
