# 📋 Documentação Completa da API - Sistema de Consultório

## 🚀 Informações Gerais

- **Base URL**: `http://localhost:8080`
- **Versão**: 1.0.0
- **Tecnologia**: Spring Boot 3.5.6 + MySQL
- **Formato**: JSON
- **Autenticação**: Não implementada (pode ser adicionada futuramente)

---

## 📊 Entidade Paciente

### Campos da Entidade

| Campo | Tipo | Obrigatório | Descrição | Exemplo |
|-------|------|-------------|-----------|---------|
| `id` | Long | ❌ | ID único (gerado automaticamente) | 1 |
| `nome` | String | ✅ | Nome completo do paciente | "João Silva Santos" |
| `cpf` | String | ✅ | CPF no formato 000.000.000-00 | "123.456.789-00" |
| `rg` | String | ✅ | RG do paciente | "123456789" |
| `dataNascimento` | LocalDate | ❌ | Data de nascimento | "1990-05-15" |
| `sexo` | Enum | ✅ | MASCULINO, FEMININO ou OUTRO | "MASCULINO" |
| `telefone` | String | ✅ | Telefone no formato (00) 00000-0000 | "(11) 99999-9999" |
| `email` | String | ❌ | Email válido | "joao@email.com" |
| `endereco` | String | ✅ | Endereço completo | "Rua das Flores, 123" |
| `cidade` | String | ✅ | Cidade | "São Paulo" |
| `estado` | String | ✅ | Estado (sigla de 2 letras) | "SP" |
| `cep` | String | ❌ | CEP no formato 00000-000 | "01234-567" |
| `dataCadastro` | LocalDateTime | ❌ | Data de cadastro (automática) | "2024-01-15T10:30:00" |
| `dataAtualizacao` | LocalDateTime | ❌ | Data da última atualização | "2024-01-15T15:45:00" |
| `observacoes` | String | ❌ | Observações médicas | "Alergia a penicilina" |
| `ativo` | Boolean | ❌ | Status do paciente (padrão: true) | true |

---

## 🔗 Endpoints da API

### Base URL: `/api/pacientes`

---

## 1. 📝 Criar Paciente

**Cria um novo paciente no sistema.**

```http
POST /api/pacientes
Content-Type: application/json
```

#### Request Body:
```json
{
  "nome": "João Silva Santos",
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

#### Response (201 Created):
```json
{
  "id": 1,
  "nome": "João Silva Santos",
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
  "dataCadastro": "2024-01-15T10:30:00",
  "dataAtualizacao": null,
  "observacoes": "Paciente com alergia a penicilina",
  "ativo": true
}
```

#### Códigos de Resposta:
- `201 Created`: Paciente criado com sucesso
- `400 Bad Request`: Dados inválidos ou CPF/RG/Telefone já existem
- `500 Internal Server Error`: Erro interno do servidor

---

## 2. 📋 Listar Pacientes (com Paginação)

**Lista todos os pacientes ativos com paginação.**

```http
GET /api/pacientes?pagina=0&tamanho=10&ordenacao=asc&nome=filtro
```

#### Query Parameters:
| Parâmetro | Tipo | Padrão | Descrição |
|-----------|------|--------|-----------|
| `pagina` | int | 0 | Número da página (começa em 0) |
| `tamanho` | int | 10 | Número de itens por página |
| `ordenacao` | string | "asc" | Ordenação: "asc" ou "desc" |
| `nome` | string | null | Filtro por nome (opcional) |

#### Response (200 OK):
```json
{
  "content": [
    {
      "id": 1,
      "nome": "João Silva Santos",
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
      "dataCadastro": "2024-01-15T10:30:00",
      "dataAtualizacao": null,
      "observacoes": "Paciente com alergia a penicilina",
      "ativo": true
    }
  ],
  "pageable": {
    "sort": {
      "sorted": true,
      "unsorted": false
    },
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalElements": 1,
  "totalPages": 1,
  "last": true,
  "first": true,
  "numberOfElements": 1
}
```

---

## 3. 🔍 Buscar Paciente por ID

**Busca um paciente específico pelo ID.**

```http
GET /api/pacientes/{id}
```

#### Path Parameters:
| Parâmetro | Tipo | Descrição |
|-----------|------|-----------|
| `id` | Long | ID do paciente |

#### Response (200 OK):
```json
{
  "id": 1,
  "nome": "João Silva Santos",
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
  "dataCadastro": "2024-01-15T10:30:00",
  "dataAtualizacao": null,
  "observacoes": "Paciente com alergia a penicilina",
  "ativo": true
}
```

#### Códigos de Resposta:
- `200 OK`: Paciente encontrado
- `404 Not Found`: Paciente não encontrado

---

## 4. 🔎 Buscar Pacientes por Nome

**Busca pacientes que contenham o nome especificado.**

```http
GET /api/pacientes/buscar?nome=João
```

#### Query Parameters:
| Parâmetro | Tipo | Obrigatório | Descrição |
|-----------|------|-------------|-----------|
| `nome` | string | ✅ | Nome ou parte do nome para buscar |

#### Response (200 OK):
```json
[
  {
    "id": 1,
    "nome": "João Silva Santos",
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
    "dataCadastro": "2024-01-15T10:30:00",
    "dataAtualizacao": null,
    "observacoes": "Paciente com alergia a penicilina",
    "ativo": true
  }
]
```

---

## 5. 🆔 Buscar Paciente por CPF

**Busca um paciente específico pelo CPF.**

```http
GET /api/pacientes/cpf/{cpf}
```

#### Path Parameters:
| Parâmetro | Tipo | Descrição |
|-----------|------|-----------|
| `cpf` | string | CPF no formato 000.000.000-00 |

#### Response (200 OK):
```json
{
  "id": 1,
  "nome": "João Silva Santos",
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
  "dataCadastro": "2024-01-15T10:30:00",
  "dataAtualizacao": null,
  "observacoes": "Paciente com alergia a penicilina",
  "ativo": true
}
```

#### Códigos de Resposta:
- `200 OK`: Paciente encontrado
- `404 Not Found`: Paciente não encontrado

---

## 6. 🆔 Buscar Paciente por RG

**Busca um paciente específico pelo RG.**

```http
GET /api/pacientes/rg/{rg}
```

#### Path Parameters:
| Parâmetro | Tipo | Descrição |
|-----------|------|-----------|
| `rg` | string | RG do paciente |

#### Response (200 OK):
```json
{
  "id": 1,
  "nome": "João Silva Santos",
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
  "dataCadastro": "2024-01-15T10:30:00",
  "dataAtualizacao": null,
  "observacoes": "Paciente com alergia a penicilina",
  "ativo": true
}
```

---

## 7. 🏙️ Buscar Pacientes por Cidade

**Lista todos os pacientes de uma cidade específica.**

```http
GET /api/pacientes/cidade/{cidade}
```

#### Path Parameters:
| Parâmetro | Tipo | Descrição |
|-----------|------|-----------|
| `cidade` | string | Nome da cidade |

#### Response (200 OK):
```json
[
  {
    "id": 1,
    "nome": "João Silva Santos",
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
    "dataCadastro": "2024-01-15T10:30:00",
    "dataAtualizacao": null,
    "observacoes": "Paciente com alergia a penicilina",
    "ativo": true
  }
]
```

---

## 8. 🗺️ Buscar Pacientes por Estado

**Lista todos os pacientes de um estado específico.**

```http
GET /api/pacientes/estado/{estado}
```

#### Path Parameters:
| Parâmetro | Tipo | Descrição |
|-----------|------|-----------|
| `estado` | string | Sigla do estado (2 letras) |

#### Response (200 OK):
```json
[
  {
    "id": 1,
    "nome": "João Silva Santos",
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
    "dataCadastro": "2024-01-15T10:30:00",
    "dataAtualizacao": null,
    "observacoes": "Paciente com alergia a penicilina",
    "ativo": true
  }
]
```

---

## 9. 🎂 Buscar Pacientes por Faixa Etária

**Lista pacientes nascidos em uma faixa etária específica.**

```http
GET /api/pacientes/faixa-etaria?dataInicio=1990-01-01&dataFim=2000-12-31
```

#### Query Parameters:
| Parâmetro | Tipo | Obrigatório | Descrição |
|-----------|------|-------------|-----------|
| `dataInicio` | string | ✅ | Data de início (YYYY-MM-DD) |
| `dataFim` | string | ✅ | Data de fim (YYYY-MM-DD) |

#### Response (200 OK):
```json
[
  {
    "id": 1,
    "nome": "João Silva Santos",
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
    "dataCadastro": "2024-01-15T10:30:00",
    "dataAtualizacao": null,
    "observacoes": "Paciente com alergia a penicilina",
    "ativo": true
  }
]
```

---

## 10. ✏️ Atualizar Paciente

**Atualiza os dados de um paciente existente.**

```http
PUT /api/pacientes/{id}
Content-Type: application/json
```

#### Path Parameters:
| Parâmetro | Tipo | Descrição |
|-----------|------|-----------|
| `id` | Long | ID do paciente |

#### Request Body:
```json
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

#### Response (200 OK):
```json
{
  "id": 1,
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
  "dataCadastro": "2024-01-15T10:30:00",
  "dataAtualizacao": "2024-01-15T15:45:00",
  "observacoes": "Paciente com alergia a penicilina",
  "ativo": true
}
```

#### Códigos de Resposta:
- `200 OK`: Paciente atualizado com sucesso
- `400 Bad Request`: Dados inválidos ou CPF/RG já existem em outro paciente
- `404 Not Found`: Paciente não encontrado

---

## 11. 🚫 Desativar Paciente (Soft Delete)

**Desativa um paciente (soft delete).**

```http
PATCH /api/pacientes/{id}/desativar
```

#### Path Parameters:
| Parâmetro | Tipo | Descrição |
|-----------|------|-----------|
| `id` | Long | ID do paciente |

#### Response (200 OK):
```json
"Paciente desativado com sucesso"
```

#### Códigos de Resposta:
- `200 OK`: Paciente desativado com sucesso
- `400 Bad Request`: Paciente não encontrado

---

## 12. ✅ Reativar Paciente

**Reativa um paciente previamente desativado.**

```http
PATCH /api/pacientes/{id}/reativar
```

#### Path Parameters:
| Parâmetro | Tipo | Descrição |
|-----------|------|-----------|
| `id` | Long | ID do paciente |

#### Response (200 OK):
```json
"Paciente reativado com sucesso"
```

#### Códigos de Resposta:
- `200 OK`: Paciente reativado com sucesso
- `400 Bad Request`: Paciente não encontrado

---

## 13. 🗑️ Excluir Paciente Permanentemente

**Remove um paciente permanentemente do banco de dados.**

```http
DELETE /api/pacientes/{id}
```

#### Path Parameters:
| Parâmetro | Tipo | Descrição |
|-----------|------|-----------|
| `id` | Long | ID do paciente |

#### Response (200 OK):
```json
"Paciente excluído com sucesso"
```

#### Códigos de Resposta:
- `200 OK`: Paciente excluído com sucesso
- `400 Bad Request`: Paciente não encontrado

---

## 14. 📊 Contar Pacientes Ativos

**Retorna o número total de pacientes ativos.**

```http
GET /api/pacientes/contar
```

#### Response (200 OK):
```json
"Total de pacientes ativos: 25"
```

---

## 15. 🔧 Testar Conexão com Banco

**Testa a conexão com o banco de dados.**

```http
GET /api/pacientes/testar-conexao
```

#### Response (200 OK):
```json
"✅ Conexão com o banco de dados está funcionando!"
```

#### Response (500 Internal Server Error):
```json
"❌ Erro na conexão: Access denied for user 'root'@'localhost'"
```

---

## 🚨 Códigos de Erro Comuns

### 400 Bad Request
```json
{
  "error": "CPF já cadastrado: 123.456.789-00"
}
```

```json
{
  "error": "RG já cadastrado: 123456789"
}
```

```json
{
  "error": "Nome é obrigatório"
}
```

### 404 Not Found
```json
{
  "error": "Paciente não encontrado com ID: 999"
}
```

### 500 Internal Server Error
```json
{
  "error": "Erro interno do servidor: Connection refused"
}
```

---

## 📝 Exemplos de Uso com cURL

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

### Atualizar paciente:
```bash
curl -X PUT http://localhost:8080/api/pacientes/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Maria Santos Silva",
    "cpf": "987.654.321-00",
    "rg": "987654321",
    "sexo": "FEMININO",
    "telefone": "(11) 77777-7777",
    "endereco": "Av. Paulista, 1000",
    "cidade": "São Paulo",
    "estado": "SP"
  }'
```

### Desativar paciente:
```bash
curl -X PATCH http://localhost:8080/api/pacientes/1/desativar
```

---

## 🔧 Configuração e Execução

### Executar a aplicação:
```bash
# Com MySQL
.\mvnw.cmd spring-boot:run
```

### URLs importantes:
- **API Base**: `http://localhost:8080/api/pacientes`
- **Teste de Conexão**: `http://localhost:8080/api/pacientes/testar-conexao`

---

## 📋 Validações Implementadas

- ✅ **CPF único**: Não permite cadastrar pacientes com o mesmo CPF
- ✅ **RG único**: Não permite cadastrar pacientes com o mesmo RG
- ✅ **Telefone único**: Não permite cadastrar pacientes com o mesmo telefone
- ✅ **Campos obrigatórios**: Nome, CPF, RG, Sexo, Telefone, Endereço, Cidade, Estado
- ✅ **Formato de data**: Aceita formato ISO (YYYY-MM-DD)
- ✅ **Soft delete**: Desativação em vez de exclusão permanente

---

## 🎯 Próximos Passos

Este sistema pode ser expandido com:
- 🔐 **Autenticação e autorização** (JWT, OAuth2)
- 👨‍⚕️ **Cadastro de médicos**
- 📅 **Sistema de agendamentos**
- 📋 **Prontuário médico**
- 📊 **Relatórios e dashboards**
- 📱 **Documentação com Swagger/OpenAPI**
- 🧪 **Testes automatizados**
- 🐳 **Containerização com Docker**
