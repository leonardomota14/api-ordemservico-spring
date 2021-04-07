package com.leonardomota.ordemservico.controller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leonardomota.ordemservico.domain.EntityNotFoundException;
import com.leonardomota.ordemservico.model.Comentario;
import com.leonardomota.ordemservico.model.OrdemServico;
import com.leonardomota.ordemservico.model.vos.ComentarioVO;
import com.leonardomota.ordemservico.repository.ComentarioRepository;
import com.leonardomota.ordemservico.repository.OrdemServicoRepository;

@RestController
@RequestMapping("/ordemservico/{ordemServicoId}/comentarios")
public class ComentarioController {

	@Autowired
	private ComentarioRepository comentarioRepository;
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;

	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping
	public List<ComentarioVO> listar(@PathVariable Integer ordemServicoId) {
		
		OrdemServico ordemServicoProcurada = ordemServicoRepository
				.findById(ordemServicoId)
				.orElseThrow(() -> new EntityNotFoundException("Ordem de serviço não encontrada."));
		
		return convertToComentariosList(ordemServicoProcurada.getComentarios());
	}
		
	@PostMapping
	public Comentario registrar(@PathVariable Integer ordemServicoId, @RequestBody String descricao) {
		
		OrdemServico os = ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow(() -> new EntityNotFoundException("Ordem de serviço não foi encontrada."));
		
		Comentario novoComentario = new Comentario();
		novoComentario.setOrdemServico(os);
		novoComentario.setDescricao(descricao);
		novoComentario.setDataPublicacao(OffsetDateTime.now());
		
		return comentarioRepository.save(novoComentario);
	}
	
	@DeleteMapping("/{comentarioId}")
	public void remover(@PathVariable Integer comentarioId) {		
		comentarioRepository.deleteById(comentarioId);
		
	}
	
	private ComentarioVO convertToComentarioVO(Comentario comentario) {
		return modelMapper.map(comentario, ComentarioVO.class);
	}
	
	private List<ComentarioVO> convertToComentariosList(List<Comentario> comentarios) {
		return comentarios.stream()
				.map(comentario -> convertToComentarioVO(comentario))
				.collect(Collectors.toList());
	}

}
