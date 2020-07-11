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
public class Paciente 
{
    String nome, cpf, cidade, numero, bairro, uf, cep, complemento;
    
    public Paciente(String pnome, String pcpf, String pcid,
                    String pnum, String pbai, String puf,
                    String pcep, String pcom) 
    {
        nome = pnome;
        cpf = pcpf;
        cidade = pcid;
        numero = pnum;
        bairro = pbai;
        uf = puf;
        cep = pcep;
        complemento = pcom;
    }
    
    Banco bd = new Banco("BD_HC");
    
    public String insere(String pnome, String pcpf, String pcid,
                         String pnum, String pbai, String puf,
                         String pcep, String pcom) 
    {
        try 
        {
            String sql = "INSERT INTO BD_HC.PACIENTE (NOME_COMPLETO, CPF, CIDADE, NUMERO, BAIRRO, UF, CEP, COMPLEMENTO)"
                       + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

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

            st.executeUpdate();                 //equivalente ao ExecuteNonQuery()

            bd.desconecta(con);

            return "Cliente cadastrado com sucesso";
            
        } 
        catch (SQLException ex) 
        {
            return "Erro ao cadastrar funcionário \n\n" + ex.getMessage();
        }
    }
}
