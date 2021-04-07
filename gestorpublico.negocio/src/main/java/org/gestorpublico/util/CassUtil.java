package org.gestorpublico.util;

import org.gestorpublico.dao.Log_Erro_ExecucaoDAO;
import org.gestorpublico.dao.SequencialDAO;
import org.gestorpublico.entidade.Log_Erro_Execucao;
import org.gestorpublico.entidade.Modulo_Acao;
import org.gestorpublico.entidade.Sequencial;
import org.gestorpublico.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.json.JSONObject;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.regex.Pattern;

public class CassUtil {
	
	public static final Integer SHORT = 4;
	public static final Integer MEDIUM = 3;
	public static final Integer LONG = 2;
	public static final Integer FULL = 1;
	
	/**
	 * Gera o hash MD5 ou SHA1
	 * @param file
	 * @param formato MD5 ou SHA1
	 * @return hash String
	 */
	public static String gerarHash(File file, String formato) {
		try {
			MessageDigest md = MessageDigest.getInstance(formato);
			FileInputStream fis = new FileInputStream(file);
			
			byte[] data = new byte[1024];
			int read = 0; 
			while ((read = fis.read(data)) != -1) {
				md.update(data, 0, read);
			};
			byte[] hashBytes = md.digest();
			
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < hashBytes.length; i++) {
				sb.append(Integer.toString((hashBytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			
			return sb.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
			return "erro";
		}
    }
	
	/**
	 * Verifica se o hash do arquivo e o hash passados como parâmetro são iguais.
	 * @param file
	 * @param formato MD5 ou SHA1
	 * @param hash a ser comparado
	 * @return boolean true ou false
	 */
	public static boolean verificarHash(File file, String formato, String hash) {
		try {
			MessageDigest md = MessageDigest.getInstance(formato);
			FileInputStream fis = new FileInputStream(file);
			
			byte[] data = new byte[1024];
			int read = 0; 
			while ((read = fis.read(data)) != -1) {
				md.update(data, 0, read);
			};
			byte[] hashBytes = md.digest();
			
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < hashBytes.length; i++) {
				sb.append(Integer.toString((hashBytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			
			String fileHash = sb.toString();
			
			return fileHash.equals(hash);
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }
	
	private static final BigDecimal MINIMO = new BigDecimal(1.01);
	
//	public static String converterIntToHexString(Session session, String nome, Integer numero, int digitos) {
//		try {
//			Integer divisor = getProximoNumeroInteiroReiniciarEm(session, nome, 100);
//			numero = numero / divisor;
//			String hexa = Integer.toHexString(numero);
//			while (hexa.length() < digitos) {
//				hexa = "0"+hexa;
//			}
//			return hexa;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
	
	public static List<Integer> gerarListarCom(int total, int max) {
		Set<Integer> list = new HashSet<Integer>();
		Random random = new Random();
		while (list.size() < total) {
			list.add(random.nextInt(max));
		}
		
		return new ArrayList<>(list);
	}
	
	public static List<Integer> gerarListarNaoInclusoCom(int total, int max, List<Integer> nums) {
		Set<Integer> list = new HashSet<Integer>();
		Random random = new Random();
		Integer n;
		while (list.size() < total) {
			n = random.nextInt(max);
			if (!nums.contains(n))
				list.add(n);
		}
		
		return new ArrayList<>(list);
	}
	
	/**
	 * Encontrada em 10/12/2017 às 09h40min
	 * http://www.sanfoundry.com/java-program-generate-all-possible-combinations-given-list-numbers/
	 * Alterada em 10/12/2017 às 09h50min
	 * Alterada por Cass
	 * @param num número, no formato array de inteiros, a partir do qual deve ser gerada as sequências.
	 * @param numDigitos quantidade de dígitos que cada sequência deve ter. 
	 * @param sequencias lista de sequências geradas, não haverá na lista retornada.
	 */
	public static void gerarCombinacoes(int[] num, int numDigitos, Set<String> sequencias) {
		String s = "";
		if (numDigitos == num.length) {
			for (int i = 0; i < num.length; i++) {
				s += num[i];
			}
			sequencias.add(s);
		} else {
			for (int i = numDigitos; i < num.length; i++) {
				int temp = num[numDigitos];
				num[numDigitos] = num[i];
				num[i] = temp;

				gerarCombinacoes(num, numDigitos + 1, sequencias);

				temp = num[numDigitos];
				num[numDigitos] = num[i];
				num[i] = temp;
			}
		}
	}
	
//	public static void enviarZenviaSMS(String mensagem, String nomeRemetente, String celularDestino, String idMensagem) throws RemoteException {
//		enviarZenviaSMS("alex.costa.api", "tiLL9jc5Bw", mensagem, nomeRemetente, celularDestino, idMensagem);
//	}
//	
//	public static void enviarZenviaSMS(String usuario, String senha, String mensagem, String nomeRemetente,
//			String celularDestino, String idMensagem) throws RemoteException {
//		SendSmsRequest sms = new SendSmsRequest(usuario, senha, mensagem, nomeRemetente, celularDestino, idMensagem, null, 0);
//		new Sms_BindingImplProxy().sendSms(sms);
//	}
//	
//	public static void enviarEmail(String assunto, String mensagem, String emailDestino) throws EmailException {
//		enviarEmail("smtp.gmail.com", true, "assandrocosta@gmail.com", "@lex C0st@", assunto, mensagem, emailDestino);
//	}
//	
//	public static void enviarEmail(String smtp, boolean tls, String emailRemetente, String senha,
//			String assunto, String mensagem, String emailDestino) throws EmailException {
//		SimpleEmail email = new SimpleEmail();
//		email.setHostName(smtp);
//		email.setTLS(tls);
//		email.setAuthenticator(new DefaultAuthenticator(emailRemetente, senha));
//		email.setFrom(emailRemetente);
//		email.setSubject(assunto);
//		email.setMsg(mensagem);
//		email.addTo(emailDestino);
//		email.send();
//	}
	
	public static void envieEmail(String servidor, String emailOrigem, String emailOrigemSenha, String assunto, String mensagem, String emailsDestino) {
		if (servidor.equals("Gmail")) {
			enviarEmailGmail(emailOrigem, emailOrigemSenha, assunto, mensagem, emailsDestino);
		} else if (servidor.equals("Outlook")) {
			enviarEmailOutlook(emailOrigem, emailOrigemSenha, assunto, mensagem, emailsDestino);
		}
	}
	
	public static void enviarEmailGmail(String email, String senha, String assunto, String mensagem, String emailsDestino) {
		new Thread() {
			public void run() {
				try {
					Properties props = new Properties();
					props.put("mail.smtp.host", "smtp.gmail.com");
					props.put("mail.smtp.socketFactory.port", "465");
					props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
					props.put("mail.smtp.auth", "true");
					props.put("mail.smtp.port", "465");
					
					javax.mail.Session session = javax.mail.Session.getDefaultInstance(props,
							new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(email, senha);}
					});
					// Habilita ou Desabilita DEBUG
					session.setDebug(false);
					
					// Remetente
					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress(email));
					
					// Destinatário(s)
					Address[] toUser = InternetAddress.parse(emailsDestino);
					message.setRecipients(Message.RecipientType.TO, toUser);
					message.setSubject(assunto);
					
					//Assunto
					message.setText(mensagem);
					
					/** Método para enviar a mensagem criada */
					Transport.send(message);
					
				} catch (MessagingException e) {
					Session session = HibernateUtil.getSession();
					new Log_Erro_ExecucaoDAO(session).salvar(new Log_Erro_Execucao("CassUltil.enviarEmail", e.getMessage()));
					session.close();
					throw new RuntimeException(e);
				}
			};
		}.start();
	}
	
	public static void enviarEmailOutlook(String email, String senha, String assunto, String mensagem, String emailsDestino) {
		new Thread() {
			public void run() {
				try {
					Properties props = new Properties();
					props.put("mail.smtp.host", "smtp.office365.com");
					props.put("mail.smtp.starttls.enable", true);
					props.put("mail.smtp.auth", "true");
					props.put("mail.smtp.port", "587");
					
//			        props.put("mail.smtp.user", User);
//			        props.put("mail.smtp.pwd", Pwd);
					
					javax.mail.Session session = javax.mail.Session.getDefaultInstance(props,
							new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(email, senha);}
					});
					// Habilita ou Desabilita DEBUG
					session.setDebug(false);
					
					// Remetente
					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress(email));
					
					// Destinatário(s)
					Address[] toUser = InternetAddress.parse(emailsDestino);
					message.setRecipients(Message.RecipientType.TO, toUser);
					message.setSubject(assunto);
					
					//Assunto
					message.setText(mensagem);
					
					/** Método para enviar a mensagem criada */
					Transport.send(message);
					
				} catch (MessagingException e) {
					Session session = HibernateUtil.getSession();
					new Log_Erro_ExecucaoDAO(session).salvar(new Log_Erro_Execucao("CassUltil.enviarEmail", e.getMessage()));
					session.close();
					throw new RuntimeException(e);
				}
			};
		}.start();
	}
	
	public static String getCodigoValidador(int comprimento) {
		String[] caracteres = {"0","a","1","b","2","c","3","d","4","e","5","f","6","g","7","h","8","i","9","j","A",
							   "k","B","l","C","m","D","n","E","o","F","p","G","q","H","r","I","s","J","t","K","u",
							   "L","v","M","w","N","x","O","y","P","z","Q","R","S","T","U","V","W","X","Y","Z"};
		StringBuilder codigo = new StringBuilder();

        for (int i = 0; i < comprimento; i++) {
            int posicao = (int) (Math.random() * caracteres.length);
            codigo.append(caracteres[posicao]);
        }
        
        return codigo.toString();
	}
	
	public static int getCodigoValidador() {
		Random random = new Random();
		return random.nextInt();
	}
	
	/**
	 * 
	 * @throws IOException
	 */
//	public static void converterPdfParaImagem(String pdfCaminhoNome, String imagemCaminhoNomeSemExtensao,
//			String extensao, int tipo, int resolucao) throws IOException {
//        PDDocument document = PDDocument.load(pdfCaminhoNome);
//        List<PDPage> pages = document.getDocumentCatalog().getAllPages();
//        PDFImageWriter pdfImageWriter = new PDFImageWriter();
//        pdfImageWriter.writeImage(document, extensao, "", 1, 1, imagemCaminhoNomeSemExtensao, tipo, resolucao);
//	}

	/*
	 * http://www.devmedia.com.br/como-validar-imei-com-algoritmo-de-lunh-em-java/28898
	 * Visto em 12/01/2016, 15h10min
	 */
	public static boolean validarIMEI(String numero) {
		int soma = 0;
		for (int i = 0; i < 15; i++) {
			soma = soma
					+ calcularDigitoDoIMEI((Integer.parseInt(String.valueOf(numero
							.charAt(i)))), (i + 1) % 2 == 0);
		}
		
		return soma % 10 == 0;
	}

	private static int calcularDigitoDoIMEI(int valorSemDigito, boolean indexKey) {
		if (indexKey) {
			int number = valorSemDigito * 2;
			while (number > 9) {
				number = Integer.parseInt(String.valueOf(String.valueOf(number)
						.charAt(0)))
						+ Integer.parseInt(String.valueOf(String
								.valueOf(number).charAt(1)));
			}
			return number;
		} else {
			return valorSemDigito;
		}
	}

	public static String getNumeroStringComZerosEsquerda(String numero, int totalDigitos) {
		if (numero == null || numero.length() == 0) return "";
		
		while (numero.length() < totalDigitos) {
			numero = "0" + numero;
		}
		
		return numero;
	}
	
//	public static String getProximoCodigoComTransacao(Session session) {
//		SequencialDAO sequencialDAO = new SequencialDAO(session);
//		Sequencial sequencial = sequencialDAO.getSequencialPorNome("netjogo");
//		if (sequencial == null) {
//			sequencial = new Sequencial("netjogo");
//			sequencialDAO.salvar(sequencial);
//		}
//		String numero = sequencial.getProxCodigo();
//		session.update(sequencial);
//		
//		return numero;
//	}
//	
//	public static String getProximoCodigo(Session session) {
//		SequencialDAO sequencialDAO = new SequencialDAO(session);
//		Sequencial sequencial = sequencialDAO.getSequencialPorNome("netjogo");
//		if (sequencial == null) {
//			sequencial = new Sequencial("netjogo");
//			sequencialDAO.salvar(sequencial);
//		}
//		String numero = sequencial.getProxCodigo();
//		sequencialDAO.atualizar(sequencial);
//		
//		return numero;
//	}
//	
//	public static String getProximoNumero(Session session, String nome) {
//		SequencialDAO sequencialDAO = new SequencialDAO(session);
//		Sequencial sequencial = sequencialDAO.getSequencialPorNome(nome);
//		if (sequencial == null) {
//			sequencial = new Sequencial(nome);
//			sequencialDAO.salvar(sequencial);
//		}
//		String numero = sequencial.getProxNumero();
//		sequencialDAO.atualizar(sequencial);
//		
//		return numero;
//	}
//	
//	public static String getProximoNumero(Session session, String nome, int tamanho) {
//		SequencialDAO sequencialDAO = new SequencialDAO(session);
//		Sequencial sequencial = sequencialDAO.getSequencialPorNome(nome);
//		if (sequencial == null) {
//			sequencial = new Sequencial(nome);
//			sequencialDAO.salvar(sequencial);
//		}
//		String numero = sequencial.getProxNumero(tamanho);
//		sequencialDAO.atualizar(sequencial);
//		
//		return numero;
//	}
	
	public static String converterBigDecimalParaNumeroStringPtBrSemDecimal(BigDecimal valor) {
		if (valor == null)
			return null;
		
		DecimalFormat df =
				new DecimalFormat("#,###,##0", new DecimalFormatSymbols(new Locale("pt","BR")));
		
		return df.format(valor);
	}
	
	/**
	 * Converte o valor BigDecimal para String de acordo com o formato
	 * @param valor BigDecimal
	 * @param formato: ex.: #,###,##0.00, se não for informado retorna null
	 * @return String
	 */
	public static String converterBigDecimalParaNumeroStringPtBrFormato(BigDecimal valor, String formato) {
		if (valor == null || formato == null || formato.isEmpty())
			return null;
		
		DecimalFormat df =
				new DecimalFormat(formato, new DecimalFormatSymbols(new Locale("pt","BR")));
		
		return df.format(valor);
	}
	
	public static String converterBigDecimalParaNumeroStringPtBr(BigDecimal valor) {
		if (valor == null)
			return null;
		
		DecimalFormat df =
				new DecimalFormat("#,###,##0.00", new DecimalFormatSymbols(new Locale("pt","BR")));
		
		return df.format(valor);
	}
	
	public static BigDecimal converterNumeroStringPtBrParaBigDecimal(String numeroStringPtBr) {
		if (numeroStringPtBr == null || numeroStringPtBr.isEmpty())
			return null;
		
		numeroStringPtBr = numeroStringPtBr.replaceAll("\\.", "");
		numeroStringPtBr = numeroStringPtBr.replaceAll("\\,", "\\.");
		
		if (numeroStringPtBr == null || !Pattern.matches("\\d+(\\.\\d+)?", numeroStringPtBr))
			return null;
		
		return new BigDecimal(numeroStringPtBr);
	}
	
	public static String padronizarNomeVariavelSoLetras(String str) {
		str = substituirCaracteresEspeciais(str);
		String[] array = str.toLowerCase().split(" ");
		String result = array[0];
		for (int i = 1; i < array.length; i++) {
			result += array[i].substring(0, 1).toUpperCase() + array[i].substring(1);
		}
		
		return result;
	}

	public static String substituirCaracteresEspeciais(String str) {
		return str == null ? null : Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}
	
	public static String getNomeNovo(String str) {
		String n = str.replaceAll("-", "");
		n = n.replaceAll(Pattern.quote("/"), "");
		n = n.replaceAll(Pattern.quote("("), "");
		n = n.replaceAll(Pattern.quote(")"), "");
		n = n.replaceAll(Pattern.quote("+"), "");
		n = n.replaceAll(Pattern.quote("."), "");
		n = n.replaceAll(Pattern.quote(":"), "");
		n = n.replaceAll(Pattern.quote("|"), "");
		n = n.replaceAll(" ", "");
		return n;
	}
	
	public static String UTF8toISO(String str){
        Charset utf8charset = Charset.forName("UTF-8");
        Charset iso88591charset = Charset.forName("ISO-8859-1");

        ByteBuffer inputBuffer = ByteBuffer.wrap(str.getBytes());

        // decode UTF-8
        CharBuffer data = utf8charset.decode(inputBuffer);

        // encode ISO-8559-1
        ByteBuffer outputBuffer = iso88591charset.encode(data);
        byte[] outputData = outputBuffer.array();

        return new String(outputData);
    }
	
	public static String ISOtoUTF8(String str){
        Charset utf8charset = Charset.forName("UTF-8");
        Charset iso88591charset = Charset.forName("ISO-8859-1");

        ByteBuffer inputBuffer = ByteBuffer.wrap(str.getBytes());

        // decode ISO-8559-1
        CharBuffer data = iso88591charset.decode(inputBuffer);

        // encode UTF-8
        ByteBuffer outputBuffer = utf8charset.encode(data);
        byte[] outputData = outputBuffer.array();

        return new String(outputData);
    }
	
	public static String criptografar(String senha) {
		BigInteger hash = null;
		try  {  
			MessageDigest md = MessageDigest.getInstance("MD5");  
			md.update(senha.getBytes());
			hash = new BigInteger(1, md.digest());
			String senh = hash.toString(16);
			while (senh.length() < 32) {senh = "0" + senh;}
			
			return senh;
			
		} catch(NoSuchAlgorithmException ns) {  
			ns.printStackTrace();
			return senha;
		}
	}      

	public static String mascarar(String valor, String mascara) {
		if (valor == null || valor.length() == 0) return "";
		
		StringBuilder dado = new StringBuilder();
		// remove caracteres não numéricos
		for (int i = 0; i < valor.length(); i++) {
			char c = valor.charAt(i);
			if (Character.isDigit(c) || Character.isAlphabetic(c)) {
				dado.append(c);
			}
		}
		
		int indMascara = mascara.length();
		int indCampo = dado.length();

		for (; indCampo > 0 && indMascara > 0; )
		{
			if (mascara.charAt(--indMascara) == '#')
				indCampo--;
		}

		StringBuilder saida = new StringBuilder();
		for (; indMascara < mascara.length(); indMascara++)
			saida.append((mascara.charAt(indMascara) == '#') ? dado.charAt(indCampo++) : mascara.charAt(indMascara));

		return saida.toString();
	}
	
	public static String removerMascara(String valor) {
		if (valor == null || valor.trim().isEmpty()) return null;
		valor = valor.replaceAll("\\:", "");
		valor = valor.replaceAll("\\.", "");
		valor = valor.replaceAll("\\/", "");
		valor = valor.replaceAll("\\-", "");
		valor = valor.replaceAll("\\(", "");
		valor = valor.replaceAll("\\)", "");

		return valor; 
	}

	public static String getCepFormatado(String cep) {
		return cep == null || cep.isEmpty() ? "" : mascarar(cep, "##.###-###"); 
	}

	public static String getCNPJFormatado(String cnpj) {
		return cnpj == null || cnpj.isEmpty() ? "" : mascarar(cnpj, "##.###.###/####-##"); 
	}

	public static String getCPFFormatado(String cpf) {
		return cpf == null || cpf.isEmpty() ? "" :  mascarar(cpf, "###.###.###-##"); 
	}

	public static String getDataUSASoNumero(Calendar data) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		return data == null ? "" : sdf.format(data.getTime()); 
	}

	public static String getDataSoNumero(Calendar data) {
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		
		return data == null ? "" : sdf.format(data.getTime()); 
	}

	public static String getDataHoraUSASoNumero(Calendar data) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		
		return data == null ? "" : sdf.format(data.getTime()); 
	}

	public static String getDataHoraZeradaPorFormato(Calendar data, String formato) {
		if (data == null) {
			return "";
		} else {
			Calendar dt = Calendar.getInstance();
			dt.setTime(data.getTime());
			dt.set(Calendar.HOUR_OF_DAY, 0);
			dt.set(Calendar.MINUTE, 0);
			dt.set(Calendar.SECOND, 0);
			dt.set(Calendar.MILLISECOND, 0);

			SimpleDateFormat sdf = new SimpleDateFormat(formato);

			return sdf.format(dt.getTime()); 
		}
	}

	public static String getDataHoraPorFormato(String formato, Calendar data) {
		SimpleDateFormat sdf = new SimpleDateFormat(formato);
		
		return data == null ? "" : sdf.format(data.getTime()); 
	}

	public static String getDataFormatadaMesAno(Calendar data) {
		SimpleDateFormat sdf = new SimpleDateFormat("MMM/yyyy");
		
		return data == null ? "" : sdf.format(data.getTime()); 
	}
	
	public static String getDataAtualIncrementadaPorFormatado(int dias, String formato) {
		Calendar data = Calendar.getInstance();
		data.add(Calendar.DATE, dias);
		
		return getDataHoraPorFormato(formato, data);
	}
	
	public static Calendar getDataAtualFormatadaIncrementada(int dias) {
		return getDataHoraIncrementadaComHoraZerada(Calendar.getInstance(), dias); 
	}
	
	public static String getDataHoraAtualPorFormato(String formato) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato);
		LocalDateTime data = LocalDateTime.now();
		return data.format(formatter);
	}
	
	public static String getDataAtualFormatada() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate data = LocalDate.now();
		return data.format(formatter);
	}
	
	public static Calendar getDataAtualComHoraZerada() {
		Calendar dt = Calendar.getInstance();
		dt.set(Calendar.HOUR_OF_DAY, 0);
		dt.set(Calendar.MINUTE, 0);
		dt.set(Calendar.SECOND, 0);
		dt.set(Calendar.MILLISECOND, 0);
		
		return dt;
	}
	
	public static String getDataHoraAtualFormatada() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		return sdf.format(Calendar.getInstance().getTime()); 
	}
	
	public static String getDataFormatadaPorFormato(Calendar data, String formato) {
		SimpleDateFormat sdf = new SimpleDateFormat(formato);
		
		return data == null ? "" : sdf.format(data.getTime()); 
	}
	
	public static String getDataHoraFormatadaPorFormato(LocalDateTime data, String formato) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato);
		return data == null || formato == null || formato.isEmpty() ? "" : data.format(formatter); 
	}
	
	/**
	 * Retorna a data LocalDate passada com parâmetro no formato String.
	 * @param data LocalDate
	 * @param comprimento 1:FULL, 2:LONG, 3:MEDIUM ou 4:SHORT
	 * @return String, exemplo:<br/>
	 * <b>FULL</b>: Sábado, 28 de Dezembro de 2019<br/>
	 * <b>LONG</b>: 28 de Dezembro de 2019<br/>
	 * <b>MEDIUM</b>: 28/12/2019<br/>
	 * <b>SHORT</b>: 28/12/19 
	 */
	public static String getDataFormatada(LocalDate data, int comprimento) {
		FormatStyle formato = FormatStyle.MEDIUM;
		switch (comprimento) {
			case 1:formato = FormatStyle.FULL;break;
			case 2:formato = FormatStyle.LONG;break;
			case 3:formato = FormatStyle.MEDIUM;break;
			case 4:formato = FormatStyle.SHORT;break;
		}
		return data == null ? "" : data.format(DateTimeFormatter.ofLocalizedDate(formato)); 
	}
	
	public static String getDataFormatada(LocalDate data) {
		return data == null ? "" : data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}
	
	public static String getDataFormatadaPorFormato(LocalDate data, String formato) {
		return data == null ? "" : data.format(DateTimeFormatter.ofPattern(formato)); 
	}
	
	public static String getDataFormatada(Calendar data) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		return data == null ? "" : sdf.format(data.getTime()); 
	}
	
	public static String getDataFormatada(LocalDateTime dataHora) {
		return dataHora == null ? "" : dataHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}

	public static String getDataHoraFormatada(Calendar dataHora) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		return dataHora == null ? "" : sdf.format(dataHora.getTime()); 
	}

	public static String getDataHoraFormatada(LocalDateTime dataHora) {
		return dataHora == null ? "" : dataHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
	}

	public static Calendar getDataHoraIncrementada(Calendar data, int dias) {
		data.add(Calendar.DATE, dias);
		
		return data;
	}

	public static LocalDate getDataIncrementada(LocalDate data, int dias) {
		return data.plusDays(dias);
	}

	public static LocalDateTime getDataHoraIncrementada(LocalDateTime data, int dias) {
		return data.plusDays(dias);
	}

	public static Calendar getDataHoraUltimoMinutoDeHoje() {
		Calendar dt = Calendar.getInstance();
		dt.set(Calendar.HOUR_OF_DAY, 23);
		dt.set(Calendar.MINUTE, 59);
		dt.set(Calendar.SECOND, 59);
		dt.set(Calendar.MILLISECOND, 0);
		
		return dt;
	}

	public static Calendar converterDataStringParaCalendarUltimoMinutoDoDia(String data) {
		if (data == null || data.isEmpty())
			return null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar dt = Calendar.getInstance();
		try {
			dt.setTime(sdf.parse(data));
			dt.set(Calendar.HOUR_OF_DAY, 23);
			dt.set(Calendar.MINUTE, 59);
			dt.set(Calendar.SECOND, 59);
			dt.set(Calendar.MILLISECOND, 0);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return dt;
	}

	public static LocalDateTime getDataHoraUltimoMinutoDoDia(LocalDate data) {
		return data.atTime(23, 59, 59);
	}

	public static Calendar getDataHoraUltimoMinutoDoDia(Calendar data) {
		Calendar dt = Calendar.getInstance();
		dt.setTime(data.getTime());
		
		dt.set(Calendar.HOUR_OF_DAY, 23);
		dt.set(Calendar.MINUTE, 59);
		dt.set(Calendar.SECOND, 59);
		dt.set(Calendar.MILLISECOND, 0);
		
		return dt;
	}

	public static Calendar getDataHoraIncrementadaComHoraZerada(Calendar data, int dias) {
		Calendar dt = Calendar.getInstance();
		dt.setTime(data.getTime());
		
		dt.add(Calendar.DATE, dias);
		dt.set(Calendar.HOUR_OF_DAY, 0);
		dt.set(Calendar.MINUTE, 0);
		dt.set(Calendar.SECOND, 0);
		dt.set(Calendar.MILLISECOND, 0);
		
		return dt;
	}

	public static LocalDate getDataHoraIncrementadaComHoraZerada(LocalDate data, int dias) {
		LocalDate dt = data.plusDays(dias);
		dt.atTime(0, 0, 0);
		return dt;
	}

	public static String getDataHoraMinutosAlterarStringFormatado(Calendar data, int minutos, String formatado) {
		Calendar dt = Calendar.getInstance();
		dt.setTime(data.getTime());
		
		dt.add(Calendar.MINUTE, minutos);
		
		SimpleDateFormat sdf = new SimpleDateFormat(formatado);
		
		return sdf.format(dt.getTime());
	}

	public static Calendar getDataHoraMinutosAlterar(Calendar data, int minutos) {
		Calendar dt = Calendar.getInstance();
		dt.setTime(data.getTime());
		
		dt.add(Calendar.MINUTE, minutos);
		
		return dt;
	}

	public static Calendar converterDataUSAStringParaCalendar(String data) {
		if (data == null || data.isEmpty())
			return null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar dt = Calendar.getInstance();
		try {
			dt.setTime(sdf.parse(data));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return dt;
	}
	
	public static Calendar converterDataStringParaCalendar(String data) {
		if (data == null || data.isEmpty())
			return null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if (data.contains("-")) sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar dh = Calendar.getInstance();
		try {
			dh.setTime(sdf.parse(data));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return dh;
	}

	public static LocalDate converterDataStringParaLocalDate(String data) {
		if (data == null || data.isEmpty())
			return null;
		
		int year, month, dayOfMonth;
		if (data.contains("-")) {
			year = Integer.valueOf(data.substring(0,4));
			month = Integer.valueOf(data.substring(5,7));
			dayOfMonth = Integer.valueOf(data.substring(8,10));
		} else {
			dayOfMonth = Integer.valueOf(data.substring(0,2));
			month = Integer.valueOf(data.substring(3,5));
			year = Integer.valueOf(data.substring(6,10));
		}
		
		return LocalDate.of(year, month, dayOfMonth);
	}
	
	public static LocalDateTime converterDataHoraStringParaLocalDateTime(String dataHora) {
		if (dataHora == null || dataHora.isEmpty())
			return null;
		String formatoEntrada = dataHora.length() == 19 ? "dd/MM/yyyy HH:mm:ss" : "dd/MM/yyyy HH:mm";
		
		if (dataHora.contains("T")) {
			formatoEntrada = dataHora.length() == 19 ? "yyyy-MM-dd HH:mm:ss" : "yyyy-MM-dd HH:mm";
			dataHora = dataHora.replace("T", " ");
			
		} else if (dataHora.contains("-")) {
			formatoEntrada = dataHora.length() == 19 ? "yyyy-MM-dd HH:mm:ss" : "yyyy-MM-dd HH:mm";
		}
		
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(formatoEntrada);
		return LocalDateTime.parse(dataHora, dtf);
	}
	
	public static LocalDateTime converterDataHoraStringFormatoParaLocalDateTime(String dataHora, String formatoEntrada) {
		if (dataHora == null || dataHora.isEmpty())
			return null;
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(formatoEntrada);
		return LocalDateTime.parse(dataHora, dtf);
	}
	
	public static Calendar converterDataHoraStringPorFormatoParaCalendar(String dataHora, String formato) {
		if (dataHora == null || dataHora.isEmpty())
			return null;
		
		SimpleDateFormat sdf = new SimpleDateFormat(formato);
		Calendar dh = Calendar.getInstance();
		try {
			dh.setTime(sdf.parse(dataHora));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return dh;
	}
	
	public static Calendar converterDataHoraStringParaCalendar(String dataHora) {
		if (dataHora == null || dataHora.isEmpty())
			return null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Calendar dh = Calendar.getInstance();
		try {
			dh.setTime(sdf.parse(dataHora));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return dh;
	}
	
	public static Calendar converterDataHoraUSAStringParaCalendar(String dataHora) {
		if (dataHora == null || dataHora.isEmpty())
			return null;
		dataHora = dataHora.replace("T", " ");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Calendar dh = Calendar.getInstance();
		try {
			dh.setTime(sdf.parse(dataHora));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return dh;
	}
	
	public static Calendar getDataComHoraAtual(Calendar data) {
		if (data == null)
			return null;
		
		Calendar dt = Calendar.getInstance();
		Calendar cal = Calendar.getInstance();
		cal.setTime(data.getTime());
		
		cal.set(Calendar.HOUR_OF_DAY, dt.get(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, dt.get(Calendar.MINUTE));
		cal.set(Calendar.SECOND, dt.get(Calendar.SECOND));
		
		return cal;
	}
	
	public static String getDataFormatadaComHoraZerada(Calendar data) {
		data.set(Calendar.HOUR_OF_DAY, 0);
		data.set(Calendar.MINUTE, 0);
		data.set(Calendar.SECOND, 0);
		data.set(Calendar.MILLISECOND, 0);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		return data == null ? "" : sdf.format(data.getTime()); 
	}
	
	public static boolean dataIguais(Calendar data, Calendar data2) {
		Calendar dt = Calendar.getInstance();
		dt.setTime(data.getTime());
		dt.set(Calendar.HOUR_OF_DAY, 0);
		dt.set(Calendar.MINUTE, 0);
		dt.set(Calendar.SECOND, 0);
		dt.set(Calendar.MILLISECOND, 0);
		
		Calendar dt2 = Calendar.getInstance();
		dt2.setTime(data2.getTime());
		dt2.set(Calendar.HOUR_OF_DAY, 0);
		dt2.set(Calendar.MINUTE, 0);
		dt2.set(Calendar.SECOND, 0);
		dt2.set(Calendar.MILLISECOND, 0);
		
		return dt.equals(dt2);
	}

	public static LocalDateTime getDataComHoraZerada(LocalDate data) {
		return data.atTime(0, 0, 0);
	}

	public static Calendar getDataComHoraZerada(Calendar data) {
		Calendar dt = Calendar.getInstance();
		dt.setTime(data.getTime());
		
		dt.set(Calendar.HOUR_OF_DAY, 0);
		dt.set(Calendar.MINUTE, 0);
		dt.set(Calendar.SECOND, 0);
		dt.set(Calendar.MILLISECOND, 0);
		
		return dt;
	}

	public static Calendar getDataComAnoIncrementado(Calendar data, int anos) {
		data.add(Calendar.YEAR, anos);
		
		return data;
	}

	public static Calendar getDataComMesIncrementado(Calendar data, int meses) {
		data.add(Calendar.MONTH, meses);
		
		return data;
	}

	public static String getSaudacao() {
		Integer hora = LocalDateTime.now().getHour();

		if (hora >= 0 && hora <= 5) return "Boa madrugada";
		if (hora >= 5 && hora <= 11) return "Bom dia";
		if (hora >= 12 && hora <= 17) return "Boa tarde";
		if (hora >= 18 && hora <= 23) return "Boa noite";
		else
			return "Bem vindo";
	}

	public static String getTelefoneFormatado(String telefone) {
		if (telefone == null || telefone.length() == 0) return "";
		String mascara = telefone.length() == 10 ? "(##)####-####" : "(##)#####-####";
		String tel = "("+ mascarar(telefone, mascara);
		return tel;
	}

	public static Map<Integer, String> getPalpites(String palpites) {
		Map<Integer, String> pals = new HashMap<Integer, String>();
		
		palpites = palpites.substring(1, palpites.length()-1);
		
		String[] dados = palpites.split(", ");
		
		for (String dado : dados) {
			Integer key = new Integer(dado.substring(2));
			String pal = new String(dado.substring(0,2));
			
			pals.put(key, pal);
		}
		
		return pals;
	}

	public static String completarEsquerda(int tamanho, String texto, String complemento) {
		if (texto == null || texto.trim().isEmpty())
			return "";
		
		if (tamanho <= texto.length())
			return texto;
		
		String resultado = "";
		
		tamanho = tamanho - texto.length();
		
		for (int i = 0; i < tamanho; i++) {
			resultado += complemento;
		}
		
		return resultado + texto;
	}

//	public static String completarEsquerda(int tamanho, String texto, String complemento) {
//		String resultado = new String(texto);
//		
//		tamanho = tamanho > resultado.length() ? tamanho - resultado.length() : 1;
//		
//		for (int i = 0; i < tamanho; i++) {
//			complemento += resultado;
//		}
//		
//		return resultado;
//	}
//
	public static String completarCentralizado(int tamanho, String texto, String complemento) {
		String resultado = new String(texto);
		int textoTamanho = texto.length();
		
		if (tamanho < textoTamanho)
			return "Texto maior que espaço";
		
		int esquerda = (tamanho - textoTamanho) / 2;
		int direita = tamanho - esquerda - textoTamanho;
		
		for (int i = 0; i <= esquerda; i++) {
			resultado = complemento + resultado;
		}
		
		for (int i = 0; i <= direita; i++) {
			resultado += complemento;
		}
		
		return resultado;
	}

	public static String completarDireita(int tamanho, String texto, String complemento) {
		String resultado = new String(texto);
		
		tamanho = tamanho > resultado.length() ? tamanho - resultado.length() : 1;
		
		for (int i = 0; i < tamanho; i++) {
			resultado += complemento;
		}
		
		return resultado;
	}

	public static Calendar getSegundaFeiraDestaSemana() {
		Calendar segundaFeira = getDataAtualComHoraZerada();
		segundaFeira.set(Calendar.DAY_OF_WEEK, segundaFeira.getFirstDayOfWeek()+1);
		
		return segundaFeira;
	}

	public static Calendar getSegundaFeiraDaProximaSemana() {
		Calendar segundaFeira = Calendar.getInstance();
		segundaFeira.set(Calendar.DAY_OF_WEEK, segundaFeira.getFirstDayOfWeek()+1);
		Calendar domingo = Calendar.getInstance();
		domingo.setTime(segundaFeira.getTime());
		domingo.add(Calendar.DATE, 7);
		domingo.set(Calendar.HOUR_OF_DAY, 0);
		domingo.set(Calendar.MINUTE, 0);
		domingo.set(Calendar.SECOND, 0);
		domingo.set(Calendar.MILLISECOND, 0);
		
		return domingo;
	}

	public static Calendar getProximoDomingo() {
		Calendar segundaFeira = Calendar.getInstance();
		segundaFeira.set(Calendar.DAY_OF_WEEK, segundaFeira.getFirstDayOfWeek()+1);
		Calendar domingo = Calendar.getInstance();
		domingo.setTime(segundaFeira.getTime());
		domingo.add(Calendar.DATE, 6);
		domingo.set(Calendar.HOUR_OF_DAY, 23);
		domingo.set(Calendar.MINUTE, 59);
		domingo.set(Calendar.SECOND, 59);
		domingo.set(Calendar.MILLISECOND, 0);
		
		return domingo;
	}

	public static Calendar getSegundaFeiraDaProximaSemanaDestaData(Calendar data) {
		Calendar segundaFeira = Calendar.getInstance();
		segundaFeira.setTime(data.getTime());
		segundaFeira.set(Calendar.DAY_OF_WEEK, segundaFeira.getFirstDayOfWeek()+1);
		Calendar domingo = Calendar.getInstance();
		domingo.setTime(segundaFeira.getTime());
		domingo.add(Calendar.DATE, 7);
		domingo.set(Calendar.HOUR_OF_DAY, 0);
		domingo.set(Calendar.MINUTE, 0);
		domingo.set(Calendar.SECOND, 0);
		domingo.set(Calendar.MILLISECOND, 0);
		
		return domingo;
	}

	public static Calendar getSegundaFeiraDaSemanaDessaData(Calendar data) {
		Calendar segundaFeira = Calendar.getInstance();
		segundaFeira.setTime(data.getTime());
		segundaFeira.set(Calendar.DAY_OF_WEEK, segundaFeira.getFirstDayOfWeek()+1);

		return segundaFeira;
	}

	public static Calendar getSegundaFeiraDaSemanaDessaDataComHoraZarada(Calendar data) {
		Calendar segundaFeira = Calendar.getInstance();
		segundaFeira.setTime(data.getTime());
		segundaFeira.set(Calendar.DAY_OF_WEEK, segundaFeira.getFirstDayOfWeek()+1);
		segundaFeira.set(Calendar.HOUR_OF_DAY, 0);
		segundaFeira.set(Calendar.MINUTE, 0);
		segundaFeira.set(Calendar.SECOND, 0);
		segundaFeira.set(Calendar.MILLISECOND, 0);

		return segundaFeira;
	}

	public static Calendar getProximoDomingoApartirDessaData(Calendar data) {
		Calendar segundaFeira = Calendar.getInstance();
		segundaFeira.setTime(data.getTime());
		segundaFeira.set(Calendar.DAY_OF_WEEK, segundaFeira.getFirstDayOfWeek()+1);
		Calendar domingo = Calendar.getInstance();
		domingo.setTime(segundaFeira.getTime());
		domingo.add(Calendar.DATE, 6);
		domingo.set(Calendar.HOUR_OF_DAY, 0);
		domingo.set(Calendar.MINUTE, 0);
		domingo.set(Calendar.SECOND, 0);
		domingo.set(Calendar.MILLISECOND, 0);
		
		return domingo;
	}

	public static Calendar getProximoDomingoUltimosMinutosApartirDessaData(Calendar data) {
		Calendar segundaFeira = Calendar.getInstance();
		segundaFeira.setTime(data.getTime());
		segundaFeira.set(Calendar.DAY_OF_WEEK, segundaFeira.getFirstDayOfWeek()+1);
		Calendar domingo = Calendar.getInstance();
		domingo.setTime(segundaFeira.getTime());
		domingo.add(Calendar.DATE, 6);
		domingo.set(Calendar.HOUR_OF_DAY, 23);
		domingo.set(Calendar.MINUTE, 59);
		domingo.set(Calendar.SECOND, 59);
		domingo.set(Calendar.MILLISECOND, 0);
		
		return domingo;
	}

	public static String getProximoNumero(Session session, String nome) {
		SequencialDAO sequencialDAO = new SequencialDAO(session);
		Sequencial sequencial = sequencialDAO.getSequencialPorNome(nome);
		if (sequencial == null) {
			sequencial = new Sequencial(nome);
			sequencialDAO.salvar(sequencial);
		}
		String numero = sequencial.getProxNumero();
		sequencialDAO.atualizar(sequencial);
		
		return numero;
	}
	
	public static String getProximoNumeroComAno(Session session, String nome, int tamanho) {
		SequencialDAO sequencialDAO = new SequencialDAO(session);
		Sequencial sequencial = sequencialDAO.getSequencialPorNome(nome);
		if (sequencial == null) {
			sequencial = new Sequencial(nome);
			sequencialDAO.salvar(sequencial);
		}
		String numero = Calendar.getInstance().get(Calendar.YEAR) + sequencial.getProxNumero(tamanho-4);
		sequencialDAO.atualizar(sequencial);
		
		return numero;
	}
	
	public static String getProximoNumero(Session session, String nome, int tamanho) {
		SequencialDAO sequencialDAO = new SequencialDAO(session);
		Sequencial sequencial = sequencialDAO.getSequencialPorNome(nome);
		if (sequencial == null) {
			sequencial = new Sequencial(nome);
			sequencialDAO.salvar(sequencial);
		}
		String numero = sequencial.getProxNumero(tamanho);
		sequencialDAO.atualizar(sequencial);
		
		return numero;
	}
	
	public static boolean dataHoraEntre(Calendar dataHora, Integer horasInicio, Integer horasTermino) {
		Calendar hoje = Calendar.getInstance();
		int hoje_hora = hoje.get(Calendar.HOUR_OF_DAY);
		Calendar dataHoraInicio;
		Calendar dataHoraTermino;
		if (hoje_hora >= 0 && hoje_hora < 8) {
			dataHoraInicio = getDataHoraIncrementada(Calendar.getInstance(), -1);
			dataHoraTermino = hoje;
		} else {
			dataHoraInicio = hoje;
			dataHoraTermino = getDataHoraIncrementada(Calendar.getInstance(), 1);
		}
		
		dataHoraInicio.set(Calendar.HOUR_OF_DAY, horasInicio);
		dataHoraInicio.set(Calendar.MINUTE, 0);
		dataHoraInicio.set(Calendar.SECOND, 0);
		dataHoraInicio.set(Calendar.MILLISECOND, 0);
		
		dataHoraTermino.set(Calendar.HOUR_OF_DAY, horasTermino);
		dataHoraTermino.set(Calendar.MINUTE, 0);
		dataHoraTermino.set(Calendar.SECOND, 0);
		dataHoraTermino.set(Calendar.MILLISECOND, 0);
		
		return dataHora.after(dataHoraInicio) & dataHora.before(dataHoraTermino);
	}

	public static int getDiferencaEmDiasDasDatas(Calendar inicio, Calendar termino) {
		int MILLIS_IN_DAY = 86400000;
		Calendar c1 = Calendar.getInstance();
		c1.setTime(termino.getTime());
		c1.set(Calendar.MILLISECOND, 0);
		c1.set(Calendar.SECOND, 0);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.HOUR_OF_DAY, 0);
		
		Calendar c2 = Calendar.getInstance();
		c2.setTime(inicio.getTime());
		c2.set(Calendar.MILLISECOND, 0);
		c2.set(Calendar.SECOND, 0);
		c2.set(Calendar.MINUTE, 0);
		c2.set(Calendar.HOUR_OF_DAY, 0);
		return (int) ((c1.getTimeInMillis() - c2.getTimeInMillis()) / MILLIS_IN_DAY);
	}

	// OBTEM A REGIAO POR IP
	public static String getCidadePorIP(String ip) {
		// PARAMETROS PARA IMPORTACAO DAS COTACOES
		URL url;
		try {
			url = new URL("http://freegeoip.net/json/"+ip);
			URLConnection con = url.openConnection();
			con.setConnectTimeout(10000);
			
			// RESPOSTA DA EXECUCAO DA URL CONVERTIDA EM BUFFEREDREADER
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			
			// CONVERTE O BUFFEREDREADER PARA STRING
			StringBuffer buffer = new StringBuffer();
			int read;
			char[] chars = new char[1024];
			while ((read = in.read(chars)) != -1) {
				buffer.append(chars, 0, read);
			}
			
			// CONVERTE O STRING PARA JSONARRAY COM TODOS OS JOGOS IMPORTADOS
			JSONObject json = new JSONObject(buffer.toString());
			
//			String cidade = json.getString("city");
//			cidade = cidade == null || cidade.isEmpty() ? json.getString("region_name") : cidade;
			String regiao = json.getString("country_code")+"|"+json.getString("region_code")+"|"+json.getString("ip");
			
			return regiao;
			
		} catch (Exception e) {
			return "||"+ip;
		}
	}

//	public static Integer getProximoNumeroInteiroReiniciarEm(Session session, String nome, Integer valorReiniciar) {
//		SequencialDAO sequencialDAO = new SequencialDAO(session);
//		Sequencial sequencial = sequencialDAO.getSequencialPorNome(nome);
//		if (sequencial == null) {
//			sequencial = new Sequencial(nome);
//			sequencialDAO.salvar(sequencial);
//		}
//		Integer numero = sequencial.getProxNumeroInteiro();
//		if (numero >= valorReiniciar) sequencial.setProxNumero(0);
//		sequencialDAO.atualizar(sequencial);
//		
//		return numero;
//	}
//
//	public static Integer getProximoNumeroInteiro(Session session, String nome) {
//		SequencialDAO sequencialDAO = new SequencialDAO(session);
//		Sequencial sequencial = sequencialDAO.getSequencialPorNome(nome);
//		if (sequencial == null) {
//			sequencial = new Sequencial(nome);
//			sequencialDAO.salvar(sequencial);
//		}
//		Integer numero = sequencial.getProxNumeroInteiro();
//		sequencialDAO.atualizar(sequencial);
//		
//		return numero;
//	}

	public static String getValorOddFormatado(boolean top, Object jogoOdd) {
		BigDecimal valor = ((BigDecimal)((Object[])jogoOdd)[0]);
		if (top && !((String)((Object[])jogoOdd)[4]).startsWith("Resultado")) return converterBigDecimalParaNumeroStringPtBr(valor);
		
		BigDecimal novoValor = valor.multiply(((BigDecimal)((Object[])jogoOdd)[1]));
		
		novoValor = valor.subtract(novoValor);
		
		return (novoValor.compareTo(MINIMO) > 0 ? novoValor : MINIMO).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
	}
	
	public static String getValorOddFormatado(boolean top, Map<String, Object> jogoOdd) {
		BigDecimal valor = (BigDecimal) jogoOdd.get("valor");
		if (top && !((String)jogoOdd.get("oddNome")).startsWith("Resultado")) return converterBigDecimalParaNumeroStringPtBr(valor);
		
		BigDecimal novoValor = valor.multiply((BigDecimal)jogoOdd.get("percMenor"));
		
		novoValor = valor.subtract(novoValor);
		
		return (novoValor.compareTo(MINIMO) > 0 ? novoValor : MINIMO).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
	}
	
	public static BigDecimal getValorOdd(boolean top, Object jogoOdd) {
		BigDecimal valor = ((BigDecimal)((Object[])jogoOdd)[0]);
		if (top && !((String)((Object[])jogoOdd)[4]).startsWith("Resultado")) return valor;
		
		BigDecimal novoValor = valor.multiply(((BigDecimal)((Object[])jogoOdd)[1]));
		
		novoValor = valor.subtract(novoValor);
		
		return (novoValor.compareTo(MINIMO) > 0 ? novoValor : MINIMO).setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	public static BigDecimal getValorOdd(boolean top, Map<String, Object> jogoOdd) {
		BigDecimal valor = (BigDecimal) jogoOdd.get("valor");
		if (top && !((String)jogoOdd.get("oddNome")).startsWith("Resultado")) return valor;
		
		BigDecimal novoValor = valor.multiply((BigDecimal)jogoOdd.get("percMenor"));
		
		novoValor = valor.subtract(novoValor);
		
		return (novoValor.compareTo(MINIMO) > 0 ? novoValor : MINIMO).setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * Verifica se o dia atual é Domingo ou Sábado a partir da hora informada.
	 * @param hora número inteiro entre 0 e 23 horas
	 * @return boolean
	 */
	public static boolean hojeDomingoOuSabadoApartirDas(int hora) {
		Calendar hoje = Calendar.getInstance();
		int diaDaSemana = hoje.get(Calendar.DAY_OF_WEEK);
		if ((diaDaSemana == 7 && hoje.get(Calendar.HOUR_OF_DAY) >= hora) || diaDaSemana == 1)
			return true;
		
		return false;
	}

	public static BigDecimal getCotacaoPorTaxa(List<Object> cotacaoUmaAposta, BigDecimal taxa) {
		BigDecimal menorCotacao = (BigDecimal)((Object[])cotacaoUmaAposta.get(0))[2];
		BigDecimal novaCotacao = taxa.subtract(taxa.multiply((BigDecimal)((Object[])cotacaoUmaAposta.get(0))[1]));
		if (novaCotacao.compareTo(menorCotacao) < 0) {
			if (menorCotacao.compareTo(taxa) > -1)
				return taxa;
			
			return menorCotacao;
		}
		
		return novaCotacao.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public static void enviarFcmNotificacaoMultiplos(final Integer id, final String titulo, final String mensagem, List<String> tokens) {
		if (tokens == null) return;
		
		for (String tok : tokens) {
			final String token = tok;
			
			new Thread() {
				@Override
				public void run() {
					try {
						URL url = new URL("https://fcm.googleapis.com/fcm/send");
						HttpURLConnection connection = (HttpURLConnection) url.openConnection();           
						connection.setDoOutput(true);
						connection.setDoInput(true);
						connection.setUseCaches(false);
						connection.setInstanceFollowRedirects(false); 
						connection.setRequestMethod("POST"); 
						connection.setRequestProperty("Content-Type", "application/json"); 
						connection.setRequestProperty("charset", "UTF-8");
						connection.addRequestProperty("Authorization", "key=AAAAKN9Yyac:APA91bErrurOcc-_STWLOVlGIVLGBtx94Rz0YoH2yriMJt1z91n6G2Sz0sPMsaGqZxR3qmxWXHx1FG4kCs_t2r2SUREaEasuw3Leik7b1m-SS-8geh3e3vpAOVoLxkIAij_b_hjHVdFMju1EEii79InBOnjHLhKLIA");
						JSONObject notif = new JSONObject();
						JSONObject dados = new JSONObject();
						
						dados.put("tag", id);
						dados.put("title", titulo);
						dados.put("text", mensagem);
						dados.put("sound", "default");
						
						notif.put("notification", dados);
						notif.put("to", token);
						
						OutputStreamWriter wr= new OutputStreamWriter(connection.getOutputStream());
						wr.write(notif.toString());
						wr.flush();
						
						connection.getResponseCode();
						
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
			}.start();
		}
	}
		
	public static void enviarFcmNotificacaoIndividual(final Integer id, final String titulo, final String mensagem, final String token) {
		new Thread() {
			@Override
			public void run() {
				try {
					URL url = new URL("https://fcm.googleapis.com/fcm/send");
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();           
					connection.setDoOutput(true);
					connection.setDoInput(true);
					connection.setUseCaches(false);
					connection.setInstanceFollowRedirects(false); 
					connection.setRequestMethod("POST"); 
					connection.setRequestProperty("Content-Type", "application/json"); 
					connection.setRequestProperty("charset", "UTF-8");
					connection.addRequestProperty("Authorization", "key=AAAAKN9Yyac:APA91bErrurOcc-_STWLOVlGIVLGBtx94Rz0YoH2yriMJt1z91n6G2Sz0sPMsaGqZxR3qmxWXHx1FG4kCs_t2r2SUREaEasuw3Leik7b1m-SS-8geh3e3vpAOVoLxkIAij_b_hjHVdFMju1EEii79InBOnjHLhKLIA");
					JSONObject notif = new JSONObject();
					JSONObject dados = new JSONObject();
					
					dados.put("tag", id);
					dados.put("title", titulo);
					dados.put("text", mensagem);
					dados.put("sound", "default");
					
					notif.put("notification", dados);
					notif.put("to", token);
					
					OutputStreamWriter wr= new OutputStreamWriter(connection.getOutputStream());
					wr.write(notif.toString());
					wr.flush();
					
					connection.getResponseCode();
					
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}.start();
	}

	public static String getBytesString(String string) {
		String bytesString = "erro";
		try {
			byte[] bytes = string.getBytes("UTF-8");
			bytesString = Arrays.toString(bytes);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			bytesString = "erro";
		}
		return bytesString;
	}

	public static String arrayToString(List<?> indisponiveis) {
		String str = indisponiveis.toString();
		return str.replace("[", "").replace(" ", "").replace("]", "");
	}

	public static String getProximoCodigoComTransacao(Session session) {
		SequencialDAO sequencialDAO = new SequencialDAO(session);
		Sequencial sequencial = sequencialDAO.getSequencialPorNome("rifadosamigos");
		if (sequencial == null) {
			sequencial = new Sequencial("rifadosamigos");
			sequencialDAO.salvar(sequencial);
		}
		String numero = sequencial.getProxCodigo();
		session.update(sequencial);
		
		return numero;
	}

	public static String getDataAtualPorFormato(String formato) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato);
		LocalDateTime data = LocalDateTime.now();
		return data.format(formatter);
	}

//	public static String gerarMenuNovo(List<Modulo_Acao> acoes) {
//		return null;
//	} // Esse método é só para tirar o alerta de erro

	public static String getDataFormatadaUSA(Calendar instance) {
		return null;
	} // Só para tirar a mensagem de erro

	public static boolean validaRegexSenha(String senha) {
		return senha.matches("^(?=.*[A-Z])(?=.*[0-9])[A-Za-z!@#$&*?\\d]{6,}$");
	}

	/**
	 * Gera a lista de ações para armazenar na seção da pessoa logada.
	 * @param acaoAdicional, String, adiciona uma ação que não está na lista passada.
	 * @param acoes, List<Modulo_Acao>, é a lista de ações que a pessoa pode executar.
	 * @return List<String> com as ações do sistema que a pessoa logada pode executar no módulo.
	 */
//	public static List<String> getListaAcoes(List<Modulo_Acao> acoes, String acaoAdicional) {
//		List<String> listaAcoes = new ArrayList<String>();
//		String link;
//		for (Modulo_Acao ma : acoes) {
//			link = ma.getLink();
//			if (link != null && !listaAcoes.contains(link)) listaAcoes.add(ma.getLink());
//
//			listarAcoesPrecedentes(ma, listaAcoes);
//			for (Modulo_Acao m : ma.getAcoesPrecedentes()) {
//				listaAcoes.add(m.getLink());
//
//				for (Modulo_Acao m2 : m.getAcoesPrecedentes()) {
//					listaAcoes.add(m2.getLink());
//				}
//			}
//		}

//		if (acaoAdicional != null && !acaoAdicional.isEmpty()) {
//			listaAcoes.add(acaoAdicional);
//		}
//
//		return listaAcoes;
//	}

//	private static void listarAcoesPrecedentes(Modulo_Acao acao, List<String> listaAcoes) {
//		String link;
//		for (Modulo_Acao ma : acao.getAcoesPrecedentes()) {
//			link = ma.getLink();
//			if (link != null && !listaAcoes.contains(link)) listaAcoes.add(link);
//			listarAcoesPrecedentes(ma, listaAcoes);
//		}
//		return;
//	}
	/**
	 * Gera o menu com base na lista de Modulo_Acao.
	 * @param acoes, List<Modulo_Acao>, é a lista de ações que a pessoa pode executar.
	 * @return um String com o menu criado.
	 */
	public static String gerarMenu(List<Modulo_Acao> acoes) {
		Collections.sort(acoes);
		String menu = "";

		for (Modulo_Acao ma : acoes) {
//			if (ma.getAcaoPai() != null || !ma.isMenu()) continue;
			if (ma.getLink() != null) {
				if (menu.contains(ma.getLink())) continue;
			} else {
				if (menu.contentEquals(ma.getNome())) continue;
			}
//			if (ma.getTemLink()) {
//				if (ma.isIconeImagem()) {
//					menu += "<li class=\"has-sub\"><a onclick=\"javascript:execute('"+ma.getLink()+"')\"><i><img src=\"images/"+ma.getIcone()+"\" height=\"16px\"></i> "+ma.getNome()+"</a></li>";
//				} else {
//					menu += "<li class=\"has-sub\"><a onclick=\"javascript:execute('"+ma.getLink()+"')\"><i class=\""+ma.getIcone()+"\"></i> "+ma.getNome()+"</a></li>";
//				}
//			} else {
//				if (ma.isIconeImagem()) {
//					menu += "<li class=\"has-sub\"><a onclick=\"javascript:void(0)\"><i><img src=\"images/"+ma.getIcone()+"\" height=\"16px\"></i> "+ma.getNome()+"<i class=\"mais glyphicon glyphicon-plus right plus\"></i></a><ul>";
//				} else {
//					menu += "<li class=\"has-sub\">"
//							+ "<a onclick=\"javascript:void(0)\">"
//							+ "<i class=\""+ma.getIcone()+"\"></i> "+ma.getNome()+"<i class=\"mais glyphicon glyphicon-plus right plus\"></i></a><ul>";
//				}
//				if (ma.getAcoesFilhos().size() > 0)
//					menu += gerarSubmenu(acoes, ma.getAcoesFilhos());
//				menu += "</ul></li>";
			}
//		}

		return menu;
	}

	//	Menu temporário para transição do layout novo
	public static String gerarMenuNovo(List<Modulo_Acao> acoes) {
		Collections.sort(acoes);
		StringBuilder menu = new StringBuilder();
		String iconeCss;

		for (Modulo_Acao ma : acoes) {
//			if (ma.getAcaoPai() != null || !ma.isMenu()) continue;
			if (ma.getLink() != null) {
				if (menu.toString().contains(ma.getLink())) continue;
			} else {
				if (menu.toString().contentEquals(ma.getNome())) continue;
			}

//			if (ma.getTemLink()) {
//				if (ma.getIcone() != null && ma.isIconeImg()) {
//					if (ma.getIcone().endsWith(".svg")) iconeCss = "nav-icon-svg"; else iconeCss = "";
//					String[] dir = ma.getIcone().split("\\.");
//					menu.append("<li class=\"nav-item\">" +
//							"<a class=\"nav-link\" href=\"#\" role=\"button\" data-target=\""+ma.getLink()+"\" onclick=\"javascript:execute('"+ma.getLink()+"')\">" +
//							"<img class=\"nav-icon-img "+iconeCss+" mr-1\" src=\"/portal/images/icons/"+dir[1]+"/"+ma.getIcone()+"\" />");
//					if (ma.getNome().length() >= 19) {
//						menu.append(
//								"<p class='text-sm d-inline-flex'>"+ma.getNome() + "</p>"
//						);
//					} else {
//						menu.append(
//								"<p class=''>"+ma.getNome() + "</p>"
//						);
//					}
//					menu.append("</a> </li>");
//				} else {
//					menu.append("<li class=\"nav-item\">"
//							+ "<a class=\"nav-link\" href=\"#\" role=\"button\" data-target='"+ma.getLink()+"' onclick=\"javascript:execute('"+ma.getLink()+"')\">"
//							+ "<i class=\"nav-icon "+ma.getIcone()+"\"></i>");
//					if (ma.getNome().length() >= 19) {
//						menu.append( "<p class='text-sm d-inline-flex'>"+ma.getNome() + "</p>" );
//					} else {
//						menu.append( "<p class=''>"+ma.getNome() + "</p>"	);
//					}
//					menu.append("</a> </li>");
//				}
//			} else {
//				if (ma.getIcone() != null && ma.isIconeImg()) {
//					if (ma.getIcone().endsWith(".svg")) iconeCss = "nav-icon-svg"; else iconeCss = "";
//					String[] dir = ma.getIcone().split("\\.");
//					menu.append("<li class=\"nav-item has-treeview\">" +
//							"<a class=\"nav-link\">" +
//							"<img class=\"nav-icon-img "+iconeCss+" mr-1\" src=\"/portal/images/icons/"+dir[1]+"/"+ma.getIcone()+"\" />");
//					if (ma.getNome().length() >= 19) {
//						menu.append( "<p class='text-sm d-inline-flex'>"+ma.getNome() );
//					} else {
//						menu.append( "<p class=''>"+ma.getNome() );
//					}
//					menu.append("<i class=\"right fas fa-angle-down\"></i></p></a>");
//				} else {
//					menu.append(
//							"<li class=\"nav-item has-treeview\">" +
//									"<a class=\"nav-link\" href=\"#\" onclick=\"javascript:void(0)\">" +
//									"<i class=\"nav-icon "+ma.getIcone()+"\"></i> ");
//					if (ma.getNome().length() >= 19) {
//						menu.append( "<p class='text-sm d-inline-flex'>"+ma.getNome() );
//					} else {
//						menu.append( "<p class=''>"+ma.getNome() );
//					}
//					menu.append("<i class=\"right fas fa-angle-down\"></i></p></a>");
//				}
//				if (ma.getAcoesFilhos().size() > 0) menu.append(gerarSubmenuNovo(acoes, ma.getAcoesFilhos()) + "</li>");
//			}
		}
		return menu.toString();
	}

	/**
	 * Auxiliar na criação do menu, criando os subsmenus.
	 * @param acoes, List<Modulo_Acao>, é a lista de ações que a pessoa pode executar.
	 * @return um String com o submenu criado.
	 */
	private static String gerarSubmenu(List<Modulo_Acao> acoes, List<Modulo_Acao> subacoes) {
		String submenu = "";

		for (Modulo_Acao ma : subacoes) {
			if (!ma.isMenu()) continue;
			if (!acoes.contains(ma)) continue;
//			if (ma.getTemLink()) {
//				if (ma.isIconeImagem()) {
//					submenu += "<li class=\"has-sub\"><a onclick=\"javascript:execute('"+ma.getLink()+"')\"><i><img src=\"images/"+ma.getIcone()+"\" height=\"16px\"></i> "+ma.getNome()+"</a></li>";
//				} else {
//					submenu += "<li class=\"has-sub\"><a onclick=\"javascript:execute('"+ma.getLink()+"')\"><i class=\""+ma.getIcone()+"\"></i> "+ma.getNome()+"</a></li>";
//				}
//			} else {
//				if (ma.isIconeImagem()) {
//					submenu += "<li class=\"has-sub\"><a onclick=\"javascript:void(0)\"><i><img src=\"images/"+ma.getIcone()+"\" height=\"16px\"></i> "+ma.getNome()+"<i class=\"mais glyphicon glyphicon-plus right plus\"></i></a><ul>";
//				} else {
//					submenu += "<li class=\"has-sub\"><a onclick=\"javascript:void(0)\"><i class=\""+ma.getIcone()+"\"></i> "+ma.getNome()+"<i class=\"mais glyphicon glyphicon-plus right plus\"></i></a><ul>";
//				}
//				if (ma.getAcoesFilhos().size() > 0)
//					submenu += gerarSubmenu(acoes, ma.getAcoesFilhos());
//				submenu += "</ul></li>";
//			}
		}

		return submenu;
	}
}
