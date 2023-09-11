package crud.service;

import java.util.Optional;
import java.util.Scanner;

import crud.dominio.Anime;
import crud.dominio.Producer;
import crud.repository.AnimeRepository;
import crud.repository.ProducerRepository;

public class AnimeService {
	
	private static final Scanner SCANNER = new Scanner(System.in);
	
	public static void buildMenu(int op) {
		switch(op) {
		case 1 -> findByName();
		case 2 -> delete();
		case 3 -> save();
		case 4 -> update();
		default -> throw new IllegalArgumentException("Not a valid option");
		}
	}
	
	private static void findByName() {
		System.out.println("Type the name or empty to all");
		String name = SCANNER.nextLine();
		AnimeRepository.findByName(name).forEach(p -> System.out.printf("[%d] - %s %d %s%n", p.getId(), p.getName(),p.getEpisodes(),p.getProducer().getName()));
	}
	
	private static void delete() {
		System.out.println("Type the id of the anime you want to delete");
		int id = Integer.parseInt(SCANNER.nextLine());
		System.out.println("Are you sure? Y/N");
		String choice = SCANNER.next();
		if("y".equalsIgnoreCase(choice)){
			AnimeRepository.delete(id);
		}
	}
	
	private static void save() {
		System.out.println("Type the name of the anime");
		String name = SCANNER.nextLine();
		System.out.println("Type the number of the episodes");
		int episodes = Integer.parseInt(SCANNER.nextLine());
		System.out.println("Type the id of the producer");
		Integer producerId = Integer.parseInt(SCANNER.nextLine());
		
		Anime anime = Anime.AnimeBuilder.builder()
				.name(name)
				.episodes(episodes)
				.producer(Producer.ProducerBuilder.builder().id(producerId).build())
				.build();
		AnimeRepository.save(anime);
	}
	
	private static void update() {
		System.out.println("Type the id of the object you want to update");
		Optional<Anime> animeOptional = AnimeRepository.findById(Integer.parseInt(SCANNER.nextLine()));
		if(animeOptional.isEmpty()) {
			System.out.println("Anime not found");
			return;
		}
		Anime animeFromDB = animeOptional.get();
		System.out.println("Anime found: "+animeFromDB);
		System.out.println("Type the new name or enter to keep the same");
		String name = SCANNER.nextLine();
		name = name.isEmpty() ? animeFromDB.getName() : name;
		
		System.out.println("Type the new number of episodes");
		int episodes = Integer.parseInt(SCANNER.nextLine());
		
		Anime animeToUpdate = Anime.AnimeBuilder.builder()
				.id(animeFromDB.getId())
				.episodes(episodes)
				.producer(animeFromDB.getProducer())
				.name(name)
				.build();
		AnimeRepository.update(animeToUpdate);
	}
}
