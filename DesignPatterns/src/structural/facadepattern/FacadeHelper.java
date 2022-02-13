package structural.facadepattern;

public class FacadeHelper {
	
	public static void generateReport(String dbType,String reportType,String tableName) {
		Connection con = null;
		
		switch (dbType) {
		case "Oracle":
			con = OracleHelper.getOracleConnection();
			OracleHelper oHelper = new OracleHelper();
			switch (reportType) {
			case "HTML":
				oHelper.generateHTMLReport(tableName, con);
				break;
			case "PDF":
				oHelper.generatePDFReport(tableName, con);
				break;
			}
			break;
		
		case "MySQL":
			con = MysqlHelper.getOracleConnection();
			MysqlHelper mHelper = new MysqlHelper();
			switch (reportType) {
			case "HTML":
				mHelper.generateHTMLReport(tableName, con);
				break;
			case "PDF":
				mHelper.generatePDFReport(tableName, con);
				break;
			}
			break;
		}
	}

}
