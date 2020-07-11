/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Banco 
{
    
    final private String usuario = "root"; // = seu usuário
    final private String senha = "root123"; // = sua senha
    public String banco;
    private Connection conexao;
    
    //método construtor
    public Banco(String pbd) 
    {
        banco = pbd;
    }
    

    //string de conexão
    private String montaString()
    {
        //:mysql:
        //:postgresql        
        return "jdbc:derby://localhost:1527/" + banco;
    }
        
    //método de conexão
    
    public Connection conecta()
    {
        try 
        {
            //postgresql
            //com.mysql.jdbc.Driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            conexao = DriverManager.getConnection(montaString(), usuario, senha);

            System.out.println("Conexao estabelecida com sucesso.");
            
            return conexao;
        }
        catch (ClassNotFoundException | SQLException e) 
        {
           System.out.println("Erro ao conectar\n" + e.getMessage());
      
           return null;
        }
    }
        
    //método de desconexão
    
    public void desconecta(Connection pcon)
    {
        try
        {
            pcon.close();
        }
        catch(Exception e)
        {
           System.out.println("Erro ao desconectar\n" + e.getMessage());     
        }
    }
    
    
    
    //método de consulta 
    
    public ResultSet consulta(String ptbl)
    {
        try 
        {
            ResultSet rs;                           //equivalente ao DataTable

            //Comando DML
            String sql = "SELECT * FROM BANCO." + ptbl;

            Connection con = conecta();

            Statement st = (Statement) con.createStatement();   //equivalente ao Command 

            rs = st.executeQuery(sql);               //equivalente ao Fill()

            return rs;
        } 
        catch (Exception ex) 
        {
            JOptionPane.showMessageDialog(null, ex.getMessage());

            return null;
        } 
        finally 
        {
            Connection con = conecta();

            desconecta(con);
        }
    }
}
