package crud.dominio;

import java.util.Objects;

public class Anime {

	Integer id;
	String name;
	int episodes;
	Producer producer;
	
	public static final class AnimeBuilder{
		Integer id;
		String name;
		int episodes;
		Producer producer;
		
		private AnimeBuilder() {}
		
		public static AnimeBuilder builder() {
			return new AnimeBuilder();
		}
		
		public AnimeBuilder id(Integer id) {
			this.id = id;
			return this;
		}
		
		public AnimeBuilder name(String name) {
			this.name = name;
			return this;
		}
		
		public AnimeBuilder episodes(int episodes) {
			this.episodes = episodes;
			return this;
		}
		
		public AnimeBuilder producer(Producer producer) {
			this.producer = producer;
			return this;
		}
		
		public Anime build() {
			Anime anime = new Anime();
			anime.id = id;
			anime.name = name;
			anime.episodes = episodes;
			anime.producer = producer;
			return anime;
			
		}
	}
	
	public Integer getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public int getEpisodes() {
		return episodes;
	}
	
	public Producer getProducer() {
		return producer;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(episodes, id, name, producer);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Anime other = (Anime) obj;
		return episodes == other.episodes && Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(producer, other.producer);
	}
	@Override
	public String toString() {
		return "Anime [id=" + id + ", name=" + name + ", episodes=" + episodes + ", producer=" + producer + "]";
	}
	
	
}
