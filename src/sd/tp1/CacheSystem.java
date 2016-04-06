package sd.tp1;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CacheSystem {

	public static final long MINUTE = 60000;

	private Map<String, byte[]> pictures;
	private Map<String, Long> lastAcess;

	public CacheSystem () {
		pictures=new HashMap<String, byte[]>();
		lastAcess=new HashMap<String, Long>();
	}

	public void addPicture(String name, byte[] data){
		pictures.put(name, data);
		lastAcess.put(name, System.currentTimeMillis());
	}

	public boolean isInCache (String name) {
		return pictures.containsKey(name);
	}

	public byte [] getData (String name) {
		lastAcess.put(name, System.currentTimeMillis());
		return pictures.get(name);
	}

	public void deletePicture (String name){
		pictures.remove(name);
		lastAcess.remove(name);
	}

	public void updateCache () {
		for(String key : lastAcess.keySet()){
			long time = lastAcess.get(key);
			if(System.currentTimeMillis()-time>MINUTE){
				deletePicture(key);
			}
		}	
	}
}
