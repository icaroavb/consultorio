package lemes.consultorio.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

@Component
public class DatabaseConfig implements CommandLineRunner {
    
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);
    
    @Autowired
    private DataSource dataSource;
    
    @Override
    public void run(String... args) throws Exception {
        testarConexao();
    }
    
    public void testarConexao() {
        logger.info("🔍 Testando conexão com o banco de dados...");
        
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            
            logger.info("✅ CONEXÃO ESTABELECIDA COM SUCESSO!");
            logger.info("📊 Informações do Banco:");
            logger.info("   - Driver: {}", metaData.getDriverName());
            logger.info("   - Versão do Driver: {}", metaData.getDriverVersion());
            logger.info("   - URL: {}", metaData.getURL());
            logger.info("   - Usuário: {}", metaData.getUserName());
            logger.info("   - Nome do Banco: {}", metaData.getDatabaseProductName());
            logger.info("   - Versão do Banco: {}", metaData.getDatabaseProductVersion());
            
        } catch (SQLException e) {
            logger.error("❌ ERRO AO CONECTAR COM O BANCO DE DADOS!");
            logger.error("🔴 Código do Erro: {}", e.getErrorCode());
            logger.error("🔴 Estado SQL: {}", e.getSQLState());
            logger.error("🔴 Mensagem: {}", e.getMessage());
            
            // Mensagens de erro mais amigáveis
            if (e.getMessage().contains("Access denied")) {
                logger.error("💡 Verifique o usuário e senha do MySQL!");
            } else if (e.getMessage().contains("Unknown database")) {
                logger.error("💡 Crie o banco de dados 'consultorio' no MySQL!");
            } else if (e.getMessage().contains("Connection refused")) {
                logger.error("💡 Verifique se o MySQL está rodando na porta 3306!");
            } else if (e.getMessage().contains("timeout")) {
                logger.error("💡 Verifique se o MySQL está acessível!");
            }
            
            throw new RuntimeException("Falha na conexão com o banco de dados", e);
        }
    }
}
