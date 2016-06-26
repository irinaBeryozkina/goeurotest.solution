package goeuro.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Implements an API query and transforms the retrieved data into a csv file
 * 
 * @author ira
 *
 */
public class GoEuroTestMain {

	private static Logger log = Logger.getLogger("goeuro.main.goeurotest");
	public static final String GO_EURO_URL = "http://api.goeuro.com/api/v2/position/suggest/en/";

	public static void main(String[] args) {

		if (args.length == 0) {
			System.out.println("Please specify the city name in the first argument. E.g:");
			System.out.println("java -jar GoEuroTest.jar \"Berlin\"");
			System.out.println("Optionally you can specify the csv file name. E.g:");
			System.out.println("java -jar GoEuroTest.jar \"Berlin\" /home/username/tmp/report.csv");
			return;
		}

		// i don't validate cityName because API endpoint does
		String cityName = args[0];

		List<LocationRecord> lrs;
		try {
			lrs = GoEuroTestMain.fetchRecords(cityName);
		} catch (Exception e) {
			log.log(Level.SEVERE, "Cannot fetch data for \"" + cityName + "\"");
			return;
		}

		CSVExporter ce = new CSVExporter();
		// if file name is specified then use it
		if (args.length > 1) {
			ce.exportLocationRecords(args[1], lrs);
		} else {
			// otherwise create a file with in default location
			ce.exportLocationRecords("./csvReports/" + cityName + ".csv", lrs);
		}
	}

	/**
	 * Retrieve city information from GoEuro
	 * 
	 * @param cityName
	 * @return
	 * @throws Exception
	 *             when it was unable to fetch the data
	 */
	public static List<LocationRecord> fetchRecords(String cityName) throws Exception {
		List<LocationRecord> ret = new LinkedList<>();
		try {
			URL endpoint = new URL(GO_EURO_URL + cityName);

			// parce json
			try (BufferedReader in = new BufferedReader(new InputStreamReader(endpoint.openStream()))) {
				JSONParser parser = new JSONParser();

				Object obj = parser.parse(in);
				JSONArray records = (JSONArray) obj;

				@SuppressWarnings("unchecked")
				Iterator<JSONObject> iterator = records.iterator();
				while (iterator.hasNext()) {
					JSONObject cityObj = iterator.next();
					LocationRecord lr = new LocationRecord();
					// _id, name, type, latitude, longitude
					lr.id = (Long) cityObj.get("_id");
					lr.name = (String) cityObj.get("name");
					lr.type = (String) cityObj.get("type");
					JSONObject geoPos = (JSONObject) cityObj.get("geo_position");
					if (geoPos != null) {
						lr.latitude = (Double) geoPos.get("latitude");
						lr.longitude = (Double) geoPos.get("longitude");
					} else {
						log.log(Level.WARNING, "No geoposition for " + lr.name + ", _id=" + lr.id);
					}
					ret.add(lr);
				}
			} catch (IOException e) {
				String msg = "IO exception for " + GO_EURO_URL + cityName;
				log.log(Level.SEVERE, msg, e);
				throw new Exception(msg);
			} catch (ParseException e) {
				String msg = "Cannot parse JSON data for " + cityName;
				log.log(Level.SEVERE, msg, e);
				throw new Exception(msg);
			}
		} catch (MalformedURLException e) {
			String msg = "Malformed URL " + GO_EURO_URL + cityName;
			log.log(Level.SEVERE, msg, e);
			throw new Exception(msg);
		}

		return ret;
	}

}
