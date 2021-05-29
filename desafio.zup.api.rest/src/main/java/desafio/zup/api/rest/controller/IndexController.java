package desafio.zup.api.rest.controller;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import desafio.zup.api.rest.dto.UserResponse;
import desafio.zup.api.rest.funcions.RodizioHoje;
import desafio.zup.api.rest.model.Usuario;
import desafio.zup.api.rest.model.Veiculo;
import desafio.zup.api.rest.repository.BuscaValorVeiculoFIPE;
import desafio.zup.api.rest.repository.UsuarioRepository;
import desafio.zup.api.rest.repository.VeiculoRepository;


@RestController  /*Arquitetura REST*/
@RequestMapping(value = "/usuario") 
@EnableFeignClients

public class IndexController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private VeiculoRepository veiculoRepository;

	@Autowired
	private BuscaValorVeiculoFIPE buscaValorVeiculoFIPERepository;

	/*Serviços RESTfull*/

	/* --- Lista todos Usuários x Veiculos ---*/
	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity<List<Usuario>> usuario() {
		List<Usuario> list = (List<Usuario>) usuarioRepository.findAll();

		return new ResponseEntity<List<Usuario>>(list, HttpStatus.OK);
	}
	
	
	/* ==============  E N D   P O I N T   U S U A R I O S  =========================== */
	/* --- Inclui Usuário ---*/
	@PostMapping(value="/", produces = "application/json")
	public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) {

		Usuario usuarioSalvo = usuarioRepository.save(usuario);
		return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK);
	}
	
	/*--- Consulta de usuario por ID ---*/
	@GetMapping(value = "/{id}", produces = "application/json")
		public ResponseEntity<Usuario> init(@PathVariable (value = "id") Long id) {
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		
		return new ResponseEntity<Usuario>(usuario.get(),HttpStatus.OK);
	}
		
	/* --- Altera Usuário ---*/
	@PutMapping(value="/", produces = "application/json")
	public ResponseEntity<Usuario> atualizar(@RequestBody Usuario usuario) {
		
		Usuario usuarioSalvo = usuarioRepository.save(usuario);
		return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK);
	}

	/* --- Deleta Usuário ---*/
	@DeleteMapping(value = "/{id}" , produces = "application/text")
	public String delete (@PathVariable("id") long id) { 
		usuarioRepository.deleteById(id);
		return "ok";
	}

/* ==============  E N D   P O I N T   V E I C U L O S  =========================== */
/* --- Inclui Veiculo ---*/
		@PostMapping(value="/veiculo/{codigo}", produces = "application/json") 
		public ResponseEntity<Veiculo> detalhesUsuario(	@PathVariable("codigo") long codigo, 
														@RequestBody Veiculo veiculo) {

		/* Recupera usuario para atribuir ao veiculo */
		Optional<Usuario> usuario;
		usuario = usuarioRepository.findById(codigo);
		veiculo.setUsuario_id(usuario.get());
		
	    /* --- Consulta Tabela FIPE do Veiculo ---*/
		String vMarcaFipe 		= Integer.toString(veiculo.getMarcaFipe()		);
		String vVeiculoFipe 	= Integer.toString(veiculo.getModeloFipe()		);
		String vAnoVeiculoFipe 	= Integer.toString(veiculo.getAno()				);
		String vCombustivelFipe = Integer.toString(veiculo.getCombustivelFipe()	);
				
		UserResponse buscaFipe;
		buscaFipe = buscaValorVeiculoFIPERepository.buscaValorFipe(vMarcaFipe, vVeiculoFipe, vAnoVeiculoFipe, vCombustivelFipe);
		veiculo.setValor			(buscaFipe.getValor()		);
		veiculo.setCombustivel		(buscaFipe.getCombustivel()	);
		veiculo.setMarca			(buscaFipe.getMarca()		);	
		veiculo.setModelo			(buscaFipe.getModelo()		);
		veiculo.setAno				(buscaFipe.getAnoModelo()	);

		
		/* Verifica dia da semana do rodizio do veiculo */
		String anoModelo = Integer.toString(buscaFipe.getAnoModelo());
		String finalAno;
		finalAno = anoModelo.substring(3, 4); 
		
		String diaRodizio = null;
		int diaSemanaRodizio = 0;
		
		switch (finalAno) {
		case "0":
			diaRodizio = "Segunda-feira";
			diaSemanaRodizio = 2;
			break;
		case "1":
			diaRodizio = "Segunda-feira";
			diaSemanaRodizio = 2;
			break;
		case "2":
			diaRodizio = "Terça-feira";
			diaSemanaRodizio = 3;
			break;
		case "3":
			diaRodizio = "Terça-feira";
			diaSemanaRodizio = 3;
			break;
		case "4":
			diaRodizio = "Quarta-feira";
			diaSemanaRodizio = 4;
			break;
		case "5":
			diaRodizio = "Quarta-feira";
			diaSemanaRodizio = 4;
			break;
		case "6":
			diaRodizio = "Quinta-feira";
			diaSemanaRodizio = 5;
			break;
		case "7":
			diaRodizio = "Quinta-feira";
			diaSemanaRodizio = 5;
			break;
		case "8":
			diaRodizio = "Sexta-feira";
			diaSemanaRodizio = 6;
			break;
		case "9":
			diaRodizio = "Sexta-feira";
			diaSemanaRodizio = 6;
			break;
		}
		
		veiculo.setDiarodiziodesc(diaRodizio);
		veiculo.setDiarodizio(diaSemanaRodizio);
		
		/* Verifica se o carro está no rodizio na data da execução */
		Boolean rodizioHoje;
		rodizioHoje = RodizioHoje.verificarodizio(diaSemanaRodizio);
		
		veiculo.setRodiziohoje(rodizioHoje);
		
		/* Salva veiculo */
		veiculoRepository.save(veiculo);
   		return new ResponseEntity<Veiculo>(veiculo, HttpStatus.OK);
		}
	
	
/*--- Consulta de veiculo por ID ---*/
		@GetMapping(value = "/veiculo/consulta/{id}", produces = "application/json")
		public ResponseEntity<Veiculo> buscaveiculo(@PathVariable (value = "id") Long id) {
		Optional<Veiculo> veiculo = veiculoRepository.findById(id); 

		/* Verifica se o carro está no rodizio na data da execução */
		Boolean rodizioHoje;
		rodizioHoje = RodizioHoje.verificarodizio(veiculo.get().getDiarodizio());
		veiculo.get().setRodiziohoje(rodizioHoje);
		return new ResponseEntity<Veiculo>(veiculo.get(),HttpStatus.OK);
	}	
	
/* --- Altera Veiculo ---*/
		@PutMapping(value="/veiculo/altera/{id}", produces = "application/json")
		public ResponseEntity<Veiculo> atualizar(@RequestBody Veiculo veiculo,
												 @PathVariable (value = "id") Long id 	) {
		
		Optional<Veiculo> veiculo_existente;
		veiculo_existente = veiculoRepository.findById(id);
		Usuario user_id = veiculo_existente.get().getUsuario_id();
	
		veiculo.setUsuario_id(user_id);
		
		
	    /* --- Consulta Tabela FIPE do Veiculo ---*/
		String vMarcaFipe 		= Integer.toString(veiculo_existente.get().getMarcaFipe()		);
		String vVeiculoFipe 	= Integer.toString(veiculo_existente.get().getModeloFipe()		);
		String vAnoVeiculoFipe 	= Integer.toString(veiculo_existente.get().getAno()				);
		String vCombustivelFipe = Integer.toString(veiculo_existente.get().getCombustivelFipe()	);
				
		UserResponse buscaFipe;
		buscaFipe = buscaValorVeiculoFIPERepository.buscaValorFipe(vMarcaFipe, vVeiculoFipe, vAnoVeiculoFipe, vCombustivelFipe);
		veiculo.setValor			(buscaFipe.getValor()		);
		veiculo.setCombustivel		(buscaFipe.getCombustivel()	);
		veiculo.setMarca			(buscaFipe.getMarca()		);	
		veiculo.setModelo			(buscaFipe.getModelo()		);
		veiculo.setAno				(buscaFipe.getAnoModelo()	);

		
		/* Verifica dia da semana do rodizio do veiculo */
		String anoModelo = Integer.toString(buscaFipe.getAnoModelo());
		String finalAno;
		finalAno = anoModelo.substring(3, 4); 
		
		String diaRodizio = null;
		int diaSemanaRodizio = 0;
		
		switch (finalAno) {
		case "0":
			diaRodizio = "Segunda-feira";
			diaSemanaRodizio = 2;
			break;
		case "1":
			diaRodizio = "Segunda-feira";
			diaSemanaRodizio = 2;
			break;
		case "2":
			diaRodizio = "Terça-feira";
			diaSemanaRodizio = 3;
			break;
		case "3":
			diaRodizio = "Terça-feira";
			diaSemanaRodizio = 3;
			break;
		case "4":
			diaRodizio = "Quarta-feira";
			diaSemanaRodizio = 4;
			break;
		case "5":
			diaRodizio = "Quarta-feira";
			diaSemanaRodizio = 4;
			break;
		case "6":
			diaRodizio = "Quinta-feira";
			diaSemanaRodizio = 5;
			break;
		case "7":
			diaRodizio = "Quinta-feira";
			diaSemanaRodizio = 5;
			break;
		case "8":
			diaRodizio = "Sexta-feira";
			diaSemanaRodizio = 6;
			break;
		case "9":
			diaRodizio = "Sexta-feira";
			diaSemanaRodizio = 6;
			break;
		}
		
		veiculo.setDiarodiziodesc(diaRodizio);
		veiculo.setDiarodizio(diaSemanaRodizio);
		
		/* Verifica se o carro está no rodizio na data da execução */
		Boolean rodizioHoje;
		rodizioHoje = RodizioHoje.verificarodizio(diaSemanaRodizio);
		
		veiculo.setRodiziohoje(rodizioHoje);
		
		/* Salva veiculo */
		veiculoRepository.save(veiculo);
   		return new ResponseEntity<Veiculo>(veiculo, HttpStatus.OK);		
	}

/* --- Deleta Usuário ---*/
		@DeleteMapping(value = "/veiculo/exclui/{id}" , produces = "application/text")
		public String deletaveiculo (@PathVariable("id") long id) { 
			veiculoRepository.deleteById(id);
			return "ok";
		}	
	
	
}		