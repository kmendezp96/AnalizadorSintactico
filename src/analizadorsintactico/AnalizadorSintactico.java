//package analizador;
package analizadorlexico;
/*
    Kevin Mendez Paez 1013665722
    Diego Alejandro Guevera Rocha 97091722406 
 */

import java.io.*;
import java.math.BigInteger;
import static java.time.Clock.system;
import java.util.*;
import java.util.Scanner;

public class AnalizadorLexico {
    
    public static void main(String[] args) {
        LinkedList<String> PalabrasReservadas=new LinkedList<String>();
        Map<String, String> tokens = new HashMap<String, String>();
        
        PalabrasReservadas.add("dim");
        PalabrasReservadas.add("as");
        PalabrasReservadas.add("string");
        PalabrasReservadas.add("integer");
        PalabrasReservadas.add("long");
        PalabrasReservadas.add("single");
        PalabrasReservadas.add("double");
        PalabrasReservadas.add("print");
        PalabrasReservadas.add("input");
        PalabrasReservadas.add("if");
        PalabrasReservadas.add("else");
        PalabrasReservadas.add("then");
        PalabrasReservadas.add("elseif");
        PalabrasReservadas.add("end");
        PalabrasReservadas.add("select");
        PalabrasReservadas.add("case");
        PalabrasReservadas.add("while");
        PalabrasReservadas.add("wend");
        PalabrasReservadas.add("do");
        PalabrasReservadas.add("loop");
        PalabrasReservadas.add("for");
        PalabrasReservadas.add("to");
        PalabrasReservadas.add("next");
        PalabrasReservadas.add("step");
        PalabrasReservadas.add("sub");
        PalabrasReservadas.add("shared");
        PalabrasReservadas.add("function");
        PalabrasReservadas.add("const");
        PalabrasReservadas.add("until");
        
        tokens.put("not","token_neg");
        tokens.put("=","token_igual");
        tokens.put("<>","token_dif");
        tokens.put("<","token_menor");
        tokens.put(">","token_mayor");
        tokens.put("<=","token_menor_igual");
        tokens.put(">=","token_mayor_igual");
        tokens.put("+","token_mas");
        tokens.put("-","token_menos");
        tokens.put("/","token_div");
        tokens.put("*","token_mul");
        tokens.put("mod","token_mod");
        tokens.put("(","token_par_izq");
        tokens.put(")","token_par_der");
        tokens.put("or","token_o");
        tokens.put("and","token_y");
        tokens.put("xor","token_xor");
        tokens.put(";","token_pyc");
        tokens.put(",","token_coma");
        tokens.put("^","token_pot");
        tokens.put("%","token_porcentaje");
        tokens.put("&","token_ampersand");
        tokens.put("!","token_admiracion");
        tokens.put("#","token_numeral");
        tokens.put("$","token_pesos");
        
        Scanner tecl = new Scanner (System.in);
        String comentario="'";
        int ContadorLinea=0;
        boolean cond=false;
        while((tecl.hasNext())){
            ContadorLinea++;
            String lineaO = tecl.nextLine();
            String linea=lineaO.toLowerCase();
            cond=analizar(linea,lineaO ,PalabrasReservadas,tokens,ContadorLinea);
            //System.out.println(cond);
            if (cond){
                break;
            }
        }

    }
    
    public static boolean analizar(String linea, String lineaO ,LinkedList<String> PalabrasReservadas, Map<String, String> tokens, int ContadorLinea){
        boolean condSald=false;
        byte tipo=0;
        int inicio=0;
        for (int i=0;i<linea.length();i++){
            if( linea.charAt(i)=='\''){
                //la linea es un comentario, se ignora
                break;
            }
            if (linea.charAt(i)=='"'){

                inicio=i+1;
                String palabra="";
                i++;
                while ((i<linea.length()) &&(lineaO.charAt(i)!='"') ){
         
                    palabra=palabra+lineaO.charAt(i);
                    i++;
                }
                if ((i<lineaO.length())&&(lineaO.charAt(i)=='"')){
         
                    System.out.println("<token_string,"+palabra+","+ContadorLinea+","+inicio+">");
                }
                else{
      
                    System.out.println(">>> Error lexico (linea: "+ContadorLinea+", posicion: "+inicio+")");
                    condSald=true;
                    break;
                    
                }
            }

            else if (linea.charAt(i)>47 & linea.charAt(i)<58){

                inicio=i+1;
                String numero="";
                byte contPunto=0;
                int posPunt=0;

                while ((i<linea.length()) &&(((linea.charAt(i)>47 & linea.charAt(i)<58)|(linea.charAt(i)=='.'))&(contPunto<=1))){
                    numero=numero+linea.charAt(i);
                    if(linea.charAt(i)=='.'){
                        //marca el tipo como numero decimal, guarda posicion del punto
                        posPunt=numero.length()-1;
                        tipo=1;
                    }
                    else if(tipo!=1) {
 
                        tipo=2;
                    }
                    i++;
                }
                
                if(tipo==1){
                    if((numero.length()>posPunt+1)&&(numero.charAt(posPunt+1)>47 & numero.charAt(posPunt+1)<58)){
                        if (numero.substring(posPunt+1).length()<=6){

                            System.out.println("<token_single,"+numero+","+ContadorLinea+","+inicio+">");
                        }else {
                            //es un double
                            System.out.println("<token_double,"+numero+","+ContadorLinea+","+inicio+">");
                        }
                    }
                    else{
                        System.out.println("<token_integer,"+numero.substring(0,numero.length()-1)+","+ContadorLinea+","+inicio+">");
                        System.out.println(">>> Error lexico (linea: "+ContadorLinea+", posicion: "+(inicio+numero.substring(0,numero.length()-1).length())+")");
                        condSald=true;
                        break;
                    }
                    
                }else {
                    //paso numero de string a int para compararlo 
                    int num=Integer.valueOf(numero);
                    if (num<=32767 & num>=-32767){
                        //es un integer, esta dentro de este rango
                        System.out.println("<token_integer,"+numero+","+ContadorLinea+","+inicio+">");
                    } else{
                        //es un long
                        System.out.println("<token_long,"+numero+","+ContadorLinea+","+inicio+">");
                    }
                }

                tipo=0;
                i--;
            }
            else if (linea.charAt(i)>96 & linea.charAt(i)<123){

                inicio=i+1;
                String palabra="";

                while ((i<linea.length()) &&((linea.charAt(i)>96 & linea.charAt(i)<123)|(linea.charAt(i)=='_')|(linea.charAt(i)>47 & linea.charAt(i)<58))){
                    palabra=palabra+linea.charAt(i);

                    if((PalabrasReservadas.contains(palabra)) && (((inicio-2>=0)&&(linea.charAt(inicio-2)==' '))|(inicio==1)) && (((i+1<linea.length())&&(linea.charAt(i+1)==' '))|(i==linea.length()-1)) ){

                        System.out.println("<"+palabra+","+ContadorLinea+","+inicio+">");
                    }

                    else if((tokens.containsKey(palabra)) && (((i+1<linea.length())&&((linea.charAt(i+1)>=123 | linea.charAt(i+1)<=96 )&(linea.charAt(i+1)>=58 | linea.charAt(i+1)<=47 )))|(i==linea.length()-1)) ){

                        System.out.println("<"+tokens.get(palabra)+","+ContadorLinea+","+inicio+">");
                    }
                    
                    else if(((i+1<linea.length())&&((linea.charAt(i+1)==' ')|(((linea.charAt(i+1)<=96 | linea.charAt(i+1)>=123)&(linea.charAt(i+1)<=47 | linea.charAt(i+1)>=58))&(linea.charAt(i+1)!='_'))))|(i+1==linea.length())) {

                        System.out.println("<id,"+palabra+","+ContadorLinea+","+inicio+">");
                    }
                    i++;
                }
                i--;
            }

            else if(((linea.charAt(i)>32 & linea.charAt(i)<48)&&(linea.charAt(i)!=34 && linea.charAt(i)!=39 && linea.charAt(i)!=46))|(linea.charAt(i)>58 & linea.charAt(i)<63)|(linea.charAt(i)==94)){
                inicio=i+1;
                String palabra="";

                if (linea.charAt(i)=='<'){

                    if ((i+1<linea.length())&&(linea.charAt(i+1)=='=')){
        
                        System.out.println("<token_menor_igual"+","+ContadorLinea+","+inicio+">");
                        i++;
                    }
                    else if ((i+1<linea.length())&&(linea.charAt(i+1)=='>')){
                        System.out.println("<token_dif"+","+ContadorLinea+","+inicio+">");
                        i++;
                    }
                    else{

                        palabra=palabra+linea.charAt(i);
                        System.out.println("<"+tokens.get(palabra)+","+ContadorLinea+","+inicio+">");
                    }
                }
                else if (linea.charAt(i)=='>'){
                    if ((i+1<linea.length())&&(linea.charAt(i+1)=='=')){
                        System.out.println("<token_mayor_igual"+","+ContadorLinea+","+inicio+">");
                        i++;
                    }
                    else{
                        palabra=palabra+linea.charAt(i);
                        System.out.println("<"+tokens.get(palabra)+","+ContadorLinea+","+inicio+">");
                    }
                }
                else{
   
                    palabra=palabra+linea.charAt(i);
                    System.out.println("<"+tokens.get(palabra)+","+ContadorLinea+","+inicio+">");
                }
            }
            else if (linea.charAt(i)!=' '){
       
                System.out.println(">>> Error lexico (linea: "+ContadorLinea+", posicion: "+(i+1)+")");
                condSald=true;
                break;
               
            }

        }
        return condSald;
    } 
    
    
}
