package maratonajava.jdbc.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.JdbcRowSet;

import maratonajava.jdbc.conn.ConnectionFactory;
import maratonajava.jdbc.dominio.Anime;
import maratonajava.jdbc.listener.CustomRowSetListener;

public class ProducerRepositoryRowSet {
	
	public static List<Anime> findByNameJdbcRowSet(String name){
		String sql = "select * from anime_store.producer where name like ?;";
		List<Anime> producers = new ArrayList<>();
		try(JdbcRowSet jrs = ConnectionFactory.getJdbcRowSet()){
			jrs.addRowSetListener(new CustomRowSetListener());
			jrs.setCommand(sql);
			jrs.setString(1, String.format("%%%s%%", name));
			jrs.execute();
			while(jrs.next()) {
				Anime producer = Anime.ProducerBuilder.builder().id(jrs.getInt("id")).name(jrs.getString("name")).build();
				producers.add(producer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return producers;
	}
	
	public static void updateJdbcRowSet(Anime producer){
		String sql = "select * from anime_store.producer WHERE (`id` = ?);";
		try(JdbcRowSet jrs = ConnectionFactory.getJdbcRowSet()){
			jrs.addRowSetListener(new CustomRowSetListener());
			jrs.setCommand(sql);
			jrs.setInt(1, producer.getId());
			jrs.execute();
			if(!jrs.next()) return;
			jrs.updateString("name", producer.getName());
			jrs.updateRow();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateCachedRowSet(Anime producer){
		String sql = "select * from producer WHERE (`id` = ?);";
		try(CachedRowSet crs = ConnectionFactory.getCachedRowSet();
			Connection connection = ConnectionFactory.getConnection()){
			connection.setAutoCommit(false);
//			crs.addRowSetListener(new CustomRowSetListener());
			crs.setCommand(sql);
			crs.setInt(1, producer.getId());
			crs.execute(connection);
			if(!crs.next()) return;
			crs.updateString("name", producer.getName());
			crs.updateRow();
//			TimeUnit.SECONDS.sleep(10); // fiz teste alterando o banco enquanto ele faria outra alteração
			crs.acceptChanges();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
