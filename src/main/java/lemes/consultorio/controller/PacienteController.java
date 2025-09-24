package lemes.consultorio.controller;

import lemes.consultorio.entity.Paciente;
import lemes.consultorio.service.PacienteService;
import lemes.consultorio.config.DatabaseConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pacientes")
@CrossOrigin(origins = "*")
public class PacienteController {
    
    @Autowired
    private PacienteService pacienteService;
    
    @Autowired
    private DatabaseConfig databaseConfig;
    
    // Criar novo paciente
    @PostMapping
    public ResponseEntity<?> criarPaciente(@RequestBody Paciente paciente) {
        try {
            pacienteService.validarPaciente(paciente);
            Paciente novoPaciente = pacienteService.criarPaciente(paciente);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoPaciente);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro interno do servidor: " + e.getMessage());
        }
    }
    
    // Buscar paciente por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            Optional<Paciente> paciente = pacienteService.buscarPorId(id);
            if (paciente.isPresent()) {
                return ResponseEntity.ok(paciente.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro interno do servidor: " + e.getMessage());
        }
    }
    
    // Buscar todos os pacientes ativos
    @GetMapping
    public ResponseEntity<?> buscarTodos(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanho,
            @RequestParam(defaultValue = "asc") String ordenacao,
            @RequestParam(required = false) String nome) {
        try {
            if (nome != null && !nome.trim().isEmpty()) {
                // Buscar com filtro por nome
                Page<Paciente> pacientes = pacienteService.buscarPacientesComFiltro(nome, pagina, tamanho);
                return ResponseEntity.ok(pacientes);
            } else {
                // Buscar todos
                Page<Paciente> pacientes = pacienteService.buscarTodosAtivosPaginado(pagina, tamanho, ordenacao);
                return ResponseEntity.ok(pacientes);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro interno do servidor: " + e.getMessage());
        }
    }
    
    // Buscar todos os pacientes ativos (sem paginação)
    @GetMapping("/todos")
    public ResponseEntity<?> buscarTodosAtivos() {
        try {
            List<Paciente> pacientes = pacienteService.buscarTodosAtivos();
            return ResponseEntity.ok(pacientes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro interno do servidor: " + e.getMessage());
        }
    }
    
    // Buscar por nome
    @GetMapping("/buscar")
    public ResponseEntity<?> buscarPorNome(@RequestParam String nome) {
        try {
            List<Paciente> pacientes = pacienteService.buscarPorNome(nome);
            return ResponseEntity.ok(pacientes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro interno do servidor: " + e.getMessage());
        }
    }
    
    // Buscar por CPF
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<?> buscarPorCpf(@PathVariable String cpf) {
        try {
            Optional<Paciente> paciente = pacienteService.buscarPorCpf(cpf);
            if (paciente.isPresent()) {
                return ResponseEntity.ok(paciente.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro interno do servidor: " + e.getMessage());
        }
    }
    
    // Buscar por RG
    @GetMapping("/rg/{rg}")
    public ResponseEntity<?> buscarPorRg(@PathVariable String rg) {
        try {
            Optional<Paciente> paciente = pacienteService.buscarPorRg(rg);
            if (paciente.isPresent()) {
                return ResponseEntity.ok(paciente.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro interno do servidor: " + e.getMessage());
        }
    }
    
    // Buscar por cidade
    @GetMapping("/cidade/{cidade}")
    public ResponseEntity<?> buscarPorCidade(@PathVariable String cidade) {
        try {
            List<Paciente> pacientes = pacienteService.buscarPorCidade(cidade);
            return ResponseEntity.ok(pacientes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro interno do servidor: " + e.getMessage());
        }
    }
    
    // Buscar por estado
    @GetMapping("/estado/{estado}")
    public ResponseEntity<?> buscarPorEstado(@PathVariable String estado) {
        try {
            List<Paciente> pacientes = pacienteService.buscarPorEstado(estado);
            return ResponseEntity.ok(pacientes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro interno do servidor: " + e.getMessage());
        }
    }
    
    // Buscar por faixa etária
    @GetMapping("/faixa-etaria")
    public ResponseEntity<?> buscarPorFaixaEtaria(
            @RequestParam String dataInicio,
            @RequestParam String dataFim) {
        try {
            LocalDate inicio = LocalDate.parse(dataInicio);
            LocalDate fim = LocalDate.parse(dataFim);
            List<Paciente> pacientes = pacienteService.buscarPorFaixaEtaria(inicio, fim);
            return ResponseEntity.ok(pacientes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }
    
    // Atualizar paciente
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarPaciente(@PathVariable Long id, @RequestBody Paciente paciente) {
        try {
            pacienteService.validarPaciente(paciente);
            Paciente pacienteAtualizado = pacienteService.atualizarPaciente(id, paciente);
            return ResponseEntity.ok(pacienteAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro interno do servidor: " + e.getMessage());
        }
    }
    
    // Desativar paciente
    @PatchMapping("/{id}/desativar")
    public ResponseEntity<?> desativarPaciente(@PathVariable Long id) {
        try {
            pacienteService.desativarPaciente(id);
            return ResponseEntity.ok("Paciente desativado com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro interno do servidor: " + e.getMessage());
        }
    }
    
    // Reativar paciente
    @PatchMapping("/{id}/reativar")
    public ResponseEntity<?> reativarPaciente(@PathVariable Long id) {
        try {
            pacienteService.reativarPaciente(id);
            return ResponseEntity.ok("Paciente reativado com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro interno do servidor: " + e.getMessage());
        }
    }
    
    // Excluir paciente permanentemente
    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirPaciente(@PathVariable Long id) {
        try {
            pacienteService.excluirPaciente(id);
            return ResponseEntity.ok("Paciente excluído com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro interno do servidor: " + e.getMessage());
        }
    }
    
    // Contar pacientes ativos
    @GetMapping("/contar")
    public ResponseEntity<?> contarPacientesAtivos() {
        try {
            long total = pacienteService.contarPacientesAtivos();
            return ResponseEntity.ok("Total de pacientes ativos: " + total);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro interno do servidor: " + e.getMessage());
        }
    }
    
    // Testar conexão com o banco
    @GetMapping("/testar-conexao")
    public ResponseEntity<?> testarConexao() {
        try {
            databaseConfig.testarConexao();
            return ResponseEntity.ok("✅ Conexão com o banco de dados está funcionando!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("❌ Erro na conexão: " + e.getMessage());
        }
    }
}
