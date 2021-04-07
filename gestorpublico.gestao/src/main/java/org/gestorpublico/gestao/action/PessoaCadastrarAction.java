package org.gestorpublico.gestao.action;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.gestorpublico.dao.*;
import org.gestorpublico.entidade.*;
import org.gestorpublico.hibernate.HibernateUtil;
import org.gestorpublico.util.CassUtil;
import org.gestorpublico.util.PadraoAction;
import org.hibernate.Session;

import javax.persistence.Tuple;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@ParentPackage("default")
public class PessoaCadastrarAction extends PadraoAction {

    private HttpServletRequest request = ServletActionContext.getRequest();
    private HttpServletResponse response = ServletActionContext.getResponse();

    private Pessoa pessoa;
    private Pessoa_Endereco pessoaEndereco;

    @Action(value="pessoaCadastrar",
        results={
            @Result(name="ok", type="httpheader", params={"status", "200"}),
            @Result(name="erro", type="httpheader", params={"status", "409"})
        }
    )
    public String execute() {
        Session session = getSession();
        try {
            PessoaDAO pessoaDAO = new PessoaDAO(session);

            Pessoa p= pessoaDAO.getPessoaPorCPF(pessoa.getCpf());
            if (p != null) {
                if (p.getNome().equals(pessoa.getNome())) {
                    addErro("Essa pessoa já está cadastrada.");
                    return "erro";
                }

                addErro("Já exisite uma pessoa cadastrada com esse CPF");
                return "erro";
            }

            if (!pessoa.isVivo() && pessoa.getDataFalecimento() != null) {
                addErro("Se a pessoa não está viva, informe a data de falecimento.");
                return "erro";
            }

            pessoa.setLogin(pessoa.getCpf());
            pessoa.setSenha(CassUtil.criptografar(pessoa.getPrimeiroNome()+pessoa.getDataNascimento().getYear()));

            pessoaDAO.salvar(pessoa);
            pessoaEndereco.setPessoa(pessoa);
            new Pessoa_EnderecoDAO(session).salvar(pessoaEndereco);

            if(pessoa.isMenorIdade()) {
                Pessoa_DependenteDAO pessoaDependenteDAO = new Pessoa_DependenteDAO(session);
                Tipo FILHO = new TipoDAO(session).getTipoPorObjetoNomeTipo("Tipo_Dependente", "Filho");

                Pessoa_Dependente pessoaDependente = new Pessoa_Dependente();
                pessoaDependente.setTipoDependente(FILHO);
                pessoaDependente.setAscendente(pessoa.getMae());
                pessoaDependente.setDependente(pessoa);
                pessoaDependente.setAtivo(pessoa.isVivo());

                pessoaDependenteDAO.salvar(pessoaDependente);

                if (pessoa.getPai() != null || pessoa.getPai().getId() != null) {
                    pessoaDependente = new Pessoa_Dependente();
                    pessoaDependente.setTipoDependente(FILHO);
                    pessoaDependente.setAscendente(pessoa.getPai());
                    pessoaDependente.setDependente(pessoa);
                    pessoaDependente.setAtivo(pessoa.isVivo());

                    pessoaDependenteDAO.salvar(pessoaDependente);
                }
            }

            addId(pessoa.getId().toString());
            addMsg("Pessoa cadastrada com sucesso!");

            return "ok";

        } catch (Exception e) {
            e.printStackTrace();
            String erro = e.getMessage() == null ? ExceptionUtils.getRootCauseMessage(e.fillInStackTrace()) : e.getMessage();

            response.addHeader("erro", "Ocorreu o seguinte erro: " + erro);

            Session sessao = HibernateUtil.getSession();
            new Log_Erro_ExecucaoDAO(sessao).salvar(new Log_Erro_Execucao(getActionName(), erro));
            sessao.close();

            return "erro";
        }
    }

    // ****************************** GETs e SETs ******************************
    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Pessoa_Endereco getPessoaEndereco() {
        return pessoaEndereco;
    }

    public void setPessoaEndereco(Pessoa_Endereco pessoaEndereco) {
        this.pessoaEndereco = pessoaEndereco;
    }
}
