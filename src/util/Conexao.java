package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {
	public static Connection conectar() throws Exception {
		//definindo o drive de conex�o com o mysql
		Class.forName("com.mysql.cj.jdbc.Driver");
		//definindo o parametros de conexao
		return DriverManager.getConnection(
				"jdbc:mysql://localhost/senac","root","");
	}
}
