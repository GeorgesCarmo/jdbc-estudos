package crud.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import crud.dominio.Producer;
import maratonajava.jdbc.conn.ConnectionFactory;
import maratonajava.jdbc.dominio.Anime;
import maratonajava.jdbc.logger.Logger;

public class AnimeRepository {

	public static List<crud.dominio.Anime> findByName(String name) {
		Logger.loggerCustom.info("Finding anime by name '{}'", name);
		List<crud.dominio.Anime> animes = new ArrayList<>();
		try (Connection conn = ConnectionFactory.getConnection();
				PreparedStatement ps = createPreparedStatementFindByName(conn, name);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				Producer producer = Producer.ProducerBuilder.builder()
						.name(rs.getString("producer_name"))
						.id(rs.getInt("producer_id"))
						.build();
				crud.dominio.Anime anime = crud.dominio.Anime.AnimeBuilder.builder()
						.id(rs.getInt("id"))
						.name(rs.getString("name"))
						.episodes(rs.getInt("episodes"))
						.producer(producer)
						.build();
				animes.add(anime);
			}
		} catch (SQLException e) {
			Logger.loggerCustom.error("Error while trying to find all animes.", e);
		}
		return animes;
	}

	private static PreparedStatement createPreparedStatementFindByName(Connection connection, String name)
			throws SQLException {
		String sql = """
				select a.id, a.name, a.episodes, a.producer_id, p.name as 'producer_name' from anime_store.anime a inner join anime_store.producer p 
				on a.producer_id = p.id where a.name like ?;
				""";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, String.format("%%%s%%", name));
		return ps;
	}

	public static Optional<crud.dominio.Anime> findById(Integer id) {
		Logger.loggerCustom.info("Finding anime by id '{}'", id);
		try (Connection conn = ConnectionFactory.getConnection();
				PreparedStatement ps = createPreparedStatementFindById(conn, id);
				ResultSet rs = ps.executeQuery()) {
			if (!rs.next()) return Optional.empty();
			Producer producer = Producer.ProducerBuilder.builder()
					.name(rs.getString("producer_name"))
					.id(rs.getInt("producer_id"))
					.build();
			crud.dominio.Anime anime = crud.dominio.Anime.AnimeBuilder.builder()
					.id(rs.getInt("id"))
					.name(rs.getString("name"))
					.episodes(rs.getInt("episodes"))
					.producer(producer)
					.build();
			return Optional.of(anime);
		} catch (SQLException e) {
			Logger.loggerCustom.error("Error while trying to find id.", e);
		}
		return Optional.empty();

	}

	private static PreparedStatement createPreparedStatementFindById(Connection connection, Integer id)
			throws SQLException {
		String sql = """
				select a.id, a.name, a.episodes, a.producer_id, p.name as 'producer_name' from anime_store.anime a inner join anime_store.producer p 
				on a.producer_id = p.id where a.id = ?;
				""";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setInt(1, id);
		return ps;
	}

	public static void delete(int id) {
		try (Connection conn = ConnectionFactory.getConnection();
				PreparedStatement ps = createPreparedStatementDelete(conn, id)) {
			ps.execute();
			Logger.loggerCustom.info("Deleted anime '{}' from the database", id);
		} catch (SQLException e) {
			Logger.loggerCustom.error("Error while trying to delete anime '{}'", id, e);
		}
	}

	private static PreparedStatement createPreparedStatementDelete(Connection connection, Integer id)
			throws SQLException {
		String sql = "DELETE FROM `anime_store`.`anime` WHERE (`id` = ?);";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setInt(1, id);
		return ps;
	}

	public static void save(crud.dominio.Anime anime) {
		Logger.loggerCustom.info("Saving anime '{}'", anime);
		try (Connection conn = ConnectionFactory.getConnection();
			PreparedStatement ps = createPreparedStatementSave(conn, anime)) {
			ps.execute();
		} catch (SQLException e) {
			Logger.loggerCustom.error("Error while trying to save anime '{}'", anime.getId(), e);
		}
	}

	private static PreparedStatement createPreparedStatementSave(Connection connection, crud.dominio.Anime anime)
			throws SQLException {
		String sql = "INSERT INTO `anime_store`.`anime` (`name`,`episodes`,`producer_id`) VALUES (?,?,?);";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, anime.getName());
		ps.setInt(2, anime.getEpisodes());
		ps.setInt(3, anime.getProducer().getId());
		return ps;
	}
	
	public static void update(crud.dominio.Anime anime) {
		Logger.loggerCustom.info("Updated anime '{}'", anime);
		try(Connection conn = ConnectionFactory.getConnection(); 
			PreparedStatement ps = createPreparedStatementUpdate(conn, anime)){
			ps.execute();	
		} catch (SQLException e) {
			Logger.loggerCustom.error("Error while trying to update producer '{}'",anime.getId(),e);
		}
	}
		
	private static PreparedStatement createPreparedStatementUpdate(Connection connection, crud.dominio.Anime anime) throws SQLException {
		String sql = "UPDATE `anime_store`.`anime` SET `name` = ?, `episodes` = ? WHERE (`id` = ?);";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, anime.getName());
		ps.setInt(2, anime.getEpisodes());
		ps.setInt(3, anime.getId());
		return ps;
	}
}
