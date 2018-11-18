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
    (
    (Z is X - 1, jon(Z,Y,S), A = left);
    (Z is X + 1, jon(Z,Y,S), A = right);
    (Z is Y + 1, jon(X,Z,S), A = down);
    (Z is Y - 1, jon(X,Z,S), A = up)
    ).

    
jon(X, Y, result(A, S)) :- 
    jon(X,Y,S), (A = stab ; A = refill ; (A = up, Z is Y + 1, \+approachable(X, Z, S)) ; (A = down, Z is Y-1, \+approachable(X, Z, S)) ; 
(A = left, Z is X + 1, \+approachable(Z, Y, S)) ; (A = right, Z is X - 1, \+approachable(Z, Y, S))).
    



ammo(X,result(A,S)) :-
	
	(A = refill , maxAmmo(X)) ; (A = stab , X is X-1 ).
	

ammo(X,result(A,S)) :-
	
	ammo(X,S), (A = up ; A = down ;  A = right ; A = left).
	











