package pl.asie.endernet.api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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
	class RegistryInformation {
		HashSet<Integer> addresses;
		int maxAddress;
		
		public void init() {
			addresses = new HashSet<Integer>();
			maxAddress = 0;
		}
		
		public void updateAddresses() {
			if(addresses.contains(maxAddress)) {
				// New address added, look up
				while(addresses.contains(maxAddress)) maxAddress++;
			} else {
				// Old address removed, look down
				while(!addresses.contains(maxAddress)) maxAddress--;
				maxAddress++;
			}
		}
	}
	
	public static EnderNetRegistry instance;
	
	private RegistryInformation r;
	private final HashMap<Integer, Object> objects = new HashMap<Integer, Object>();
	private final File file;
	
	public EnderNetRegistry(File file) {
		this.file = file;
		try {
			r = (RegistryInformation)(EnderNet.gson.fromJson(new FileReader(file), RegistryInformation.class));
		} catch(FileNotFoundException e) {
			r = new RegistryInformation();
			r.init();
		}
	}
	
	public int register(Object o) {
		r.updateAddresses();
		int address = r.maxAddress;
		r.addresses.add(address);
		objects.put(address, o);
		r.updateAddresses();
		return address;
	}
	
	public void unregister(int address) {
		r.addresses.remove(address);
		objects.remove(address);
		r.updateAddresses();
	}
	
	public Object get(int address) {
		return objects.get(address);
	}
	
	public void set(int address, Object o) {
		objects.put(address, o);
		r.updateAddresses();
	}
	
	public void save() {
		try {
			EnderNet.gson.toJson(r, new FileWriter(file));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
