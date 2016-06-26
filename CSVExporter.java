package goeuro.solution.goeurosolution;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.opencsv.CSVWriter;

/**
 * Exports list of LocationRerords to csv file
 * 
 * @author ira
 *
 */
public class CSVExporter {

	private static Logger log = Logger.getLogger("goeuro.main.csvexporter");

	/**
	 * Creates csv file of the following structure: _id, name, type, latitude,
	 * longitude (with header)
	 * 
	 * @param filePath
	 *            csv file path
	 * @param lrs
	 *            list of location records
	 */
	public void exportLocationRecords(String filePath, List<LocationRecord> lrs) {
		if (filePath == null) {
			//in the bigger application i would also throw some exception here
			log.log(Level.SEVERE,
					"filePath has to be specified for exportLocationRecords(String filePath, List<LocationRecord> lrs)");
			return;
		}
		if (lrs == null) {
			//in the bigger application i would also throw some exception here
			log.log(Level.SEVERE,
					"location record list (lrs) has to be specified for exportLocationRecords(String filePath, List<LocationRecord> lrs)");
			return;
		}

		try {
			File outputFile = new File(filePath);
			outputFile.getParentFile().mkdirs();
			outputFile.createNewFile();

			// writer will be closed automatically
			try (CSVWriter writer = new CSVWriter(new FileWriter(outputFile), ',')) {
				writer.writeNext(LocationRecord.HEADER);
				for (LocationRecord lr : lrs) {
					writer.writeNext(lr.toStringArray());
				}

				log.log(Level.INFO, filePath + " was succesfully recorded");

			} catch (Exception e) {
				log.log(Level.SEVERE, "wasn't able to write records to csv file", e);
			}
		} catch (IOException e) {
			log.log(Level.SEVERE, "wasn't able to create a new file" + filePath, e);
			e.printStackTrace();

		}

	}
}
