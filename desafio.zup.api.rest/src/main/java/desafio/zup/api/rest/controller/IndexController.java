package desafio.zup.api.rest.controller;

import java.util.Optional;
import java.util.List;

import org.apache.cassandra.thrift.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
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

	public int coderro = 0;
	
	/*Serviços RESTfull*/
	/* ============ LISTA USUARIOS X VEICULOS ============*/
	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity<List<Usuario>> usuario() {
		List<Usuario> list = (List<Usuario>) usuarioRepository.findAll();
		
		for (int listaUsuarios = 0; listaUsuarios < list.size(); listaUsuarios ++) {
			
			List<Veiculo> veiculoslist;
			veiculoslist = list.get(listaUsuarios).getVeiculos();
								
			for (int listaVeiculos = 0; listaVeiculos <  veiculoslist.size(); listaVeiculos ++ ) {
				recuperaDadosFipe(veiculoslist.get(listaVeiculos));
				}
			}
		return new ResponseEntity<List<Usuario>>(list, HttpStatus.OK);
	}
	
	
	/* ==============  E N D   P O I N T   U S U A R I O S  =========================== */
	/*--- Consulta de usuario por ID ---*/
	@GetMapping(value = "/{id}", produces = "application/json")
		public ResponseEntity<?> init(@PathVariable (value = "id") Long id) throws NotFoundException {
		
		Optional<Usuario> usuario = usuarioRepository.findById(id);
   
		 if ( usuario.isEmpty()) {
			coderro = 1;
			throw new NotFoundException();
		} else {
			return new ResponseEntity<Usuario>(usuario.get(),HttpStatus.OK); 
		}
	}	

	
	/* --- Inclui Usuário ---*/
	@PostMapping(value="/", produces = "application/json")
	public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) throws NotFoundException {

		try {
	 		usuarioRepository.save(usuario);
	 		return new ResponseEntity<Usuario>(usuario, HttpStatus.CREATED);
		} catch (Exception e) {
			coderro = 2;
			throw new NotFoundException();	
		}
	}
	
	/* --- Altera Usuário ---*/
	@PutMapping(value="/", produces = "application/json")
	public ResponseEntity<Usuario> atualizar(@RequestBody Usuario usuario) throws NotFoundException {
		
		try {
	 		usuarioRepository.save(usuario);
	 		return new ResponseEntity<Usuario>(usuario, HttpStatus.CREATED);
		} catch (Exception e) {
			coderro = 3;
			throw new NotFoundException();	
		}
	}

	/* --- Deleta Usuário ---*/
	@DeleteMapping(value = "/{id}" , produces = "application/text")
	public String delete (@PathVariable("id") long id) throws NotFoundException { 
		
		try {
			usuarioRepository.deleteById(id);
			return "Usuário Excluído com sucesso";
		} catch (Exception e) {
			coderro = 4;
			throw new NotFoundException();
		}	
	}

/* ==============  E N D   P O I N T   V E I C U L O S  =========================== */
/* --- Inclui Veiculo ---*/
		@PostMapping(value="/veiculo/{codigo}", produces = "application/json") 
		public ResponseEntity<Veiculo> detalhesUsuario(	@PathVariable("codigo") long codigo, 
														@RequestBody Veiculo veiculo) throws NotFoundException {
		
		/* Recupera usuario para atribuir ao veiculo */
		try {	
			veiculo.setUsuario_id(recuperausuario(codigo));
		} catch (Exception e) {
			coderro = 9;
			throw new NotFoundException(); 	}
		
		/* Obtem valor do veiculo da tabela FIPE e atualiza atributos do rodizio */
		try {
			recuperaDadosFipe(veiculo);
		} catch (Exception e) {
			coderro = 10;
			throw new NotFoundException(); 
		}	
		
		/* Salva veiculo */
		try {
			veiculoRepository.save(veiculo);
			return new ResponseEntity<Veiculo>(veiculo, HttpStatus.CREATED);
		} catch (Exception e) {
			coderro = 5;
			throw new NotFoundException(); 	}
		}
	

/*--- Consulta de veiculo por ID ---*/
		@GetMapping(value = "/veiculo/consulta/{id}", produces = "application/json")
		public ResponseEntity<Veiculo> buscaveiculo(@PathVariable (value = "id") Long id) throws NotFoundException {
			Optional<Veiculo> veiculo;
			veiculo = veiculoRepository.findById(id);
			
			if ( veiculo.isEmpty()) {
				coderro = 6;
				throw new NotFoundException();	
			} 
		
		/* Verifica se o carro está no rodizio na data da execução */
		Boolean rodizioHoje;
		rodizioHoje = RodizioHoje.verificarodizio(veiculo.get().getDiarodizio());
		veiculo.get().setRodiziohoje(rodizioHoje);
		return new ResponseEntity<Veiculo>(veiculo.get(),HttpStatus.OK);
	}	
		
/* --- Altera Veiculo ---*/
		@PutMapping(value="/veiculo/altera/{id}", produces = "application/json")
		public ResponseEntity<Veiculo> atualizar(@RequestBody Veiculo veiculo,
												 @PathVariable (value = "id") Long id 	) throws NotFoundException {
		
		/* Recupera o usuário do veiculo */	
		Optional<Usuario> usuarioVeiculo;
		usuarioVeiculo = usuarioRepository.findById(id);
		
		if ( usuarioVeiculo.isEmpty() ) {
			coderro = 1;
			throw new NotFoundException();
		}
		
		veiculo.setUsuario_id(usuarioVeiculo.get());
		
		/* Obtem valor do veiculo atualizado da tabela FIPE e atualiza atributos do rodizio */
		try {
			recuperaDadosFipe(veiculo);
		} catch (Exception e) {
			coderro = 10;
			throw new NotFoundException(); 
		}	
	
		/* Salva veiculo */
		veiculoRepository.save(veiculo);
   		return new ResponseEntity<Veiculo>(veiculo, HttpStatus.OK);		
	}

/* --- Deleta Veículo ---*/
		@DeleteMapping(value = "/veiculo/exclui/{id}" , produces = "application/text")
		public String deletaveiculo (@PathVariable("id") long id) throws NotFoundException { 
		
			try {
				veiculoRepository.deleteById(id);
			} catch (Exception e) {
				coderro = 6;
				throw new NotFoundException();				
			}
			return "Veículo excluído";
		}	
	
		
		/* ====== Metodos de Apoio ====== */
		/* Recupera usuario por id */
		public Usuario recuperausuario(long codigo) {
			Optional<Usuario> usuario;
			usuario = usuarioRepository.findById(codigo);
			return usuario.get();
		}	
		
		
		/* Chama API FIPE para recuperar valor do veiculo */
		public Veiculo recuperaDadosFipe( Veiculo veiculo) {
			
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
			
		return veiculo;
			
		}
	
		
		/* Tratamento de Exception para erro nos dados do usuario enviados para inclusão */
		@ExceptionHandler(NotFoundException.class)
		public String errorPage() {
			switch (coderro) {
			case 1:
				return "Usuário não encontrado";
			case 2:
				return "Dados do usuário incorretos para inclusão";
			case 3:
				return "Dados do usuário incorretos para atualização";				
			case 4:
				return "Usuário não localilado para exclusão";	
			case 5:
				return "Dados do veiculo incorretos para inclusão";
			case 6:
				return "Veiculo não encontrado";
			case 7:
				return "Dados do veiculo incorretos para atualização";				
			case 8:
				return "Veiculo não localilado para exclusão";
			case 9:
				return "Usuário proprietário informado não encontrado";
			case 10:
				return "Erro ao buscar o valor do veículo no serviço da FIPE, verifique a marca/modelo/ano/combustivel informados";				
			}
		return "";	
		}
		
		
		/*--- Tratamento de Exception para URL invalida ---*/
		@GetMapping(value = "/veiculo/consulta/", produces = "application/json")
		public String urlInvalida1 () {
			return "Url invalida - Informe o id do veiculo para consulta!";
		}

		/*--- Tratamento de Exception para URL invalida ---*/
		@GetMapping(value = "/veiculo/", produces = "application/json")
		public String urlInvalida2 () {
			return "Url invalida - verifique a documentação da API para ver as URL's validas para este serviço!";
		}
		
		/*--- Tratamento de Exception para URL invalida ---*/
		@GetMapping(value = "/veiculo/{id}", produces = "application/json")
		public String urlInvalida3 () {
			return "Url invalida - verifique a documentação da API para ver as URL's validas para este serviço!";
		}
		
}


