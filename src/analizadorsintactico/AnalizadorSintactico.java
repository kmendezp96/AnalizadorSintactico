//package analizador;
/*
    Kevin Mendez Paez 1013665722
    Diego Alejandro Guevera Rocha 97091722406 
 */
package analizadorsintactico;
import java.util.*;
import java.util.Scanner;


public class AnalizadorSintactico {
	static class Token {
		public String token;
        public int fila, columna;
		public Token(String token, int fila, int columna) {
	    	this.token = token;
	    	this.fila = fila;
	    	this.columna = columna;
		}
	}
	
	static String anterior = "";
	static void imprimeArrayToken(Token[] array) {
        for (int i = s1; i < s-1; i++) {
            //System.out.println(array[i].token+" "+pila.size());
        	if (array[i].token.equals("for")){
        		if (!(array[i+1].token.equals("id"))){
        			System.out.println("<"+array[i+1].fila+":"+array[i+1].columna+"> Error sintactico: se encontro: '"+array[i+1].token+"'; se esperaba: 'id'.");       			
        		}else {
                            pila.push("next");
                        }
        	}
                else if (array[i].token.equals("dim")){        		
        		if (!(array[i+1].token.equals("id") || array[i+1].token.equals("shared"))){
        			System.out.println("<"+array[i+1].fila+":"+array[i+1].columna+"> Error sintactico: se encontro: '"+array[i+1].token+"'; se esperaba: 'id' o 'shared'.");        		
        		}
        	}
                else if (array[i].token.equals("print")){        		
        		if (!(array[i+1].token.equals("token_string") || array[i+1].token.equals("id"))){
        			System.out.println("<"+array[i+1].fila+":"+array[i+1].columna+"> Error sintactico: se encontro: '"+array[i+1].token+"'; se esperaba: 'id' o 'token_string'.");        			
        		}
        	}
                else if (array[i].token.equals("input")){        		
        		if (!(array[i+1].token.equals("id"))){
        			System.out.println("<"+array[i+1].fila+":"+array[i+1].columna+"> Error sintactico: se encontro: '"+array[i+1].token+"'; se esperaba: 'id'.");
        		}
        	}
        	
                else if (array[i].token.equals("end")){
                        if (pila.isEmpty()){
                            System.out.println("<"+array[i].fila+":"+array[i].columna+"> Error sintactico: se encontro: '"+array[i].token+"' sin una instruccion de apertura");
                        }
                        else if (array[i+1].token.equals("select")){
        			if(pila.peek().equals("end select")){
        				pila.pop();
                                         //System.out.println(array[i].fila+":"+array[i].columna+" "+pila.peek());
					i+=1;
        			}
                                else{
                                    System.out.println("<"+array[i].fila+":"+array[i].columna+"> Error sintactico: se encontro: '"+array[i].token+"' sin una instruccion de apertura");
                                }
        		}
                        else if(array[i+1].token.equals("if")){
        			if(pila.peek().equals("end if")){
        				pila.pop();
					i+=1;
        			}
                                else{
                                    //ends sin aperturas
                                    System.out.println("<"+array[i].fila+":"+array[i].columna+"> Error sintactico: se encontro: '"+array[i].token+"' sin una instruccion de apertura");
                                }
        		}
                        else if(array[i+1].token.equals("sub")){
        			if(pila.peek().equals("end sub")){
        				pila.pop();
					i+=1;
        			}
                                else{
                                    System.out.println("<"+array[i].fila+":"+array[i].columna+"> Error sintactico: se encontro: '"+array[i].token+"' sin una instruccion de apertura");
                                }
        		}
                        else if(array[i+1].token.equals("function")){
        			if(pila.peek().equals("end function")){
        				pila.pop();
					i+=1;
        			}
                                else{
                                    System.out.println("<"+array[i].fila+":"+array[i].columna+"> Error sintactico: se encontro: '"+array[i].token+"' sin una instruccion de apertura");
                                }
        		}
                        else {        			
                            System.out.println("<"+array[i].fila+":"+array[i].columna+"> Error sintactico: se encontro: '"+array[i].token+"' sin una instruccion de apertura");                                
        		}
        	}
                else if(array[i].token.equals("select") && !(anterior.equals("end"))){
        		if(array[i+1].token.equals("case")){
                            i++;
        			if(array[i+1].token.equals("id")){
                                    i++;
        				if(array[i+1].token.equals("token_pesos")){
						i++;
                                                //System.out.println(array[i-1].token+" "+array[i].token);
        					pila.push("end select");
                                                break;
        				}
        				else
        					System.out.println("<"+array[i+1].fila+":"+array[i+1].columna+"> Error sintactico: se encontro: '"+array[i+1].token+"'; se esperaba: 'token_pesos'.");
        			}
        			else
        				System.out.println("<"+array[i+1].fila+":"+array[i+1].columna+"> Error sintactico: se encontro: '"+array[i+1].token+"'; se esperaba: 'id'.");	
        		}
        		else
        			System.out.println("<"+array[i+1].fila+":"+array[i+1].columna+"> Error sintactico: se encontro: '"+array[i+1].token+"'; se esperaba: 'case'.");	
        	}

                
                else if (array[i].token.equals("case")){        		
        		if (!(array[i+1].token.equals("token_string"))){
        			System.out.println("<"+array[i+1].fila+":"+array[i+1].columna+"> Error sintactico: se encontro: '"+array[i+1].token+"'; se esperaba: 'id'.");
        		}
                        else{
                            if (!(pila.peek().equals("end select"))){
                                //no se como se imprime este error no esta dentro de un select case
                                System.out.println("<"+array[i+1].fila+":"+array[i+1].columna+"> Error sintactico: se encontro: '"+array[i+1].token+"'; No hay expresion de apertura'.");
                            }
                        }
        	}
                else if (array[i].token.equals("while")){        		
        		if (!(array[i+1].token.equals("id"))){
        			System.out.println("<"+array[i+1].fila+":"+array[i+1].columna+"> Error sintactico: se encontro: '"+array[i+1].token+"'; se esperaba: 'id'.");
        			pila.push("wend");
        		}
        	}
                else if (array[i].token.equals("wend")){  
                        if (pila.isEmpty()){
                            System.out.println("<"+array[i].fila+":"+array[i].columna+"> Error sintactico: se encontro: '"+array[i].token+"' sin una instruccion de apertura");
                        }
                        else if(pila.peek().equals("wend")){
                            pila.pop();
        		}
                        else{
                             System.out.println("<"+array[i].fila+":"+array[i].columna+"> Error sintactico: se encontro: '"+array[i].token+"' sin una instruccion de apertura");                         
                        }
                }
                else if (array[i].token.equals("loop")){        		
        		if (!(array[i+1].token.equals("until") || array[i+1].token.equals("while"))){
        			System.out.println("<"+array[i+1].fila+":"+array[i+1].columna+"> Error sintactico: se encontro: '"+array[i+1].token+"'; se esperaba: 'while' o 'until'.");
        		}
        	}
                else if (array[i].token.equals("sub")){        		
        		if (!(array[i+1].token.equals("id"))){
        			System.out.println("<"+array[i+1].fila+":"+array[i+1].columna+"> Error sintactico: se encontro: '"+array[i+1].token+"'; se esperaba: 'id'.");
        		}
        	}
                else if (array[i].token.equals("function")){        		
        		if (!(array[i+1].token.equals("id"))){
        			System.out.println("<"+array[i+1].fila+":"+array[i+1].columna+"> Error sintactico: se encontro: '"+array[i+1].token+"'; se esperaba: 'id'.");
        		}
        	}
            

            //ids, operaciones asignaciones    
                else if (array[i].token.equals("id")){
                        //declaracion                    
        		if (array[i].columna==1){
                            if (array[i+1].token.equals("token_igual")){
                                
                            }
        		}
        	}
        	anterior = array[i].token;
        }
        
    }
	
	static Token[] arraytokens= new Token[1000];
	
	static Stack<String> pila = new Stack<String>();
	
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
        
        
        
        @SuppressWarnings("resource")
		Scanner tecl = new Scanner (System.in);
        int ContadorLinea=0;
        
        while((tecl.hasNext())){
            
            ContadorLinea++;
            String lineaO = tecl.nextLine();
            String linea=lineaO.toLowerCase();            
            analizar(linea,lineaO ,PalabrasReservadas,tokens,ContadorLinea);
            imprimeArrayToken(arraytokens);
            /*
            for (int i=0;i< arraytokens.length;i++){
                if(arraytokens[i]!=null){
                    arraytokens[i]=null;
                }
          
            }*/
        }
        
        
    }
   static int s1=0;
    static int s=0;
    
    public static void analizar(String linea, String lineaO ,LinkedList<String> PalabrasReservadas, Map<String, String> tokens, int ContadorLinea){
        byte tipo=0;
        s1=s;
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
                	arraytokens[s] = new Token("token_string", ContadorLinea, inicio);
                	s+=1;
                }
                else{
                    System.out.println(">>> Error lexico (linea: "+ContadorLinea+", posicion: "+inicio+")");
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
                        	arraytokens[s] = new Token("token_single", ContadorLinea, inicio);
                        	s+=1;
                        }else {
                        	arraytokens[s] = new Token("token_double", ContadorLinea, inicio);
                            s+=1;
                        }
                    }
                    else{
                        arraytokens[s] = new Token("token_integer", ContadorLinea, inicio);
                        s+=1;
                        System.out.println(">>> Error lexico (linea: "+ContadorLinea+", posicion: "+(inicio+numero.substring(0,numero.length()-1).length())+")");
                        break;
                    }
                    
                }else {
                    //paso numero de string a int para compararlo 
                    int num=Integer.valueOf(numero);
                    if (num<=32767 & num>=-32767){
                        //es un integer, esta dentro de este rango
                    	arraytokens[s] = new Token("token_integer", ContadorLinea, inicio);
                    	s+=1;
                    } else{
                        //es un long
                    	arraytokens[s] = new Token("token_long", ContadorLinea, inicio);
                    	s+=1;
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
                    	arraytokens[s] = new Token(palabra, ContadorLinea, inicio);
                    	s+=1;
                    }

                    else if((tokens.containsKey(palabra)) && (((i+1<linea.length())&&((linea.charAt(i+1)>=123 | linea.charAt(i+1)<=96 )&(linea.charAt(i+1)>=58 | linea.charAt(i+1)<=47 )))|(i==linea.length()-1)) ){
                    	arraytokens[s] = new Token(tokens.get(palabra), ContadorLinea, inicio);
                    	s+=1;
                    }
                    
                    else if(((i+1<linea.length())&&((linea.charAt(i+1)==' ')|(((linea.charAt(i+1)<=96 | linea.charAt(i+1)>=123)&(linea.charAt(i+1)<=47 | linea.charAt(i+1)>=58))&(linea.charAt(i+1)!='_'))))|(i+1==linea.length())) {
                    	arraytokens[s] = new Token("id", ContadorLinea, inicio);             
                    	s+=1;
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
                    	arraytokens[s] = new Token("token_menor_igual", ContadorLinea, inicio);
                    	s+=1;
                        i++;
                    }
                    else if ((i+1<linea.length())&&(linea.charAt(i+1)=='>')){
                    	arraytokens[s] = new Token("token_dif", ContadorLinea, inicio);
                    	s+=1;
                        i++;
                    }
                    else{
                        palabra=palabra+linea.charAt(i);
                        arraytokens[s] = new Token(tokens.get(palabra), ContadorLinea, inicio);
                    	s+=1;
                    }
                }
                else if (linea.charAt(i)=='>'){
                    if ((i+1<linea.length())&&(linea.charAt(i+1)=='=')){
                    	arraytokens[s] = new Token("token_mayor_igual", ContadorLinea, inicio);
                    	s+=1;
                        i++;
                    }
                    else{
                        palabra=palabra+linea.charAt(i);
                        arraytokens[s] = new Token(tokens.get(palabra), ContadorLinea, inicio);
                    	s+=1;
                    }
                }
                else{                	
                    palabra=palabra+linea.charAt(i);
                    arraytokens[s] = new Token(tokens.get(palabra), ContadorLinea, inicio);
                	s+=1;
                }
            }
            else if (linea.charAt(i)!=' '){
                System.out.println(">>> Error lexico (linea: "+ContadorLinea+", posicion: "+(i+1)+")");
                break;
               
            }

        }
    }     
}
