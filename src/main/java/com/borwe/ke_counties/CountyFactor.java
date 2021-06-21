/*
 * Copyright 2021 BRIAN.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.borwe.ke_counties;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author BRIAN
 */
public class CountyFactor {

	private static final ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * Return Observable <b>County</b> to caller 
	 * @return 
	 * @throws java.io.IOException 
	 */
	public static Flowable<County> getCounties() throws IOException{
		InputStream inStream =
				CountyFactor.class.getResourceAsStream("/kenyan-counties.json");

		JsonNode countiesJSON=mapper.readTree(inStream);

		return Flowable.create(emitter->{
			ArrayNode featureNode=(ArrayNode) countiesJSON.get("features");
			featureNode.forEach(node->{
				emitter.onNext(node);
			});
			emitter.onComplete();
		}, BackpressureStrategy.BUFFER)
		.flatMapMaybe(node->{
			JsonNode tmp=(JsonNode) node;
			return parseACounty(tmp);
		});
	}

	private static Maybe<County> parseACounty(JsonNode node){
		return Maybe.create(emitter->{
			JsonNode propNode=node.get("properties");
			County.Builder builder=new County.Builder();
			builder.setArea(propNode.get("AREA").asDouble());
			builder.setPerimeter(propNode.get("PERIMETER").asDouble());
			builder.setName(propNode.get("COUNTY").asText());
			builder.setCoordinates(CountyFactor
					.createCoordinatesFroCounty(node.get("geometry")));

			emitter.onSuccess(builder.build());
		});
	}

	private static Flowable<CoOrdinates> createCoordinatesFroCounty(JsonNode node){
		return Flowable.create(emitter->{
			ArrayNode cods=(ArrayNode) node.get("coordinates").get(0);
			for(JsonNode nd:cods){
				emitter.onNext(nd);
			}
			emitter.onComplete();
		}, BackpressureStrategy.BUFFER)
		.observeOn(Schedulers.computation())
		.flatMapSingle(nd->{
			ArrayNode tmp=(ArrayNode) nd;
			return Single.create(emitter->{
				CoOrdinates.Builder builder=new CoOrdinates.Builder();
				builder.setLongitude(tmp.get(0).asDouble());
				builder.setLatitude(tmp.get(0).asDouble());
				emitter.onSuccess(builder.build());
			});
		});
	}
}
