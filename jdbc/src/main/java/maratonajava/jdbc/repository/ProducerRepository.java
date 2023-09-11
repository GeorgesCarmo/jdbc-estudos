package maratonajava.jdbc.repository;
import org.apache.logging.log4j.*;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.log.Log;

import lombok.extern.log4j.Log4j2;
import maratonajava.jdbc.conn.ConnectionFactory;
import maratonajava.jdbc.dominio.Anime;

@Log4j2
public class ProducerRepository {
	
	public static Logger logger = LogManager.getLogger(ProducerRepository.class);
	
	public static void save(Anime producer) {
		String sql = "INSERT INTO `anime_store`.`producer` (`name`) VALUES ('%s');".formatted(producer.getName());
		try(Connection conn = ConnectionFactory.getConnection(); Statement stmt = conn.createStatement()){
			int rowsAffected = stmt.executeUpdate(sql);

			logger.info("Insert producer '{}' in the database, rows affected '{}'",producer.getName(),rowsAffected);

			
		} catch (SQLException e) {
			logger.error("Error while trying to insert producer '{}'",producer.getName(),e);
		}
	}
	
	public static void delete(int id) {
		String sql = "DELETE FROM `anime_store`.`producer` WHERE (`id` = '%d');".formatted(id);
		try(Connection conn = ConnectionFactory.getConnection(); Statement stmt = conn.createStatement()){
			int rowsAffected = stmt.executeUpdate(sql);

			logger.info("Deleted producer '{}' from the database, rows affected '{}'",id,rowsAffected);

			
		} catch (SQLException e) {
			logger.error("Error while trying to delete producer '{}'",id,e);
		}
	}
	
	public static void update(Anime producer) {
		String sql = "UPDATE `anime_store`.`producer` SET `name` = '%s' WHERE (`id` = '%d');".formatted(producer.getName(), producer.getId());
		try(Connection conn = ConnectionFactory.getConnection(); Statement stmt = conn.createStatement()){
			int rowsAffected = stmt.executeUpdate(sql);
			logger.info("Updated producer '{}', rows affected '{}'",producer.getId(),rowsAffected);
		} catch (SQLException e) {
			logger.error("Error while trying to delete producer '{}'",producer.getId(),e);
		}
	}
	
	public static List<Anime> findAll() {
		logger.info("Finding all producers");
		return findByName("");
	}
	
	public static List<Anime> findByName(String ProducerName) {
		logger.info("Finding producers by name");
		String sql = "select * from anime_store.producer where name like '%%%s%%';".formatted(ProducerName);
		List<Anime> producers = new ArrayList<>();
		try(Connection conn = ConnectionFactory.getConnection(); Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){
			while(rs.next()) {
				var id = rs.getInt("id");
				var name = rs.getString("name");
				Anime producer = Anime.ProducerBuilder.builder().id(id).name(name).build();
				producers.add(producer);
			}
		} catch (SQLException e) {
			logger.error("Error while trying to find all producers.",e);
		}
		return producers;
	}
	
	public static void showProducerMetaData() {
		logger.info("Showing producer metadata");
		String sql = "select * from anime_store.producer;";
		try(Connection conn = ConnectionFactory.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql)){
			ResultSetMetaData rsMetaData = rs.getMetaData();
			int columnCount = rsMetaData.getColumnCount();
			logger.info("Columns count '{}'", columnCount);
			for (int i = 1; i <= columnCount; i++) {
				logger.info("Table name '{}'",rsMetaData.getTableName(i));
				logger.info("Column name '{}'",rsMetaData.getColumnName(i));
				logger.info("Column size '{}'",rsMetaData.getColumnDisplaySize(i));
				logger.info("Column type '{}'",rsMetaData.getColumnTypeName(i));
			}
			
		} catch (SQLException e) {
			logger.error("Error while trying to find all producers.",e);
		}
	}
	
	public static void showDivererMetaData() {
		logger.info("Showing driver metadata");
		try(Connection conn = ConnectionFactory.getConnection()){
			
			DatabaseMetaData dbMetaData = conn.getMetaData();
			
			if(dbMetaData.supportsResultSetType(ResultSet.TYPE_FORWARD_ONLY)) {
				logger.info("Supports TYPE_FORWARD_ONLY");
				if(dbMetaData.supportsResultSetConcurrency(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE)) {
					logger.info("and supports CONCUR_UPDATABLE");
				}
			}
			
			if(dbMetaData.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE)) {
				logger.info("Supports TYPE_SCROLL_INSENSITIVE");
				if(dbMetaData.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
					logger.info("and supports CONCUR_UPDATABLE");
				}
			}
			
			if(dbMetaData.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE)) {
				logger.info("Supports TYPE_SCROLL_SENSITIVE");
				if(dbMetaData.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
					logger.info("and supports CONCUR_UPDATABLE");
				}
			}
		} catch (SQLException e) {
			logger.error("Error while trying to find all producers.",e);
		}
	}
	
	public static void showTypeScrollWorking() {
		String sql = "select * from anime_store.producer;";
		try(Connection conn = ConnectionFactory.getConnection();
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stmt.executeQuery(sql)){

			logger.info("Last row? '{}'",rs.last());
			logger.info("Row number? '{}'",rs.getRow());
			logger.info(Anime.ProducerBuilder.builder().id(rs.getInt("id")).name(rs.getString("name")).build());
			
			logger.info("First row? '{}'",rs.first());
			logger.info("Row number? '{}'",rs.getRow());
			logger.info(Anime.ProducerBuilder.builder().id(rs.getInt("id")).name(rs.getString("name")).build());
			
			logger.info("Row absolute? '{}'",rs.absolute(2));
			logger.info("Row number? '{}'",rs.getRow());
			logger.info(Anime.ProducerBuilder.builder().id(rs.getInt("id")).name(rs.getString("name")).build());
			
			logger.info("Row relative? '{}'",rs.relative(-1));
			logger.info("Row number? '{}'",rs.getRow());
			logger.info(Anime.ProducerBuilder.builder().id(rs.getInt("id")).name(rs.getString("name")).build());
			
			logger.info("Is last? '{}'",rs.isLast());
			logger.info("Row number? '{}'",rs.getRow());
			
			logger.info("Is first? '{}'",rs.isFirst());
			logger.info("Row number? '{}'",rs.getRow());
			
			logger.info("Last row? '{}'", rs.last());
			logger.info("------------------------");
			rs.next();
			
			logger.info("After last row? '{}'", rs.isAfterLast());
			
			while(rs.previous()) {
				logger.info(Anime.ProducerBuilder.builder().id(rs.getInt("id")).name(rs.getString("name")).build());
			}
			
		} catch (SQLException e) {
			logger.error("Error while trying to find all producers.",e);
		}
	}
	
	public static List<Anime> findByNameAndUpdateToUpperCase(String ProducerName) {
		logger.info("Finding producers by name");
		String sql = "select * from anime_store.producer where name like '%%%s%%';".formatted(ProducerName);
		List<Anime> producers = new ArrayList<>();
		try(Connection conn = ConnectionFactory.getConnection();
				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				ResultSet rs = stmt.executeQuery(sql)){
			while(rs.next()) {
				rs.updateString("name", rs.getString("name").toUpperCase());
				rs.updateRow();
				Anime producer = Anime.ProducerBuilder.builder().id(rs.getInt("id")).name(rs.getString("name")).build();
				producers.add(producer);
			}
		} catch (SQLException e) {
			logger.error("Error while trying to find all producers.",e);
		}
		return producers;
	}
	
	public static List<Anime> findByNameAndInsertWhenNotFound(String name) {
		logger.info("Finding producers by name");
		String sql = "select * from anime_store.producer where name like '%%%s%%';".formatted(name);
		List<Anime> producers = new ArrayList<>();
		try(Connection conn = ConnectionFactory.getConnection();
				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				ResultSet rs = stmt.executeQuery(sql)){
			if(rs.next()) return producers;
			
				rs.moveToInsertRow();
				rs.updateString("name", name);
				rs.insertRow();
				rs.beforeFirst();
				rs.next();
				Anime producer = Anime.ProducerBuilder.builder().id(rs.getInt("id")).name(rs.getString("name")).build();
				producers.add(producer);
			
			
		} catch (SQLException e) {
			logger.error("Error while trying to find all producers.",e);
		}
		return producers;
	}
	
	public static List<Anime> findByNameAndDelete(String name) {
		logger.info("Deleting producers by name");
		String sql = "select * from anime_store.producer where name like '%%%s%%';".formatted(name);
		List<Anime> producers = new ArrayList<>();
		try(Connection conn = ConnectionFactory.getConnection();
				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				ResultSet rs = stmt.executeQuery(sql)){
			while(rs.next()) {
				logger.info("Deleting '{}'", rs.getString("name"));
				rs.deleteRow();
			}
			
		} catch (SQLException e) {
			logger.error("Error while trying to find all producers.",e);
		}
		return producers;
	}
	
	public static List<Anime> findByNamePreparedStatement(String name) {
		logger.info("Finding producers by name");
		List<Anime> producers = new ArrayList<>();
		try(Connection conn = ConnectionFactory.getConnection(); 
			PreparedStatement ps = preparedStatementFindByName(conn, name);
			ResultSet rs = ps.executeQuery()){
			while(rs.next()) {
				Anime producer = Anime.ProducerBuilder.builder().id(rs.getInt("id")).name(rs.getString("name")).build();
				producers.add(producer);
			}
		} catch (SQLException e) {
			logger.error("Error while trying to find all producers.",e);
		}
		return producers;
	}
	
	private static PreparedStatement preparedStatementFindByName(Connection connection, String name) throws SQLException {
		String sql = "select * from anime_store.producer where name like ?;";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, String.format("%%%s%%", name));
		return ps;
	}
	
	public static void updatePreparedStatement(Anime producer) {
		try(Connection conn = ConnectionFactory.getConnection(); 
			PreparedStatement ps = preparedStatementUpdate(conn, producer)){
			int rowsAffected = ps.executeUpdate();
			logger.info("Updated producer '{}', rows affected '{}'",producer.getId(),rowsAffected);
			
		} catch (SQLException e) {
			logger.error("Error while trying to update producer '{}'",producer.getId(),e);
		}
	}
		
	private static PreparedStatement preparedStatementUpdate(Connection connection, Anime producer) throws SQLException {
		String sql = "UPDATE `anime_store`.`producer` SET `name` = ? WHERE (`id` = ?);";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, producer.getName());
		ps.setInt(2, producer.getId());
		return ps;
	}
	
	public static List<Anime> findByNameCallableStatement(String name) {
		logger.info("Finding producers by name");
		List<Anime> producers = new ArrayList<>();
		try(Connection conn = ConnectionFactory.getConnection(); 
			PreparedStatement ps = callableStatementFindByName(conn, name);
			ResultSet rs = ps.executeQuery()){
			while(rs.next()) {
				Anime producer = Anime.ProducerBuilder.builder().id(rs.getInt("id")).name(rs.getString("name")).build();
				producers.add(producer);
			}
		} catch (SQLException e) {
			logger.error("Error while trying to find all producers.",e);
		}
		return producers;
	}
	
	private static PreparedStatement callableStatementFindByName(Connection connection, String name) throws SQLException {
		String sql = "CALL `anime_store`.`sp_get_producer_by_name`(?);";
		CallableStatement cs = connection.prepareCall(sql);
		cs.setString(1, String.format("%%%s%%", name));
		return cs;
	}
	
	public static void saveTransaction(List<Anime> producers) {
		try(Connection conn = ConnectionFactory.getConnection()){
			conn.setAutoCommit(false);
			preparedStatementSaveTransaction(conn, producers);
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			logger.error("Error while trying to save producer '{}'",producers, e);
		}
	}
		
	private static void preparedStatementSaveTransaction(Connection connection, List<Anime> producers) throws SQLException {
		String sql = "insert into `anime_store`.`producer` (`name`) values (?);";
		boolean shouldRollback = false;
		for (Anime p : producers) {
			try(PreparedStatement ps = connection.prepareStatement(sql)) {
				logger.info("Saving producer '{}'",p.getName());
				ps.setString(1, p.getName());
//				if(p.getName().equals("White Fox")) throw new SQLException("Can't save white fox");
				ps.execute();
			}catch (SQLException e) {
				e.printStackTrace();
				shouldRollback = true;
			}
		}
		if(shouldRollback) {
			logger.warn("Transaction is going be rollback");
			connection.rollback();
		}
	}
	
	
}
