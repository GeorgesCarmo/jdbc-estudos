package maratonajava.jdbc.test;

import java.util.List;

import maratonajava.jdbc.dominio.Anime;
import maratonajava.jdbc.repository.ProducerRepository;
import maratonajava.jdbc.service.ProducerServiceRowSet;

public class ConnectionFactoryTest02 {

	public static void main(String[] args) {
		Anime producerToUpdate = Anime.ProducerBuilder.builder().id(1).name("MAD3").build();
		
//		ProducerServiceRowSet.updateJdbcRowSet(producerToUpdate);
		
//		List<Producer> producers = ProducerServiceRowSet.findByNameJdbcRowSet("");
//		ProducerRepository.logger.info(producers);
	
		ProducerServiceRowSet.updateCachedRowSet(producerToUpdate);
		
		
		
		
		
		
		
		
		
		
		
	}
}
