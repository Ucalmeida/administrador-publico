package org.gestorpublico.site.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.gestorpublico.dao.PessoaDAO;
import org.gestorpublico.entidade.Pessoa;
import org.gestorpublico.entidade.Sexo;
import org.gestorpublico.util.PadraoAction;
import org.hibernate.Session;

@ParentPackage("default")
public class CadastrarPessoaAction extends PadraoAction {

    private Pessoa pessoa;
    private Sexo sexo;
    private String cpf;
    private Integer postsLiberados;
    private Integer midiasLiberadas;
    private Integer acessaSistema;

    @Action(value = "cadastrarPessoa",
            results = {
                @Result(name = "ok", type = "httpheader", params = { "status", "200" }),
                @Result(name = "erro", type = "httpheader", params = {"status", "409"})
            }
    )

    public String execute() {
        try {
            Session session = getSession();
            PessoaDAO pessoaDAO = new PessoaDAO(session);

            pessoa.setSexo(sexo);
            pessoa.setLogin(cpf);

            if(postsLiberados == 1) {
                pessoa.setPostsLiberados(true);
            } else {
                pessoa.setPostsLiberados(false);
            }

            if(midiasLiberadas == 1) {
                pessoa.setPostsMidiaLiberados(true);
            } else {
                pessoa.setPostsMidiaLiberados(false);
            }

            if(acessaSistema == 1) {
                pessoa.setAcessaSistema(true);
            } else {
                pessoa.setAcessaSistema(false);
            }

            pessoaDAO.salvar(pessoa);

            addId(pessoa.getId().toString());
            addMsg("Pessoa cadastrada com sucesso!");

            return "ok";

        } catch (Exception e) {
            e.printStackTrace();
            addErro(e.getMessage());
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

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public void setPostsLiberados(Integer postsLiberados) {
        this.postsLiberados = postsLiberados;
    }

    public void setMidiasLiberadas(Integer midiasLiberadas) {
        this.midiasLiberadas = midiasLiberadas;
    }

    public void setAcessaSistema(Integer acessaSistema) {
        this.acessaSistema = acessaSistema;
    }
}
