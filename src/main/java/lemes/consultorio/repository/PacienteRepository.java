package lemes.consultorio.repository;

import lemes.consultorio.entity.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    
    // Buscar por CPF
    Optional<Paciente> findByCpf(String cpf);
    
    // Buscar por RG
    Optional<Paciente> findByRg(String rg);
    
    // Buscar por nome (ignorando case)
    List<Paciente> findByNomeContainingIgnoreCase(String nome);
    
    // Buscar pacientes ativos
    List<Paciente> findByAtivoTrue();
    
    // Buscar pacientes ativos com paginação
    Page<Paciente> findByAtivoTrue(Pageable pageable);
    
    // Buscar por nome e ativo
    List<Paciente> findByNomeContainingIgnoreCaseAndAtivoTrue(String nome);
    
    // Buscar por cidade
    List<Paciente> findByCidade(String cidade);
    
    // Buscar por estado
    List<Paciente> findByEstado(String estado);
    
    // Buscar por telefone
    Optional<Paciente> findByTelefone(String telefone);
    
    // Buscar por email
    Optional<Paciente> findByEmail(String email);
    
    // Verificar se CPF já existe (excluindo o próprio registro)
    @Query("SELECT COUNT(p) > 0 FROM Paciente p WHERE p.cpf = :cpf AND p.id != :id")
    boolean existsByCpfAndIdNot(@Param("cpf") String cpf, @Param("id") Long id);
    
    // Verificar se RG já existe (excluindo o próprio registro)
    @Query("SELECT COUNT(p) > 0 FROM Paciente p WHERE p.rg = :rg AND p.id != :id")
    boolean existsByRgAndIdNot(@Param("rg") String rg, @Param("id") Long id);
    
    // Buscar pacientes por faixa etária
    @Query("SELECT p FROM Paciente p WHERE p.dataNascimento BETWEEN :dataInicio AND :dataFim AND p.ativo = true")
    List<Paciente> findPacientesPorFaixaEtaria(@Param("dataInicio") java.time.LocalDate dataInicio, 
                                               @Param("dataFim") java.time.LocalDate dataFim);
    
    // Contar pacientes ativos
    long countByAtivoTrue();
    
    // Buscar pacientes com paginação e filtro por nome
    @Query("SELECT p FROM Paciente p WHERE p.ativo = true AND " +
           "(:nome IS NULL OR LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%')))")
    Page<Paciente> findPacientesAtivosComFiltro(@Param("nome") String nome, Pageable pageable);
}
