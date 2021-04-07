package org.gestorpublico.gestao.action;

import org.gestorpublico.dao.PessoaDAO;
import org.gestorpublico.entidade.Pessoa;
import org.gestorpublico.hibernate.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;

public class _testes {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSession();
        Pessoa pessoa = new PessoaDAO(session).localizar(11);
        System.out.println('"'+pessoa.getPrimeiroNome()+ pessoa.getDataNascimento().getYear()+'"');
    }
}
