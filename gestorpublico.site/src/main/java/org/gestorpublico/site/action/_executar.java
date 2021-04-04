package org.gestorpublico.site.action;

import org.gestorpublico.dao.PessoaDAO;
import org.gestorpublico.dao.Pessoa_DependenteDAO;
import org.gestorpublico.dao.SexoDAO;
import org.gestorpublico.dao.TipoDAO;
import org.gestorpublico.entidade.Pessoa;
import org.gestorpublico.entidade.Pessoa_Dependente;
import org.gestorpublico.hibernate.HibernateUtil;
import org.hibernate.Session;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class _executar {
    public static void main(String[] args) {

        Session session = HibernateUtil.getSession();
        PessoaDAO pessoaDAO = new PessoaDAO(session);

        Pessoa mae = pessoaDAO.getPessoaPorCPF("03409914536");
        Pessoa pai = pessoaDAO.getPessoaPorCPF("71156135591");

        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Alex Gabriel Santos Costa");
        pessoa.setCelular("79988029294");
        pessoa.setSenha("0");
        pessoa.setAcessaSistema(true);
        pessoa.setPostsLiberados(false);
        pessoa.setPostsMidiaLiberados(false);
        pessoa.setLogin("10101010101");
        pessoa.setSexo(new SexoDAO(session).getSexoPorNome("Masculino"));
        pessoa.setAuxilioAluguel(false);
        pessoa.setBolsaFamilia(false);
        pessoa.setCpf("10101010101");
        pessoa.setDataNascimento("27/03/2009");
        pessoa.setMae(mae);
        pessoa.setPai(pai);
        pessoa.setVivo(true);
        pessoaDAO.salvar(pessoa);
        System.out.println("Pessoa salva com sucesso!");

        Pessoa_Dependente pessoaDependente = new Pessoa_Dependente();
        pessoaDependente.setAscendente(pai);
        pessoaDependente.setDependente(pessoa);
        pessoaDependente.setTipoDependente(new TipoDAO(session).getTipoPorObjetoNomeTipo("Tipo_Dependente", "Filho"));
        pessoaDependente.setAtivo(true);
        pessoaDependente.setDataHoraCadastro(LocalDateTime.now());
        new Pessoa_DependenteDAO(session).salvar(pessoaDependente);
        System.out.println("Dependente incluído com sucesso!");
    }
}
