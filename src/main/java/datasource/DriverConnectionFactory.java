package datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

public class DriverConnectionFactory implements ConnectionFactory {

    private String connectionUrl;

    private String userName;

    private String password;

    public DriverConnectionFactory(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }

    public DriverConnectionFactory(String connectionUrl, Properties properties) {
        this.connectionUrl = connectionUrl;
        this.properties = properties;
    }

    public DriverConnectionFactory(String connectionUrl, String userName, String password) {
        this.connectionUrl = connectionUrl;
        this.userName = userName;
        this.password = password;
    }

    private Properties properties;

    static {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection createConnection() {
        Connection connection = null;
        try {
            if (properties != null) {
                String username = (String) properties.get("username");
                String password = properties.getProperty("password");
                this.userName = username;
                this.password = password;
            }
            if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password)) {
                connection = DriverManager.getConnection(connectionUrl, this.userName, this.password);
            } else {
                connection = DriverManager.getConnection(connectionUrl);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

}
