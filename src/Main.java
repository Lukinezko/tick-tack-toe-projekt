import java.util.Scanner;

public class Main {
    // Premenné
    private static char[][] gameBoard = new char[3][3];

    // Inicializacia hracej plochy kde sme zvolili medzeru ' '
    public static void initializeGameBoard(char[][] gameBoard) {
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                gameBoard[i][j] = ' ';
            }
        }
    }

    // Funkcia na zobrazenie hracej plochy
    public static void showGameBoard(char[][] gameBoard) {
        System.out.println("-------------");
        for (int i = 0; i < gameBoard.length; i++) {
            System.out.print("| ");
            for (int j = 0; j < gameBoard[i].length; j++) {
                System.out.print(gameBoard[i][j] + " | ");
            }
            System.out.println();
            System.out.println("-------------");
        }
    }

    //Funkcia ktorá dá symbol na zvolenú súradnicu ktorá je prepočítaná na indexy
    public static boolean pickSymbol(int userNumber, char symbol) {
        if (userNumber >= 1 && userNumber <= 9) {
            int row = (userNumber - 1) / 3;
            int colum = (userNumber - 1) % 3;
            if (gameBoard[row][colum] == ' ') {
                gameBoard[row][colum] = symbol;
                return true;
            } else {
                System.out.println("Daná pozicia je obsadená zvol druhú pozíciu");
                return false;
            }
        } else {
            System.out.println("Zadaj si číslo mimo rozmedzia 1-9:");
            return false;
        }
    }

    // Funkcia ktorá stanový symbol
    public static char symbolPlayer(boolean currentPlayer) {
        char symbol;
        if (currentPlayer) {
            symbol = 'X';
        } else {
            symbol = 'O';
        }
        return symbol;
    }
    public static boolean nextPlayer(boolean currentPlayer){
        if (currentPlayer){
            return false;
        } else{
            return true;
        }
    }

    // Funkcia ktorá overí všetky kombinácie či hráč nevyhral
    public static boolean checkWinner(char[][] gameBoard, char symbol) {
        for (int i = 0; i < gameBoard.length; i++) {
            // Overenie riadkov a stĺpcov
            if (gameBoard[i][0] == symbol && gameBoard[i][1] == symbol && gameBoard[i][2] == symbol) {
                return true;
            } else if (gameBoard[0][i] == symbol && gameBoard[1][i] == symbol && gameBoard[2][i] == symbol) {
                return true;
            }
            // Overenie diagonál
            if (gameBoard[0][0] == symbol && gameBoard[1][1] == symbol && gameBoard[2][2] == symbol) {
                return true;
            } else if (gameBoard[0][2] == symbol && gameBoard[1][1] == symbol && gameBoard[2][0] == symbol) {
                return true;
            }
        }
        return false;
    }
    // Overenie či neni remíza
    public static boolean checkDraw(char[][] gameBoard){
        for (int i = 0; i <gameBoard.length; i++){
            for (int j = 0; j < gameBoard[i].length; j++){
                if (gameBoard[i][j] == ' '){
                    return false;
                }
            }
        }
        return true;
    }
    // Zadanie pozicie od hráča a overenie či zadal spravny symbol
    public static int enterNumber(Scanner scanner, boolean currentPlayer){
        int userNumber = 0;
        while(true){
            try{
                userNumber = scanner.nextInt();
                break;
            } catch (Exception e){
                if (currentPlayer){
                    System.out.println("Invalid input. Zadaj znova hráč č.1:");
                }else {
                    System.out.println("Invalid input. Zadaj znova hráč č.2:");
                }
                scanner.nextLine();
            }
        }

        return userNumber;

    }

    public static void main(String[] args) {
        // Na začiatku hry inicializujeme hracú plochu vytvárame scanner a nastavime koniec hry na false
        initializeGameBoard(gameBoard);
        Scanner scanner = new Scanner(System.in);
        boolean gameOver = false;
        boolean currentPlayer = true;
        char symbol =symbolPlayer(currentPlayer);

        // Cyklus hry ktorý sa opakuje až kým hra neskončí
        while (!gameOver) {
            // zobrazenie aktualnej hracej plochy
            showGameBoard(gameBoard);
            // Vyzvatie hráča na zadanie súradnice na umiestnenie symbolu daného hráča
            if (currentPlayer){
                System.out.println("Hráč č.1 zadaj pozíciu od 1 po 9: ");
            } else{
                System.out.println("Hráč č.2 zadaj pozíciu od 1 po 9: ");
            }

            int userChoice = enterNumber(scanner, currentPlayer);

            boolean validPick = pickSymbol(userChoice, symbolPlayer(currentPlayer));
            // Ak hráč zadal obsadenú púoziciu alebo mimo 1-9 tak while cyklus začína odznova nepokračuje
            if (!validPick) {
                continue;
            }
            // Overíme či nevyhral hráč ak nevyhral tak zmeníme aj hráča
            gameOver = checkWinner(gameBoard, symbol);
            if (gameOver){
                if (currentPlayer){
                    System.out.println("Hráč č.1 vyhráva gratulujeme. Koniec hry.");
                } else {
                    System.out.println("Hráč č.2 vyhráva gratulujeme. Koniec hry.");
                }
            }
            if (!gameOver && checkDraw(gameBoard)){
                System.out.println("Remiza nikto nevyhrava");
                break;
            }

            currentPlayer = nextPlayer(currentPlayer);
        }


    }
}