package br.com.mda.ws.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;

import br.com.mda.model.Agendamento;
import br.com.mda.model.Cliente;
import br.com.mda.model.Email;
import br.com.mda.model.Funcionario;
import br.com.mda.model.Usuario;
import br.com.mda.util.SalvarEnviarLogs;
import br.com.mda.ws.service.AgendamentoService;
import br.com.mda.ws.service.ClienteService;
import br.com.mda.ws.service.FuncionarioService;
import br.com.mda.ws.service.UsuarioService;

@RestController
public class AgendaController {

	@Autowired
	private FuncionarioService funcionarioService;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private AgendamentoService agendamentoService;

	@Autowired
	private UsuarioService usuarioService;

	private SmsClientController smsController = new SmsClientController();

	public static String COR_AGENDAMENTO_DEFAULT = "#0A6CAC";
	public static String COR_AGENDAMENTO_NAO_COMPARECEU = "#FF0000";
	public static String COR_AGENDAMENTO_FECHADO = "#00FF00";
	public static String password = "tccmda18";

	private List<Agendamento> listaAgendamentos;

	/** Application name. */
	private static final String APPLICATION_NAME = "mda";

	/** Directory to store user credentials for this application. */
	private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"),
			".credentials/mda");

	/** Global instance of the {@link FileDataStoreFactory}. */
	private static FileDataStoreFactory DATA_STORE_FACTORY;

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	/** Global instance of the HTTP transport. */
	private static HttpTransport HTTP_TRANSPORT;



	@RequestMapping(value = "/listarAgendamentos", method = {
			RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Agendamento> listarAgendamentos(@RequestParam("dataInicial") String dataInicial,
			@RequestParam("dataFinal") String dataFinal, Principal user) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar di = Calendar.getInstance();
		Calendar df = Calendar.getInstance();
		List<Agendamento> agendamentos = new ArrayList<Agendamento>();

		try {
			di.setTime(format.parse(dataInicial));
			df.setTime(format.parse(dataFinal));
		} catch (ParseException e) {
			SalvarEnviarLogs.gravarArquivo(e);
			throw new Exception("Erro ao listar agendamentos: formato de data inválido.");
		}
		Usuario usuario = new Usuario();
		Funcionario funcionario = new Funcionario();
		Cliente cliente = new Cliente();
		if (user != null) {
			usuario = this.usuarioService.buscaPorLogin(user.getName());
		}
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if (usuario.getTipoUsuario().equals("F")) {
			funcionario = funcionarioService.buscaPorLogin(user.getName());
			agendamentos = agendamentoService.listarPorPeriodoePorProfissional(di, df, funcionario);
		}else if (usuario.getTipoUsuario().equals("C")) {
			cliente = clienteService.buscarPorId(usuario.getIdUsuario());
			agendamentos = agendamentoService.listarPorPeriodoePorPaciente(di, df, cliente);
		}else{
			agendamentos = agendamentoService.listarAllPorPeriodo(di, df);
			
		}
		System.out.println("listarAgendamentos: fim");
		return agendamentos;
	}

	@RequestMapping(value = "/removerAgendamento", method = {
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public void removerAgendamento(@RequestBody Agendamento agendamento, Principal user) throws Exception {
		System.out.println("removerAgendamento: início");
		if (agendamento == null) {
			System.out.println("Agendamento recebido nulo");
			throw new Exception("Não foi possível remover o agendamento.");
		}
		agendamentoService.delete(agendamento);

		System.out.println("removerAgendamento: fim");
	}

	@RequestMapping(value = "/salvarAgendamento", method = {
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Agendamento salvarAgendamento(@RequestBody Agendamento agendamento, Principal user) throws Exception {
		System.out.println("salvarAgendamento: início");
		boolean ehEdicao = false;

		if (agendamento.getId() != null) {
			ehEdicao = true;
		}

		Funcionario funcionario;

		if (user != null) {
			System.out.println("user.getName(): " + user.getName());

			funcionario = funcionarioService.buscaPorLogin(user.getName());

			if (agendamento.getFuncionario() == null) {
				agendamento.setFuncionario(funcionario);
			}

			if (funcionario == null) {
				System.out.println("Funcionario nulo em getFuncionarioLogado");

				throw new Exception("Erro ao carregar funcionario. Faça login novamente.");
			}

		} else {
			System.out.println("user nulo em getFuncionarioLogado");
			throw new Exception("Erro ao carregar funcionario. Faça login novamente.");
		}

		if (agendamento.isNaoCompareceu()) {
			agendamento.setColor(COR_AGENDAMENTO_NAO_COMPARECEU);
		} else if (agendamento.getValor() != null) {
			agendamento.setFechado(true);
			agendamento.setColor(COR_AGENDAMENTO_FECHADO);
		} else {
			agendamento.setColor(COR_AGENDAMENTO_DEFAULT);
		}

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");

		sdf.setCalendar(agendamento.getStart());

		String dateFormatted = sdf.format(agendamento.getStart().getTime());
		Calendar atual = Calendar.getInstance();
		if (agendamento.getStart().after(atual)) {
			agendamento.setNaoCompareceu(false);
			agendamento.setColor(COR_AGENDAMENTO_DEFAULT);
		}

		agendamento = agendamentoService.salvar(agendamento);

		if (agendamento == null) {
			System.out.println("Erro ao salvar no BD.");
			throw new Exception("Não foi possível salvar o agendamento!");
		} else {
			if (!ehEdicao && agendamento.getStart().getTime().after(new Date())) {
				Email email = new Email();
				// Pra quem vai mandar
				email.setTo(agendamento.getCliente().getEmail());
				email.setCc(agendamento.getFuncionario().getEmail());
				// Quem ta mandando
				email.setFrom("sistema.mda.tcc@gmail.com");
				// senha
				email.setPass(password);
				email.setTexto("");
				email.setEmailFormatado("<html>" + "<body>" + "<div style=\"text-align: center;\">"
						+ "<span style=\"font-size:16px;\">Olá, " + agendamento.getCliente().getNomeCompleto()
						+ ",</span></h2></br>"
						+ "<span style=\"font-size:16px;\">Este &eacute; um e-mail autom&aacute;tico "
						+ "para informar que seu agendamento esta marcado para as "
						+ agendamento.getStart().getTime().getHours() + ":"
						+ agendamento.getStart().getTime().getMinutes() + " do dia " + dateFormatted
						+ ", com o Profissional " + agendamento.getFuncionario().getNomeCompleto() + ".</span></h2>"
						+ "<p>" + "<strong><span style=\"font-size:16px;\">Na MDA,"
						+ " Avenida João Pessoa 1643.</span></strong></p>");
				// comentado para a defesa do TCC, pois a porta do smtp e a
				// porta de envio do sms são bloqueadas no firewall do campus
				SendEmailController sendMail = new SendEmailController(email);
				smsController.EnviaSMS(agendamento);
			}
		}

		if (agendamento.getColor() != null && agendamento.getColor().equals(COR_AGENDAMENTO_NAO_COMPARECEU)) {
			agendamento.setNaoCompareceu(true);
		}

		System.out.println("salvarAgendamento: fim");
		return agendamento;
	}

	@RequestMapping(value = "/editarAgendamento", method = {
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Agendamento editarAgendamento(@RequestBody Agendamento agendamento, Principal user) throws Exception {
		System.out.println("salvarAgendamento: início");

		Funcionario funcionario;

		if (user != null) {
			System.out.println("user.getName(): " + user.getName());

			funcionario = funcionarioService.buscaPorLogin(user.getName());

			if (agendamento.getFuncionario() == null) {
				agendamento.setFuncionario(funcionario);
			}

			if (funcionario == null) {
				System.out.println("Funcionario nulo em getFuncionarioLogado");

				throw new Exception("Erro ao carregar funcionario. Faça login novamente.");
			}

		} else {
			System.out.println("user nulo em getFuncionarioLogado");
			throw new Exception("Erro ao carregar funcionario. Faça login novamente.");
		}

		if (agendamento.isNaoCompareceu()) {
			agendamento.setColor(COR_AGENDAMENTO_NAO_COMPARECEU);
		} else if (agendamento.getValor() != null) {
			agendamento.setFechado(true);
			agendamento.setColor(COR_AGENDAMENTO_FECHADO);
		} else {
			agendamento.setColor(COR_AGENDAMENTO_DEFAULT);
		}

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");

		sdf.setCalendar(agendamento.getStart());

		String dateFormatted = sdf.format(agendamento.getStart().getTime());

		agendamento = agendamentoService.salvar(agendamento);

		if (agendamento == null) {
			System.out.println("Erro ao salvar no BD.");
			throw new Exception("Não foi possível salvar o agendamento!");
		} else {
			if (agendamento.getStart().getTime().after(new Date())) {
				Email email = new Email();
				// Pra quem vai mandar
				email.setTo(agendamento.getCliente().getEmail());
				email.setCc(agendamento.getFuncionario().getEmail());
				// Quem ta mandando
				email.setFrom("sistema.mda.tcc@gmail.com");
				// senha
				email.setPass(password);
				email.setTexto("");
				email.setEmailFormatado("<html>" + "<body>" + "<div style=\"text-align: center;\">"
						+ "<span style=\"font-size:16px;\">Olá, " + agendamento.getCliente().getNomeCompleto()
						+ ",</span></h2></br>"
						+ "<span style=\"font-size:16px;\">Este &eacute; um e-mail autom&aacute;tico "
						+ "para informar que seu agendamento esta marcado para as "
						+ agendamento.getStart().getTime().getHours() + ":"
						+ agendamento.getStart().getTime().getMinutes() + " do dia " + dateFormatted
						+ ", com o Profissional " + agendamento.getFuncionario().getNomeCompleto() + ".</span></h2>"
						+ "<p>" + "<strong><span style=\"font-size:16px;\">Na MDA,"
						+ " Avenida João Pessoa 1643.</span></strong></p>");
				// comentado para a defesa do TCC, pois a porta do smtp e a
				// porta de envio do sms são bloqueadas no firewall do campus
				SendEmailController sendMail = new SendEmailController(email);
				// smsController.EnviaSMS(agendamento);
			}
		}

		if (agendamento.getColor() != null && agendamento.getColor().equals(COR_AGENDAMENTO_NAO_COMPARECEU)) {
			agendamento.setNaoCompareceu(true);
		}

		System.out.println("salvarAgendamento: fim");
		return agendamento;
	}

	@RequestMapping(value = "/listarAgendamentosDoDia", method = {
			RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	public int listarAgendamentosDoDia(Principal user) throws Exception {
		System.out.println("AgendaController.listarAgendamentosDoDia: início");
		try {
			Funcionario funcionario = new Funcionario();
			Usuario usuario = new Usuario();

			if (user != null) {
				usuario = usuarioService.buscaPorLogin(user.getName());
				if (usuario.getTipoUsuario().equals("F")) {
					funcionario = funcionarioService.buscaPorLogin(user.getName());

					List<Agendamento> lstAgendamentos = this.agendamentoService.listarAgendamentosDoDia(funcionario);

					System.out.println("AgendaController.listarAgendamentosDoDia: fim");
					return lstAgendamentos.size();

				} else if (usuario.getTipoUsuario().equals("C")) {
					Cliente cliente;
					cliente = clienteService.buscarPorId(usuario.getIdUsuario());

					List<Agendamento> lstAgendamentos = this.agendamentoService.listarAgendamentosDoDiaCliente(cliente);

					System.out.println("AgendaController.listarAgendamentosDoDia: fim");
					return lstAgendamentos.size();

				}
			}
		} catch (Exception ex) {
			SalvarEnviarLogs.gravarArquivo(ex);
			throw new Exception(ex.getMessage());
		}
		return 0;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/listarAgendamentosDoDiaGeral", produces = MediaType.APPLICATION_JSON_VALUE)
	public int listarAgendamentosDoDiaGeral() {
		List<Agendamento> agendamentos = agendamentoService.listarAgendamentosDoDiaGeral();
		return (agendamentos.size());
	}

	@RequestMapping(value = "/listarAgendamentosDoMesPorFuncionario", method = {
			RequestMethod.GET }, produces = MediaType.APPLICATION_JSON_VALUE)
	public int listarAgendamentosDoMesPorFuncionario(Principal user) throws Exception {
		System.out.println("AgendaController.listarAgendamentosDoMesPorFuncionario: início");
		try {
			Funcionario funcionario = new Funcionario();
			Usuario usuario = new Usuario();
			if (user != null) {
				usuario = usuarioService.buscaPorLogin(user.getName());
				if (usuario.getTipoUsuario().equals("F")) {
					funcionario = this.funcionarioService.buscaPorLogin(user.getName());
					if (funcionario == null) {
						System.out.println("Funcionario nulo em getfuncionarioLogado");
						throw new Exception("Erro ao carregar funcionario. Faça login novamente.");
					}

					Calendar dataInicial = Calendar.getInstance();
					dataInicial.set(Calendar.DAY_OF_MONTH, 1);

					Calendar dataFinal = Calendar.getInstance();
					dataFinal.set(Calendar.DAY_OF_MONTH,
							Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));

					List<Agendamento> lstAgendamentos = this.agendamentoService
							.listarAgendamentosDoMesPorFuncionario(dataInicial, dataFinal, funcionario);

					System.out.println("AgendaController.listarAgendamentosDoDia: fim");
					return lstAgendamentos.size();

				} else if (usuario.getTipoUsuario().equals("C")) {
					Cliente cliente;
					cliente = clienteService.buscarPorId(usuario.getIdUsuario());
					if (cliente == null) {
						System.out.println("Cliente nulo em getclienteLogado");
						throw new Exception("Erro ao carregar cliente. Faça login novamente.");
					}

					Calendar dataInicial = Calendar.getInstance();
					dataInicial.set(Calendar.DAY_OF_MONTH, 1);

					Calendar dataFinal = Calendar.getInstance();
					dataFinal.set(Calendar.DAY_OF_MONTH,
							Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));

					List<Agendamento> lstAgendamentos = this.agendamentoService
							.listarAgendamentosDoMesPorCliente(dataInicial, dataFinal, cliente);

					System.out.println("AgendaController.listarAgendamentosDoDia: fim");
					return lstAgendamentos.size();
				}
			}
		} catch (Exception ex) {
			SalvarEnviarLogs.gravarArquivo(ex);
			throw new Exception(ex.getMessage());
		}
		return 0;
	}

}
