import random
import time
from importlib import reload
import game


debug = False


class TestTeekoPlayer:

    def __init__(self):
        super().__init__()

        self.n_expts = 6

        self.score = 100

        # Illegal move in drop phase: -10 points if True
        self.illegal_drop_move = False
        # Illegal move in continued gameplay: -15 points if True
        self.illegal_later_move = False

        # If make_move() modifies board in drop phase: -5 if True
        self.board_modified_drop = False
        # If make_move() modifies board in continued gameplay: -5 if True
        self.board_modified_later = False

        # make_move in drop phase type: -5 if not a list of length 1 tuple
        self.illegal_drop_move_type = False
        # make move in continued gameplay: -5 if not a list of 2 tuples
        self.illegal_later_move_type = False

        # make_move() takes > 5sec in drop phase: -5 if True
        self.drop_time_exceeded = False
        # make_move() takes > 5sec in continued gameplay: -5 if True
        self.later_time_exceeded = False

        # AI wins at least 2/3 times: -25 if val < 2/3 * n_expts
        self.ai_wins = 0

        # 20 points: manual code inspection for recursive minimax implementation

    def is_board_modified(self, board_before, board_after):
        for i in range(5):
            for j in range(5):
                if board_after[i][j] != board_before[i][j]:
                    return True
        return False

    def is_legal_make_move_type(self, move, phase):
        if not isinstance(move, list):
            # print(phase, move)
            return False
        if phase:
            # Drop phase
            if len(move) != 1 or not isinstance(move[0], tuple) or len(move[0]) != 2 or not isinstance(move[0][0], int):
                # print(phase, move)
                return False
        else:
            # Continued gameplay
            if len(move) != 2 or not isinstance(move[1], tuple) or len(move[1]) != 2 or not isinstance(move[1][0], int):
                # print(phase, move)
                return False
        return True

    def is_legal_move(self, ai, move, drop_phase):
        if not drop_phase:
            if len(move) > 1:
                source_row = move[1][0]
                source_col = move[1][1]
                if source_row != None and ai.board[source_row][source_col] != ai.my_piece:
                    # Source should be an AI piece
                    return False
                if abs(source_row - move[0][0]) > 1 or abs(source_col - move[0][1]) > 1:
                    # Can only move to an adjacent space')
                    return False
            else:
                return False
        else:
            if ai.board[move[0][0]][move[0][1]] != ' ':
                # Destination should be an empty location
                return False

        # Legal move
        return True

    def test_gameplay(self):
        reload(game)
        ai = game.TeekoPlayer()

        piece_count = 0
        turn = 0

        # drop phase
        while piece_count < 8 and ai.game_value(ai.board) == 0:
            # get the player or AI's move
            if ai.my_piece == ai.pieces[turn]:
                # ai.print_board()

                board_before = ai.board
                start = time.time()
                move = ai.make_move(ai.board)
                end = time.time()
                board_after = ai.board

                if self.is_board_modified(board_before, board_after):
                    self.board_modified_drop = True

                if not self.is_legal_make_move_type(move, True):
                    self.illegal_drop_move_type = True

                if not self.is_legal_move(ai, move, True):
                    self.illegal_drop_move = True

                if end - start > 5:
                    self.drop_time_exceeded = True

                ai.place_piece(move, ai.my_piece)
                # print(ai.my_piece+" moved at "+chr(move[0][1]+ord("A"))+str(move[0][0]))
            else:
                # Choose a random legal move
                # # select an unoccupied space randomly
                move = []
                (row, col) = (random.randint(0,4), random.randint(0,4))
                while not ai.board[row][col] == ' ':
                    (row, col) = (random.randint(0,4), random.randint(0,4))

                ai.opponent_move([(row, col)])

            # update the game variables
            piece_count += 1
            turn += 1
            turn %= 2
            if debug:
                ai.print_board()

        # move phase - can't have a winner until all 8 pieces are on the board
        while ai.game_value(ai.board) == 0:

            # get the player or AI's move
            if ai.my_piece == ai.pieces[turn]:
                # ai.print_board()
                board_before = ai.board
                start = time.time()
                move = ai.make_move(ai.board)
                end = time.time()
                board_after = ai.board

                if self.is_board_modified(board_before, board_after):
                    self.board_modified_later = True

                if not self.is_legal_make_move_type(move, False):
                    self.illegal_later_move_type = True

                if not self.is_legal_move(ai, move, False):
                    self.illegal_later_move = True

                if end - start > 5:
                    self.later_time_exceeded = True

                ai.place_piece(move, ai.my_piece)
                # print(ai.my_piece+" moved from "+chr(move[1][1]+ord("A"))+str(move[1][0]))
                # print("  to "+chr(move[0][1]+ord("A"))+str(move[0][0]))
            else:
                possible_moves = []
                for r in range(5):
                    for c in range(5):
                        if ai.board[r][c] == ai.opp:
                            for i in [-1, 0, 1]:
                                for j in [-1, 0, 1]:
                                    if r+i >= 0 and r+i < 5 and c+j >= 0 and c+j < 5 and ai.board[r+i][c+j] == ' ':
                                        possible_moves.append([(r+i, c+j), (r, c)])
                ai.opponent_move(random.choice(possible_moves))

            # update the game variables
            turn += 1
            turn %= 2
            if debug:
                ai.print_board()

        # ai.print_board()
        if debug:
            if self.game_value(ai.board, ai.my_piece) == 1:
                print("AI wins! Game over.")
            else:
                print("You win! Game over.")
        return self.game_value(ai.board, ai.my_piece)

    def game_value(self, state, ai_piece):
        """ Checks the current board status for a win condition

        Args:
        state (list of lists): either the current state of the game as saved in
            this TeekoPlayer object, or a generated successor state.

        Returns:
            int: 1 if this TeekoPlayer wins, -1 if the opponent wins, 0 if no winner
        """
        num_ai_pieces = 0
        for i in range(5):
            for j in range(5):
                if state[i][j] == ai_piece:
                    num_ai_pieces += 1

        # If illegally placed more than 4 pieces, opponent wins by default
        if num_ai_pieces > 4:
            return -1

        # check horizontal wins
        for row in state:
            for i in range(2):
                if row[i] != ' ' and row[i] == row[i+1] == row[i+2] == row[i+3]:
                    return 1 if row[i]==ai_piece else -1

        # check vertical wins
        for col in range(5):
            for i in range(2):
                if state[i][col] != ' ' and state[i][col] == state[i+1][col] == state[i+2][col] == state[i+3][col]:
                    return 1 if state[i][col]==ai_piece else -1

        # Check \ diagonal wins
        # 1,0; 2,1; 3,2; 4,3;;
        # 0,0; 1,1; 2,2; 3,3;;
        # 1,1; 2,2; 3,3; 4,4;;
        # 0,1; 1,2; 2,3; 3,4
        for i in range(2):
            for j in range(2):
                cur_st = state[i][j]
                win = True
                for pos in range(4):
                    # (i+pos, j+pos)
                    if state[i+pos][j+pos] == ' ' or state[i+pos][j+pos] != cur_st:
                        win = False
                        break
                if win:
                    return 1 if cur_st==ai_piece else -1

        # Check / diagonal wins
        # 0,3; 1,2; 2,1; 3,0;;
        # 0,4; 1,3; 2,2; 3,1;;
        # 1,3; 2,2; 3,1; 4,0;;
        # 1,4; 2,3; 3,2; 4,1
        for i in range(2):
            for j in range(2):
                cur_st = state[i][4-j]
                win = True
                for pos in range(4):
                    # (i+pos, 4-j-pos)
                    if state[i+pos][4-j-pos] == ' ' or state[i+pos][4-j-pos] != cur_st:
                        win = False
                        break
                if win:
                    return 1 if cur_st==ai_piece else -1

        # Check box wins
        for i in range(0,4):
            for j in range(0,4):
                if state[i][j] != ' ' and state[i][j] == state[i][j+1] == state[i+1][j] == state[i+1][j+1]:
                    return 1 if state[i][j]==ai_piece else -1

        return 0 # no winner yet

    def test_code(self):
        for _ in range(self.n_expts):
            game_val = self.test_gameplay()
            if game_val == 1:
                self.ai_wins += 1

        if self.illegal_drop_move:
            print('-10: Illegal move by make_move() in drop phase')
            self.score -= 10
        if self.illegal_later_move:
            print('-15: Illegal move by make_move() during continued gameplay after drop phase')
            self.score -= 15

        if self.board_modified_drop:
            print('-5: Board modified by make_move() in drop phase')
            self.score -= 5
        if self.board_modified_later:
            print('-5: Board modified by make_move() during continued gameplay after drop phase')
            self.score -= 5

        if self.illegal_drop_move_type:
            print('-5: Illegal return type of make_move() in drop phase')
            self.score -= 5
        if self.illegal_later_move_type:
            print('-5: Illegal return type of make_move() during continued gameplay after drop phase')
            self.score -= 5

        if self.drop_time_exceeded:
            print('-5: make_move() takes more than 5 secs in drop phase')
            self.score -= 5
        if self.later_time_exceeded:
            print('-10: make_move() takes more than 5 secs during continued gameplay after drop phase')
            self.score -= 10

        if self.ai_wins < 2/3 * self.n_expts:
            print('-40: AI loses', self.n_expts - self.ai_wins, 'times out of', self.n_expts)
            self.score -= 40

        return self.score


def main():
    test_game = TestTeekoPlayer()
    score = test_game.test_code()

    print('Total Score:', score)

if __name__ == "__main__":
    main()
