# uncapacitated-facility-location
A solution to the uncapacitated facility location problem

This code is an implementation of the primal solution to uncapacitated facility location problem

**direct quote**
Let t=0 (t is interpreted as time), set all dual variables to zero. With proceeding time t, all Vj are increased simultaneously,
i.e. they are all equal to t until they are frozen and then remain constant until the end. (Vj can be viewed as the price that
customer j pays for being served.)

There are three types of events:

  Vj = Cij for some i and j, where i is not tentatively open.
Then start to increase wij at the same rate, maintaining Vj - Wij = Cij. (Wij can be regarded as the amount that j offers to
contribute to the opening cost of facility i. At any stage Wij = max{0,Vj-Cij})

  Sum of Wij = Fi for some i.
Then tentatively open i. For all unconnected customers j with Vj >= Cij: connect j to i and freeze Vj, and all Wi'j for all i'

  Vj = Cij for some i and j, where i is not tentatively open.
Then connect j to i and freeze Vj.

Several events can occur at the same time and are then processed in arbitrary order. This continues until all customers are connected.

Now let V be the set of facilities that are tentatively open, and let E be the set of pairs {i, i'} of distinct tentatively open
facilities such that there is a customer j with Wij > 0 and Wi'j > 0. Choose a maximal stable set X in the graph (V,E). Open
facilities in X. For each customer j that is connected to a facility i not element of X, connect j to an open neighbor of i in
(V,E).
