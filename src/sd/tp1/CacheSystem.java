package sd.tp1;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import sd.tp1.gui.GalleryContentProvider.Album;

public class CacheSystem {
	
	private long lastAccess;
	private SecureRandom random;
	Map<Album, List<cachePicture>> albuns;
	
	public CacheSystem () {
		//
		//SecureRandom random = new SecureRandom();
		albuns = new HashMap<>();
		//hash=nextSessionId();
	}
	
	public void setAlbumList (List<Album> list) {
		for(int i=0;i<list.size();i++)
			albuns.put(list.get(i), new ArrayList<cachePicture>());
		System.out.println(albuns.keySet().toString());
	}
	
	public List<Album> getAlbuns (){
		List<Album> lst = new ArrayList<Album>();
		Iterator<Album> t = albuns.keySet().iterator();
		while(t.hasNext())
			lst.add(t.next());

		return lst;
	}
	
	public void setPicturesList (String album, List<String> pictures){
		List<cachePicture> lst = albuns.get(album);
		for(int i=0;i<pictures.size();i++)
			lst.add(new cachePicture(pictures.get(i)));
	}
	
	/*public void setHash () {
		this.hash=nextSessionId();
	}*/
	
	/*public String nextSessionId() {
	    return new BigInteger(130, random).toString(32);
	}*/
}

class cachePicture{
	String name;
	byte [] data;
	
	public cachePicture (String name){
		this.name=name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setData (byte [] data) {
		this.data=data;
	}
	
	public byte [] getData () {
		return data;
	}
}
