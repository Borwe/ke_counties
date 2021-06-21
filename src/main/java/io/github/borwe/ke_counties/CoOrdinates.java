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
package io.github.borwe.ke_counties;

/**
 *
 * @author BRIAN
 */
class CoOrdinates {
	
	private final Double latitude;
	private final Double longitude;

	private CoOrdinates(double longitude,double latitude){
		this.longitude=longitude;
		this.latitude=latitude;
	}

	/**
	 * Get the latitude
	 * @return 
	 */
	public Double getLatitude() {
		return latitude;
	}

	/**
	 * Get the longitude
	 * @return 
	 */
	public Double getLongitude() {
		return longitude;
	}

	static class Builder{
		private Double lat;
		private Double lon;

		public Builder(){
			lat=null;
			lon=null;
		}

		public CoOrdinates build(){
			if(lat==null)
				throw new RuntimeException("You did not give a latitude value");

			if(lon==null)
				throw new RuntimeException("You did not give a longitude value");

			return new CoOrdinates(lon,lat);
		}

		public void setLatitude(Double lat) {
			this.lat = lat;
		}

		public void setLongitude(Double lon) {
			this.lon = lon;
		}
	}

	@Override
	public String toString() {
		return "CoOrdinates{" + "latitude=" + latitude + ", longitude=" + longitude + '}';
	}
}
