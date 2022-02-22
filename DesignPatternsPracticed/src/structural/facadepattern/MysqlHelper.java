package structural.facadepattern;

public class MysqlHelper {
	
	public static Connection getOracleConnection() {
		return new Connection("MySQL");
	}
	
	public void generateHTMLReport(String tableName,Connection con) {
		System.out.println("HTML report generated for table "+tableName);
	}
	
	public void generatePDFReport(String tableName,Connection con) {
		System.out.println("PDF report generated for table "+tableName);
	}

}
