package com.HudLuca.TestTKM.service;

import com.HudLuca.TestTKM.domain.Cidade;
import com.HudLuca.TestTKM.domain.Cliente;
import com.HudLuca.TestTKM.domain.Endereco;
import com.HudLuca.TestTKM.domain.dto.ClienteNovoDTO;
import com.HudLuca.TestTKM.domain.enums.TipoCliente;
import com.HudLuca.TestTKM.repositories.ClienteRepository;
import com.HudLuca.TestTKM.repositories.EnderecoRepository;
import com.HudLuca.TestTKM.service.exception.ObjetoNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;

    public Cliente buscarPorId(Long id){
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.orElseThrow(() -> new ObjetoNaoEncontradoException(
                "Cliente não encontrada! id: " + id + ". Tipo: " + Cliente.class
        ));
    }

    public List<Cliente> buscarTudo() {
        return clienteRepository.findAll();
    }

    public Page<Cliente> buscarPage(Integer paginas, Integer linhas, String ordenacao, String direcaoOrdenacao){
        PageRequest pageRequest = PageRequest.of(paginas,linhas, Sort.Direction.valueOf(direcaoOrdenacao), ordenacao);
        return clienteRepository.findAll(pageRequest);
    }

    @Transactional
    public Cliente inserir(Cliente cliente){
        cliente.setId(null);
        Cliente clienteNovo = clienteRepository.save(cliente);
        enderecoRepository.saveAll(clienteNovo.getEnderecos());
        return clienteNovo;
    }

    public Cliente DTOParaCliente(ClienteNovoDTO clienteNovoDTO){
        Cliente cliente = new Cliente(clienteNovoDTO.getNome(),
                clienteNovoDTO.getEmail(), clienteNovoDTO.getCpfOuCnpj(), TipoCliente.toEnum(clienteNovoDTO.getTipoCliente()));

        Cidade cidade = new Cidade(clienteNovoDTO.getCidadeId(), null, null);

        Endereco endereco = new Endereco(clienteNovoDTO.getLogradouro(), clienteNovoDTO.getNumero(),
                clienteNovoDTO.getComplemento(), clienteNovoDTO.getBairro(), clienteNovoDTO.getCep(), cliente, cidade);

        cliente.getEnderecos().add(endereco);

        cliente.getTelefones().add(clienteNovoDTO.getTelefone1());

        if (clienteNovoDTO.getTelefone2() != null) {
            cliente.getTelefones().add(clienteNovoDTO.getTelefone2());
        }

        if (clienteNovoDTO.getTelefone3() != null) {
            cliente.getTelefones().add(clienteNovoDTO.getTelefone3());
        }

        return cliente;
    }
}
