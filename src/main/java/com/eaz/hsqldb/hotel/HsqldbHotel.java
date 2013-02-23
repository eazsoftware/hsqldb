package com.eaz.hsqldb.hotel;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import org.hsqldb.Server;
import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;



/**
 *
 * @author javier
 */
public final class HsqldbHotel 
{
    public static String URL_SCRIPT_CREATION_DB = "/dbscript/com_eazsoftware_hsqldb_hotel.sql";
    public static String URL_SCRIPT_DATA_DB = "/dbscript/DATA_com_eazsoftware_hsqldb_hotel.sql";
    
    private Connection connection = null;
    private Server hsqlServer = null;

    public HsqldbHotel( String strTypeHSQLDB,
                        String strDbName,
                        String strUserDb,
                        String strPwdDb) throws IOException, SQLException, InterruptedException, SqlToolError {
        this.startHSQLDBServer(strTypeHSQLDB, strDbName);
        this.establishDBConnection(strTypeHSQLDB, strDbName, strUserDb, strPwdDb);
        
        URL urlLocationScript = this.getClass().getResource(HsqldbHotel.URL_SCRIPT_CREATION_DB);        
        this.executeFile(urlLocationScript.getFile());
        urlLocationScript = this.getClass().getResource(HsqldbHotel.URL_SCRIPT_DATA_DB);
        this.executeFile(urlLocationScript.getFile());
//        this.createTableDB(strDbName);
//        this.createDataDB(strDbName);
        this.query("SELECT * FROM public.\"com_eaz_software_hsqldb_Guest\";");
        this.query("SELECT * FROM public.\"com_eaz_software_hsqldb_Guest\" WHERE ;");
    }
    
    private void startHSQLDBServer( String strTypeHSQLDB,
                                    String strDbName) {    
        hsqlServer = new Server();
        hsqlServer.setLogWriter(new PrintWriter(System.out));
        hsqlServer.setSilent(false);
        hsqlServer.setDatabaseName(0, strTypeHSQLDB + ":" + strDbName);
        
        hsqlServer.start(); 
        System.out.println("\n\t*** HSQLServer Started [" + strTypeHSQLDB + ":" + strDbName + "]");
    }
    
    private void establishDBConnection( String strTypeHSQLDB,
                                        String strDbName,
                                        String strUserDb,
                                        String strPwdDb) {
        // making a connection
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            connection = DriverManager.getConnection("jdbc:hsqldb:" + strTypeHSQLDB + ":" + strDbName + ";shutdown=true;hsqldb.write_delay_millis=0", strUserDb, strPwdDb);
            connection.setAutoCommit(true);
            
            System.out.println("\n\t*** HSQLDB Connection established [jdbc:hsqldb:" + strTypeHSQLDB + ":" + strDbName + ";shutdown=true;hsqldb.write_delay_millis=0]");
        } catch (SQLException sqle) {
            sqle.printStackTrace(System.out);
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace(System.out);
        }     
    }
    
//    public synchronized void createTableDB(String strDbName) throws IOException, SQLException, SqlTool.SqlToolException {        
//
//        URL urlLocationScript = this.getClass().getResource(HsqldbHotel.URL_SCRIPT_CREATION_DB);
//        //http://stackoverflow.com/questions/573679/open-resource-with-relative-path-in-java
//        SqlTool.objectMain( new String[] { strDbName,
//                                           urlLocationScript.getFile()} );
//        connection.commit();
//        //http://stackoverflow.com/questions/2295457/running-a-script-to-create-tables-with-hsqldb 
//        //http://stackoverflow.com/questions/6364171/hsql-question-on-data-being-saved
//        //http://www.d.umn.edu/~tcolburn/cs4531/hsqldb_docs/guide/ch07.html
//
//        System.out.println("\n\t*** Tables created ["+strDbName+"] ["+urlLocationScript.getFile()+"]");        
//    }  
    
//    public synchronized void createDataDB(String strDbName) throws IOException, SQLException, SqlTool.SqlToolException {        
//        URL urlLocationScript = this.getClass().getResource(HsqldbHotel.URL_SCRIPT_DATA_DB);
//        //http://stackoverflow.com/questions/573679/open-resource-with-relative-path-in-java
//        SqlTool.objectMain( new String[] { strDbName,
//                                           urlLocationScript.getFile()} ); 
//        connection.commit();
//        
//        System.out.println("\n\t*** Data created ["+strDbName+"] ["+urlLocationScript.getFile()+"]");         
//    }    
    
    public synchronized void executeFile(String strURLFile)
            throws IOException, SQLException, SqlToolError {
        File file = new File(strURLFile);
        if (!file.isFile()) {
            throw new IOException("SQL file not present: "
                    + file.getAbsolutePath());
        }
        
        SqlFile sqlFile = new SqlFile(file);
        sqlFile.setConnection(connection);
        sqlFile.execute();
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
    
    public int shutdown() throws SQLException {
        Statement statement = connection.createStatement();    // statements
        statement.execute("SHUTDOWN");     
        
        connection.close();    // if there are no other open connection        
        
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