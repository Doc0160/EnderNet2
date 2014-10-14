package pl.asie.endernet.api;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;

import com.google.gson.Gson;

import pl.asie.endernet.EnderNet;

/**
 * This class keeps track of EnderNet addresses on the map.
 * Please use it to register everything you need: items, tile entities, etc.
 * 
 * @author asie
 */
public class EnderNetRegistry {
	public static EnderNetRegistry instance;
	
	private final HashSet<Integer> addresses = new HashSet<Integer>();
	private final HashMap<Integer, Object> objects = new HashMap<Integer, Object>();
	private int maxAddress = 0;
	
	public EnderNetRegistry(File file) {
		new Gson().fromJson(new FileReader(file), // TODO
	}
	
	private void updateMA() {
		while(addresses.contains(maxAddress)) maxAddress++;
	}
	
	public int register(Object o) {
		updateMA();
		int address = maxAddress;
		addresses.add(address);
		objects.put(address, o);
		updateMA();
		return address;
	}
	
	public void unregister(int address) {
		addresses.remove(address);
		objects.remove(address);
		if(address < maxAddress) maxAddress = address;
	}
	
	public Object get(int address) {
		return objects.get(address);
	}
	
	public void set(int address, Object o) {
		objects.put(address, o);
		if(maxAddress == address)
			updateMA();
	}
}
