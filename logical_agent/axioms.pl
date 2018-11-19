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
    (\+walker(X,Y,s0) ; killed(X,Y,S)) ,\+obstacle(X,Y), height(Z), \+(Y is Z + 1), Y \= 0, width(W), \+(X is W + 1), X \= 0.

unapproachable(X, Y, S) :-
    walker(X,Y,s0) ; obstacle(X,Y) ; ( height(Z), Y is Z + 1 ) ; Y = 0 ; (width(W), X is W + 1);  X = 0.


% walker(1,2, result(stab, s0)).

jon(X, Y, result(A, S)) :- 
    jon(X1,Y1,S),
    (
    (X is X1 + 1, Y = Y1, approachable(X ,Y, S),  A = left);
    (X is X1+ 1, Y = Y1,  approachable(X ,Y, S), A = right);
    (X = X1, Y is Y1 - 1, approachable(X ,Y, S), A = down);
    (X = X1, Y is Y1 + 1, approachable(X ,Y, S), A = up)
    ).

jon(X, Y, result(A, S)) :- 
    jon(X,Y,S), ( (A = stab; A = refill) ; (A = up, Z is Y + 1, unapproachable(X, Z, S) , !) ; (A = down, Z is Y-1, unapproachable(X, Z, S), !) ; 
    (A = left, Z is X + 1, unapproachable(Z, Y, S), !) ; (A = right, Z is X - 1, unapproachable(Z, Y, S), ! ) ).
    
adjacentToJon(X1,Y1,S) :-
    jon(X,Y,S), ( (X1 = X, Y1 is Y +1) ; (X1 is X + 1, Y1 is Y) ; (X1 is X -1, Y1 is Y); (X1 = X, Y1 is Y -1) ).

walker(X,Y, result(A,S)) :-
    walker(X,Y,S), A \= stab, !;
    walker(X,Y,S), A = stab,!, ( \+adjacentToJon(X,Y,S) ; (ammo(X1,S), X1 = 0 ) ).

killed(X,Y,result(A,S)) :-
    walker(X,Y,S), adjacentToJon(X,Y,S), ammo(X1,S), X1 > 0, A = stab.

killed(X,Y,result(A,S)) :-
    killed(X,Y,S).

iterative_deepening(Goal, Limit) :-
    call_with_depth_limit(Goal, Limit,X),
    X \= depth_limit_exceeded,
    !.

iterative_deepening(Goal, Limit) :-
    NewLimit is Limit + 1,
    iterative_deepening(Goal, NewLimit).

% ammo(X,result(A,S)) :-
% 	(A = refill , maxAmmo(X)) ; (A = stab , ammo(X1, S), X1 is X + 1, X1 > 0, jon(X2,Y2,S), adjacentToJon(X2,Y2,S)).
	
ammo(X,result(A,S)) :-
	ammo(X,S), ( (A \= stab, A \= refill) ; (A = stab, ( ( jon(X1,Y1,S), \+adjacentToJon(X1,Y1,S) ) ; X = 0) )).

walkersAlive(N, result(A, S)) :-
	walkersAlive(N, S), (A = up ; A = down ; A = left; A = right ; A = refill ; 
        (A = stab, jon(X, Y, S), Z1 is X + 1, (\+walker(Z1, Y, s0) ; killed(Z1, Y, S)) , Z2 is X - 1, (\+walker(Z2, Y, s0); killed(Z2, Y, S)), Z3 is Y + 1, 
		(\+walker(X, Z3, s0); killed(X, Z3, S)) , Z4 is Y - 1, (\+walker(X, Z4, s0); killed(X,Z4,S) ) )).


walkersAlive(N, result(A, S)) :-
	M is N + 1, walkersAlive(M, S), A = stab, jon(X, Y, S), ( (Z1 is X + 1, walker(Z1, Y, S)) ; (Z2 is X - 1, walker(Z2, Y, S)) ; (Z3 is Y + 1, walker(X, Z3, S)) ; (Z4 is Y - 1, walker(X, Z4, S)) ).
