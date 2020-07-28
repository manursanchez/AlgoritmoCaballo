/**
 * Cálculo de los movimientos del caballo de ajedrez en un tablero de NxN.
 * 
 * @author Manuel Rodríguez Sánchez 
 * @version 1.0
 */
public class saltocaballo 
{
    final int numFilas;
    final int numColumnas;
    // A continuación variables que almacenarán los argumentos de entrada:
    // traza: relacionada con -t. todasSoluciones relacionada con -a."error" controla datos
    // correctos o no en la entrada de argumentos. Si "error" está a false se ejecutara el algoritmo por que las entradas de
    // parámetros han sido correctas. Si "error" se pone a true, algún parámetro es erróneo y no se ejecutará el algoritmo
    static int N=8,x=1,y=1; //N --> Tamaño de tablero; x-->Posición en el eje de abcisa; y--> Posición en el eje de coordenadas
    //Variables booleanas para el control de parametros de entrada
    static boolean traza=false,todasSoluciones=false,error=false;
    static boolean solucion;
    static long contadorSoluciones=0;//Variable para contar las soluciones con el parametro -a
    int[][] tablero;
    int     contador;//Cuenta el número de ciclos

    //CONSTRUCTOR DE LA CLASE
    public saltocaballo(int nf, int nc) 
    {
        numFilas = nf;
        numColumnas = nc;
        tablero     = new int[nf][nc];
    }
    public void mostrarTablero() {
        for(int i=tablero.length-1;i>=0;i--)//Empiezo desde la fila N. Este es el contador de filas.
        {
            for(int j=0; j<tablero[i].length; j++) //Contador de columnas
            {
                System.out.printf("  %2d  ", tablero[i][j]);
            }
            System.out.println();
        }
    }
    public boolean saltaCaballo(int f, int c, int num) 
    {
            contador++;//Para contar el numero de ciclos
            tablero[f][c] = num;
            if(num==numFilas*numColumnas) return true;// Esto nos servirá para ver si encontramos solucion o no
            int[][] posiciones = buscarPosiciones(f, c, num);
            for(int i=0; i<posiciones.length; i++) 
            {
                if(saltaCaballo(posiciones[i][0], posiciones[i][1],num+1))
                {
                    return true;
                }
            }
            tablero[f][c]=0;//Si no ha encontrado coordenadas nuevas, estamos en ultimo nodo de una rama del arbol,
                            // asignamos 0 para cuando volvamos a encontrar este nodo
            return false;
        
    }
    int[][] buscarPosiciones(int f, int c, int numeroPosicion) //COMPLECIONES
    {
       int[][] resp = new int[8][2];//Matriz donde se almacenarán las posiciones encontradas
                                    // desde la posicion (f,c)recibida.
       int     pos  = 0;//Me va a indicar el numero de filas que tendra el array temporal donde
                         // guarda las posiciones encontradas.
       int posF=0,posC=0;// Variables locales necesarias para el cálculo de posiciones 
       for (int a=-2; a!=3; a++)//Empezamos a recorrer desde -2 hasta 2 para la fila
       {
           for (int b=-2; b!=3; b++)//Empezamos a recorrer desde -2 hasta 2 para la columna
           {
               if (absoluto(a)+absoluto(b)==3)//comprobamos que la suma del valor a y el b es igual a 3
               {
                   posF=f+a;//Guardamos en variables locales la suma que nos dará la nueva fila
                   posC=c+b;//Guardamos en variables locales la suma que nos dará la nueva columna
                   if (esValido(posF,posC))//Comprobamos que son posiciones legales
                   {
                       contadorSoluciones++;
                       resp[pos][0]=f+a;//Si la posicion es legal, la guardamos en la matriz
                       resp[pos++][1]=c+b;
                    }
                }
            }
        }
        /*Con resp, hemos creado una matriz de 8x2.Lo normal es que no todas las casillas se rellenen y por eso 
        realizamos el siguiente proceso, que es crear una nueva matriz solo con el numero exacto de filas y 
        columnas creadas con las posiciones encontradas. Esta va a ser la matriz (tmp) que devolvamos.*/
       int[][] tmp = new int[pos][2];//creamos matriz auxiliar y metemos las posiciones legales encontradas
       for(int i=0; i<pos; i++) 
        { 
            tmp[i][0] = resp[i][0];
            tmp[i][1] = resp[i][1]; 
        }
       if(todasSoluciones==true)SolucionMovimientos(tmp);//llamada cuando se ha introducido -a
       return tmp;//devolvemos soluciones o posiciones nuevas
    }
    boolean esValido(int f, int c) //Comprobamos que la posición es válida
    {
       if(f>=0 && f<=numFilas-1 && c>=0 && c<=numColumnas-1 && tablero[f][c]==0) return true;
        return false;
    }
    public static int absoluto(int valor)// Método que calcula el valor absoluto de un número entero
    {
        if (valor<0)
        {
            valor=valor * -1;
        }
        return valor;
    }
    // CON EL PARAMETRO -a
    // Lista todas las soluciones posibles dependiendo de la posición del árbol en la que nos encontremos
    void SolucionMovimientos(int [][] arrayMovimientos)
    {
            System.out.println();
            for(int i=0;i<arrayMovimientos.length;i++)
            {
                System.out.print("("+(arrayMovimientos[i][0]+1)+","+(arrayMovimientos[i][1]+1)+")");//Posicion en el array temporal
            }
            System.out.println();
            if (arrayMovimientos.length==0)System.out.print("Sin solucion, vuelvo atrás");
    }
    //FIN PARAMETRO -a
    
    // METODOS PARA EL TRAZADO CON EL PARAMETRO -t ************************************************************
    //*********************************************************************************************************
    
    public void trazadoCaballo(int z,int w)
    {
        int posicionCaballo=1;//Contador que nos da la posición del caballo desde 1 hasta N
        String [][] trazadoMatriz=new String[N][N];//Matriz donde se va almacenar cada número de movimiento
        //Buscamos posiciones en el array de string y colocamos la posicion del caballo, con las mismas visualizamos todo
        // el array llamando a visualizarTrazado, previamente eliminamos los null de la matriz de string.
        while(posicionCaballo<(N*N)+1)
        {
        for(int i=0;i<tablero.length;i++)
        {
            for(int j=0;j<tablero.length;j++)
            {
                if (tablero[i][j]==posicionCaballo)
                {
                    trazadoMatriz[i][j]=String.valueOf(posicionCaballo);
                    eliminarNull(trazadoMatriz);
                    visualizarTrazado(trazadoMatriz);
                }
            }
        }
        posicionCaballo++;
    }
    }
    //Método para colocar espacios vacíos en la matriz y que no salga la palabra null en el trazado
    public void eliminarNull(String [][] matrizTablero)
    {
        for(int i=0;i<matrizTablero.length;i++)
        {
            for(int j=0;j<matrizTablero.length;j++)
            {
                if (matrizTablero[i][j]==null)
                {
                    matrizTablero[i][j]=" ";
                }
            }
        }
    }
    // Método que nos permite visualizar cada salto del caballo
    public void visualizarTrazado(String [][] matrizTablero)
    {
    
        String [] separadores=new String[N*4];//Se crea una matriz con guiones que van a hacer de separadores.
        //Rellena matriz "sepadores" y muestra separadores de arriba
        for(int nRaya=0;nRaya<separadores.length;nRaya++)
        {
            separadores[nRaya]="-";
            System.out.print(separadores[nRaya]);
        }
        System.out.println();
        //mostramos movimiento en el tablero
        for(int i=tablero.length-1;i>=0;i--)
        {
            for(int j=0; j<tablero[i].length; j++)
            {
                System.out.printf(" %2s ",matrizTablero[i][j]);
                
            }
            System.out.println();
        }
        
        //Escribe separadores de abajo
        for(int nRaya=0;nRaya<separadores.length;nRaya++)
        {
            System.out.print(separadores[nRaya]);
        }
        System.out.println();
    }
             
    // FIN DE LOS METODOS PARA EL TRAZADO CON EL PARAMETRO -t *********************************************************
    //*****************************************************************************************************************
    
    
    //*****************************************************************************************************************
    //****************************************************PRINCIPAL****************************************************
    //*****************************************************************************************************************
    public static void main(String[] args) 
    {
      //VAMOS A COMPROBAR LOS ARGUMENTOS QUE VIENEN
      if (args.length>0) // Si es verdadero, sé que vienen argumentos
      {
          // Compruebo si viene el argumento -n con N
          if((args[0].equals("-n")))
          {
              try{
                  if(Integer.parseInt(args[1])>=5)
                  {
                    N=Integer.parseInt(args[1]); //Le doy el nuevo tamaño del tablero
                  }else {
                       error=true;
                       //System.out.println("Error, tamaño del tablero no válido");
                    }
                }catch(Exception e){
                    //System.out.println("Error, datos no numericos");
                    error=true;}
          }else{if(!args[0].equals("-a") && !args[0].equals("-t")) error=true;}
          // fin de comprobación del argumento -n
          //Comprobacion de -x y -y
          try{
            if(args.length>2 && args[2].equals("-x") && args[4].equals("-y"))
            {
                  if(Integer.parseInt(args[3])>=1 && Integer.parseInt(args[3])<=N)//Comprobamos la nueva x
                  {
                    x=Integer.parseInt(args[3]);
                  }else {
                      //System.out.println("Error de rango en x");
                      error=true;
                    }
                  if(Integer.parseInt(args[5])>=1 && Integer.parseInt(args[5])<=N)//Comprobamos la nueva y
                  {
                    y=Integer.parseInt(args[5]);
                  }else{
                      //System.out.println("Error de rango en y");
                      error=true;
                    }
            } 
            }catch(Exception e){  
                //System.out.println("Error de datos con excepcion");
                error=true;
          }
          //Comprobación que se hayan introducido correctamente los parametros -t y -a al final
          if(error==false)
          {
                if (args.length==7)
                {
                    if(!args[6].equals("-t") && !args[6].equals("-a")) error=true;
                }
          }
          if (error==false)
          {
                if (args.length==8)
                {
                    if(!args[7].equals("-t") && !args[7].equals("-a") || !args[6].equals("-t") && !args[6].equals("-a")) error=true;
                }
          }      
          // Comprobacion de -a y -t. Vamos a recorrer todo el array args, a ver si están en algun lugar, 
          // siempre y cuando no se haya producido un error en las comprobaciones anteriores.
          if (error==false)
          {
            for(int c=0;c<args.length;c++)
            {
                  if(args[c].equals("-t"))traza=true;
                  if(args[c].equals("-a"))todasSoluciones=true;
            }
          }
      }  
       // FIN DE LA COMPROBACION DE LOS ARGUMENTOS QUE NOS LLEGAN
      if(error==false)
      {
           saltocaballo caballo = new saltocaballo(N,N);
           solucion=caballo.saltaCaballo(x-1,y-1,1);
           if (solucion==true)
           {
                if(traza==true)
                {
                    caballo.trazadoCaballo(x-1,y-1);
                }else{
                    caballo.mostrarTablero();
                }
            }
            else
            {
                System.out.println("No hay solucion para los parametros introducidos");
            }
           if (todasSoluciones==true)
           {
               System.out.println("El numero de soluciones encontradas han sido: "+contadorSoluciones);
               System.out.printf("Cantidad de veces que entra al ciclo: %,d %n", caballo.contador);    
            }
        }
    }
}

