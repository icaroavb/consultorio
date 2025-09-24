# Script para cadastrar 15 pacientes no sistema de consultório
# Execute este script com a aplicação rodando

$baseUrl = "http://localhost:8080/api/pacientes"
$jsonFile = "pacientes_exemplo.json"

Write-Host "🚀 Iniciando cadastro de pacientes..." -ForegroundColor Green

# Verificar se o arquivo JSON existe
if (-not (Test-Path $jsonFile)) {
    Write-Host "❌ Arquivo $jsonFile não encontrado!" -ForegroundColor Red
    exit 1
}

# Ler o arquivo JSON
try {
    $pacientes = Get-Content $jsonFile | ConvertFrom-Json
    Write-Host "📋 Carregados $($pacientes.Count) pacientes do arquivo JSON" -ForegroundColor Blue
} catch {
    Write-Host "❌ Erro ao ler o arquivo JSON: $($_.Exception.Message)" -ForegroundColor Red
    exit 1
}

# Verificar se a API está rodando
try {
    $response = Invoke-RestMethod -Uri "$baseUrl/testar-conexao" -Method GET -TimeoutSec 5
    Write-Host "✅ API está funcionando: $response" -ForegroundColor Green
} catch {
    Write-Host "❌ API não está respondendo. Certifique-se de que a aplicação está rodando em $baseUrl" -ForegroundColor Red
    Write-Host "Execute: .\mvnw.cmd spring-boot:run" -ForegroundColor Yellow
    exit 1
}

# Cadastrar cada paciente
$sucesso = 0
$erro = 0

foreach ($paciente in $pacientes) {
    try {
        $jsonBody = $paciente | ConvertTo-Json -Depth 10
        $response = Invoke-RestMethod -Uri $baseUrl -Method POST -Body $jsonBody -ContentType "application/json" -TimeoutSec 10
        
        Write-Host "✅ Paciente cadastrado: $($paciente.nome) (ID: $($response.id))" -ForegroundColor Green
        $sucesso++
        
        # Pequena pausa entre as requisições
        Start-Sleep -Milliseconds 500
        
    } catch {
        $erro++
        if ($_.Exception.Response.StatusCode -eq 400) {
            Write-Host "⚠️  Erro de validação para $($paciente.nome): $($_.Exception.Message)" -ForegroundColor Yellow
        } else {
            Write-Host "❌ Erro ao cadastrar $($paciente.nome): $($_.Exception.Message)" -ForegroundColor Red
        }
    }
}

# Resumo final
Write-Host "`n📊 RESUMO DO CADASTRO:" -ForegroundColor Cyan
Write-Host "✅ Sucessos: $sucesso" -ForegroundColor Green
Write-Host "❌ Erros: $erro" -ForegroundColor Red
Write-Host "📋 Total processados: $($sucesso + $erro)" -ForegroundColor Blue

if ($sucesso -gt 0) {
    Write-Host "`n🎉 Cadastro concluído! Você pode verificar os pacientes em:" -ForegroundColor Green
    Write-Host "   GET $baseUrl" -ForegroundColor Yellow
    Write-Host "   GET $baseUrl/contar" -ForegroundColor Yellow
}

Write-Host "`n💡 Para testar outros endpoints, consulte a documentação em DOCUMENTACAO_API.md" -ForegroundColor Magenta
