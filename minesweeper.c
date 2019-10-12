#include<stdio.h>
#include<time.h>
#include<math.h>
#include<stdlib.h>
#define MAX_ROW 100
#define MAX_COL 100

//Text-based Minesweeper

char game[MAX_ROW][MAX_COL];
unsigned char display[MAX_ROW][MAX_COL];
int m, n;
unsigned char difficulty = 1;
int i, j;
int r, c;
unsigned char hit_a_mine = 0;
int num_of_hidden_cells = 0;
char *colors[] = {"\x1b[34m", "\x1b[32m", "\x1b[31m", "\x1b[33m", "\x1b[36m", "\x1b[35m", "\x1b[35m", "\x1b[35m"};
int num_of_mines;
int markedMines;
 
 int assignMines() {
         //Assign mines
         for (i = 0; i < num_of_mines; i++) {
                 int found = -1;
                 while (found == -1) {
                         int tryRow = rand() % m;
                         int tryCol = rand() % n;
                         if (game[tryRow][tryCol] == 0 && (tryRow > r + 1 || tryRow < r - 1 || tryCol > c + 1 || tryCol < c - 1)) {
                                 game[tryRow][tryCol] = -1;
                                 found = 0;
                         }
                 }
         }
         return 0;
 }
 
 int calculateNumbers() {
         //Calculate number of mines adjacent to each cell
         for (i = 0; i < m; i++) {
                 for (j = 0; j < n; j++) {
                         if (game[i][j] == 0) {
                                 //Not a mine, so count adjacent mines
                                 int mineCount = 0;
                                 for (int k = i - 1; k <= i + 1; k++) {
                                         for (int l = j - 1; l <= j + 1; l++) {
                                                 if (game[k][l] == -1) mineCount++;
                                         }
                                 }
                                 game[i][j] = mineCount;
                         }
                 }
         }
         return 0;
 }
 
 int displayBoard(int gameGoing) {
         system("clear");
         printf("Total mines: %i.  Mines remaining: %i.\n\n", num_of_mines, num_of_mines - markedMines);
         //Prints the grid of the current game state or the entire board, with the numbers in color
         printf("   ");
         for (i = 1; i <= n; i++) {
                 printf("%3i", i);
         }
         for (i = 0; i < m; i++) {
                 printf("\n%3i", i+1);
                 for (j = 0; j < n; j++) {
                         if (!gameGoing) {
                                 //The game is over, so display the full board
                                 if (game[i][j] == 0) {
                                         printf("   ");
                                 } else if (game[i][j] == -1) {
                                         printf("  *");
                                 } else {
                                         printf("%s%3i\x1b[0m", colors[game[i][j] - 1], game[i][j]);
                                 }
                         } else {
                                 //Dispaly only what has been reveald
                                 if (display[i][j] == 255) {
                                         printf("  -");
                                 } else if (display[i][j] == 0) {
                                         printf("   ");
                                 } else if (display[i][j] == 10) {
                                         printf("  *");
                                 } else {
                                         printf("%s%3i\x1b[0m", colors[display[i][j] - 1], display[i][j]);
                                 }
                         }
                 }
                 printf("%3i", i+1);
         }
         printf("\n   ");
         for (int i = 1; i <= n; i++) {
                 printf("%3i", i);
         }
         printf("\n\n");
 }
 
 int reveal(int row, int col) {
         //Reveal this cell, and if it is a zero, recursively reveal more cells
         display[row][col] = game[row][col];
         num_of_hidden_cells = num_of_hidden_cells - 1;
         if (game[row][col] == 0) {
                 //If it is a zero, search for more cells to reveal
                 if (row < (m-1) && display[row + 1][col] == 255) reveal(row + 1, col);
                 if (row > 0 && display[row - 1][col] == 255) reveal(row - 1, col);
                 if (col < (n-1) && display[row][col + 1] == 255) reveal(row, col + 1);
                 if (col > 0 && display[row][col - 1] == 255) reveal(row, col - 1);
                 //Diagonal
                 if (row > 0 && col > 0 && display[row - 1][col - 1] == 255) reveal(row - 1, col - 1);
                 if (row > 0 && col < (n-1)  && display[row - 1][col + 1] == 255) reveal(row - 1, col + 1);
                 if (row < (m-1) && col > 0 && display[row + 1][col - 1] == 255) reveal(row + 1, col - 1);
                 if (row < (m-1) && col < (n-1) && display[row + 1][col + 1] == 255) reveal(row + 1, col + 1);
         }
         return 0;
 }
 
 int main(int argc, char ** argv) {
         srand(time(NULL));
         //Initialize with the command line arguments
         if (argc < 4) return -1;
         m = atoi(argv[1]);
         n = atoi(argv[2]);
         difficulty = atoi(argv[3]);
         if (difficulty > 25) difficulty = 25;
         int want_to_play_more = 0;
 
         do {
                 //Initialize the game
                 for (i = 0; i < MAX_ROW; i++) {
                         for (j = 0; j < MAX_COL; j++) {
                                 game[i][j] = 0;
                                 display[i][j] = 255;
                         }
                 }
                 num_of_hidden_cells = m * n;
                 want_to_play_more = 0;
                 hit_a_mine = 0;
                 markedMines = 0;
                 num_of_mines = (int)(m * n * (0.05 + 0.025 * difficulty));
                 int t = time(NULL);
 
                 //Get first move and guarentee it doesn't hit a mine
                 do {
                         displayBoard(1);
                         printf("Enter row: ");
                         char input[10];
                         scanf("%s", input);
                         r = atoi(input);
                         printf("Enter column: ");
                         scanf("%s", input);
                         c = atoi(input);
                         r--; c--;
                 } while (!(0 <= r && r < m && 0 <= c && c < n));
                 assignMines(num_of_mines);
                 num_of_hidden_cells = m * n;
                 calculateNumbers();
                 reveal(r, c);
 
                 //All subsequent moves
                 do {
                         //Get a valid move from the user, until the game is over
                         displayBoard(1);
                         char input[10];
                         printf("Enter row, or enter in negatives to select a mine: ");
                         scanf("%s", input);
                         r = atoi(input);
                         printf("Enter column: ");
                         scanf("%s", input);
                         c = atoi(input);
                         if (r == 0 || c == 0) continue;
                         if (0 < r && r <= m && 0 < c && c <= n && display[r-1][c-1] == 255) {
                                 //Traditional select cell
                                 r--; c--;
 
                                 //Deal with the spot of the picked cell
                                 if (game[r][c] == -1) { //hit a mine
                                         hit_a_mine = 1;
                                 } else { //reveal the cell since it's not a mine
                                        reveal(r, c);
                                 }
                         } else if (0 < r && r <= m && 0 < c && c <= n && display[r-1][c-1] < 10) {
                                 //Click each cell adjacent to this one.  Hopefully all mines are selected
                                 r--;c--;
                                 for (int i = r-1; i <= r+1; i++) {
                                         for (int j = c-1; j <= c+1; j++) {
                                                 if (i >= 0 && i < m && j < n && j >= 0 && display[i][j] == 255) {
                                                         //Deal waith the spot of the picked cell
                                                         if (game[i][j] == -1) { //hit a mine
                                                                 hit_a_mine = 1;
                                                         } else { //reveal the cell since it's not a mine
                                                                 reveal(i, j);
                                                         }
                                                 }
                                         }
                                 }
                         } else if (0 < -r && -r <= m && 0 < -c && -c <= n && (display[-r-1][-c-1] == 255 || display[-r-1][-c-1] == 10)) {
                                 if (display[-r-1][-c-1] == 10) {
                                         //Deselect a marked mine
                                         display[-r-1][-c-1] = 255;
                                         markedMines--;
                                 } else {
                                         //Negative entering to select a mine
                                         display[-r-1][-c-1] = 10;
                                         markedMines++;
                                 }
                         }
                 } while ((!hit_a_mine) && (num_of_hidden_cells != num_of_mines));
                 //Game over; display results
                 markedMines = num_of_mines;
                 displayBoard(0);
                 if (hit_a_mine) printf("You hit a mine!"); else printf("You win!");
 
                 int totalTime = time(NULL) - t;
                 if (totalTime > 59) {
                         printf("\nThe game took %i minutes and %i seconds.\n\n", totalTime / 60, totalTime % 60);
                 } else {
                         printf("\nThe game took %i seconds.\n\n", totalTime);
                 }
 
                 //Prompt for next game
                 printf("Do you want to play again? Type 1 for yes, 0 for no: ");
                 scanf("%d", &want_to_play_more);
         } while (want_to_play_more);
         return 0;
 }
