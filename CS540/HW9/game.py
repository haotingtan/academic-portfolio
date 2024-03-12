import random
import copy


class TeekoPlayer:
    """ An object representation for an AI game player for the game Teeko.
    """
    board = [[' ' for j in range(5)] for i in range(5)]
    pieces = ['b', 'r']

    def __init__(self):
        """ Initializes a TeekoPlayer object by randomly selecting red or black as its
        piece color.
        """
        self.my_piece = random.choice(self.pieces)
        self.opp = self.pieces[0] if self.my_piece == self.pieces[1] else self.pieces[1]

    def succ(self, state, cur_player, num_markers):
        successors = []
        if num_markers < 4:
            drop_phase = True
        else:
            drop_phase = False

        if drop_phase:
            for row in range(5):
                for col in range(5):
                    if state[row][col] == ' ':
                        move = []
                        move.insert(0, (row, col))
                        successors.append(move)
        else:
            for row in range(5):
                for col in range(5):
                    if state[row][col] == cur_player:
                        for i in range(-1, 2):
                            if 0 <= (row+i) <= 4:
                                for j in range(-1, 2):
                                    if 0 <= (col+j) <= 4:
                                        if state[row+i][col+j] == ' ' and (i!=0 or j!=0):
                                            move = []
                                            move.insert(0, (row+i, col+j))
                                            move.insert(1, (row, col))
                                            successors.append(move)
        return successors

    def heuristic_game_value(self, state):
        mycnt = 0
        oppcnt = 0

        my_piece_row = []
        my_piece_col = []
        opp_piece_row = []
        opp_piece_col = []
        for row in range(5):
            for col in range(5):
                if state[row][col] == self.my_piece:
                    my_piece_row.append(row)
                    my_piece_col.append(col)
                elif state[row][col] == self.opp:
                    opp_piece_row.append(row)
                    opp_piece_col.append(col)

        if max(set(my_piece_row), key=my_piece_row.count) > max(set(opp_piece_row), key=my_piece_row.count):
            mycnt += 1
        elif max(set(opp_piece_row), key=my_piece_row.count) > max(set(my_piece_row), key=my_piece_row.count):
            oppcnt += 1
        if max(set(my_piece_col), key=my_piece_row.count) > max(set(opp_piece_col), key=my_piece_row.count):
            mycnt += 1
        elif max(set(opp_piece_col), key=my_piece_row.count) > max(set(my_piece_col), key=my_piece_row.count):
            oppcnt += 1

        my = 0
        opp = 0
        for i in range(len(my_piece_row)):
            my += abs(my_piece_row[i] - my_piece_col[i])
        for i in range(len(opp_piece_row)):
            opp += abs(opp_piece_row[i] - opp_piece_col[i])
        if my < opp:
            mycnt += 1
        elif opp < my:
            oppcnt += 1

        my = 0
        opp = 0
        for i in range(len(my_piece_row)):
            my += abs(4-my_piece_row[i] - my_piece_col[i])
        for i in range(len(opp_piece_row)):
            opp += abs(4-opp_piece_row[i] - opp_piece_col[i])
        if my < opp:
            mycnt += 1
        elif opp < my:
            oppcnt += 1

        row_dist = []
        col_dist = []
        avg_row = float(sum(my_piece_row)) / len(my_piece_row)
        avg_col = float(sum(my_piece_col)) / len(my_piece_col)
        for i in range(len(my_piece_row)):
            row_dist.append(abs(my_piece_row[i] - avg_row))
            col_dist.append(abs(my_piece_col[i] - avg_col))
        my_sum = sum(row_dist) + sum(col_dist)
        row_dist = []
        col_dist = []
        avg_row = float(sum(opp_piece_row)) / len(opp_piece_row)
        avg_col = float(sum(opp_piece_col)) / len(opp_piece_col)
        for i in range(len(opp_piece_row)):
            row_dist.append(abs(opp_piece_row[i] - avg_row))
            col_dist.append(abs(opp_piece_col[i] - avg_col))
        opp_sum = sum(row_dist) + sum(col_dist)

        if my_sum < opp_sum:
            mycnt += 1
        elif opp_sum < my_sum:
            oppcnt += 1

        if mycnt + oppcnt == 0:
            return 0.0
        else:
            return float(mycnt-oppcnt) / (mycnt+oppcnt)

    def max_value(self, state, depth, my_num_markers, opp_num_markers):
        if self.game_value(state) != 0:
            return self.game_value(state)
        elif depth == 0:
            return self.heuristic_game_value(state)
        elif depth % 2 == 1:
            succs = self.succ(state, self.my_piece, my_num_markers)
            value = []
            for succ in succs:
                copy_state = copy.deepcopy(state)
                if len(succ) > 1:
                    copy_state[succ[1][0]][succ[1][1]] = ' '
                copy_state[succ[0][0]][succ[0][1]] = self.my_piece
                value.append(self.max_value(copy_state, depth-1, my_num_markers+1, opp_num_markers))
            return max(value)
        else:
            succs = self.succ(state, self.opp, opp_num_markers)
            value = []
            for succ in succs:
                copy_state = copy.deepcopy(state)
                if len(succ) > 1:
                    copy_state[succ[1][0]][succ[1][1]] = ' '
                copy_state[succ[0][0]][succ[0][1]] = self.opp
                value.append(self.max_value(copy_state, depth-1, my_num_markers, opp_num_markers+1))
            return min(value)

    def make_move(self, state):

        """ Selects a (row, col) space for the next move. You may assume that whenever
        this function is called, it is this player's turn to move.

        Args:
            state (list of lists): should be the current state of the game as saved in
                this TeekoPlayer object. Note that this is NOT assumed to be a copy of
                the game state and should NOT be modified within this method (use
                place_piece() instead). Any modifications (e.g. to generate successors)
                should be done on a deep copy of the state.

                In the "drop phase", the state will contain less than 8 elements which
                are not ' ' (a single space character).

        Return:
            move (list): a list of move tuples such that its format is
                    [(row, col), (source_row, source_col)]
                where the (row, col) tuple is the location to place a piece and the
                optional (source_row, source_col) tuple contains the location of the
                piece the AI plans to relocate (for moves after the drop phase). In
                the drop phase, this list should contain ONLY THE FIRST tuple.

        Note that without drop phase behavior, the AI will just keep placing new markers
            and will eventually take over the board. This is not a valid strategy and
            will earn you no points.
        """
        my_num_markers = 0
        opp_num_markers = 0
        for row in range(5):
            for col in range(5):
                if state[row][col] == self.my_piece:
                    my_num_markers += 1
                elif state[row][col] == self.opp:
                    opp_num_markers += 1

        succs = self.succ(state, self.my_piece, my_num_markers)
        value = []
        for succ in succs:
            copy_state = copy.deepcopy(state)
            if len(succ) > 1:
                copy_state[succ[1][0]][succ[1][1]] = ' '
            copy_state[succ[0][0]][succ[0][1]] = self.my_piece
            value.append(self.max_value(copy_state, 2, my_num_markers+1, opp_num_markers))

        return succs[value.index(max(value))]

    def opponent_move(self, move):
        """ Validates the opponent's next move against the internal board representation.
        You don't need to touch this code.

        Args:
            move (list): a list of move tuples such that its format is
                    [(row, col), (source_row, source_col)]
                where the (row, col) tuple is the location to place a piece and the
                optional (source_row, source_col) tuple contains the location of the
                piece the AI plans to relocate (for moves after the drop phase). In
                the drop phase, this list should contain ONLY THE FIRST tuple.
        """
        # validate input
        if len(move) > 1:
            source_row = move[1][0]
            source_col = move[1][1]
            if source_row != None and self.board[source_row][source_col] != self.opp:
                self.print_board()
                print(move)
                raise Exception("You don't have a piece there!")
            if abs(source_row - move[0][0]) > 1 or abs(source_col - move[0][1]) > 1:
                self.print_board()
                print(move)
                raise Exception('Illegal move: Can only move to an adjacent space')
        if self.board[move[0][0]][move[0][1]] != ' ':
            raise Exception("Illegal move detected")
        # make move
        self.place_piece(move, self.opp)

    def place_piece(self, move, piece):
        """ Modifies the board representation using the specified move and piece

        Args:
            move (list): a list of move tuples such that its format is
                    [(row, col), (source_row, source_col)]
                where the (row, col) tuple is the location to place a piece and the
                optional (source_row, source_col) tuple contains the location of the
                piece the AI plans to relocate (for moves after the drop phase). In
                the drop phase, this list should contain ONLY THE FIRST tuple.

                This argument is assumed to have been validated before this method
                is called.
            piece (str): the piece ('b' or 'r') to place on the board
        """
        if len(move) > 1:
            self.board[move[1][0]][move[1][1]] = ' '
        self.board[move[0][0]][move[0][1]] = piece

    def print_board(self):
        """ Formatted printing for the board """
        for row in range(len(self.board)):
            line = str(row)+": "
            for cell in self.board[row]:
                line += cell + " "
            print(line)
        print("   A B C D E")

    def game_value(self, state):
        """ Checks the current board status for a win condition

        Args:
        state (list of lists): either the current state of the game as saved in
            this TeekoPlayer object, or a generated successor state.

        Returns:
            int: 1 if this TeekoPlayer wins, -1 if the opponent wins, 0 if no winner

        TODO: complete checks for diagonal and box wins
        """
        # check horizontal wins
        for row in state:
            for i in range(2):
                if row[i] != ' ' and row[i] == row[i+1] == row[i+2] == row[i+3]:
                    return 1 if row[i]==self.my_piece else -1

        # check vertical wins
        for col in range(5):
            for i in range(2):
                if state[i][col] != ' ' and state[i][col] == state[i+1][col] == state[i+2][col] == state[i+3][col]:
                    return 1 if state[i][col]==self.my_piece else -1

        # check \ disgonal wins
        for row in range(2):
            for col in range(2):
                if state[row][col] != ' ' and state[row][col] == state[row+1][col+1] == state[row+2][col+2] == state[row+3][col+3]:
                    return 1 if state[row][col]==self.my_piece else -1

        # check / diagonal wins
        for row in range(2):
            for col in range(2):
                if state[4-row][4-col] != ' ' and state[4-row][4-col] == state[4-row-1][4-col-1] == state[4-row-2][4-col-2] == state[4-row-3][4-col-3]:
                    return 1 if state[4-row][4-col]==self.my_piece else -1

        # check box wins
        for row in range(4):
            for col in range(4):
                if state[row][col] != ' ' and state[row][col] == state[row][col+1] == state[row+1][col] == state[row+1][col+1]:
                    return 1 if state[row][col] == self.my_piece else -1

        return 0 # no winner yet

############################################################################
#
# THE FOLLOWING CODE IS FOR SAMPLE GAMEPLAY ONLY
#
############################################################################
def main():
    print('Hello, this is Samaritan')
    ai = TeekoPlayer()
    piece_count = 0
    turn = 0

    # drop phase
    while piece_count < 8 and ai.game_value(ai.board) == 0:

        # get the player or AI's move
        if ai.my_piece == ai.pieces[turn]:
            ai.print_board()
            move = ai.make_move(ai.board)
            ai.place_piece(move, ai.my_piece)
            print(ai.my_piece+" moved at "+chr(move[0][1]+ord("A"))+str(move[0][0]))
        else:
            move_made = False
            ai.print_board()
            print(ai.opp+"'s turn")
            while not move_made:
                player_move = input("Move (e.g. B3): ")
                while player_move[0] not in "ABCDE" or player_move[1] not in "01234":
                    player_move = input("Move (e.g. B3): ")
                try:
                    ai.opponent_move([(int(player_move[1]), ord(player_move[0])-ord("A"))])
                    move_made = True
                except Exception as e:
                    print(e)

        # update the game variables
        piece_count += 1
        turn += 1
        turn %= 2

    # move phase - can't have a winner until all 8 pieces are on the board
    while ai.game_value(ai.board) == 0:

        # get the player or AI's move
        if ai.my_piece == ai.pieces[turn]:
            ai.print_board()
            move = ai.make_move(ai.board)
            ai.place_piece(move, ai.my_piece)
            print(ai.my_piece+" moved from "+chr(move[1][1]+ord("A"))+str(move[1][0]))
            print("  to "+chr(move[0][1]+ord("A"))+str(move[0][0]))
        else:
            move_made = False
            ai.print_board()
            print(ai.opp+"'s turn")
            while not move_made:
                move_from = input("Move from (e.g. B3): ")
                while move_from[0] not in "ABCDE" or move_from[1] not in "01234":
                    move_from = input("Move from (e.g. B3): ")
                move_to = input("Move to (e.g. B3): ")
                while move_to[0] not in "ABCDE" or move_to[1] not in "01234":
                    move_to = input("Move to (e.g. B3): ")
                try:
                    ai.opponent_move([(int(move_to[1]), ord(move_to[0])-ord("A")),
                                    (int(move_from[1]), ord(move_from[0])-ord("A"))])
                    move_made = True
                except Exception as e:
                    print(e)

        # update the game variables
        turn += 1
        turn %= 2

    ai.print_board()
    if ai.game_value(ai.board) == 1:
        print("AI wins! Game over.")
    else:
        print("You win! Game over.")


if __name__ == "__main__":
    main()
