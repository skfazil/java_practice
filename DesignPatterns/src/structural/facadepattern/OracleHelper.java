package structural.facadepattern;

public class OracleHelper {
	
	public static Connection getOracleConnection() {
		return new Connection("Oracle");
	}
	
	public void generateHTMLReport(String tableName,Connection con) {
		System.out.println("HTML report generated for table "+tableName);
	}
	
	public void generatePDFReport(String tableName,Connection con) {
		System.out.println("PDF report generated for table "+tableName);
	}

}
