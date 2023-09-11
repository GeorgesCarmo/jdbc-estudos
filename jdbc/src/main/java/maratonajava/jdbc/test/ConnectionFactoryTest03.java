package maratonajava.jdbc.test;

import java.util.List;

import maratonajava.jdbc.dominio.Anime;
import maratonajava.jdbc.service.ProducerService;

public class ConnectionFactoryTest03 {

	public static void main(String[] args) {
		Anime producer1 = Anime.ProducerBuilder.builder().name("Toei Animation").build();
		Anime producer2 = Anime.ProducerBuilder.builder().name("White Fox").build();
		Anime producer3 = Anime.ProducerBuilder.builder().name("Studio Ghibli").build();
		
		ProducerService.saveTransaction(List.of(producer1, producer2, producer3));

	}

}
