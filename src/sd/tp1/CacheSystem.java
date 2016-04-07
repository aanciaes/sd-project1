package sd.tp1;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Implements a basic cache system
 * @author miguel
 *
 */
public class CacheSystem {

	public static final long MINUTE = 60000;

	//Storing pictures
	private Map<String, byte[]> pictures;

	//Storing the last Access to pictures
	private Map<String, Long> lastAcess;

	public CacheSystem () {
		pictures=new ConcurrentHashMap<String, byte[]>();
		lastAcess=new ConcurrentHashMap<String, Long>();
	}

	/**
	 * Adds one picture to cache
	 * @param name Name of the picture
	 * @param data byte array with data
	 */
	public void addPicture(String name, byte[] data){
		pictures.put(name, data);
		lastAcess.put(name, System.currentTimeMillis());
	}

	/**
	 * Checks if a picture is in cache
	 * @param name Name of the picture
	 * @return true if the picture is in cache
	 */
	public boolean isInCache (String name) {
		return pictures.containsKey(name);
	}

	/**
	 * Access one picture data and updates last access to that picture
	 * @param name Name of the picture to access
	 * @return byte array with data
	 */
	public byte [] getData (String name) {
		lastAcess.put(name, System.currentTimeMillis());
		return pictures.get(name);
	}

	/**
	 * Deletes one picture from cache
	 * @param name
	 */
	public void deletePicture (String name){
		pictures.remove(name);
		lastAcess.remove(name);
	}

	/**
	 * Updates the cache system deleting all pictures that were not accessed in the last minute
	 */
	public void updateCache () {
		for(String key : lastAcess.keySet()){
			long time = lastAcess.get(key);
			if(System.currentTimeMillis()-time>MINUTE){
				deletePicture(key);
			}
		}	
	}
}
