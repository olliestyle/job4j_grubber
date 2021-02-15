package ru.baib.store;

import ru.baib.model.Post;

import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Для выполнения запроса PreparedStatement имеет три метода:
 * 1. boolean execute(): выполняет любую SQL-команду
 * 2. ResultSet executeQuery(): выполняет команду SELECT,
 *    которая возвращает данные в виде ResultSet
 * 3. int executeUpdate(): выполняет такие SQL-команды,
 *    как INSERT, UPDATE, DELETE, CREATE и возвращает количество измененных строк
 */

public class PsqlStore implements Store, AutoCloseable {

    private Connection con;

    public PsqlStore(Properties cfg) {
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
            con = DriverManager.getConnection(
                    cfg.getProperty("url"),
                    cfg.getProperty("username"),
                    cfg.getProperty("password")
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try (InputStream in = PsqlStore.class.getClassLoader().getResourceAsStream("grabber.properties")) {
        Properties prop = new Properties();
        prop.load(in);
        PsqlStore psqlStore = new PsqlStore(prop);
        Post postToAdd = new Post();
        postToAdd.setName("java dev");
        postToAdd.setDescription("testesttest");
        postToAdd.setLink("https://testtest4.test");
        postToAdd.setCreationDate(LocalDateTime.now());
        psqlStore.save(postToAdd);
          List<Post> posts = psqlStore.getAll();
          for (Post post: posts) {
              System.out.println(post.getId() + " ; " + post.getName() + " ; " + post.getDescription() + " ; "
                      + post.getLink() + " ; " + post.getCreationDate());
          }
          Post postToFind = psqlStore.findById("2");
          System.out.println(postToFind);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
        if (con != null) {
            con.close();
        }
    }

    @Override
    public void save(Post post) {
        String sqlSave = "insert into post(name, description, link, created) values (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sqlSave, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setString(3, post.getLink());
            ps.setObject(4, post.getCreationDate());
            ps.execute();
        } catch (SQLException s) {
            s.printStackTrace();
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> result = new ArrayList<>();
        String sqlGetAll = "select * from post";
        try (PreparedStatement ps = con.prepareStatement(sqlGetAll)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Post post = new Post(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("link"),
                        rs.getString("description"),
                        rs.getTimestamp("created").toLocalDateTime()
                );
                result.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Post findById(String id) {
        Post postToFind = null;
        String sqlFindById = "select * from post where id = ?";
        try (PreparedStatement ps = con.prepareStatement(sqlFindById)) {
            ps.setInt(1, Integer.parseInt(id));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                postToFind = new Post();
                postToFind.setId(rs.getInt("id"));
                postToFind.setName(rs.getString("name"));
                postToFind.setDescription(rs.getString("description"));
                postToFind.setLink(rs.getString("link"));
                postToFind.setCreationDate(rs.getTimestamp("created").toLocalDateTime());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return postToFind;
    }
}
