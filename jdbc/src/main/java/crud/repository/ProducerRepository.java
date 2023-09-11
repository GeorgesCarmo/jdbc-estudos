package crud.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import maratonajava.jdbc.conn.ConnectionFactory;
import maratonajava.jdbc.dominio.Anime;
import maratonajava.jdbc.logger.Logger;

public class ProducerRepository {

	public static List<Anime> findByName(String name) {
		Logger.loggerCustom.info("Finding producers by name '{}'", name);
		List<Anime> producers = new ArrayList<>();
		try (Connection conn = ConnectionFactory.getConnection();
				PreparedStatement ps = createPreparedStatementFindByName(conn, name);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				Anime producer = Anime.ProducerBuilder.builder().id(rs.getInt("id")).name(rs.getString("name"))
						.build();
				producers.add(producer);
			}
		} catch (SQLException e) {
			Logger.loggerCustom.error("Error while trying to find all producers.", e);
		}
		return producers;
	}

	private static PreparedStatement createPreparedStatementFindByName(Connection connection, String name)
			throws SQLException {
		String sql = "select * from anime_store.producer where name like ?;";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, String.format("%%%s%%", name));
		return ps;
	}

	public static Optional<crud.dominio.Producer> findById(Integer id) {
		Logger.loggerCustom.info("Finding producers by id '{}'", id);
		try (Connection conn = ConnectionFactory.getConnection();
				PreparedStatement ps = createPreparedStatementFindById(conn, id);
				ResultSet rs = ps.executeQuery()) {
			if (!rs.next()) return Optional.empty();
			return Optional.of(crud.dominio.Producer.ProducerBuilder.builder().id(rs.getInt("id")).name(rs.getString("name")).build());
		} catch (SQLException e) {
			Logger.loggerCustom.error("Error while trying to find id.", e);
		}
		return Optional.empty();

	}

	private static PreparedStatement createPreparedStatementFindById(Connection connection, Integer id)
			throws SQLException {
		String sql = "select * from anime_store.producer where id = ?;";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setInt(1, id);
		return ps;
	}

	public static void delete(int id) {
		try (Connection conn = ConnectionFactory.getConnection();
				PreparedStatement ps = createPreparedStatementDelete(conn, id)) {
			ps.execute();
			Logger.loggerCustom.info("Deleted producer '{}' from the database", id);
		} catch (SQLException e) {
			Logger.loggerCustom.error("Error while trying to delete producer '{}'", id, e);
		}
	}

	private static PreparedStatement createPreparedStatementDelete(Connection connection, Integer id)
			throws SQLException {
		String sql = "DELETE FROM `anime_store`.`producer` WHERE (`id` = ?);";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setInt(1, id);
		return ps;
	}

	public static void save(crud.dominio.Producer producer) {
		Logger.loggerCustom.info("Saving producer '{}'", producer);
		try (Connection conn = ConnectionFactory.getConnection();
			PreparedStatement ps = createPreparedStatementSave(conn, producer)) {
			ps.execute();
		} catch (SQLException e) {
			Logger.loggerCustom.error("Error while trying to save producer '{}'", producer.getId(), e);
		}
	}

	private static PreparedStatement createPreparedStatementSave(Connection connection, crud.dominio.Producer producer)
			throws SQLException {
		String sql = "INSERT INTO `anime_store`.`producer` (`name`) VALUES (?);";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, producer.getName());
		return ps;
	}
	
	public static void update(crud.dominio.Producer producer) {
		Logger.loggerCustom.info("Updated producer '{}'", producer);
		try(Connection conn = ConnectionFactory.getConnection(); 
			PreparedStatement ps = createPreparedStatementUpdate(conn, producer)){
			ps.execute();	
		} catch (SQLException e) {
			Logger.loggerCustom.error("Error while trying to update producer '{}'",producer.getId(),e);
		}
	}
		
	private static PreparedStatement createPreparedStatementUpdate(Connection connection, crud.dominio.Producer producer) throws SQLException {
		String sql = "UPDATE `anime_store`.`producer` SET `name` = ? WHERE (`id` = ?);";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, producer.getName());
		ps.setInt(2, producer.getId());
		return ps;
	}
}
