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
