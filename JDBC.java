import java.sql.*;
import java.io.*;
import java.util.*;

class JDBC {
    
    public static void main(String args[]) throws IOException
        {
            String url;
            Connection conn;
            PreparedStatement pStatement;
            ResultSet rs;
            String queryString;

            try {
                Class.forName("org.postgresql.Driver");
            }
            catch (ClassNotFoundException e) {
                System.out.println("Failed to find the JDBC driver");
            }
            try
            {
                url = "jdbc:postgresql://localhost:5432/csc343h-nizhison";
                conn = DriverManager.getConnection(url, "nizhison", "");

		
		queryString = "select count(name) from guesses";
		PreparedStatement ps1 = conn.prepareStatement(queryString);
		rs = ps1.executeQuery();
		int count = 0;		

		while (rs.next()) {
			count = rs.getInt("count");
		}
		String[] storeName = new String[count];
		
		
                queryString = "select name from guesses";
                pStatement = conn.prepareStatement(queryString);
                rs = pStatement.executeQuery();

                // Iterate through the result set and report on each tuple.
		int i = 0;
                while (rs.next()) {
                    String name = rs.getString("name");
               	    storeName[i] = name;
		    i++;
                }
		System.out.println(Arrays.toString(storeName));
                
                // The next query depends on user input, so we are wise to
                // prepare it before inserting the user input.
                queryString = "select avg(guess) from guesses where age >= ?";
                PreparedStatement ps = conn.prepareStatement(queryString);

                //Prompt the user for an age, read the age, 
		//and then print the average guess among those from people who are at least that age.                 
		BufferedReader br = new BufferedReader(new 
                InputStreamReader(System.in));
                System.out.println("What age?");
                int age = Integer.parseInt(br.readLine());

                // Insert that string into the PreparedStatement and execute it.
                ps.setInt(1, age);
                rs = ps.executeQuery();

                while (rs.next()) {
                    int guess = rs.getInt("avg");
                    System.out.println("age >= " + age + " on avg guessed " + guess);
                }
                
            }
            catch (SQLException se)
            {
                System.err.println("SQL Exception." +
                        "<Message>: " + se.getMessage());
            }

        }
        
}
