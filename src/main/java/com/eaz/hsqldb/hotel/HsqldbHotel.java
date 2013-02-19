package com.eaz.hsqldb.hotel;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Random;
import org.hsqldb.Server;
import org.hsqldb.util.SqlFile;
import org.hsqldb.util.SqlToolError;

/**
 *
 * @author javier
 */
public class HsqldbHotel 
{
    private Connection connection = null;
    private Server hsqlServer = null;

    public HsqldbHotel( String strDbName,
                        String strUserDb,
                        String strPwdDb) {
        hsqlServer = new Server();
        hsqlServer.setLogWriter(new PrintWriter(System.out));
        hsqlServer.setSilent(false);
        hsqlServer.setDatabaseName(0, strDbName);
        hsqlServer.setDatabasePath(0, "file:" + strDbName);
        
        hsqlServer.start();

        // making a connection
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/" + strDbName, strUserDb, strPwdDb);
            connection.setAutoCommit(true);
        } catch (SQLException sqle) {
            sqle.printStackTrace(System.out);
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace(System.out);
        }   
    }
    
    public synchronized void createDB() throws IOException, SQLException, SqlToolError {
        File file = new File("/home/javier/projects/HSQLDB/src/main/resources/dbscript/com_eazsoftware_hsqldb_hotel.sql");
        SqlFile sqlFile = new SqlFile(file, true, new HashMap());

        sqlFile.execute(connection, true);
        //http://stackoverflow.com/questions/2295457/running-a-script-to-create-tables-with-hsqldb 
        //http://stackoverflow.com/questions/6364171/hsql-question-on-data-being-saved
    
    }    
    
    public synchronized void query(String expression) throws SQLException {
        Statement st = null;
        ResultSet rs = null;

        st = connection.createStatement();         // statement objects can be reused with

        // repeated calls to execute but we choose to make a new one each time
        rs = st.executeQuery(expression);    // run the query

        // do something with the result set.
        dump(rs);
        st.close();    /* NOTE!! if you close a statement the associated ResultSet is
                          closed too so you should copy the contents to some other object.
                          the result set is invalidated also  if you recycle an Statement
                          and try to execute some other query before the result set has been
                          completely examined. */
    }

    //use for SQL commands CREATE, DROP, INSERT and UPDATE
    public synchronized void update(String expression) throws SQLException {
        Statement st = null;

        st = connection.createStatement();    // statements

        int i = st.executeUpdate(expression);    // run the query
        if (i == -1) {
            System.out.println("db error : " + expression);
        }

        st.close();
    }

    public static void dump(ResultSet rs) throws SQLException {
        // the order of the rows in a cursor
        // are implementation dependent unless you use the SQL ORDER statement
        ResultSetMetaData meta   = rs.getMetaData();
        int               colmax = meta.getColumnCount();
        int               i;
        Object            o = null;

        // the result set is a cursor into the data.  You can only
        // point to one row at a time
        // assume we are pointing to BEFORE the first row
        // rs.next() points to next row and returns true
        // or false if there is no next row, which breaks the loop
        for (; rs.next(); ) {
            for (i = 0; i < colmax; ++i) {
                o = rs.getObject(i + 1);    // Is SQL the first column is indexed
                // with 1 not 0
                System.out.print(o.toString() + " ");
            }

            System.out.println(" ");
        }
    }    
    
    public int shutdown() {
        int iStatusServerStop = hsqlServer.stop();
        hsqlServer = null;
        
        return iStatusServerStop;
    }
    
    public BigInteger nextRandomBigInteger() { 
        BigInteger n = BigInteger.valueOf(Long.MAX_VALUE);
        
        Random rand = new Random();
        BigInteger result = new BigInteger(n.bitLength(), rand);
        while( result.compareTo(n) >= 0 ) {
            result = new BigInteger(n.bitLength(), rand);
        }
        return result;
    }    
    
}