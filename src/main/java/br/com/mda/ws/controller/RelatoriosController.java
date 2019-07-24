package br.com.mda.ws.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsPdfView;

import br.com.mda.model.Agendamento;
import br.com.mda.model.Funcionario;
import br.com.mda.model.Evolucao;
import br.com.mda.util.SalvarEnviarLogs;
import br.com.mda.ws.repository.AgendamentoRepository;
import br.com.mda.ws.repository.FuncionarioRepository;
import br.com.mda.ws.service.EvolucaoService;

import org.springframework.context.ApplicationContext;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@RestController
public class RelatoriosController {
	
	@Autowired
	FuncionarioRepository funcionarioRepository;
	
	@Autowired
	AgendamentoRepository agendamentoRepository;
	
	
	@Autowired
    private ApplicationContext appContext;	
	
	@Autowired
	private EvolucaoService evolucaoService;
	
	
	@RequestMapping(
			value = "/imprimirRelatorioevolucao", 
			method={RequestMethod.POST},
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE
			)
	public ModelAndView imprimirRelatorioEvolucao(@RequestBody Evolucao evolucao, 
			Principal user) throws Exception {	
		System.out.println("RelatorioController.imprimirRelatorioEvolucao: início");	
		
		List <Evolucao> evolucoes = evolucaoService.buscarPorPaciente(evolucao.getCliente().getIdUsuario());
		
		JRBeanCollectionDataSource beanColDataSource = 
				new JRBeanCollectionDataSource(evolucoes);
		
		try {												
			JasperReportsPdfView view = new JasperReportsPdfView();
	        view.setUrl("classpath:br/com/mda/jasper/Evolucao.jrxml");
	        view.setApplicationContext(appContext);
	        view.setContentType("application/pdf");	    
	        view.setReportDataKey("datasource");
	        	        
	        Properties p = new Properties();
	        p.setProperty("Content-disposition", "inline; filename=\"relatorioEvolucao.pdf\"");
	        view.setHeaders(p);

	        Map<String, Object> params = new HashMap<>();
	        params.put("cliente", evolucao.getCliente().getNomeCompleto());	        
	        params.put("idEvolucao", evolucao.getIdEvolucao());
	        params.put("data", evolucao.getDataFormatada());
	        params.put("hora", evolucao.getHoraFormatada());
	        params.put("relatorio", evolucao.getRelatorio());
	        params.put("datasource", beanColDataSource);

	        System.out.println("RelatorioController.imprimirRelatorioevolucao: fim");
	        return new ModelAndView(view, params);	
		} catch(Exception ex) {
			System.out.println("Erro ao gerar relatório: " + ex.getMessage());	
			SalvarEnviarLogs.gravarArquivo(ex);
			throw new Exception("Não foi possível gerar o relatório");
		}
	}
	
	
	@RequestMapping(
			value = "/imprimirRelatorioAtestado", 
			method={RequestMethod.POST},
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE
			)
	public ModelAndView imprimirRelatorioAtestado(@RequestBody Agendamento agendamento, 
			Principal user) throws Exception {	
		System.out.println("RelatorioController.imprimirRelatorioAtestado: início");								
		
		Funcionario funcionario;
		if (user != null) {			
			System.out.println("user.getName(): " + user.getName());
			funcionario = this.funcionarioRepository.findByLogin(user.getName());
			if (funcionario == null) {
				System.out.println("Funcionario nulo em getfuncionarioLogado");
				throw new Exception("Erro ao carregar funcionario. Faça login novamente.");
			}		
		} else {
			System.out.println("User nulo em getfuncionarioLogado");
			throw new Exception("Erro ao carregar funcionario. Faça login novamente.");
		}	
		
		try {												
			JasperReportsPdfView view = new JasperReportsPdfView();
	        view.setUrl("classpath:br/com/mda/jasper/Atestado.jrxml");
	        view.setApplicationContext(appContext);
	        view.setContentType("application/pdf");	    
	        view.setReportDataKey("datasource");
	        	        
	        Properties p = new Properties();
	        p.setProperty("Content-disposition", "inline; filename=\"relatorioAtestado.pdf\"");
	        view.setHeaders(p);

	        Map<String, Object> params = new HashMap<>();
	        params.put("cliente", agendamento.getCliente().getNomeCompleto());	        
	        params.put("cpf_cnpj", agendamento.getCliente().getCpf_cnpj());
	        params.put("rua", agendamento.getCliente().getRua());
	        params.put("numero", agendamento.getCliente().getNumero());
	        params.put("datasource", new JREmptyDataSource());

	        System.out.println("RelatorioController.imprimirRelatorioAtestado: fim");
	        return new ModelAndView(view, params);	
		} catch(Exception ex) {
			System.out.println("Erro ao gerar relatório: " + ex.getMessage());	
			SalvarEnviarLogs.gravarArquivo(ex);
			throw new Exception("Não foi possível gerar o relatório");
		}
	}
	
	
}
