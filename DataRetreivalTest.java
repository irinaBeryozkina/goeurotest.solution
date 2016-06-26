package goeuro.solution.goeurosolution;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import goeuro.solution.goeurosolution.GoEuroTestMain;
import goeuro.solution.goeurosolution.LocationRecord;

public class DataRetreivalTest {

	@Test
	public void test() throws Exception {

		List<LocationRecord> lrs = GoEuroTestMain.fetchRecords("Potsdam");
		assertEquals(3, lrs.size());
		
		LocationRecord r = lrs.get(0);
		assertEquals(377078, (long) r.id);
		assertEquals("Potsdam", r.name);
		assertEquals("location", r.type);
		assertEquals(0, Double.compare(13.06566, r.longitude));
		assertEquals(0, Double.compare(52.39886, r.latitude));

		r = lrs.get(1);
		assertEquals(410978, (long) r.id);
		assertEquals("Potsdam", r.name);
		assertEquals("location", r.type);
		assertEquals(0, Double.compare(-74.98131, r.longitude));
		assertEquals(0, Double.compare(44.66978, r.latitude));

		r = lrs.get(2);
		assertEquals(334178, (long) r.id);
		assertEquals("Berlin Potsdamer Platz", r.name);
		assertEquals("station", r.type);
		assertEquals(0, Double.compare(13.37647, r.longitude));
		assertEquals(0, Double.compare(52.509462, r.latitude));

	}

}
