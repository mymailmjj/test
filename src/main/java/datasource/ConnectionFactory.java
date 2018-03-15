package datasource;

import java.sql.Connection;

public interface ConnectionFactory {
    
    public Connection createConnection();

}
