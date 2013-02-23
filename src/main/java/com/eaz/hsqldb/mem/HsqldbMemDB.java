package com.eaz.hsqldb.mem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author javier
 */
public class HsqldbMemDB {
    protected Connection conn;
    
    public HsqldbMemDB(String strDBName) throws Exception {
        Class.forName("org.hsqldb.jdbcDriver");
        conn = DriverManager.getConnection("jdbc:hsqldb:mem"
                                           + strDBName,
                                           "sa",
                                           "");
    }

    public void shutdown() throws SQLException {

        Statement st = conn.createStatement();
        st.execute("SHUTDOWN");
        conn.close();
    }

    public synchronized void query(String strExpression) throws SQLException {
        Statement st = conn.createStatement();

        ResultSet rs = st.executeQuery(strExpression);    // run the query

        dump(rs);
        st.close();
    }

    public synchronized void update(String strExpression) throws SQLException {
        Statement st = conn.createStatement();

        int i = st.executeUpdate(strExpression);

        if (i == -1) {
            System.out.println("db error : " + strExpression);
        }

        st.close();
    }

    public static void dump(ResultSet rs) throws SQLException {
        ResultSetMetaData meta   = rs.getMetaData();
        int               colmax = meta.getColumnCount();
        int               i;
        Object            o = null;

        for (; rs.next(); ) {
            for (i = 0; i < colmax; ++i) {
                o = rs.getObject(i + 1);    // Is SQL the first column is indexed
                // with 1 not 0
                System.out.print(o.toString() + " ");
            }

            System.out.println(" ");
        }
    }
    
}    // class Testdb