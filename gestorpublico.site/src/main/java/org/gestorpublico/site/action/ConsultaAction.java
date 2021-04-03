package org.gestorpublico.site.action;

import org.apache.commons.collections.map.HashedMap;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.gestorpublico.dao.BeneficioDAO;
import org.gestorpublico.dao.PessoaDAO;
import org.gestorpublico.dao.Pessoa_DependenteDAO;
import org.gestorpublico.entidade.Pessoa;
import org.gestorpublico.util.PadraoAction;
import org.hibernate.Session;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ParentPackage("default")
public class ConsultaAction extends PadraoAction {

    private List<Tuple> pacientes = new ArrayList<>();
    private List<Map<String, Object>> especialidades = new ArrayList<Map<String, Object>>();

    @Action(value = "consulta",
        results = {
            @Result(name = "ok", location = "consultaFormAgenda.jsp"),
            @Result(name = "erro", type = "httpheader", params = {"status", "409"})
        }
    )
    public String execute() {
        try {
            Session session = getSession();

            pacientes.add(new PessoaDAO(session).getPessoaTuple(getPessoaLogada()));
            if (getPessoaLogada().getDependentes() != null && getPessoaLogada().getDependentes().size() > 0) {
                pacientes.addAll(new Pessoa_DependenteDAO(session).listeDependentesPorAscendente(getPessoaLogada()));
            }

            especialidades.add(new HashMap<String, Object>(){{put("id", 1);put("nome", "Clínica Médica");}});
            especialidades.add(new HashMap<String, Object>(){{put("id", 1);put("nome", "Dentista");}});

            return "ok";

        } catch (Exception e) {
            e.printStackTrace();
            addErro(e.getMessage());
            return "erro";
        }
    }

    // ****************************** GETs e SETs ******************************
    public List<Tuple> getPacientes() {
        return pacientes;
    }

    public List<Map<String, Object>> getEspecialidades() {
        return especialidades;
    }
}
