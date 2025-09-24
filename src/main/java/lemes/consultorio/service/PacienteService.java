package lemes.consultorio.service;

import lemes.consultorio.entity.Paciente;
import lemes.consultorio.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PacienteService {
    
    @Autowired
    private PacienteRepository pacienteRepository;
    
    // Criar novo paciente
    public Paciente criarPaciente(Paciente paciente) {
        // Validar se CPF já existe
        if (pacienteRepository.findByCpf(paciente.getCpf()).isPresent()) {
            throw new RuntimeException("CPF já cadastrado: " + paciente.getCpf());
        }
        
        // Validar se RG já existe
        if (pacienteRepository.findByRg(paciente.getRg()).isPresent()) {
            throw new RuntimeException("RG já cadastrado: " + paciente.getRg());
        }
        
        // Validar se telefone já existe
        if (pacienteRepository.findByTelefone(paciente.getTelefone()).isPresent()) {
            throw new RuntimeException("Telefone já cadastrado: " + paciente.getTelefone());
        }
        
        // Definir data de cadastro
        paciente.setDataCadastro(LocalDateTime.now());
        paciente.setAtivo(true);
        
        return pacienteRepository.save(paciente);
    }
    
    // Buscar paciente por ID
    @Transactional(readOnly = true)
    public Optional<Paciente> buscarPorId(Long id) {
        return pacienteRepository.findById(id);
    }
    
    // Buscar todos os pacientes ativos
    @Transactional(readOnly = true)
    public List<Paciente> buscarTodosAtivos() {
        return pacienteRepository.findByAtivoTrue();
    }
    
    // Buscar todos os pacientes ativos com paginação
    @Transactional(readOnly = true)
    public Page<Paciente> buscarTodosAtivosPaginado(int pagina, int tamanho, String ordenacao) {
        Sort sort = Sort.by(Sort.Direction.ASC, "nome");
        if (ordenacao != null && !ordenacao.isEmpty()) {
            if (ordenacao.equalsIgnoreCase("desc")) {
                sort = Sort.by(Sort.Direction.DESC, "nome");
            }
        }
        
        Pageable pageable = PageRequest.of(pagina, tamanho, sort);
        return pacienteRepository.findByAtivoTrue(pageable);
    }
    
    // Buscar pacientes por nome
    @Transactional(readOnly = true)
    public List<Paciente> buscarPorNome(String nome) {
        return pacienteRepository.findByNomeContainingIgnoreCaseAndAtivoTrue(nome);
    }
    
    // Buscar pacientes com filtro e paginação
    @Transactional(readOnly = true)
    public Page<Paciente> buscarPacientesComFiltro(String nome, int pagina, int tamanho) {
        Pageable pageable = PageRequest.of(pagina, tamanho, Sort.by(Sort.Direction.ASC, "nome"));
        return pacienteRepository.findPacientesAtivosComFiltro(nome, pageable);
    }
    
    // Buscar por CPF
    @Transactional(readOnly = true)
    public Optional<Paciente> buscarPorCpf(String cpf) {
        return pacienteRepository.findByCpf(cpf);
    }
    
    // Buscar por RG
    @Transactional(readOnly = true)
    public Optional<Paciente> buscarPorRg(String rg) {
        return pacienteRepository.findByRg(rg);
    }
    
    // Buscar por cidade
    @Transactional(readOnly = true)
    public List<Paciente> buscarPorCidade(String cidade) {
        return pacienteRepository.findByCidade(cidade);
    }
    
    // Buscar por estado
    @Transactional(readOnly = true)
    public List<Paciente> buscarPorEstado(String estado) {
        return pacienteRepository.findByEstado(estado);
    }
    
    // Buscar por faixa etária
    @Transactional(readOnly = true)
    public List<Paciente> buscarPorFaixaEtaria(LocalDate dataInicio, LocalDate dataFim) {
        return pacienteRepository.findPacientesPorFaixaEtaria(dataInicio, dataFim);
    }
    
    // Atualizar paciente
    public Paciente atualizarPaciente(Long id, Paciente pacienteAtualizado) {
        Paciente pacienteExistente = pacienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Paciente não encontrado com ID: " + id));
        
        // Validar se CPF já existe em outro paciente
        if (!pacienteExistente.getCpf().equals(pacienteAtualizado.getCpf()) &&
            pacienteRepository.existsByCpfAndIdNot(pacienteAtualizado.getCpf(), id)) {
            throw new RuntimeException("CPF já cadastrado: " + pacienteAtualizado.getCpf());
        }
        
        // Validar se RG já existe em outro paciente
        if (!pacienteExistente.getRg().equals(pacienteAtualizado.getRg()) &&
            pacienteRepository.existsByRgAndIdNot(pacienteAtualizado.getRg(), id)) {
            throw new RuntimeException("RG já cadastrado: " + pacienteAtualizado.getRg());
        }
        
        // Atualizar campos
        pacienteExistente.setNome(pacienteAtualizado.getNome());
        pacienteExistente.setCpf(pacienteAtualizado.getCpf());
        pacienteExistente.setRg(pacienteAtualizado.getRg());
        pacienteExistente.setDataNascimento(pacienteAtualizado.getDataNascimento());
        pacienteExistente.setSexo(pacienteAtualizado.getSexo());
        pacienteExistente.setTelefone(pacienteAtualizado.getTelefone());
        pacienteExistente.setEmail(pacienteAtualizado.getEmail());
        pacienteExistente.setEndereco(pacienteAtualizado.getEndereco());
        pacienteExistente.setCidade(pacienteAtualizado.getCidade());
        pacienteExistente.setEstado(pacienteAtualizado.getEstado());
        pacienteExistente.setCep(pacienteAtualizado.getCep());
        pacienteExistente.setObservacoes(pacienteAtualizado.getObservacoes());
        pacienteExistente.setDataAtualizacao(LocalDateTime.now());
        
        return pacienteRepository.save(pacienteExistente);
    }
    
    // Desativar paciente (soft delete)
    public void desativarPaciente(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Paciente não encontrado com ID: " + id));
        
        paciente.setAtivo(false);
        paciente.setDataAtualizacao(LocalDateTime.now());
        pacienteRepository.save(paciente);
    }
    
    // Reativar paciente
    public void reativarPaciente(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Paciente não encontrado com ID: " + id));
        
        paciente.setAtivo(true);
        paciente.setDataAtualizacao(LocalDateTime.now());
        pacienteRepository.save(paciente);
    }
    
    // Excluir paciente permanentemente
    public void excluirPaciente(Long id) {
        if (!pacienteRepository.existsById(id)) {
            throw new RuntimeException("Paciente não encontrado com ID: " + id);
        }
        pacienteRepository.deleteById(id);
    }
    
    // Contar pacientes ativos
    @Transactional(readOnly = true)
    public long contarPacientesAtivos() {
        return pacienteRepository.countByAtivoTrue();
    }
    
    // Validar dados do paciente
    public void validarPaciente(Paciente paciente) {
        if (paciente.getNome() == null || paciente.getNome().trim().isEmpty()) {
            throw new RuntimeException("Nome é obrigatório");
        }
        
        if (paciente.getCpf() == null || paciente.getCpf().trim().isEmpty()) {
            throw new RuntimeException("CPF é obrigatório");
        }
        
        if (paciente.getRg() == null || paciente.getRg().trim().isEmpty()) {
            throw new RuntimeException("RG é obrigatório");
        }
        
        if (paciente.getSexo() == null) {
            throw new RuntimeException("Sexo é obrigatório");
        }
        
        if (paciente.getTelefone() == null || paciente.getTelefone().trim().isEmpty()) {
            throw new RuntimeException("Telefone é obrigatório");
        }
        
        if (paciente.getEndereco() == null || paciente.getEndereco().trim().isEmpty()) {
            throw new RuntimeException("Endereço é obrigatório");
        }
        
        if (paciente.getCidade() == null || paciente.getCidade().trim().isEmpty()) {
            throw new RuntimeException("Cidade é obrigatória");
        }
        
        if (paciente.getEstado() == null || paciente.getEstado().trim().isEmpty()) {
            throw new RuntimeException("Estado é obrigatório");
        }
    }
}
