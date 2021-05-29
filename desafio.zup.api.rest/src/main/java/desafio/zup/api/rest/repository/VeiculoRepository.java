package desafio.zup.api.rest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import desafio.zup.api.rest.model.Veiculo;

@Repository
public interface VeiculoRepository extends CrudRepository<Veiculo, Long>{
}