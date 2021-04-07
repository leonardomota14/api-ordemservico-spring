package com.leonardomota.ordemservico.controller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.leonardomota.ordemservico.domain.exception.DomainException;
import com.leonardomota.ordemservico.model.Cliente;
import com.leonardomota.ordemservico.model.OrdemServico;
import com.leonardomota.ordemservico.model.StatusOrdemServico;
import com.leonardomota.ordemservico.model.vos.OrdemServicoVO;
import com.leonardomota.ordemservico.repository.ClienteRepository;
import com.leonardomota.ordemservico.repository.OrdemServicoRepository;

@RestController
@RequestMapping("/ordemservico")
public class OrdemServicoController {

	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<OrdemServicoVO> listar() {
		return converterParaOrdemServicoVO(ordemServicoRepository.findAll());
	}

	@PostMapping
	public OrdemServicoVO registrar(@RequestBody OrdemServico ordemServico) {		
		Cliente clienteExistente = clienteRepository.findById(ordemServico.getCliente().getId())
				.orElseThrow(() -> new DomainException("Cliente não encontrado."));
				
		ordemServico.setCliente(clienteExistente);
		ordemServico.setStatusOrdemServico(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());
		
		ordemServicoRepository.save(ordemServico);

		return converterParaOrdemServicoVORepresentation(ordemServico);
		
	}
	
	@DeleteMapping("/{ordemServicoId}")
	public void remover(@PathVariable Integer ordemServicoId) {		
		OrdemServico obj = encontrarOrdemServico(ordemServicoId);		
		ordemServicoRepository.delete(obj);		
	}
	
	@PutMapping("/{ordemServicoId}")
	public void finalizar(@PathVariable Integer ordemServicoId) {		
		OrdemServico ordemServicoProcurada = encontrarOrdemServico(ordemServicoId);		
		finalizarOrdemServico(ordemServicoProcurada);		
		ordemServicoRepository.save(ordemServicoProcurada);		
	}
	
	private OrdemServico encontrarOrdemServico(Integer ordemServicoId) {
		OrdemServico ordemServicoProcurada = ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow(() -> new DomainException("Ordem de serviço não encontrado"));
		return ordemServicoProcurada;
	}
	
	private boolean podeSerFinalizada(OrdemServico ordemServico) {
		return ordemServico.getStatusOrdemServico().equals(StatusOrdemServico.ABERTA);
	}
	
	private boolean naoPodeSerFinalizada(OrdemServico ordemServico) {
		return !podeSerFinalizada(ordemServico);
	}
	
	public void finalizarOrdemServico(OrdemServico ordemServico) {
		if (naoPodeSerFinalizada(ordemServico)) {
			throw new DomainException("Ordem de serviço não pode ser finalizada.");
		}
		
		ordemServico.setStatusOrdemServico(StatusOrdemServico.FINALIZADA);
		ordemServico.setDataFechou(OffsetDateTime.now());
	}
	
	private OrdemServicoVO converterParaOrdemServicoVORepresentation(OrdemServico ordemServico) {
		return modelMapper.map(ordemServico, OrdemServicoVO.class);
	}
	
	private List<OrdemServicoVO> converterParaOrdemServicoVO(List<OrdemServico> listOS) {
		return listOS.stream()
				.map(ordem -> converterParaOrdemServicoVORepresentation(ordem))
				.collect(Collectors.toList());
	}
	
}
