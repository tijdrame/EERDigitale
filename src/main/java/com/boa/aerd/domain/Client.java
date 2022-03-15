package com.boa.aerd.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "client_seq_gen")
    @SequenceGenerator(name = "client_seq_gen", sequenceName = "client_id_seq", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "cod_civil")
    private String codCivil;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "nom_mari")
    private String nomMari;

    @Column(name = "prenom_mari")
    private String prenomMari;

    @Column(name = "nom_mere")
    private String nomMere;

    @Column(name = "prenom_mere")
    private String prenomMere;

    @Column(name = "nom_pere")
    private String nomPere;

    @Column(name = "prenom_pere")
    private String prenomPere;

    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    @Column(name = "dep_naissance")
    private String depNaissance;

    @Column(name = "comm_naiss")
    private String commNaiss;

    @Column(name = "pays_naiss")
    private String paysNaiss;

    @Column(name = "nation")
    private String nation;

    @Column(name = "sexe")
    private String sexe;

    @Column(name = "situ")
    private String situ;

    @Column(name = "prof")
    private String prof;

    @Column(name = "langue")
    private String langue;

    @Column(name = "relation")
    private String relation;

    @Column(name = "identite")
    private String identite;

    @Column(name = "num_id")
    private String numId;

    @Column(name = "dat_livr")
    private LocalDate datLivr;

    @Column(name = "dat_valid")
    private LocalDate datValid;

    @Column(name = "pay_id")
    private String payId;

    @Column(name = "comm_id")
    private String commId;

    @Column(name = "addr_1")
    private String addr1;

    @Column(name = "addr_2")
    private String addr2;

    @Column(name = "addr_3")
    private String addr3;

    @Column(name = "addr_4")
    private String addr4;

    @Column(name = "cod_cour")
    private String codCour;

    @Column(name = "cod_post")
    private String codPost;

    @Column(name = "mail")
    private String mail;

    @Column(name = "pays")
    private String pays;

    @Column(name = "dep_res")
    private String depRes;

    @Column(name = "tel")
    private String tel;

    @Column(name = "tel_prof")
    private String telProf;

    @Column(name = "tele_fax")
    private String teleFax;

    @Column(name = "agence")
    private String agence;

    @Column(name = "ncg")
    private String ncg;

    @Column(name = "devise")
    private String devise;

    @Column(name = "code_exploitant")
    private String codeExploitant;

    @Column(name = "dat_cre")
    private LocalDate datCre;

    @Column(name = "pays_cpt")
    private String paysCpt;

    @Column(name = "id_client")
    private String idClient;

    @Column(name = "identif_tel")
    private String indicatifTel;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodCivil() {
        return codCivil;
    }

    public Client codCivil(String codCivil) {
        this.codCivil = codCivil;
        return this;
    }

    public void setCodCivil(String codCivil) {
        this.codCivil = codCivil;
    }

    public String getNom() {
        return nom;
    }

    public Client nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Client prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNomMari() {
        return nomMari;
    }

    public Client nomMari(String nomMari) {
        this.nomMari = nomMari;
        return this;
    }

    public void setNomMari(String nomMari) {
        this.nomMari = nomMari;
    }

    public String getPrenomMari() {
        return prenomMari;
    }

    public Client prenomMari(String prenomMari) {
        this.prenomMari = prenomMari;
        return this;
    }

    public void setPrenomMari(String prenomMari) {
        this.prenomMari = prenomMari;
    }

    public String getNomMere() {
        return nomMere;
    }

    public Client nomMere(String nomMere) {
        this.nomMere = nomMere;
        return this;
    }

    public void setNomMere(String nomMere) {
        this.nomMere = nomMere;
    }

    public String getPrenomMere() {
        return prenomMere;
    }

    public Client prenomMere(String prenomMere) {
        this.prenomMere = prenomMere;
        return this;
    }

    public void setPrenomMere(String prenomMere) {
        this.prenomMere = prenomMere;
    }

    public String getNomPere() {
        return nomPere;
    }

    public Client nomPere(String nomPere) {
        this.nomPere = nomPere;
        return this;
    }

    public void setNomPere(String nomPere) {
        this.nomPere = nomPere;
    }

    public String getPrenomPere() {
        return prenomPere;
    }

    public Client prenomPere(String prenomPere) {
        this.prenomPere = prenomPere;
        return this;
    }

    public void setPrenomPere(String prenomPere) {
        this.prenomPere = prenomPere;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public Client dateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
        return this;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getDepNaissance() {
        return depNaissance;
    }

    public Client depNaissance(String depNaissance) {
        this.depNaissance = depNaissance;
        return this;
    }

    public void setDepNaissance(String depNaissance) {
        this.depNaissance = depNaissance;
    }

    public String getCommNaiss() {
        return commNaiss;
    }

    public Client commNaiss(String commNaiss) {
        this.commNaiss = commNaiss;
        return this;
    }

    public void setCommNaiss(String commNaiss) {
        this.commNaiss = commNaiss;
    }

    public String getPaysNaiss() {
        return paysNaiss;
    }

    public Client paysNaiss(String paysNaiss) {
        this.paysNaiss = paysNaiss;
        return this;
    }

    public void setPaysNaiss(String paysNaiss) {
        this.paysNaiss = paysNaiss;
    }

    public String getNation() {
        return nation;
    }

    public Client nation(String nation) {
        this.nation = nation;
        return this;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getSexe() {
        return sexe;
    }

    public Client sexe(String sexe) {
        this.sexe = sexe;
        return this;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getSitu() {
        return situ;
    }

    public Client situ(String situ) {
        this.situ = situ;
        return this;
    }

    public void setSitu(String situ) {
        this.situ = situ;
    }

    public String getProf() {
        return prof;
    }

    public Client prof(String prof) {
        this.prof = prof;
        return this;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }

    public String getLangue() {
        return langue;
    }

    public Client langue(String langue) {
        this.langue = langue;
        return this;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public String getRelation() {
        return relation;
    }

    public Client relation(String relation) {
        this.relation = relation;
        return this;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getIdentite() {
        return identite;
    }

    public Client identite(String identite) {
        this.identite = identite;
        return this;
    }

    public void setIdentite(String identite) {
        this.identite = identite;
    }

    public String getNumId() {
        return numId;
    }

    public Client numId(String numId) {
        this.numId = numId;
        return this;
    }

    public void setNumId(String numId) {
        this.numId = numId;
    }

    public LocalDate getDatLivr() {
        return datLivr;
    }

    public Client datLivr(LocalDate datLivr) {
        this.datLivr = datLivr;
        return this;
    }

    public void setDatLivr(LocalDate datLivr) {
        this.datLivr = datLivr;
    }

    public LocalDate getDatValid() {
        return datValid;
    }

    public Client datValid(LocalDate datValid) {
        this.datValid = datValid;
        return this;
    }

    public void setDatValid(LocalDate datValid) {
        this.datValid = datValid;
    }

    public String getPayId() {
        return payId;
    }

    public Client payId(String payId) {
        this.payId = payId;
        return this;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getCommId() {
        return commId;
    }

    public Client commId(String commId) {
        this.commId = commId;
        return this;
    }

    public void setCommId(String commId) {
        this.commId = commId;
    }

    public String getAddr1() {
        return addr1;
    }

    public Client addr1(String addr1) {
        this.addr1 = addr1;
        return this;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getAddr2() {
        return addr2;
    }

    public Client addr2(String addr2) {
        this.addr2 = addr2;
        return this;
    }

    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    public String getAddr3() {
        return addr3;
    }

    public Client addr3(String addr3) {
        this.addr3 = addr3;
        return this;
    }

    public void setAddr3(String addr3) {
        this.addr3 = addr3;
    }

    public String getAddr4() {
        return addr4;
    }

    public Client addr4(String addr4) {
        this.addr4 = addr4;
        return this;
    }

    public void setAddr4(String addr4) {
        this.addr4 = addr4;
    }

    public String getCodCour() {
        return codCour;
    }

    public Client codCour(String codCour) {
        this.codCour = codCour;
        return this;
    }

    public void setCodCour(String codCour) {
        this.codCour = codCour;
    }

    public String getCodPost() {
        return codPost;
    }

    public Client codPost(String codPost) {
        this.codPost = codPost;
        return this;
    }

    public void setCodPost(String codPost) {
        this.codPost = codPost;
    }

    public String getMail() {
        return mail;
    }

    public Client mail(String mail) {
        this.mail = mail;
        return this;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPays() {
        return pays;
    }

    public Client pays(String pays) {
        this.pays = pays;
        return this;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getDepRes() {
        return depRes;
    }

    public Client depRes(String depRes) {
        this.depRes = depRes;
        return this;
    }

    public void setDepRes(String depRes) {
        this.depRes = depRes;
    }

    public String getTel() {
        return tel;
    }

    public Client tel(String tel) {
        this.tel = tel;
        return this;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTelProf() {
        return telProf;
    }

    public Client telProf(String telProf) {
        this.telProf = telProf;
        return this;
    }

    public void setTelProf(String telProf) {
        this.telProf = telProf;
    }

    public String getTeleFax() {
        return teleFax;
    }

    public Client teleFax(String teleFax) {
        this.teleFax = teleFax;
        return this;
    }

    public void setTeleFax(String teleFax) {
        this.teleFax = teleFax;
    }

    public String getAgence() {
        return agence;
    }

    public Client agence(String agence) {
        this.agence = agence;
        return this;
    }

    public void setAgence(String agence) {
        this.agence = agence;
    }

    public String getNcg() {
        return ncg;
    }

    public Client ncg(String ncg) {
        this.ncg = ncg;
        return this;
    }

    public void setNcg(String ncg) {
        this.ncg = ncg;
    }

    public String getDevise() {
        return devise;
    }

    public Client devise(String devise) {
        this.devise = devise;
        return this;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }

    public String getCodeExploitant() {
        return codeExploitant;
    }

    public Client codeExploitant(String codeExploitant) {
        this.codeExploitant = codeExploitant;
        return this;
    }

    public void setCodeExploitant(String codeExploitant) {
        this.codeExploitant = codeExploitant;
    }

    public LocalDate getDatCre() {
        return datCre;
    }

    public Client datCre(LocalDate datCre) {
        this.datCre = datCre;
        return this;
    }

    public void setDatCre(LocalDate datCre) {
        this.datCre = datCre;
    }

    public String getPaysCpt() {
        return paysCpt;
    }

    public Client paysCpt(String paysCpt) {
        this.paysCpt = paysCpt;
        return this;
    }

    public void setPaysCpt(String paysCpt) {
        this.paysCpt = paysCpt;
    }

    public String getIdClient() {
        return idClient;
    }

    public Client idClient(String idClient) {
        this.idClient = idClient;
        return this;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public String getIndicatifTel() {
        return this.indicatifTel;
    }

    public void setIndicatifTel(String indicatifTel) {
        this.indicatifTel = indicatifTel;
    } 

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return id != null && id.equals(((Client) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", codCivil='" + getCodCivil() + "'" +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", nomMari='" + getNomMari() + "'" +
            ", prenomMari='" + getPrenomMari() + "'" +
            ", nomMere='" + getNomMere() + "'" +
            ", prenomMere='" + getPrenomMere() + "'" +
            ", nomPere='" + getNomPere() + "'" +
            ", prenomPere='" + getPrenomPere() + "'" +
            ", dateNaissance='" + getDateNaissance() + "'" +
            ", depNaissance='" + getDepNaissance() + "'" +
            ", commNaiss='" + getCommNaiss() + "'" +
            ", paysNaiss='" + getPaysNaiss() + "'" +
            ", nation='" + getNation() + "'" +
            ", sexe='" + getSexe() + "'" +
            ", situ='" + getSitu() + "'" +
            ", prof='" + getProf() + "'" +
            ", langue='" + getLangue() + "'" +
            ", relation='" + getRelation() + "'" +
            ", identite='" + getIdentite() + "'" +
            ", numId='" + getNumId() + "'" +
            ", datLivr='" + getDatLivr() + "'" +
            ", datValid='" + getDatValid() + "'" +
            ", payId='" + getPayId() + "'" +
            ", commId='" + getCommId() + "'" +
            ", addr1='" + getAddr1() + "'" +
            ", addr2='" + getAddr2() + "'" +
            ", addr3='" + getAddr3() + "'" +
            ", addr4='" + getAddr4() + "'" +
            ", codCour='" + getCodCour() + "'" +
            ", codPost='" + getCodPost() + "'" +
            ", mail='" + getMail() + "'" +
            ", pays='" + getPays() + "'" +
            ", depRes='" + getDepRes() + "'" +
            ", tel='" + getTel() + "'" +
            ", telProf='" + getTelProf() + "'" +
            ", teleFax='" + getTeleFax() + "'" +
            ", agence='" + getAgence() + "'" +
            ", ncg='" + getNcg() + "'" +
            ", devise='" + getDevise() + "'" +
            ", codeExploitant='" + getCodeExploitant() + "'" +
            ", datCre='" + getDatCre() + "'" +
            ", paysCpt='" + getPaysCpt() + "'" +
            ", idClient='" + getIdClient() + "'" +
            ", indicatifTel='" + getIndicatifTel() + "'" +
            "}";
    }
}
