package maratonajava.jdbc.service;

import java.util.List;

import maratonajava.jdbc.dominio.Anime;
import maratonajava.jdbc.repository.ProducerRepositoryRowSet;

public class ProducerServiceRowSet {
	
	public static List<Anime> findByNameJdbcRowSet(String name){
		return ProducerRepositoryRowSet.findByNameJdbcRowSet(name);
	}
	
	public static void updateJdbcRowSet(Anime producer) {
		ProducerRepositoryRowSet.updateJdbcRowSet(producer);
	}
	
	public static void updateCachedRowSet(Anime producer) {
		ProducerRepositoryRowSet.updateCachedRowSet(producer);
	}
}
