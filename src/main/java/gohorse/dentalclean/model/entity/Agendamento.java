package gohorse.dentalclean.model.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name="agendamento")
public class Agendamento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    @JoinColumn(nullable=false)
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(nullable=false)
    private Dentista dentista;
    
    @Column(name="dataHorario",
            nullable=false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataHorario;
    
    @Column(name="status",
            nullable=false)
    private String status = "PENDENTE";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Dentista getDentista() {
        return dentista;
    }

    public void setDentista(Dentista dentista) {
        this.dentista = dentista;
    }

    public Date getDataHorario() {
        return dataHorario;
    }

    public void setDataHorario(Date data) {
        this.dataHorario = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Agendamento)) {
            return false;
        }
        Agendamento other = (Agendamento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Agendamento[ id="           + id +
                          " clienteid="     + cliente.getId() +
                          " dentistaid="    + dentista.getId() +
                          " status="        + status +
                          " data="          + dataHorario +"]";
    }
    
}