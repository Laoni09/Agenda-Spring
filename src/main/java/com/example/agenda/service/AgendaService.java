package com.example.agenda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.agenda.domain.Contato.Contato;
import com.example.agenda.domain.Contato.ContatoDTO;
import com.example.agenda.domain.Contato.ContatoPessoal;
import com.example.agenda.domain.Contato.ContatoPessoalDTO;
import com.example.agenda.domain.Contato.ContatoProfissional;
import com.example.agenda.domain.Contato.ContatoProfissionalDTO;
import com.example.agenda.domain.Usuario.Usuario;
import com.example.agenda.repository.ContatoRepository;
import com.example.agenda.repository.UsuarioRepository;

@Service
public class AgendaService {
    private final ContatoRepository contatoRepository;
    private final UsuarioRepository usuarioRepository;

    public AgendaService(ContatoRepository contatoRepository, UsuarioRepository usuarioRepository) {
        this.contatoRepository = contatoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<ContatoDTO> listarContatos(Integer usuarioId) {
        List<Contato> contatos = contatoRepository.findByUsuarioId(usuarioId);
        return contatos.stream().map(this::toDTO).toList();
    }
    
    @Transactional
    public void adicionarContatoProfissional(String nome, String telefone, String empresa, Integer usuarioId) {
        Usuario donoDoContato = usuarioRepository.getReferenceById(usuarioId);

        //Spring lança uma EntityNotFoundException quando executar o save()
        contatoRepository.save(new ContatoProfissional(nome, telefone, empresa, donoDoContato));
    }

    @Transactional
    public void adicionarContatoPessoal(String nome, String telefone, String cpf, Integer usuarioId) {
        Usuario donoDoContato = usuarioRepository.getReferenceById(usuarioId);
        contatoRepository.save(new ContatoPessoal(nome, telefone, cpf, donoDoContato));
    }

    @Transactional
    public void removerContato(Integer idContato, Integer usuarioLogadoId) {
        Contato contato = contatoRepository.findById(idContato)
                .orElseThrow(() -> new RuntimeException("Contato não encontrado."));

        if (!contato.getUsuario().getId().equals(usuarioLogadoId)) {
            throw new RuntimeException("Acesso negado.");
        }
        contatoRepository.delete(contato);
    }

    private ContatoDTO toDTO(Contato c) {
        return switch (c) {
            case ContatoPessoal p -> new ContatoPessoalDTO(p.getId(), p.getNome(), p.getTelefone(), p.getCpf());
            case ContatoProfissional prof -> new ContatoProfissionalDTO(prof.getId(), prof.getNome(), prof.getTelefone(), prof.getEmpresa());
            default -> throw new IllegalStateException("Tipo inesperado: " + c.getClass());
        };
    }
}
