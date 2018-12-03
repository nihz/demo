import javax.persistence.Table;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class JdbcTest {


    public static void main(String[] args) {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.1.5:3306", "debian-sys-maint", "SrwlktF91o0REqh6");

            PreparedStatement preparedStatement = connection.prepareStatement("select * from a");

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getInt("id"));
            }
        } catch (Exception e) {

        }

    }

    private static List<?> select(Object condition) {

        try {
            Class entityClass = condition.getClass();

            Table table = (Table) entityClass.getAnnotation(Table.class);

            String sql = "select * from ";
            StringBuffer sb = new StringBuffer("where 1 = 1");

            Field[] fields = entityClass.getDeclaredFields();
            for (Field field : fields) {
                Object value = field.get(condition);
                if (value != null) {
                    sb.append("and ").append(field.getName()).append(" = '").append(value).append("'");
                }
            }


        } catch (Exception e) {

        }

        return null;
    }

}
