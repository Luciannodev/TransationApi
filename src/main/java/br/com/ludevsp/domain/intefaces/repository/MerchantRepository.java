package br.com.ludevsp.domain.intefaces.repository;


import br.com.ludevsp.domain.entities.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long>{
    Merchant findByName(String name);
}
