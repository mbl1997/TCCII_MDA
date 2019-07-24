package br.com.mda.ws.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.mda.model.Backup;
import br.com.mda.util.SalvarEnviarLogs;
import br.com.mda.util.Util;
import br.com.mda.ws.repository.BackupRepository;

@RestController
public class BackupController {	
	
	private static String dbUser = "root";
	
	private static String dbPassword = ""; 
	
	private static String MYSQL_PATH = "C:\\xampp\\mysql\\bin";
	
	private String dirToSave = "C:\\Backup";
	
	private String qtdMaxArquivos = "10";

	@Autowired
	private BackupRepository backupRepository;
	
	private static String DATABASE = "odontofacil";
	private final static String SEPARATOR = File.separator;
	private final static Logger logger = Logger.getLogger(BackupController.class);
	
	private static void logMessage(String msg, boolean error) {
    	if(!error && logger.isDebugEnabled()){
    	    logger.debug(msg);
    	}

    	//logs an error message with parameter
    	if (error) {
    		logger.error(msg);
    	}
    }
	
	@RequestMapping(
			value = "/realizarBackup", 
			method={RequestMethod.GET},
			produces = MediaType.APPLICATION_JSON_VALUE					
			)			
	public void realizarBackup() throws Exception {						
		logMessage("realizarBackup(): Início", false);
		
		
		Backup todayBackup = this.backupRepository.executouBackupHoje();		
		if (todayBackup != null) {			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");			
			logMessage("Backup já realizado hoje às " + format.format(todayBackup.getInicio().getTime()), false);						
		} else {
			File file = new File(MYSQL_PATH + "\\mysqldump.exe");
			if (!file.exists()) {
				logMessage("Não foi possível localizar o arquivo mysqldump em " + MYSQL_PATH, true);
				throw new Exception("Não foi possível realizar o backup!");
			} else {	
				file = new File(dirToSave);
				if (!file.exists()) {
					logMessage("Não foi possível localizar o diretório para salvar o backup: " + dirToSave, true);
					throw new Exception("Não foi possível realizar o backup!");
				}		
								
				// Apaga arquivos de backup excedentes, caso existam
				this.realizarManutencaoArquivosBackup();
								
				String plainText = Util.decrypt(this.dbPassword);
				
				SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				Calendar inicio = Calendar.getInstance();
				logMessage("Início do backup: " + format1.format(inicio.getTime()), false);
				
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd'_'HHmm");
				String command = MYSQL_PATH + SEPARATOR + "mysqldump.exe";
				String resultFile = this.dirToSave + SEPARATOR + DATABASE + "_" + format.format(inicio.getTime()) + ".sql";
				ProcessBuilder pb = new ProcessBuilder(
				        command,
				        "--user=" + this.dbUser,
				        "--password=" + plainText,
				        "--single-transaction",
				        "--databases",
				        DATABASE,
				        "--verbose",
				        "--result-file=" + resultFile);
								
				try {														
					Process process = pb.start();
					process.waitFor(); // Aguarda o fim do backup
					
					// criptografa arquivo de backup					
					File sqlFile = new File(resultFile);
					if (sqlFile.exists()) {							
						FileWriter fw = null;
						BufferedWriter out = null; 
						try {
							logMessage("Início da criptografia do sql", false);
							
							fw = new FileWriter(resultFile + ".enc");
							out = new BufferedWriter(fw);
							List<String> linhas = Files.readAllLines(Paths.get(resultFile), Charset.forName("ISO-8859-1"));
							logMessage("Número de linhas lidas: " + linhas.size(), false);
							
							for (String linha : linhas) {								
								out.write(Util.encrypt(linha));
								out.newLine();
							}
							logMessage("Fim da criptografia do sql", false);
							
							// Apaga arquivo sql
							sqlFile.delete();							
						} catch(Exception ex) {
							logMessage("Erro ao criptografar arquivo de backup: " + ex.getMessage(), true);
							SalvarEnviarLogs.gravarArquivo(ex);
							throw new Exception("Erro ao criptografar arquivo de backup. Informe imediatamente o administrador do sistema!");
						} finally {				         
				            out.close();
				        }																																	
					}
					
					Calendar fim = Calendar.getInstance();
					logMessage("Fim do backup: " + format1.format(fim.getTime()), false);
					
					Backup backup = new Backup(inicio, fim);
					this.backupRepository.save(backup);
				} catch(Exception ex) {
					SalvarEnviarLogs.gravarArquivo(ex);
					logMessage("Erro: " + ex.getMessage(), true);
					throw new Exception("Não foi possível realizar o backup!");
				}
			}			
		}
		logMessage("realizarBackup(): Fim", false);
	}
	
	/**
	 * Apaga os arquivos excedentes de backup conforme configuração do arquivo application.yml
	 * @throws Exception
	 */
	private void realizarManutencaoArquivosBackup() throws Exception {		
		try {						
			// Apaga Arquivos excedentes, caso existam
			File dir = new File(this.dirToSave);
			// Apenas arquivos sql serão colocados na lista
			File[] files = dir.listFiles(new FilenameFilter() {
			    public boolean accept(File dir, String name) {
			        return name.toLowerCase().endsWith(".enc");
			    }
			});
			
			int qtdMaxArq = Integer.parseInt(this.qtdMaxArquivos);
			int qtdArqDir = files.length;
			int qtdArqExc = qtdArqDir - qtdMaxArq;
			logMessage("Qtd arquivos de backup excedentes: " + qtdArqExc, false);
			if (qtdArqExc > 0) {												
				// Ordena em ordem crescente. Os primeiros arquivos serão os mais antigos.
				Arrays.sort(files);
				for (File file : files) {					
					logMessage("Arquivo de backup " + file.getName() + " deletado.", false);
					file.delete();
					if (--qtdArqExc == 0) {
						break;
					}
				}
			}
			
		} catch (NumberFormatException ex) {
			SalvarEnviarLogs.gravarArquivo(ex);
			logMessage("qtdMaxArquivos inválido no arquivo application.yml: " + this.qtdMaxArquivos, true);
			throw new Exception("Configuração de quantidade de arquivos de backup inválida!");
		} catch (Exception ex) {
			SalvarEnviarLogs.gravarArquivo(ex);
			logMessage("Erro ao realizar manutenção do backup: " + ex.getMessage(), true);			
		}
	}
}
