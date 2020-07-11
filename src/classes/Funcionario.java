/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author rezno
 */
public class Funcionario 
{
    String nome, cpf, cidade, numero, bairro, uf, cep, complemento, senha, cargo, crm;
    
    public Funcionario(String pnome, String pcpf, String pcid,
                    String pnum, String pbai, String puf,
                    String pcep, String pcom, String psen, String pcar, String cr) 
    {
        nome = pnome;
        cpf = pcpf;
        cidade = pcid;
        numero = pnum;
        bairro = pbai;
        uf = puf;
        cep = pcep;
        complemento = pcom;
        senha = psen;
        cargo = pcar;
        crm = cr;
    }
    
    Banco bd = new Banco("BD_HC");
    
    public String insere(String pnome, String pcpf, String pcid,
                         String pnum, String pbai, String puf,
                         String pcep, String pcom, String psen, String pcar, String cr) 
    {
        try 
        {
            String sql = "INSERT INTO BD_HC.FUNCIONARIO (NOME_COMPLETO, CPF, CIDADE, NUMERO, BAIRRO, UF, CEP, COMPLEMENTO, SENHA, CARGO, CRM)"
                       + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            Connection con = bd.conecta();

            PreparedStatement st = con.prepareStatement(sql);   //equivalente ao Command 

            st.setString(1, pnome);                     //equivalente ao Parameters.Add
            st.setString(2, pcpf);
            st.setString(3, pcid);
            st.setString(4, pnum);
            st.setString(5, pbai);
            st.setString(6, puf);
            st.setString(7, pcep);
            st.setString(8, pcom);
            st.setString(9, psen);
            st.setString(10, pcar);
            st.setString(11, cr);

            st.executeUpdate();                 //equivalente ao ExecuteNonQuery()

            bd.desconecta(con);

            return "Funcionário cadastrado com sucesso";
            
        } 
        catch (SQLException ex) 
        {
            return "Erro ao cadastrar funcionário \n\n" + ex.getMessage();
        }
    }
    
    //sobrecarga de método
    //public Funcionario()
    //{}
        
        
    //método de atualização
    
    /*public String altera(int parametro, int id)
    {
        try {
            //Comando DML
            String sql = "UPDATE BANCO.FUNCIONARIO "
                       + "SET PARAMETRO = ? "
                       + "WHERE ID = " + id;

            Connection con = bd.conecta();

            PreparedStatement st = con.prepareStatement(sql);   //equivalente ao Command 

            //st.setString(1, pnome);                     //equivalente ao Parameters.Add

            st.executeUpdate();                 //equivalente ao ExecuteNonQuery()

            st.close();

            bd.desconecta(con);

            return "Dados atualizados com sucesso";
        } 
        catch (Exception ex) 
        {
            return "Erro ao atualizar dados \n\n" + ex.getMessage();
        }
    }*/   
}
