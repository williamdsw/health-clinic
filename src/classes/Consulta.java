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
 * @author aluno
 */
public class Consulta 
{
    String nomePac, cpfPac, nomeMed, cpfMed, espec, data, hora, retorno;
    
    public Consulta(String pnomepac, String pcpfpac, String pnomemed, String pcpfmed, String pespec,
                    String pdata, String phora, String pretorno)
    {
        nomePac = pnomepac;
        cpfPac = pcpfpac;
        nomeMed = pnomemed;
        cpfMed = pcpfmed;
        espec = pespec;
        data = pdata;
        hora = phora;
        retorno = pretorno;                    
    }
    
    Banco bd = new Banco("BD_HC");
    
    public String insere(String pnomepac, String pcpfpac, String pnomemed,
            String pcpfmed, String pespec,
            String pdata, String phora, String pretorno)
    {
        try
        {
            String sql = "INSERT INTO BD_HC.CONSULTA (NOME_PACIENTE, CPF_PACIENTE, "
                    + "NOME_MEDICO, CPF_MEDICO, ESPECIALIDADE, DATA, HORA, RETORNO)"
                       + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            Connection con = bd.conecta();

            PreparedStatement st = con.prepareStatement(sql);   //equivalente ao Command 

            st.setString(1, pnomepac);                     //equivalente ao Parameters.Add
            st.setString(2, pcpfpac);
            st.setString(3, pnomemed);
            st.setString(4, pcpfmed);
            st.setString(5, pespec);
            st.setString(6, pdata);
            st.setString(7, phora);
            st.setString(8, pretorno);
            
            
            st.executeUpdate();                 //equivalente ao ExecuteNonQuery()

            bd.desconecta(con);

            return "Consulta agendada com sucesso";
        }
        catch (SQLException ex) 
        {
            return "Erro ao agendar consulta \n\n" + ex.getMessage();
        }
    }
    
}
