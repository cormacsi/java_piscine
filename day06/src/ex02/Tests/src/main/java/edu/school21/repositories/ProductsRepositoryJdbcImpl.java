package edu.school21.repositories;

import edu.school21.exceptions.NotDeletedSubEntityException;
import edu.school21.exceptions.NotSavedSubEntityException;
import edu.school21.models.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {

    private final DataSource dataSource;

    public ProductsRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM product";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                Product product = new Product(resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price"));
                if (resultSet.wasNull()) product.setPrice(null);
                products.add(product);
            }
        } catch (SQLException e) {
            System.err.println("SQLException in findAll method of ProductsRepository");
        }
        return products;
    }

    @Override
    public Optional<Product> findById(Long id) {
        Product product = null;
        if (id != null) {
            String query = "SELECT * FROM product WHERE id = ?";
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, id);
                statement.execute();
                ResultSet resultSet = statement.getResultSet();
                if (resultSet.next()) {
                    product = new Product(resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getDouble("price"));
                    if (resultSet.wasNull()) product.setPrice(null);
                }
            } catch (SQLException e) {
                System.err.println("SQLException in findById method of ProductsRepository");
            }
        }
        return Optional.ofNullable(product);
    }

    @Override
    public void update(Product product) throws NotSavedSubEntityException {
        Long id = product.getId();
        if (!findById(id).isPresent()) {
            throw new NotSavedSubEntityException(String.format("Update is canceled: User with id=%d is not found", id));
        }
        String query = "UPDATE product SET name = ?, price = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, product.getName());
            if (product.getPrice() == null) {
                statement.setNull(2, Types.DOUBLE);
            } else {
                statement.setDouble(2, product.getPrice());
            }
            statement.setLong(3, id);
            statement.execute();
        } catch (SQLException e) {
            System.err.println("SQLException in update method of ProductsRepository");
        }
    }

    @Override
    public void save(Product product) throws NotSavedSubEntityException {
        Long id = product.getId();
        if (findById(id).isPresent()) {
            throw new NotSavedSubEntityException(String.format("Saving is canceled: User with id=%d already exists", id));
        }
        String query = product.getId() == null ?
                "INSERT INTO product (name, price) VALUES (?, ?)" :
                "INSERT INTO product (id, name, price) VALUES (?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            int index = 1;
            if (id != null) {
                statement.setLong(index++, id);
            }
            statement.setString(index++, product.getName());
            if (product.getPrice() == null) {
                statement.setNull(index, Types.DOUBLE);
            } else {
                statement.setDouble(index, product.getPrice());
            }
            statement.execute();

            if (id == null) {
                ResultSet keys = statement.getGeneratedKeys();
                keys.next();
                product.setId(keys.getLong("id"));
            }
        } catch (SQLException e) {
            System.err.println("SQLException in save method of ProductsRepository");
        }
    }

    @Override
    public void delete(Long id) throws NotDeletedSubEntityException {
        if (!findById(id).isPresent()) {
            throw new NotDeletedSubEntityException(String.format("Saving is canceled: User with id=%d already exists", id));
        }
        String query = "DELETE FROM product WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            System.err.println("SQLException in delete method of ProductsRepository");
        }
    }
}
