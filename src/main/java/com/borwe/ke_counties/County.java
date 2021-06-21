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

import io.reactivex.rxjava3.core.Flowable;

/**
 *
 * @author BRIAN
 */
class County {
	
	private final String name;
	private final double area;
	private final double perimeter;
	private final Flowable<CoOrdinates> coOrdinates;

	private County(String name,double area,double perimeter,
			Flowable<CoOrdinates> coods){
		this.name=name;
		this.area=area;
		this.perimeter=perimeter;
		this.coOrdinates=coods;
	}

	public String getName() {
		return name;
	}

	public Double getArea() {
		return area;
	}

	public Double getPerimeter() {
		return perimeter;
	}

	public Flowable<CoOrdinates> getCoOrdinates() {
		return coOrdinates;
	}

	static class Builder{
		private String name;
		private Double area;
		private Double perimeter;
		private Flowable<CoOrdinates> coordinates;

		public County build(){

			if(name==null)
				 throw new RuntimeException("Name for County.Builder not set");

			if(area==null)
				throw new RuntimeException("Area for County.Builder not set");
			
			if(perimeter==null)
				throw new RuntimeException("Perimeter for County.Builder not set");

			if(coordinates==null)
				throw new RuntimeException("Coordinates for County.Builder not set");

			return new County(name,area,perimeter,coordinates);
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setArea(Double area) {
			this.area = area;
		}

		public void setPerimeter(Double perimeter) {
			this.perimeter = perimeter;
		}

		public void setCoordinates(Flowable<CoOrdinates> coordinates) {
			this.coordinates = coordinates;
		}
	}
}
