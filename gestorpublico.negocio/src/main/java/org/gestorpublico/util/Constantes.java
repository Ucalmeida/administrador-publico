package org.gestorpublico.util;

import java.util.Objects;
import java.util.regex.Pattern;

public class Constantes {

	//LOCAL | PRODUCAO WINDOWS
	public final static String LOCAL = (System.getProperty("os.name").toLowerCase().contains("mac"))
			        ? "/Users/diegoandrade/PORTAL/"
			        : "C:/PORTAL/";

	public final String DOMINIO = "portal";
	public final static String PORTAL = System.getProperty("os.name").toLowerCase().contains("linux")
			? "/home/operador/PORTAL/"
			: LOCAL;

	public final static String ENDERECO = (Objects.nonNull(System.getenv("AMBIENTE")) && System.getenv("AMBIENTE").equals("PROD"))
			? "https://intranet.pm.se.gov.br"
			: "http://localhost:8080";

	public static final String ENDERECO_FTP_DETRAN = "187.17.2.187";
	public static final String USUARIO_FTP_DETRAN = "ftp_detran";
	public static final String SENHA_FTP_DETRAN = "9wGQfSgR";

	public static final String ENDERECO_ANTIGO = "http://intranet.pm.se.gov.br/portalAntigo";

	//Feriados variaveis Nacionais
	public static final String ANO_NOVO = "Ano Novo";
	public static final String CORPUS_CRISTI = "Corpus Cristi";
	public static final String CARNAVAL = "Carnaval";
	public static final String PASCOA = "Pascoa";
	public static final String DIA_DO_TRABALHADOR = "Dia do Trabalhador";
	public static final String SEXTA_SANTA = "Sexta-Feira Santa";
	public static final String TIRADENTES = "Tiradentes";
	public static final String INDEPENDENCIA = "Dia da Independência";
	public static final String APARECIDA = "Nossa Senhora Aparecida";
	public static final String FINADOS = "Dia de Finados";
	public static final String PROCLAMACAO_REPUBLICA = "Proclamação da República";
	public static final String NATAL = "Natal";
	public static final String SAO_JOAO = "São João";
	public static final String SAO_PEDRO = "São Pedro";
	public static final String PCSV = "PCSV";

	//Constantes para o CSM
	public static final String CSM_PENDENTE = "Pendente";
	public static final String CSM_ENTREGUE = "Entregue";
	public static final String CSM_EM_SV_EXTERNO = "Em Serviço Externo";
	public static final String CSM_SV_EX_COMPLETO = "Serviço Externo Completo";
	public static final String CSM_FINALIZADA = "Finalizada";

	//Contatnes para Corregedoria
	public static final int DOCUMENTO_ATIVO = 1;
	public static final int DOCUMENTO_INATIVO = 0;
	public static final int DENUNCIA_ATIVA = 1;
	public static final int DENUNCIA_INATIVA = 0;
	public static final int PROCEDIMENTO_ATIVO = 1;
	public static final int PROCEDIMENTO_INATIVO = 0;
	public static final int TIPO_PROCEDIMENTO_PAL = 612;
	public static final int TIPO_PROCEDIMENTO_PAAE = 613;
	public static final int TIPO_PROCEDIMENTO_CONSELHO_DE_DISCIPLINA =  614;
	public static final int TIPO_PROCEDIMENTO_INQUERITO_POLICIAL_MILITAR = 615;
	public static final int TIPO_PROCEDIMENTO_PAAD = 616;
	public static final int TIPO_PROCEDIMENTO_SINDICANCIA = 617;
	public static final int TIPO_PROCEDIMENTO_INQUERITO_TECNICO = 618;
	public static final int TIPO_PROCEDIMENTO_SINDICANCIA_SUMARIA = 619;
	public static final int TIPO_PROCEDIMENTO_ISO = 620;
	public static final int TIPO_PROCEDIMENTO_CONSELHO_DE_JUSTIFICACAO = 621;
	public static final String ARQUIVOS_CORREGEDORIA = Constantes.PORTAL + "CORREGEDORIA/";
	public static final int STATUS_PROCEDIMENTO_EM_CONFECCAO = 91;
	public static final int STATUS_PROCEDIMENTO_EM_ANÁLISE = 92;
	public static final int STATUS_PROCEDIMENTO_AUTORIZADO = 93;
	public static final int STATUS_PROCEDIMENTO_NAO_AUTORIZADO = 94;
	public static final int STATUS_PROCEDIMENTO_ENCERRADO = 97;
	public static final String SEQUENCIAL_PORTARIA = "Portaria_Procedimento";
	public static final String SESSAO_PROCEDIMENTO = "procedimento";
	public static final String OBJETO_TIPO_DENUNCIA = "Tipo_Denuncia";
	public static final String SEQUENCIAL_DENUNCIA = "Denuncia";


	//Constantes de Objetos
	public static final String OBJETO_ROP = "Rop";
	public static final String OBJETO_ARMA_FOGO = "Arma de Fogo";
	public static final String OBJETO_CALIBRE = "Calibre";
	public static final String OBJETO_OBJETO_CUSTODIADO = "Objeto Custodiado";
	public static final String OBJETO_ENVOLVIDO = "Envolvido";
	public static final String OBJETO_TIPO_VEICULO = "Veiculo";


	//Constantes de Sessao
	public static final String SESSION_ROP_NAME = "ROP";

	//Constantes de Ordem de Serviço
	public static final int ATENDIMENTO = 1;
	public static final int MANUTENCAO = 2;
	public static final int REDES = 3;
	public static final int DESENVOLVIMENTO = 4;

	//Constantes de Ordem de Serviço
	public static final String STATUS_ABERTA = "Aberta";
	public static final String STATUS_ATENDIDA = "Atendida";
	public static final String STATUS_NAO_ATENDIDA = "Não Atendida";
	public static final int STATUS_EM_ATENDIMENTO = 68;
	public static final int STATUS_DESCARGA = 78;
	public static final int STATUS_ENTREGUE = 74;
	public static final int STATUS_FINALIZADA = 69;
	public static final int STATUS_NAO_RESOLVIDA = 73;


	//Constantes de tipo
	public static final int TIPO_ROP_FLAGRANTE = 19;
	public static final int TIPO_ROP_TCO = 20;
	public static final int TIPO_ROP_COMUNICACAO = 21;
	public static final int TIPO_ROP_TESTE = 89;
	public static final int TIPO_ROP_TESTE_TCO = 90;

	//Constantes de upload de arquivo do cfap
	public static final int  TITULACAO = 1;
	public static final int EXPERIENCIA_DOCENCIA = 2;
	public static final int EXPERIENCIA_CFAP = 3;
	public static final int EXERCICIO_PROFISSIONAL = 4;
	public static final int CURRICULO = 5;
	public static final String ARQUIVOS = "ARQUIVOS/";
	public static final int TIPO_ARQUIVO_PESSOAL = 3;
	public static final String INSCRICAO_INSTRUTOR_INICIAL = "inscricaoInstrutorInicial";
	public static final String INSCRICAO_INSTRUTOR_FINAL = "inscricaoInstrutorFinal";
	public static final String CFAP = "CFAP/";
	public static final String PDF_AULAS = "PDF_AULAS";
	public static final String VIDEO_AULAS = "VIDEO_AULAS";
	public static final String ARQUIVO_CFAP = PORTAL + CFAP;

	//Constantes policial
	public static final int POLICIAL_ATIVO = 4;
	public static final int POLICIAL_INATIVO = 5;

	public static final String SENHA_EMAIL_PORTAL = "senhaEmailPortal";
	public static final String EMAILS_ROPS_ELEICAO = "emailsRopsEleicao";

	//Constantes para o SAM
	public static final int STATUS_CAUTELADA = 79;
	public static final int STATUS_DEVOLVIDA = 80;
	public static final String PRODUTO_ALGEMA_SAM = "ALGEMA";
	public static final String FABRICANTE_ALGEMA_SAM = "Fabricante Algema SAM";


	//ID Unidade_Setor SAM
	public static final int ID_UNIDADE_SETOR_SAM = 1487;

	//Constantes para PM5
	public static final String IMAGENS = PORTAL+"IMAGENS/";

	//Constantes FUNCAO
	public static final Integer FUNCAO_COMANDANTE_GERAL = 88;

	// Constantes CFAP
	public static final int CURSO_CFAP = 11;
	public static final int FUNCAO_CA = 85;
	public static final int FUNCAO_CHEFE = 83;
	public static final int US_DE = 1478;
	public static final int US_DE_CFO = 1478;
	public static final int US_DE_CFSD = 1478;
	public static final int US_CA_CFO = 1719;
	public static final int US_CA_CFSD = 1477;
	public static final int US_DE_PAI = 1478;
	public static final int US_STE = 1553;
	public static final int SETOR_DE_CFO = 188;
	public static final int SETOR_DE_CFSD = 130;
	public static final int SETOR_CA_CFO = 187;
	public static final int SETOR_CA_CFSD = 131;
	public static final int SETOR_SECAO_TEC_ENSINO = 172;
	public static final int ID_STATUS_ATIVO = 83;
	public static final int ID_STATUS_INATIVO = 84;

		//Constantes Tipo Lancamento ticket
	public static final String OBJETO_LANCAMENTO_TICKET = "Lancamento_Ticket";
	public static final String TIPO_LANCAMENTO_TICKET_CREDITO = "Crédito";
	public static final String TIPO_LANCAMENTO_TICKET_DEBITO = "Débito";
	public static final String PARAMETRO_VALOR_TICKET = "valorTicket";

	//Constantes Status ticket
	public static final String OBJETO_STATUS_TICKET = "Status_Lancamento_Ticket";
	public static final String STATUS_TICKET_EM_ABERTO = "Em Aberto";
	public static final String STATUS_TICKET_LANCADO = "Lançado";
	public static final String STATUS_TICKET_AGUARDANDO_HOMOLOGACAO = "Em Homologação";
	public static final String STATUS_TICKET_HOMOLOGACAO_REJEITADA = "Rejeitado";
	public static final int CADASTRADO_PELO_SISTEMA = 999999;
	public static final int UNIDADE_SETOR_CHEFE = 1737;
	public static final int UNIDADE_SETOR_NTI = 1191;
	public static final String TIPO_ARQUIVO_UPLOAD_TICKET = "Arquivo Ticket";
	public static final String ARQUIVOS_TICKET = Constantes.PORTAL+"TICKET/";

	public static final String OBJETO_TIPO_OPERACAO = "Tipo_Operacao";
	public static final String OBJETO_CATEGORIA_OPERACAO = "Tipo_Categoria_Operacao";


	//Constantes de integração
	public static final String API_CONSULTA_INTEGRADA = "https://intranet.ssp.se.gov.br/API/Civ2";

	//Constantes de uso geral
	public static final Pattern EXTENSAO_ICONE = Pattern.compile(".*\\.(jpg|png|svg|ico)$");
	public static final Pattern PADRAO_SENHA = Pattern.compile(
			"^(?=.*[0-9])" + 	//Números
			"(?=.*[a-z])" +		// Letras minúsculas
			"(?=.*[A-Z])" +		// Letras maiúsculas
			"(?=\\S+$)" +		//Não permite espaços em branaco
			"(.{6,30})");		//Limite de caracteres
	public static final String PARAMETRO_DATA_INICIO_ELEICAO = "dataInicioEleicao";
	public static final String PARAMETRO_DATA_FINAL_ELEICAO = "dataFinalEleicao";
}
