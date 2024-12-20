package ua.kiev.prog.case0;


import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class MyDAO<T> {
    private final Connection connection;
    private final String tableName;

    public MyDAO(Connection connection, String tableName) {
        this.connection = connection;
        this.tableName = tableName;
    }

    private Field getPrimaryKeyField(T t, Field[] field) {
        Field result = null;

        for (Field f : field) {
            if (f.isAnnotationPresent(Id.class)) {
                result = f;
                result.setAccessible(true);
                break;
            }
        }

        if (result == null)
            throw new RuntimeException("Could not find primary key field");
        return result;

    }


    public void createTable(Class<?> cls) {
        Field[] fields = cls.getDeclaredFields();
        Field id = getPrimaryKeyField(null, fields);

        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE IF NOT EXISTS ").append(tableName).append("(");
        sql.append(id.getName()).append(" ").append(" INT AUTO_INCREMENT PRIMARY KEY");

        for (Field f: fields) {
            if(f != id){
                f.setAccessible(true);

                sql.append(f.getName()).append(" ");

                if (f.getType() == int.class) {
                    sql.append("INT,");
                }else if (f.getType() == String.class){
                    sql.append("VARCHAR(100),");
                }else
                    throw new RuntimeException("Unsupported type");
            }
        }

        sql.deleteCharAt(sql.length()-1);
        sql.append(")");

        try {
            try(Statement st = connection.createStatement()){
                st.execute(sql.toString());
            }}catch (Exception ex){
                throw  new RuntimeException(ex);
            }
        }
        public  void add (T t){
        try {
            Field[] fields = t.getClass().getDeclaredFields();
            Field id = getPrimaryKeyField(t, fields);

            StringBuilder names = new StringBuilder();
            StringBuilder values = new StringBuilder();

            for (Field f: fields) {
                if (f != id){
                    f.setAccessible(true);

                  names.append(f.getName()).append(',');
                values.append('"').append(f.get(t)).append("\",");}
        }
          names.deleteCharAt(names.length()-1);
            values.deleteCharAt(values.length()-1);

            String sql = "INSERT INTO " + tableName + "(" + names.toString() +
                    ") VALUES(" + values.toString() + ")";

        try(Statement st = connection.createStatement()){
            st.execute(sql);
        }

        } catch (Exception ex){
            throw new RuntimeException(ex);
        }


    }

    public void update (T t){
        try{
            Field [] fields = t.getClass().getDeclaredFields();
            Field id = getPrimaryKeyField(t, fields);

            StringBuilder sb = new StringBuilder();

            for (Field f: fields) {
                if (f != id){
                    f.setAccessible(true);

                    sb.append(f.getName()).append(" = ")
                            .append('"').append(f.get(t))
                            .append('"').append(',');
                }
            }
            sb.deleteCharAt(sb.length()-1);

            String sql = "UPDATE " + tableName + " SET " + sb.toString() + " WHERE " +
                    id.getName() + " = \"" + id.get(t) + "\"";

            try (Statement st = connection.createStatement()){
                st.execute(sql);
            }
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public List<T> getAll (Class<T> cls){
        List<T> res = new ArrayList<>();

        try {
            try (Statement st = connection.createStatement()){
                try (ResultSet rs = st.executeQuery("SELECT * FROM " + tableName)) {
                    ResultSetMetaData rsmd = rs.getMetaData();

                    while (rs.next()){
                        T t = cls.getDeclaredConstructor().newInstance();

                        for (int i = 1; i <= rsmd.getColumnCount(); i++){
                            String columnName = rsmd.getColumnName(i);
                            Field field = cls.getDeclaredField(columnName);
                            field.setAccessible(true);

                            field.set(t, rs.getObject(columnName));
                        }
                        res.add(t);
                    }
                }
            }
            return  res;
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public void delete (T t){
        try {
            Field[] fields = t.getClass().getDeclaredFields();
            Field id = getPrimaryKeyField(t, fields);

            String sql = "DELETE FROM " + tableName + " WHERE " + id.getName() +
                    " = \"" + id.get(t) + "\"";

                    try(Statement st = connection.createStatement()){
                        st.execute(sql);
                    }
        }catch (Exception exception){
            throw new RuntimeException(exception);
        }
    }


public void showApartmentByPrice(Scanner sc) throws SQLException{
        System.out.println("'>' or '<'?");

    String moreOrLess = sc.nextLine();
    if(!moreOrLess.equals(">") && !moreOrLess.equals("<")){
        System.out.println("Invalid input");
        return;
    }

    System.out.println("What price?");
    int price = sc.nextInt();
    sc.nextLine();



try ( PreparedStatement ps = connection.prepareStatement("SELECT * FROM Apartment WHERE price" + moreOrLess +" ? ")){

    ps.setInt(1, price);



try(ResultSet  rs = ps.executeQuery()) {

    ResultSetMetaData rsmd = rs.getMetaData();



        for (int i = 1; i <= rsmd.getColumnCount(); i++)
            System.out.println(rsmd.getColumnName(i) + "\t\t");
            System.out.println();

            while (rs.next()){
                for (int j = 1; j<=rsmd.getColumnCount(); j++){
                    System.out.print(rs.getString(j) + "\t\t");
                }
                System.out.println();
            }


    }
}
}}


