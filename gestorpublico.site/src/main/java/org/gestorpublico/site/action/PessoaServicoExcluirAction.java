package org.gestorpublico.site.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.gestorpublico.dao.Pessoa_ServicoDAO;
import org.gestorpublico.entidade.Pessoa_Servico;
import org.gestorpublico.util.PadraoAction;
import org.hibernate.Session;

@ParentPackage("default")
public class PessoaServicoExcluirAction extends PadraoAction {

    private Integer id;

    @Action(value = "pessoaServicoExcluir",
        results = {
            @Result(name = "ok", type = "httpheader", params = {"status", "200"}),
            @Result(name = "erro", type = "httpheader", params = {"status", "409"})
        }
    )
    public String execute() {
        try {
            Session session = getSession();
            Pessoa_ServicoDAO pessoaServicoDAO = new Pessoa_ServicoDAO(session);
            Pessoa_Servico pessoaServico = pessoaServicoDAO.localizar(id);

            if (pessoaServico == null) {
                response.setHeader("erro", "Essa solicitação já foi cancelada.");
                return "erro";
            }

            if (pessoaServico.getAutorizado() != null) {
                response.setHeader("erro", "Essa solicitação já tem um despacho, não é possível excluí-la.");
                return "erro";
            }

            pessoaServicoDAO.excluir(pessoaServico);

            response.setHeader("id", pessoaServico.getId().toString());
            response.setHeader("msg", "Solicitação excluída com sucesso!");

            return "ok";

        } catch (Exception e) {
            e.printStackTrace();
            addErro(e.getMessage());
            return "erro";
        }
    }

    // ****************************** GETs e SETs ******************************
    public void setId(Integer id) {
        this.id = id;
    }
}
