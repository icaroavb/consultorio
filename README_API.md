# API do Sistema de Consultório - Cadastro de Pacientes

Este é um sistema backend CRUD para gerenciamento de pacientes em um consultório médico, desenvolvido com Spring Boot.

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.5.6**
- **Spring Data JPA**
- **H2 Database** (para desenvolvimento)
- **MySQL** (para produção)
- **Maven**

## Configuração do Projeto

### Banco de Dados MySQL

#### 1. Instalar MySQL
- Baixe e instale o MySQL Server
- Configure usuário: `root` e senha: `123456`
- Ou altere as credenciais em `application.properties`

#### 2. Criar Banco de Dados
```sql
CREATE DATABASE consultorio;
```

#### 3. Configurações Disponíveis

**Para Desenvolvimento (H2):**
```bash
mvn spring-boot:run -Dspring.profiles.active=dev
```
- Console H2: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:consultorio`
- Username: `sa`, Password: (vazio)

**Para Produção (MySQL):**
```bash
mvn spring-boot:run -Dspring.profiles.active=prod
```

**Padrão (MySQL):**
```bash
mvn spring-boot:run
```

### Configurações do MySQL
Edite `src/main/resources/application.properties` se necessário:
```properties
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
```

A API estará disponível em: `http://localhost:8080`

## Monitoramento e Logs

### Logs de Conexão
O sistema possui logs detalhados para monitorar a conexão com o banco:

**Ao iniciar a aplicação, você verá:**
```
🔍 Testando conexão com o banco de dados...
✅ CONEXÃO ESTABELECIDA COM SUCESSO!
📊 Informações do Banco:
   - Driver: MySQL Connector/J
   - Versão do Driver: 8.0.33
   - URL: jdbc:mysql://localhost:3306/consultorio
   - Usuário: root
   - Nome do Banco: MySQL
   - Versão do Banco: 8.0.33
```

**Em caso de erro:**
```
❌ ERRO AO CONECTAR COM O BANCO DE DADOS!
🔴 Código do Erro: 1045
🔴 Estado SQL: 28000
🔴 Mensagem: Access denied for user 'root'@'localhost'
💡 Verifique o usuário e senha do MySQL!
```

### Testar Conexão via API
```bash
curl http://localhost:8080/api/pacientes/testar-conexao
```

### Logs Disponíveis
- **Conexão do banco**: Monitora estabelecimento de conexão
- **Queries SQL**: Mostra todas as consultas executadas
- **Performance**: Logs do HikariCP (pool de conexões)
- **Erros**: Detalhamento completo de erros de banco

## Endpoints da API

Base URL: `http://localhost:8080/api/pacientes`

### 1. Criar Paciente
```http
POST /api/pacientes
Content-Type: application/json

{
  "nome": "João Silva",
  "cpf": "123.456.789-00",
  "rg": "123456789",
  "dataNascimento": "1990-05-15",
  "sexo": "MASCULINO",
  "telefone": "(11) 99999-9999",
  "email": "joao@email.com",
  "endereco": "Rua das Flores, 123",
  "cidade": "São Paulo",
  "estado": "SP",
  "cep": "01234-567",
  "observacoes": "Paciente com alergia a penicilina"
}
```

### 2. Buscar Todos os Pacientes (com paginação)
```http
GET /api/pacientes?pagina=0&tamanho=10&ordenacao=asc
```

### 3. Buscar Paciente por ID
```http
GET /api/pacientes/{id}
```

### 4. Buscar Pacientes por Nome
```http
GET /api/pacientes/buscar?nome=João
```

### 5. Buscar Paciente por CPF
```http
GET /api/pacientes/cpf/{cpf}
```

### 6. Buscar Paciente por RG
```http
GET /api/pacientes/rg/{rg}
```

### 7. Buscar Pacientes por Cidade
```http
GET /api/pacientes/cidade/{cidade}
```

### 8. Buscar Pacientes por Estado
```http
GET /api/pacientes/estado/{estado}
```

### 9. Buscar por Faixa Etária
```http
GET /api/pacientes/faixa-etaria?dataInicio=1990-01-01&dataFim=2000-12-31
```

### 10. Atualizar Paciente
```http
PUT /api/pacientes/{id}
Content-Type: application/json

{
  "nome": "João Silva Santos",
  "cpf": "123.456.789-00",
  "rg": "123456789",
  "dataNascimento": "1990-05-15",
  "sexo": "MASCULINO",
  "telefone": "(11) 88888-8888",
  "email": "joao.santos@email.com",
  "endereco": "Rua das Flores, 123",
  "cidade": "São Paulo",
  "estado": "SP",
  "cep": "01234-567",
  "observacoes": "Paciente com alergia a penicilina"
}
```

### 11. Desativar Paciente (Soft Delete)
```http
PATCH /api/pacientes/{id}/desativar
```

### 12. Reativar Paciente
```http
PATCH /api/pacientes/{id}/reativar
```

### 13. Excluir Paciente Permanentemente
```http
DELETE /api/pacientes/{id}
```

### 14. Contar Pacientes Ativos
```http
GET /api/pacientes/contar
```

### 15. Testar Conexão com Banco
```http
GET /api/pacientes/testar-conexao
```

## Campos da Entidade Paciente

| Campo | Tipo | Obrigatório | Descrição |
|-------|------|-------------|-----------|
| id | Long | Não | ID único (gerado automaticamente) |
| nome | String | Sim | Nome completo do paciente |
| cpf | String | Sim | CPF no formato 000.000.000-00 |
| rg | String | Sim | RG do paciente |
| dataNascimento | LocalDate | Não | Data de nascimento |
| sexo | Sexo | Sim | MASCULINO, FEMININO ou OUTRO |
| telefone | String | Sim | Telefone no formato (00) 00000-0000 |
| email | String | Não | Email válido |
| endereco | String | Sim | Endereço completo |
| cidade | String | Sim | Cidade |
| estado | String | Sim | Estado (sigla de 2 letras) |
| cep | String | Não | CEP no formato 00000-000 |
| dataCadastro | LocalDateTime | Não | Data de cadastro (automática) |
| dataAtualizacao | LocalDateTime | Não | Data da última atualização (automática) |
| observacoes | String | Não | Observações médicas |
| ativo | Boolean | Não | Status do paciente (padrão: true) |

## Validações Implementadas

- **CPF único**: Não permite cadastrar pacientes com o mesmo CPF
- **RG único**: Não permite cadastrar pacientes com o mesmo RG
- **Telefone único**: Não permite cadastrar pacientes com o mesmo telefone
- **Campos obrigatórios**: Nome, CPF, RG, Sexo, Telefone, Endereço, Cidade, Estado

## Códigos de Resposta HTTP

- **200 OK**: Operação realizada com sucesso
- **201 Created**: Paciente criado com sucesso
- **400 Bad Request**: Dados inválidos ou regras de negócio violadas
- **404 Not Found**: Paciente não encontrado
- **500 Internal Server Error**: Erro interno do servidor

## Exemplos de Uso

### Criar um novo paciente:
```bash
curl -X POST http://localhost:8080/api/pacientes \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Maria Santos",
    "cpf": "987.654.321-00",
    "rg": "987654321",
    "sexo": "FEMININO",
    "telefone": "(11) 88888-8888",
    "endereco": "Av. Paulista, 1000",
    "cidade": "São Paulo",
    "estado": "SP"
  }'
```

### Buscar todos os pacientes:
```bash
curl http://localhost:8080/api/pacientes
```

### Buscar paciente por CPF:
```bash
curl http://localhost:8080/api/pacientes/cpf/987.654.321-00
```

## Estrutura do Projeto

```
src/main/java/lemes/consultorio/
├── ConsultorioApplication.java          # Classe principal
├── entity/
│   ├── Paciente.java                   # Entidade Paciente
│   └── Sexo.java                       # Enum Sexo
├── repository/
│   └── PacienteRepository.java         # Repositório JPA
├── service/
│   └── PacienteService.java            # Lógica de negócio
└── controller/
    └── PacienteController.java         # Endpoints REST
```

## Próximos Passos

Este sistema pode ser expandido com:
- Cadastro de médicos
- Agendamento de consultas
- Prontuário médico
- Relatórios
- Autenticação e autorização
- Documentação com Swagger/OpenAPI
