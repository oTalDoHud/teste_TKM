package com.HudLuca.SimulacaoSeguros.service.validacao;

import com.HudLuca.SimulacaoSeguros.domain.Cliente;
import com.HudLuca.SimulacaoSeguros.domain.dto.ClienteNovoDTO;
import com.HudLuca.SimulacaoSeguros.domain.enums.TipoClienteEnum;
import com.HudLuca.SimulacaoSeguros.repositories.ClienteRepository;
import com.HudLuca.SimulacaoSeguros.resouce.exception.CampoMenssagemErro;
import com.HudLuca.SimulacaoSeguros.service.validacao.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClienteInserirValidador implements ConstraintValidator<ClienteInserir, ClienteNovoDTO> {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void initialize(ClienteInserir clienteInserir) {
    }

    @Override
    public boolean isValid(ClienteNovoDTO clienteNovoDTO, ConstraintValidatorContext context) {
        List<CampoMenssagemErro> list = new ArrayList<>();

        if (clienteNovoDTO.getTipoCliente().equals(TipoClienteEnum.PESSOA_FISICA.getCd())
                && !BR.isValidoCPF(clienteNovoDTO.getCpfOuCnpj())) {
            list.add(new CampoMenssagemErro("cpfOuCnpj", "CPF inválido"));
        }

        if (clienteNovoDTO.getTipoCliente().equals(TipoClienteEnum.PESSOA_JURIDICA.getCd())
                && !BR.isValidoCNPJ(clienteNovoDTO.getCpfOuCnpj())) {
            list.add(new CampoMenssagemErro("cpfOuCnpj", "CNPJ inválido"));
        }

        Cliente clienteAux = clienteRepository.findByEmail(clienteNovoDTO.getEmail());

        if(clienteAux != null){
            list.add(new CampoMenssagemErro("email", "E-mail já existente"));
        }

        for (CampoMenssagemErro e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMenssagem())
                    .addPropertyNode(e.getCampo()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}