package org.gestorpublico.gestao.action;

import com.opensymphony.xwork2.ActionContext;
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
import java.time.LocalDate;
import java.util.List;

@ParentPackage("default")
public class PessoaCadastrarAction {

    private HttpServletRequest request = ServletActionContext.getRequest();
    private HttpServletResponse response = ServletActionContext.getResponse();

    private Pessoa pessoa;
    private Pessoa_Endereco pessoaEndereco;
    private String dataNascimento;
    private Pessoa mae;
    private Pessoa pai;
    private Sexo sexo;
    private Rua rua;
    private Condominio condominio;
    private Edificio edificio;
    private Ponto_Referencia pontoReferencia;

    @Action(value="pessoaCadastrar",
        results={
            @Result(name="ok", type="httpheader", params={"status", "200"}),
            @Result(name="erro", type="httpheader", params={"status", "409"})
        }
    )
    public String execute() {
        Session session = (Session) request.getAttribute("sessao");
        String action = ActionContext.getContext().getName();
        try {
            PessoaDAO pessoaDAO = new PessoaDAO(session);

            Pessoa p= pessoaDAO.getPessoaPorCPF(pessoa.getCpf());
            if (p != null) {
                if (p.getNome().equals(pessoa.getNome())) {
                    response.setHeader("erro", "Essa pessoa já está cadastrada.");
                    return "erro";
                }

                response.addHeader("erro", "Já exisite uma pessoa cadastrada com esse CPF");
                return "erro";
            }

            if (!pessoa.isVivo() && pessoa.getDataFalecimento() != null) {
                response.addHeader("erro", "Se a pessoa não está viva, informe a data de falecimento.");
                return "erro";
            }

            if (mae == null || mae.getId() == null) {
                response.addHeader("erro","Informe a mãe.");
                return "erro";
            }

            if (sexo == null || sexo.getId() == null) {
                response.addHeader("erro","Informe o sexo.");
                return "erro";
            }

            if (rua == null || rua.getId() == null) {
                response.addHeader("erro","Informe a Rua.");
                return "erro";
            }

            if (pai == null || pai.getId() == null) {
                pai = null;
            }
            pessoa.setDataNascimento(dataNascimento);
            pessoa.setMae(mae);
            pessoa.setPai(pai);
            pessoa.setSexo(sexo);
            pessoa.setAcessaSistema(pessoa.isVivo());
            pessoa.setLogin(pessoa.getCpf());
            pessoa.setSenha(CassUtil.criptografar(pessoa.getPrimeiroNome()+pessoa.getDataNascimento().getYear()));

            pessoaDAO.salvar(pessoa);
            pessoaEndereco.setPessoa(pessoa);
            pessoaEndereco.setRua(rua);
            if (pessoaEndereco.getCondominio() == null || pessoaEndereco.getCondominio().getId() == null) pessoaEndereco.setCondominio(null);
            if (pessoaEndereco.getEdificio() == null || pessoaEndereco.getEdificio().getId() == null) pessoaEndereco.setEdificio(null);
            if (pessoaEndereco.getPontoReferencia() == null || pessoaEndereco.getPontoReferencia().getId() == null) pessoaEndereco.setPontoReferencia(null);
            new Pessoa_EnderecoDAO(session).salvar(pessoaEndereco);

            if(pessoa.isMenorIdade()) {
                Pessoa_DependenteDAO pessoaDependenteDAO = new Pessoa_DependenteDAO(session);
                Tipo FILHO = new TipoDAO(session).getTipoPorObjetoNomeTipo("Tipo_Dependente", "Filho");
                Pessoa_Dependente pessoaDependente = new Pessoa_Dependente();
                mae = pessoaDAO.getPessoa(mae);

                if (!mae.getNome().equalsIgnoreCase("Não Informado")) {
                    pessoaDependente.setTipoDependente(FILHO);
                    pessoaDependente.setAscendente(mae);
                    pessoaDependente.setDependente(pessoa);
                    pessoaDependente.setAtivo(pessoa.isVivo());

                    pessoaDependenteDAO.salvar(pessoaDependente);
                }

                if (pessoa.getPai() != null || pessoa.getPai().getId() != null) {
                    pai = pessoaDAO.getPessoa(pai);
                    if (!pai.getNome().equalsIgnoreCase("Não Informado")) {
                        pessoaDependente = new Pessoa_Dependente();
                        pessoaDependente.setTipoDependente(FILHO);
                        pessoaDependente.setAscendente(pai);
                        pessoaDependente.setDependente(pessoa);
                        pessoaDependente.setAtivo(pessoa.isVivo());

                        pessoaDependenteDAO.salvar(pessoaDependente);
                    }
                }
            }

            response.addHeader("id",pessoa.getId().toString());
            response.addHeader("msg","Pessoa cadastrada com sucesso!");

            return "ok";

        } catch (Exception e) {
            e.printStackTrace();
            String erro = e.getMessage() == null ? ExceptionUtils.getRootCauseMessage(e.fillInStackTrace()) : e.getMessage();

            response.addHeader("erro", "Ocorreu o seguinte erro: " + erro);

            Session sessao = HibernateUtil.getSession();
            new Log_Erro_ExecucaoDAO(sessao).salvar(new Log_Erro_Execucao(action, erro));
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

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setMae(Pessoa mae) {
        this.mae = mae;
    }

    public void setPai(Pessoa pai) {
        this.pai = pai;
    }

    public void setRua(Rua rua) {
        this.rua = rua;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public void setCondominio(Condominio condominio) {
        this.condominio = condominio;
    }

    public void setEdificio(Edificio edificio) {
        this.edificio = edificio;
    }

    public void setPontoReferencia(Ponto_Referencia pontoReferencia) {
        this.pontoReferencia = pontoReferencia;
    }
}
