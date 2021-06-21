/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.borwe.ke_counties;

import io.reactivex.rxjava3.core.Flowable;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class LibraryTest {

	@Test
	void testGettingCounties() throws IOException{
		Flowable<County> counties = CountyFactor.getCounties();
		assertNotNull(counties);
		counties.count().test().assertValue(47L);
	}

	@Test
	void testCountiesHaveCoOrdnites() throws IOException{
		Long coordinates=CountyFactor.getCounties().firstElement().map(county->{
			return county.getCoOrdinates();
		}).count().blockingGet();
		assertTrue(coordinates>0L);
	}
}
