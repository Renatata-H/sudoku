/* Este projeto busca resolver um sudoku dado apenas
 * uma matriz 9x9.
 */

public class sudoku {

    public static int[][] Le_tabuleiro() {
        /*  A partir da entrada padrão, esse método tenta ler os números que formam um
         *  tabuleiro. 
         * 
         *  Ele preenche fileira por fileira, isto é, os primeiros 9 números digitados 
         *  correspondem à primeira fileira, os 9 seguintes à segunda fileira, e assim vai. 
         * 
         *  Foi pensado para ser executado com o redirecionmento da entrada padrão "<".
         */

        int[][] jogo = new int[9][9];
        int i = 0, j;

        while (i < 9)       {
            j = 0;
            while (j < 9)   {
                jogo[i][j] = Integer.parseInt(StdIn.readString());
                j = j + 1;  }
            i = i + 1;      }

        return jogo;
    }

    public static void Imprime_tabuleiro(int[][] tabuleiro) {
        /*  Dado um matriz 9x9, esse método imprime-a, considerando também
         *  as divisões "em blocos" 3x3, típicas do jogo de sudoku.
         */
        
        int i = 0, j;

        while (i < 9)                   {
                j = 0;
                while (j < 9)   {
                    StdOut.print(tabuleiro[i][j]+" ");
                    if (j%3 == 2)           {
                        StdOut.print(" ");  }
                    j = j + 1;  }
                if (i%3 == 2)           {
                    StdOut.println();   }
                StdOut.println();
                i = i + 1;              }

        return;
    }

    public static int[] Busca(int[][] jogo, boolean[] status, int area, int indice, int elemento) {
        /*  Considere a seguinte numeração de blocos 3x3:   0 1 2
         *                                                  3 4 5
         *                                                  6 7 8
         *  Este método busca verificar se, dados um determinado tipo de área (fileiras, colunas 
         *  ou blocos, respectivamente representados por 0, 1 e 2) e um índice para representar
         *  uma dessas áreas, um determinado elemento existe, no jogo de sudoku.
         * 
         *  Caso exista, altera a variável booleana "status" para TRUE e devolve a posição do dito
         *  elemento. Caso não exista, "status" permanece FALSE e devolve a posição [0,0].
         */
    

        int[] local = new int[2];
        int i = 0, j = 0;
    
        if (area == 0) {
            // Busca elemento pela fileira "indice".

            while (i < 9)  {
                if (jogo[indice][i] == elemento) {
                    status[0] = true;
                    local[0] = indice;
                    local[1] = i;
                    return local;                }
                i = i + 1; } 
        } 

        else if (area == 1) {
            // Busca elemento pela coluna "indice".

            while (i < 9) {
                if (jogo[i][indice] == elemento) {
                    status[0] = true;
                    local[0] = i;
                    local[1] = indice;
                    return local;                }
                i = i + 1; }
        } 

        else {
            // Busca elemento pelo bloco "indice", conforme numeração apresentada.
            i = (indice/3)*3;
            int I = i+3, J;

            while (i < I) {
                j = (indice%3)*3;
                J = j+3;

                while (j < J) {
                    if (jogo[i][j] == elemento) {
                    status[0] = true;
                    local[0] = i;
                    local[1] = j;
                    return local;               }
                j = j + 1; }

            i = i + 1; }
        }

        return local;
    }

    public static void main(String args[]) {

        int[][] jogo = Le_tabuleiro();
        StdOut.println("JOGO INICIAL: ");     
        Imprime_tabuleiro(jogo);
        
        boolean[][][] estado = new boolean[9][9][9];
        boolean[] status = new boolean[1];
        int[] tampao = new int[2];
        int i = 0, j, k;

        /*  A seguir, um laço que cria "rascunhos" de possibilidades. Ele cria uma matriz 9x9x9
         *  em que, para cada posição ixj do tabuleiro, está associado uma lista de valores booleanos
         *  que representa as "possibilidades" de números para aquela posição. 
         * 
         *  Quando um determinado número k é válido na posição ixj, a posição k-1 na lista da
         *  posição ixj é "true". Caso contrário, é "false".
         * 
         *  Note que as todos os elementos das listas associadas à posições ixj inicialmente preenchidas
         *  tem valor "false".
         */
        while (i < 9) {
            j = 0;
            while (j < 9) {
                if (jogo[i][j] == 0) {
                    k = 0;
                    while (k < 9) {
                        tampao = Busca(jogo, status, 0, i, k+1);
                        if (status[0] == false) {
                            tampao = Busca(jogo, status, 1, j, k+1); 
                            if (status[0] == false) {
                                tampao = Busca(jogo, status, 2, (3*(i/3))+(j/3), k+1);
                                if (status[0] == false)
                                    estado[i][j][k] = true; }}
                        status[0] = false;
                        k = k + 1;
                    } }
                j = j + 1;}
            i = i + 1;}


        // Depuração:
        i = 0;
        StdOut.println("O número é "+ jogo[8][6]);
        while (i < 9) {
            StdOut.print(estado[8][6][i]+" ");
            i = i + 1;
        } StdOut.println();

        //Comentario aleatorio
        if (false) {
            // Área de depuração
            jogo[7][8] = 3;
            //boolean[] status = new boolean[1];

            int[] local = Busca(jogo, status, 2, 8, 3);

            StdOut.println(local[0]+" "+local[1]);
            StdOut.println(status[0]);
            
        }
    }
}