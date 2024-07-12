package ControleEstoque;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 *
 * @author robofei
 */
public class Estoque {
    private Componente componente;
    
    public Estoque() {

    }
    
    public void AdicionarComponente(Componente componente, int quantidade, String motivo, String responsavel){
        AdicionaLinhaCSVEstoque(componente.getNome(), componente.getQuantidade());
    }
    
    public int EditarComponente(String componente, int quantidade, String motivo, String responsavel, int Adicionar){
        String tempFile = "temp.csv";
        final String caminho = System.getProperty("user.dir") + "/";
        File arquivo_novo, arquivo_antigo;
        arquivo_antigo = new File(caminho + "/Estoque.csv");
        arquivo_novo = new File(caminho + tempFile);
        
        try
        {
            FileWriter fw = new FileWriter(caminho + tempFile, true);
            PrintWriter escrita_arquivo = new PrintWriter(fw);
            Scanner arquivo_lido = new Scanner(arquivo_antigo);
            arquivo_lido.useDelimiter("[,\n]");
            
            while(arquivo_lido.hasNext()){
                String nome_componente = arquivo_lido.next();
                int quantidade_componente = Integer.parseInt(arquivo_lido.next());
                if(nome_componente.equals(componente))
                {  
                    if(Adicionar == 1)
                    {
                        escrita_arquivo.println(nome_componente+","+(quantidade_componente+quantidade));
                    }
                    else if(Adicionar == 0)
                    {
                        int quantidade_inter = quantidade_componente - quantidade;
                        if(quantidade_inter < 0)
                        {
                            arquivo_lido.close();
                            escrita_arquivo.close();
                            arquivo_novo.delete();
                            return 0;
                        }
                        else
                        {
                            escrita_arquivo.println(nome_componente+","+quantidade_inter);
                        }
                    }
                }
                else
                {
                    escrita_arquivo.println(nome_componente+","+quantidade_componente);
                }
            }
            arquivo_lido.close();
            escrita_arquivo.flush();
            escrita_arquivo.close();
            arquivo_antigo.delete();
            File dump = new File(caminho + "/Estoque.csv");
            arquivo_novo.renameTo(dump);
        }
        catch(IOException e)
        {
            
        }
        return 1;
    }
    
    public void AdicionaLinhaCSVHistorico(int Adicionou, String Componente, int Quantidade, String Responsavel, String Motivo){
        try{
            FileWriter arquivo;
            final String caminho = System.getProperty("user.dir") + "/";
            arquivo = new FileWriter(caminho + "/Historico.csv", true);
//            BufferedWriter bw = new Writer(arquivo); 
            PrintWriter escritaArquivo = new PrintWriter(arquivo);
            String historico, data;
            switch(Adicionou){
                case 1 -> {
                    historico = "Adicionou " + Quantidade + " do componente " + Componente;
                    data = get_date_and_time();
                    escritaArquivo.println(historico+","+Motivo+","+Responsavel+","+data);
                }
                case 0 -> {
                    historico = "Removeu " + Quantidade + " do componente " + Componente;
                    data = get_date_and_time();
                    escritaArquivo.println(historico+","+Motivo+","+Responsavel+","+data);
                }
                case 2 -> {
                    historico = "Adicionou " + Quantidade + " do componente " + Componente + " pela primeira vez.";
                    data = get_date_and_time();
                    escritaArquivo.println(historico+","+Motivo+","+Responsavel+","+data);
                }
                default -> {
                }
            }
            arquivo.close();
        }
        catch(IOException E){}
    }
    
    public void AdicionaLinhaCSVEstoque(String Componente, int Quantidade){
        try{
            FileWriter arquivo;
            final String caminho = System.getProperty("user.dir") + "/";
            arquivo = new FileWriter(caminho + "/Estoque.csv", true);
//            BufferedWriter bw = new Writer(arquivo); 
            PrintWriter escritaArquivo = new PrintWriter(arquivo);;

            escritaArquivo.println(Componente+","+Quantidade);
            arquivo.close();
        }
        catch(IOException E){}
    }
    
    public String get_date_and_time(){
        Calendar cal = new GregorianCalendar();
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR);
        int second = cal.get(Calendar.SECOND);
        int minute = cal.get(Calendar.MINUTE);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        
        String date_and_time = day+"/"+(month+1)+"/"+year+" "+hour+":"+minute+":"+second;
        return date_and_time;
    }
    
}
