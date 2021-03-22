package org.gestorpublico.entidade;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Sequencial implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length=100, nullable=false)
	private String nome;
	
	@Column(nullable=true)
	private Integer anoAnterior;
	
	@Column(nullable=false)
	private Integer proxNumero = 0;
	
	// ************************************* CONSTRUTORES *************************************
	public Sequencial() {}
	
	public Sequencial(String nome) {
		setNome(nome);
	}
	
	// ************************************* GETS e SETs **************************************
	public int getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getProxNumero(int tamanho) {
		
		this.proxNumero += 1;
		
		String num = this.proxNumero.toString();
		while (num.length() < tamanho) {
			num = "0"+num;
		}
		
		return num;
	}

	/**
	 * Retorna o proximo numero de acordo com o tipo de documento
	 * @param tipo nota ou bol
	 * @return AAAA00000 (nota) ou AAAAMMDD0000 (bol)
	 */
	public String getProxNumero() {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(new Date());
		
		Integer anoAtual = gc.get(Calendar.YEAR);
		
		if (anoAtual == null || !anoAtual.equals(anoAnterior)) {
			this.proxNumero = 0;
			this.anoAnterior = anoAtual;
		}
		
		this.proxNumero += 1;
		
		String num = this.proxNumero.toString();
		
		switch (num.length()) {
			case 1:num = "0000"+num;break;
			case 2:num = "000"+num;break;
			case 3:num = "00"+num;break;
			case 4:num = "0"+num;break;
		}
		
		String dia = "";
		String mes = "";
		
		if (this.nome.equals("Bol")) {
			dia = String.valueOf(gc.get(Calendar.DAY_OF_MONTH));
			if (dia.length() == 1) dia = "0" + dia;
		
			mes = String.valueOf(gc.get(Calendar.MONTH) + 1);
			if (mes.length() == 1) mes = "0" + mes;
		}
		
		num = String.valueOf(gc.get(Calendar.YEAR)) + mes + dia + (this.nome.equals("Bol") ? num.substring(2) : num);
		
		return num;
	}

	public String getProxCodigo() {
		String dia = "Z";
		
		Calendar hoje = Calendar.getInstance();
		switch (hoje.get(Calendar.DAY_OF_WEEK)) {
			case Calendar.SUNDAY: dia = "S"; break;
			case Calendar.MONDAY: dia = "M"; break;
			case Calendar.TUESDAY: dia = "T"; break;
			case Calendar.WEDNESDAY: dia = "W"; break;
			case Calendar.THURSDAY: dia = "H"; break;
			case Calendar.FRIDAY: dia = "F"; break;
			case Calendar.SATURDAY: dia = "A"; break;
		}
		
		Integer mesAtual = hoje.get(Calendar.MONTH);
		
		if (mesAtual == null || !mesAtual.equals(anoAnterior)) {
			this.proxNumero = 0;
			this.anoAnterior = mesAtual;
		}
		
		this.proxNumero += 1;
		
		String num = Integer.toHexString(this.proxNumero);
		
		switch (num.length()) {
			case 1:num = "000"+num;break;
			case 2:num = "00"+num;break;
			case 3:num = "0"+num;break;
		}
		
		return dia + (num.length() <= 5 ? "1"+num : num);
	}

	public Integer getProxNumeroInteiro() {
		proxNumero += 1;
		return proxNumero;
	}

	public void setProxNumero(Integer proxNumero) {
		this.proxNumero = proxNumero;
	}

}
