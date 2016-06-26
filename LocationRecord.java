package goeuro.solution.goeurosolution;

/**
 * Contains id, name, type, latitude, longitude of some location
 * @author ira
 *
 */
public class LocationRecord {
	public Long id;
	public String name;
	public String type;
	public Double longitude;
	public Double latitude;

	public static final String[] HEADER = new String[] { "_id", "name", "type", "latitude", "longitude" };

	public String[] toStringArray() {
		String[] ret = new String[] { id + "", name, type, longitude + "", latitude + "" };
		return ret;
	}

	@Override
	public String toString() {
		return "LocationRecord [id=" + id + ", name=" + name + ", type=" + type + ", longitude=" + longitude
				+ ", latitude=" + latitude + "]";
	}

}
