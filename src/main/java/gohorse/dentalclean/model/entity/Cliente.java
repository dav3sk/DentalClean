package gohorse.dentalclean.model.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name="cliente")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

        
    @Column(name="nome",
            nullable=false,
            length=100)
    private String nome;
    
    @Column(name="telefone",
            nullable=true,
            length=15)
    private String telefone;
    
    @Column(name="email",
            nullable=false,
            length=244,
            unique=true)
    private String email;
    
    @Column(name="cpf",
            nullable=false,
            length=14,
            unique=true)
    private String cpf;
    
    @Column(name="rg",
            length=12)
    private String rg;
    
    @Column(name="cep",
            nullable=false,
            length=9)
    private String cep;
    
    @Column(name="dataDeNascimento",
            nullable=false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataDeNascimento;
    
    @Column(name="nivelAcesso",
            columnDefinition="char(14) default 'CLIENTE'")
    private String nivelAcesso;
    
    @Column(name="senha",
            nullable=false,
            length=35)
    private String senha;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Date getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(Date dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public String getNivelAcesso() {
        return nivelAcesso;
    }
    
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Cliente[ id="              + id +
                          " nome= "           + nome +
                          " telefone="        + telefone +  
                          " email="           + email +
                          " cpf="             + cpf +
                          " rg="              + rg +
                          " cep="             + cep +
                          " dtNascimento="    + dataDeNascimento + "]";
    }
    
}
