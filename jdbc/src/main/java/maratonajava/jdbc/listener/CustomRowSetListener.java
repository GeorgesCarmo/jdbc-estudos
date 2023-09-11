package maratonajava.jdbc.listener;

import java.sql.SQLException;

import javax.sql.RowSet;
import javax.sql.RowSetEvent;
import javax.sql.RowSetListener;

import maratonajava.jdbc.logger.Logger;

public class CustomRowSetListener implements RowSetListener {

	@Override
	public void rowSetChanged(RowSetEvent event) {
		Logger.loggerCustom.info("Command execute triggered");
		
	}

	@Override
	public void rowChanged(RowSetEvent event) {
		Logger.loggerCustom.info("Row inserted, updated or deleted");
		if(event.getSource() instanceof RowSet) {
			try {
				((RowSet) event.getSource()).execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void cursorMoved(RowSetEvent event) {
		Logger.loggerCustom.info("Cursor moved");
		
	}	
}
