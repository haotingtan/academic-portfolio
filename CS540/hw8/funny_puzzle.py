import heapq


def get_manhattan_distance(from_state, to_state=[1, 2, 3, 4, 5, 6, 7, 0, 0]):
    """
    INPUT: 
        Two states (if second state is omitted then it is assumed that it is the goal state)
    RETURNS:
        A scalar that is the sum of Manhattan distances for all tiles.
    """
    distance = 0
    if len(from_state) != len(to_state):
        return
    for i in range(len(from_state)):
        if from_state[i] == 0:
            continue
        for j in range(len(to_state)):
            if to_state[j] == from_state[i]:
                distance += abs(int(i/3) - int(j/3))
                distance += abs(i%3 - j%3)
                break
    return distance


def print_succ(state):
    """
    INPUT: 
        A state (list of length 9)
    WHAT IT DOES:
        Prints the list of all the valid successors in the puzzle. 
    """
    succ_states = get_succ(state)

    for succ_state in succ_states:
        print(succ_state, "h={}".format(get_manhattan_distance(succ_state)))


def get_succ(state):
    """
    INPUT: 
        A state (list of length 9)
    RETURNS:
        A list of all the valid successors in the puzzle (don't forget to sort the result as done below). 
    """
    succ_states = []
    invalid_range = [[0,3,6], [0,1,2], [2,5,8], [6,7,8]]
    for i in range(9):
        if state[i] == 0:
            if (i not in invalid_range[0]) and state[i-1] != 0:
                succ_list = state.copy()
                succ_list[i], succ_list[i-1] = succ_list[i-1], succ_list[i]
                succ_states.append(succ_list)
            if (i not in invalid_range[1]) and state[i-3] != 0:
                succ_list = state.copy()
                succ_list[i], succ_list[i-3] = succ_list[i-3], succ_list[i]
                succ_states.append(succ_list)
            if (i not in invalid_range[2]) and state[i+1] != 0:
                succ_list = state.copy()
                succ_list[i], succ_list[i+1] = succ_list[i+1], succ_list[i]
                succ_states.append(succ_list)
            if (i not in invalid_range[3]) and state[i + 3] != 0:
                succ_list = state.copy()
                succ_list[i], succ_list[i+3] = succ_list[i+3], succ_list[i]
                succ_states.append(succ_list)
    succ_states = sorted(succ_states)
    return sorted(succ_states)


def solve(state, goal_state=[1, 2, 3, 4, 5, 6, 7, 0, 0]):
    """
    INPUT: 
        An initial state (list of length 9)
    WHAT IT SHOULD DO:
        Prints a path of configurations from initial state to goal state along h values, number of moves, and max queue number in the format specified in the pdf.
    """
    pq = []
    index = -1
    start_data = (get_manhattan_distance(state), state, (0, get_manhattan_distance(state), index))
    heapq.heappush(pq, start_data)
    parents = {}
    closed_set = {}
    max_len = 1

    while pq:
        curr_data = heapq.heappop(pq)

        if curr_data[1] == goal_state:
            final_path = [curr_data]
            trace = curr_data[2][2]
            while trace != -1:
                trace = parents[curr_data[2][2]]
                curr_data = closed_set[trace]
                final_path.append(closed_set[trace])
            final_path.reverse()
            for i in range(len(final_path)):
                print(final_path[i][1], 'h={} moves: {}'.format(final_path[i][2][1], final_path[i][2][0]))
            print('Max queue length: {}'.format(max_len))
            return

        closed_set[curr_data[2][2]] = curr_data

        for neighbor in get_succ(curr_data[1]):
            neighbor_move = curr_data[2][0] + 1
            neighbor_h = get_manhattan_distance(neighbor)

            exit = False
            close_list = list(closed_set.values())
            for i in range(len(closed_set)):
                if neighbor == close_list[i][1]:
                    exit = True
                    break
            if exit:
                continue

            index += 1
            parents[index] = curr_data[2][2]
            heapq.heappush(pq, (neighbor_move+neighbor_h, neighbor, (neighbor_move, neighbor_h, index)))

        if len(pq) > max_len:
            max_len = len(pq)

    return



if __name__ == "__main__":
    """
    Feel free to write your own test code here to exaime the correctness of your functions. 
    Note that this part will not be graded.
    """
    print_succ([2,5,1,4,0,6,7,0,3])
    print()

    print(get_manhattan_distance([2,5,1,4,0,6,7,0,3], [1, 2, 3, 4, 5, 6, 7, 0, 0]))
    print()

    solve([2,5,1,4,0,6,7,0,3])
    print()