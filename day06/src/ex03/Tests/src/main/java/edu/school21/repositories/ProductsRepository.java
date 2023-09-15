package edu.school21.repositories;

import edu.school21.exceptions.NotDeletedSubEntityException;
import edu.school21.exceptions.NotSavedSubEntityException;
import edu.school21.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductsRepository {

    List<Product> findAll();

    Optional<Product> findById(Long id);

    void update(Product product) throws NotSavedSubEntityException;

    void save(Product product) throws NotSavedSubEntityException ;

    void delete(Long id) throws NotDeletedSubEntityException;
}
