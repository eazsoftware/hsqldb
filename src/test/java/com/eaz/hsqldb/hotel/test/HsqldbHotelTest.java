package com.eaz.hsqldb.hotel.test;

import com.eaz.hsqldb.hotel.HsqldbHotel;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author javier
 */
public class HsqldbHotelTest {
    
    public HsqldbHotelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void hsqlHotelTest() throws SQLException{
        try {
            HsqldbHotel hsqldbHotel 
                    = new HsqldbHotel("hotel",
                                      "sa",
                                      "");
            //hsqldbHotel.createDB();
            hsqldbHotel.update("INSERT INTO \"com_eaz_software_hsqldb_Guest\" (ID, \"name_\") values ("+hsqldbHotel.nextRandomBigInteger()+", 'Pepito')");
            hsqldbHotel.update("INSERT INTO \"com_eaz_software_hsqldb_Guest\" (ID, \"name_\") values ("+hsqldbHotel.nextRandomBigInteger()+", 'Juanito')");
            hsqldbHotel.query("SELECT * FROM public.\"com_eaz_software_hsqldb_Guest\";");
            hsqldbHotel.shutdown();
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
        
    }
    
}
