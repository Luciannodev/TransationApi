package br.com.ludevsp.dataprovider;

import br.com.ludevsp.domain.entities.Category;
import br.com.ludevsp.domain.enums.CategoryEnum;
import br.com.ludevsp.domain.intefaces.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DataSeederTest {

    @InjectMocks
    private DataSeeder dataSeeder;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSeedDataCategory() {
        when(categoryRepository.count()).thenReturn(0L);
        when(categoryRepository.findByCategoryCode(anyInt())).thenReturn(null);
        when(categoryRepository.save(any(Category.class))).thenReturn(new Category());

        dataSeeder.seedDataCategory();

        int totalMccCodes = Arrays.stream(CategoryEnum.values())
                .mapToInt(category -> category.getMccCodes().size())
                .sum();

        verify(categoryRepository, times(totalMccCodes)).save(any(Category.class));
    }
}