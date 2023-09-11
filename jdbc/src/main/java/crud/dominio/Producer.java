package crud.dominio;

import java.util.Objects;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

public class Producer {
	Integer id;
	String name;
	
	public static final class ProducerBuilder {
		Integer id;
		String name;
		
		private ProducerBuilder() {
			
		}
		
		public static ProducerBuilder builder() {
			return new ProducerBuilder();
		}
		
		public ProducerBuilder id(Integer id) {
			this.id = id;
			return this;
		}
		
		public ProducerBuilder name(String name) {
			this.name = name;
			return this;
		}
		
		public Producer build() {
			Producer producer = new Producer();
			producer.id = this.id;
			producer.name = this.name;
			return producer;
		}
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Producer other = (Producer) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Producer [id=" + id + ", name=" + name + "]";
	}
		
	
	
}
