package maratonajava.jdbc.service;

import java.util.List;

import maratonajava.jdbc.dominio.Anime;
import maratonajava.jdbc.repository.ProducerRepository;

public class ProducerService {

	public static void save(Anime producer) {
		ProducerRepository.save(producer);
	}
	
	public static void delete(Integer id) {
		requireValidId(id);
		ProducerRepository.delete(id);
	}
	
	public static List<Anime> findAll() {
		return ProducerRepository.findAll();
	}
	
	public static void update(Anime producer) {
		requireValidId(producer.getId());
		ProducerRepository.update(producer);
	}
	
	public static List<Anime> findByName(String name) {
		return ProducerRepository.findByName(name);
	}
	
	public static void showProducerMetaData() {
		ProducerRepository.showProducerMetaData();
	}
	
	public static void showDivererMetaData() {
		ProducerRepository.showDivererMetaData();
	}
	
	public static void showTypeScrollWorking() {
		ProducerRepository.showTypeScrollWorking();
	}
	
	public static List<Anime> findByNameAndUpdateToUpperCase(String name) {
		return ProducerRepository.findByNameAndUpdateToUpperCase(name);
	}
	
	public static List<Anime> findByNameAndInsertWhenNotFound(String name) {
		return ProducerRepository.findByNameAndInsertWhenNotFound(name);
	}
	
	public static void findByNameAndDelete(String name) {
		ProducerRepository.findByNameAndDelete(name);
	}
	
	public static List<Anime> findByNamePreparedStatement(String name) {
		return ProducerRepository.findByNamePreparedStatement(name);
	}
	
	public static void updatePreparedStatement(Anime producer) {
		requireValidId(producer.getId());
		ProducerRepository.updatePreparedStatement(producer);
	}
	
	public static List<Anime> findByNameCallableStatement(String name) {
		return ProducerRepository.findByNameCallableStatement(name);
	}
	private static void requireValidId(Integer id) {
		if(id == null || id <= 0) {
			throw new IllegalArgumentException("Invalid value for id");
		}
	}
	
	public static void saveTransaction(List<Anime> producers) {
		ProducerRepository.saveTransaction(producers);
	}
}