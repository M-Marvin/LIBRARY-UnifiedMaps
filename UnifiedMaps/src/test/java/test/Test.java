package test;

import de.m_marvin.unimap.UniMap;
import de.m_marvin.unimap.types.BiMap;

public class Test {
	
	public static void main(String... args) {
		
		BiMap<Object, Object> map = UniMap.LINKED.newBiMap();
		
		map.put("Key1", "Val1");
		map.put("Key1", "Val1");
		map.put("Key2", "Val2");
		map.put("Key3", "Val2");
		map.put("Key4", "Val3");
		map.put("Key4", "Val4");
		
//		map.map.entrySet().forEach(System.out::println);
		
		map.remove("Key4");
		
		map.entrySet().forEach(e -> {
			System.out.println(e.getKey() + " " + e.getValue());

			System.out.println(map.getKeys(e.getValue()) + " " + map.get(e.getKey()));
			System.out.println(map.getKeys(e.getValue()) + " " + map.get(e.getKey()));
			System.out.println(map.getKey(e.getValue()) + " " + map.get(e.getKey()));
		});

		map.getKeys("Val2").add("TEST");
		
		map.entrySet().forEach(e -> {
			System.out.println(e.getKey() + " " + e.getValue());
		});
	}
	
}
