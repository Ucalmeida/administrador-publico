package org.gestorpublico.site.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.gestorpublico.dao.SexoDAO;
import org.gestorpublico.entidade.Sexo;
import org.gestorpublico.util.PadraoAction;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

@ParentPackage("default")
public class PessoaNovoFormCadastroAction extends PadraoAction {

    private List<Sexo> sexos = new ArrayList<>();

    @Action(value = "pessoaNovoFormCadastro",
            results = {
                    @Result(name = "ok", location = "formNovoPessoa.jsp"),
                    @Result(name = "erro", type = "httpheader", params = {"status", "409"})
            }
    )
    public String execute() {
        try {
            Session session = getSession();

            sexos = new SexoDAO(session).listarSexos();

            return SUCESSO;

        } catch (Exception e) {
            e.printStackTrace();
            response.addHeader("erro", e.getMessage());
            return ERRO;
        }
    }

    // ****************************** GETs e SETs ******************************
    public List<Sexo> getSexos() {
        return sexos;
    }
}