package com.classcircle.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.DriverManager;

import com.mysql.jdbc.Connection;

public class DBHelper {
	private static final String driver = "com.mysql.jdbc.Driver";
	private static final String url = "jdbc:mysql://127.0.0.1:3306/classcircle?characterEncoding=utf8&useSSL=true";
	private static final String user = "root";
	private static final String password = "root";
	private static Connection conn = null;

	public static Connection getConnection() {
		try {
			Class.forName(driver);
			conn = (Connection) DriverManager.getConnection(url, user, password);
			if (!conn.isClosed())
				System.out.println("Succeeded connecting to the DB");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return conn;
	}

	public static void backup() {
		try {
			String databaseName = "classcircle";
			Runtime rt = Runtime.getRuntime();
			Process child = rt.exec(
					"F:\\MySQL\\mysql-5.7.16-winx64\\bin\\mysql.exe -hlocalhost -uroot -p --default-character-set=utf8 " + databaseName);
			InputStream in = child.getInputStream();
			InputStreamReader isr = new InputStreamReader(in, "utf-8");

			String inStr;
			StringBuffer sb = new StringBuffer("");
			String outStr;
			BufferedReader br = new BufferedReader(isr);
			while ((inStr = br.readLine()) != null) {
				sb.append(inStr + "\r\n");
			}
			outStr = sb.toString();

			FileOutputStream fos = new FileOutputStream(new File("F:\\Eclipse WorkSpace\\ClassCircle\\DB\\backup.sql"));
			OutputStreamWriter osw = new OutputStreamWriter(fos, "utf-8");
			osw.write(outStr);
			osw.flush();
			in.close();
			isr.close();
			br.close();
			osw.close();
			fos.close();
			System.out.println("success");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
