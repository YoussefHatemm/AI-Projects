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
    approachable(X ,Y, S),
    ((Z is X -1, jon(Z, Y, S), A = left);
    (Z is X + 1, jon(Z,Y,S), A = right);
    (Z is Y + 1, jon(X,Z,S), A = down);
    (Z is Y - 1, jon(X,Z,S), A = up)).

jon(X, Y, result(A, S)) :- 
    jon(X,Y,S), (A = stab ; A = refill ; (A = up, Z is Y + 1, \+approachable(X, Z, S)) ; (A = down, Z is Y-1, \+approachable(X, Z, S)) ; 
(A = left, Z is X + 1, \+approachable(Z, Y, S)) ; (A = right, Z is X - 1, \+approachable(Z, Y, S))).
    
    
ajdacentToJon(X1,Y1,S) :-
    jon(X,Y,S), ( (X1 = X, Y1 is Y +1) ; (X1 is X + 1, Y1 is Y) ; (X1 is X -1, Y1 is Y); (X1 = X, Y1 is Y -1) ).

walker(X,Y, result(A,S)) :-
    walker(X,Y,S), A \= stab ;
    walker(X,Y,S), A = stab, ( \+ajdacentToJon(X,Y,S)). %; (ammo(X1,s0), X1 > 0 ) ).

iterative_deepening(Goal, Limit) :-
    call_with_depth_limit(Goal, Limit,_).
    
iterative_deepening(Goal, Limit) :-
    NewLimit is Limit + 1, 
    call_with_depth_limit(Goal, NewLimit,_).

    walkersAlive(N, result(A, S)) :-
	walkersAlive(N, S), (A = up ; A = down ; A = left; A = right ; A = refill ; (A = stab, jon(X, Y, S), Z1 is X + 1, \+walker(Z1, Y, S), Z2 is X - 1, \+walker(Z2, Y, S), Z3 is Y + 1, 
		\+walker(X, Z3, S), Z4 is Y - 1, \+walker(X, Z4, S))).


walkersAlive(N, result(A, S)) :-
	M is N + 1, walkersAlive(M, S), A = stab, jon(X, Y, S), (Z1 is X + 1, walker(Z1, Y, S) ; Z2 is X - 1, walker(Z2, Y, S) ; Z3 is Y + 1, walker(X, Z3, S) ; Z4 is Y - 1, walker(X, Z4, S)).