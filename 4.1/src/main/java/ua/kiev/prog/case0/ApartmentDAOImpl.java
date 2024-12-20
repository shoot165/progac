package ua.kiev.prog.case0;

import java.sql.Connection;

public class ApartmentDAOImpl extends MyDAO<Apartment>{
    public ApartmentDAOImpl(Connection conn, String tableName) {
        super(conn, tableName);
    }
}
