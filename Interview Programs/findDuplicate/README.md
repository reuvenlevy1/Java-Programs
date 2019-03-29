Returns all duplicates from an integer array by adding numbers to a HashMap and checking/storing duplicates.

This is done in 2 ways:
  1. Add duplicates to String class using the += operator. [Slowest]
     --> 2 6 7 7 9 5 4 6 3 7 4 6 4 6 7 4 4 4 8 4 4 6 4 4 6 4 6 = 5.2222 ms Average
  2. Append duplicates to StringBuilder class using append() method. [Mid Speed]
     --> 1 4 6 4 2 2 2 4 2 2 2 4 2 5 4 2 2 2 4 2 2 4 2 2 3 2 4 = 2.8519 ms Average
  3. Add duplicates to ArrayList using add() method. [Fastest]
     --> 1 2 1 1 2 2 2 2 2 2 2 1 2 1 1 2 2 3 2 2 2 1 2 2 2 2 2 = 1.7778 ms Average
