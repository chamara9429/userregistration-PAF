package com.userregister;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class user {
	private Connection connect()
	{
		Connection con = null;
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");



			//Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/userdb", "root","");
		}
		catch (Exception e)
		{e.printStackTrace();}
		return con;
	} 



	//Read user
	public String getUser() {
		
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error during connecting to the database";
			}
			// Prepare the HTML table to be displayed
			output = "<table  class='table table-dark table-striped'>"
					+ "<tr>"
					+ "<th>id</th>"
					+ "<th>name</th>"
					+ "<th>email</th>"
					+ "<th>contact</th>" 
					+ "<th>type</th>"
					+ "<th>Update</th>"
					+ "<th>Delete</th></tr>";

			// create a prepared statement
			String query = " select * from usertb";


			PreparedStatement preparedStmt = con.prepareStatement(query);
			ResultSet rs = preparedStmt.executeQuery();

			while(rs.next()) {
				
				int id=rs.getInt("id") ;
				String name=rs.getString("name");
				String email=rs.getString("email");
				String contact=rs.getString("contact");
				String type=rs.getString("type");
				

				// Add a row into the HTML table
				output += "<tr><td>" + id + "</td>";
				output += "<td>" + name + "</td>";
				output += "<td>" + email + "</td>"; 
				output += "<td>" + contact + "</td>";
				output += "<td>" + type + "</td>";
				

				// buttons
				output += "<td><input name='btnUpdate' id='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary' data-id='" + id + "'></td>"
						+"<td><input name='btnRemove' id='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-id='" + id + "'></td></tr>";
			}

			con.close();

			// Complete the HTML table
			output += "</table>";
		}
		catch (Exception e)
		{
			output = "Error during reading the user.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	
	//insert user
	public JsonObject insertuser( String name, String email,String contact,String type)
	{
		JsonObject result = null;
		try
		{
			Connection con = connect();

			if (con == null)
			{
				result = new JsonObject();
				result.addProperty("status", "error");
				result.addProperty("data", "Error during connecting to the database for inserting.");
				return result;
			}

			// create a prepared statement
			String query = " insert into usertb"+
					"(`name`,`email`,`contact`,`type`)"
					+ " values (?, ?, ?, ?)";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, name);
			preparedStmt.setString(2, email);
			preparedStmt.setString(3, contact);
			preparedStmt.setString(4, type);
			// execute the statement

			preparedStmt.execute();
			con.close();
			result = new JsonObject();
			result.addProperty("status", "successful");
			result.addProperty("data", getUser());
		}
		catch (Exception e)
		{
			result = new JsonObject();
			result.addProperty("status", "exception");
			result.addProperty("data", "exception happened during inserting user.");
			System.err.println(e.getMessage());
		}
		return result;
	}

	//update user
	public JsonObject updateuser( int id, String name, String email,String contact,String type)
	{
		JsonObject result = null;
		try
		{
			Connection con = connect();

			if (con == null)
			{
				result = new JsonObject();
				result.addProperty("status", "error");
				result.addProperty("data", "Error during connecting to the database for updating.");
				return result;
			}

			// create a prepared statement
			String query = "UPDATE usertb SET name=?,email=?,contact=?,type=? WHERE id=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, name);
			preparedStmt.setString(2, email);
			preparedStmt.setString(3, contact);
			preparedStmt.setString(4, type);
			preparedStmt.setInt(5, id);
			// execute the statement
			int sts = preparedStmt.executeUpdate();
			con.close();
			result = new JsonObject();
			if(sts > 0) {
				result.addProperty("status", "successful");
				result.addProperty("data", getUser());
			}else {
				result.addProperty("status", "failed");
				result.addProperty("data", getUser());
			}

		}
		catch (Exception e)
		{
			result = new JsonObject();
			result.addProperty("status", "exception");
			result.addProperty("data", "exception happened during updating User.");
			System.err.println(e.getMessage());
		}
		return result;
	}

	//delete user
	public JsonObject deleteuser( int id )
	{
		JsonObject result = null;
		try
		{
			Connection con = connect();

			if (con == null)
			{
				result = new JsonObject();
				result.addProperty("status", "error");
				result.addProperty("data", "Error during connecting to the database for Removing.");
				return result;
			}

			// create a prepared statement
			String query = "delete from usertb where id=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, id);

			int sts = preparedStmt.executeUpdate();
			con.close();
			result = new JsonObject();
			if(sts > 0) {
			    
				result.addProperty("status", "successful");
				result.addProperty("data", getUser());

			}else {
			    
				result.addProperty("status", "failed");
				result.addProperty("data", getUser());

			}
		}
		catch (Exception e)
		{
			result = new JsonObject();
		    
			result.addProperty("status", "exception");
			result.addProperty("data", "exception happened during Removing User.");

			System.err.println(e.getMessage());
		}
		return result;
	}

}
