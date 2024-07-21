package br.com.ludevsp.domain.intefaces.repository;

import br.com.ludevsp.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
    Category findByCategoryCode(int mmc);
    ArrayList<Category> findByName(String food);
}
