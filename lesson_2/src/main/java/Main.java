import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class Main {
	static Connection connection;
	static Statement stmt;

	public static void main(String[] args) {

		try {
			connect();
			//createTable();
			//clearTable();
			//fillTable();

			try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
				while (true) {
					String str = br.readLine();
					ResultSet rs;
					if (str.startsWith("/price ")) {
						try (PreparedStatement psGetByCost = connection.prepareStatement("SELECT * FROM goods WHERE title = ?;")){
							try {
								String name = str.substring(6).trim();
								psGetByCost.setString(1, name);
								System.out.println(psGetByCost.executeQuery().getInt(4));
							} catch (SQLException e) {
								e.printStackTrace();
								System.out.println("Wrong name");
							}
						}
					} else if (str.startsWith("/change_price ")) {
						try (PreparedStatement psChangeCost = connection.prepareStatement("UPDATE goods SET cost = ? WHERE title = ?;")) {
							str = str.substring(14);
							String[] arr = str.split(" ");
							psChangeCost.setInt(1, Integer.parseInt(arr[1]));
							psChangeCost.setString(2, arr[0]);
							psChangeCost.executeUpdate();
						}
					} else if (str.startsWith("/price_range ")) {
						try (PreparedStatement psGoodsByCostRange = connection.prepareStatement("SELECT title, cost FROM goods WHERE cost > ? AND cost < ?;")) {
							str = str.substring(13);
							String[] arr = str.split(" ");
							psGoodsByCostRange.setInt(1, Integer.parseInt(arr[0]));
							psGoodsByCostRange.setInt(2, Integer.parseInt(arr[1]));
							rs = psGoodsByCostRange.executeQuery();
							while(rs.next()){
								System.out.println(rs.getString(1) + "\t" + rs.getInt(2));
							}
						}
					} else if (str.equals("/end")) {
						break;
					} else {
						System.out.println("Wrong input");
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		disconnect();
	}

	public static void fillTable() throws SQLException {
		connection.setAutoCommit(false);
		for (int i = 1; i <= 10000; i++) {
			stmt.executeUpdate("INSERT INTO goods (prodid, title, cost) VALUES " +
							"('id_товара " + i + "', " +
							"'товар" + i + "', " +
							(i*10) + ")");
		}
		connection.commit();
	}

	public static void createTable() throws SQLException {
		stmt.executeUpdate("CREATE TABLE IF NOT EXISTS goods (" +
						"    id 		INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
						"    prodid	TEXT UNIQUE,\n" +
						"    title	TEXT,\n" +
						"    cost		INTEGER\n" +
						");");
	}

	public static void clearTable() throws SQLException {
		stmt.executeUpdate("DELETE FROM goods;");
	}

	public static void select() throws SQLException {
		ResultSet rs = stmt.executeQuery("SELECT * FROM goods;");
		while (rs.next()){
			System.out.println(rs.getInt(1) +" "+ rs.getString(2) + " "
							+ rs.getString(3) + " " + rs.getInt(4));
		}
	}

	public static void connect() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		connection = DriverManager.getConnection("jdbc:sqlite:main.db");
		stmt = connection.createStatement();
		/*psGetByCost = connection.prepareStatement("SELECT * FROM goods WHERE title = ?;");
		psChangeCost = connection.prepareStatement("UPDATE goods SET cost = ? WHERE title = ?;");
		psGoodsByCostRange = connection.prepareStatement("SELECT title, cost FROM goods WHERE cost > ? AND cost < ?;");*/
	}

	public static void disconnect() {
		try {
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
