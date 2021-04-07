package com.leonardomota.ordemservico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leonardomota.ordemservico.domain.exception.DomainException;
import com.leonardomota.ordemservico.model.Cliente;
import com.leonardomota.ordemservico.repository.ClienteRepository;
import com.leonardomota.ordemservico.repository.EnderecoRepository;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;

	@GetMapping	
	public List<Cliente> listar() {
		return clienteRepository.findAll();	
	}
	
	@PostMapping
	public Cliente adicionar(@RequestBody Cliente cliente) {
		Cliente clienteExistente = clienteRepository.findByEmail(cliente.getEmail());
		
		if (clienteExistente != null && !clienteExistente.equals(cliente)) {
			throw new DomainException("Já existe cliente registrado com esse e-mail. Tente inserir outro.");
		}
		
		cliente.setId(null);
		enderecoRepository.save(cliente.getEndereco());
		cliente = clienteRepository.save(cliente);
		
		return cliente;
	}
	
	@DeleteMapping("/{clienteId}")
	public void remover(@PathVariable Integer clienteId) {		
		clienteRepository.deleteById(clienteId);
	}
	
}
