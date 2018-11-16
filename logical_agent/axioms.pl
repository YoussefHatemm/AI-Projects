walker(3, 3, s0).
walker(1, 2, s0).
walker(1, 3, s0).
walkersAlive(3, s0).
obstacle(3, 2).
dragonStone(3, 1).
jon(1, 1, s0).
maxAmmo(2).
ammo(X, s0) :- maxAmmo(X).
width(3).
height(3).


approachable(X, Y, S) :- 
    \+walker(X,Y,S), \+obstacle(X,Y), height(Z), \+(Y is Z + 1), Y \= 0, width(W), \+(X is W + 1), X \= 0.
    

% walker(1,2, result(stab, s0)).

jon(X, Y, result(A, S)) :- 
    jon(X,Y,S), (A = stab ; A = refill ; (A = up, \+approachable(X, Z, S), Z is Y + 1) ; (A = down, \+approachable(X, Z, S), Z is Y-1) ; 
(A = left, \+approachable(Z, Y, S), Z is X + 1) ; (A = right, \+approachable(Z, Y, S), Z is X - 1)).
    

jon(X, Y, result(A, S)) :- 
    approachable(X ,Y, S),
    ((Z is X -1, jon(Z, Y, S), A = left);
    (Z is X + 1, jon(Z,Y,S), A = right);
    (Z is Y + 1, jon(X,Z,S), A = down);
    (Z is Y - 1, jon(X,Z,S), A = up)).

walkersAlive(N, result(A, S)) :-
	walkersAlive(N, S), (A = up ; A = down ; A = left; A = right ; A = refill ; (A = stab, jon(X, Y, S), Z1 is X + 1, \+walker(Z1, Y, S), Z2 is X - 1, \+walker(Z2, Y, S), Z3 is Y + 1, 
		\+walker(X, Z3, S), Z4 is Y - 1, \+walker(X, Z4, S))).


walkersAlive(N, result(A, S)) :-
	M is N + 1, walkersAlive(M, S), A = stab, jon(X, Y, S), (Z1 is X + 1, walker(Z1, Y, S) ; Z2 is X - 1, walker(Z2, Y, S) ; Z3 is Y + 1, walker(X, Z3, S) ; Z4 is Y - 1, walker(X, Z4, S)).