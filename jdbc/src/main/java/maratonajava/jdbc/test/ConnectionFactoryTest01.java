package maratonajava.jdbc.test;

import java.util.List;

import maratonajava.jdbc.dominio.Anime;
import maratonajava.jdbc.repository.ProducerRepository;
import maratonajava.jdbc.service.ProducerService;

public class ConnectionFactoryTest01 {

	public static void main(String[] args) {
		Anime producer = Anime.ProducerBuilder.builder().name("TokyoTv5").build();
		Anime producerToUpdate = Anime.ProducerBuilder.builder().id(1).name("madhouse").build();
		
//		ProducerService.save(producer);
		
//		ProducerService.delete(0);
		
//		ProducerService.update(producerToUpdate);
		
//		List<Producer> producers = ProducerService.findAll();
//		ProducerRepository.logger.info(producers);
		
//		List<Producer> producers = ProducerService.findByName("Tok");
//		ProducerRepository.logger.info(producers);
		
//		ProducerService.showProducerMetaData();
		
//		ProducerService.showDivererMetaData();
		
//		ProducerService.showTypeScrollWorking();
		
//		List<Producer> producers = ProducerService.findByNameAndUpdateToUpperCase("Tokyo");
//		ProducerRepository.logger.info(producers);
		
//		List<Producer> producers = ProducerService.findByNameAndInsertWhenNotFound("A-1 Pictures");
//		ProducerRepository.logger.info(producers);
		
//		ProducerService.findByNameAndDelete("Tokyotv");
		
//		List<Producer> producers = ProducerService.findByNamePreparedStatement("Bo");
//		ProducerRepository.logger.info(producers);
		
//		ProducerService.updatePreparedStatement(producerToUpdate);
		
		List<Anime> producers = ProducerService.findByNameCallableStatement("nhk");
		ProducerRepository.logger.info(producers);
		
	}

}
