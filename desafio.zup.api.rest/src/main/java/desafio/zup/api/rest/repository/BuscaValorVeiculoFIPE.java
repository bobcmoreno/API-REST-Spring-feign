package desafio.zup.api.rest.repository;

import org.springframework.cloud.openfeign.FeignClient;
/* import org.springframework.cloud.netflix.feign.FeignClient; */
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import desafio.zup.api.rest.dto.UserResponse;
/*import desafio.zup.api.rest.model.ValorVeiculoFIPE;*/

@FeignClient(url="https://parallelum.com.br/fipe/api/v1/carros/marcas/" , name = "API-FIPE") 
public interface BuscaValorVeiculoFIPE {
	
	@GetMapping(value = "{marca}/modelos/{modelo}/anos/{ano}-{tipocombustivel}", produces = "application/json")
	public UserResponse buscaValorFipe(	@PathVariable("marca")           String marca, 
								 		@PathVariable("modelo")          String modelo,
								 		@PathVariable("ano")             String ano,
								 		@PathVariable("tipocombustivel") String tipocombustivel ) ;
}
/* fallback = ErroHttp404.class */