
## TL;DR;

Two pointers is an idea applicable to many algorithm challenge. The key idea is to think in a parallel way, rather than sequential way which is more natural to humans.

In this post, I will show how to apply the idea of two pointers to these example problems:

1. [Intersection of Two Linked Lists](https://leetcode.com/problems/intersection-of-two-linked-lists/)
2. [Merge Sorted Array](https://leetcode.com/problems/merge-sorted-array/)
3. [Container With Most Water](https://leetcode.com/problems/container-with-most-water/)
4. [Trapping Rain Water](https://leetcode.com/problems/trapping-rain-water/)

## How to use two pointers idea

Whenever you find the problem has two boundaries, or two chains (a human-readable word for LinkedList XD) etc. you could try with two pointers idea.

The trick for using two pointers is three step:

1. Identify where to begin, i.e. where are the start points of the two pointers.
2. Identify how to move on, i.e. each step how should the two pointers 'walk', which of them should 'walk'.
3. Identify when to stop, i.e. the condition to stop or swap turn in 'walk'.

## Example 1 - Intersection of Two Linked Lists

See problem here: [Intersection of Two Linked Lists](https://leetcode.com/problems/intersection-of-two-linked-lists/)

There are lots of ways to solve this problem, with tradeoff among time, space and development complexity.

When analyzing complexity of algorithm, we suppose both linked list has length N, and number of nodes before intersection is L.

### Most straightforward way

The most straightforward way is first iterate one linked list, store every node in a hash table. Then iterate the second linked list, check whether the node is already in hash table for each iteration and stop when find the first one.

The time complexity is O(N + L). And space complexity is O(N).

### Improve space complexity to O(1)

Suppose the lengths of two linked lists are the same, i.e. before intersection the two linked lists have same number of nodes, then if there are two pointers walking through two linked list at same time and speed, then the first time they meet it the intersection.

Now the problem is we are not guaranteed that these two linked list are of same length. Could we possibly make them the same? **stop here, and think**

Of course! If we could calculate the difference of lengths of two linked list, then just pass several nodes of the longer linked list, then the lengths are the same!

This algorithm has O(2N + 2L) time complexity but only O(1) space complexity.

The solution of this method is actually given [here at method 3](http://www.geeksforgeeks.org/write-a-function-to-get-the-intersection-point-of-two-linked-lists/)

### Improve time complexity to O(2L)

This is my original solution that I never find online. This solution actually use more space but is faster averagely. 

So the problem of our naive solution is that we traverse the two linked lists one by one. Notice these two linked lists are actually symmetric, so that we could check if node in list A has been met in list B ....... and also if node in list B has been met in list A **at same time**!

Given this idea, we could have two pointers starting together, each has a hash table saving iterated nodes. Each iteration, both pointers check if the new node is in hash table of the other pointer. And it could stop as soon as one of pointer find such a node, which is the intersection.

This algorithm gives O(2L) time complexity and O(2L) space complexity. So in worst case, this is O(2N) time complexity and O(2N) space complexity. But averagely it has O(N) time complexity and O(N) space complexity.

## Example 2 - Merge Sorted Array

See problem here: [Merge Sorted Array](https://leetcode.com/problems/merge-sorted-array/)

This is a great example to use two pointer idea. If you go through two arrays one by one, then either you could first save one array in a hash table, and check existence when traversing the second array, or you could use binary search when traversing both arrays. Both methods sacrifice time or space to some extend.

The actual trick in this problem is the second step of two pointers solution, i.e. how to move on. Notice the arrays are sorted, so that we are guaranteed the pointed value is increasing. This indicates, if pointer A is pointing to larger number than pointer B, then any number after pointer A could not be the same as the number at pointer B.

This key idea results in this algorithm.

## Example 3 -- Container With Most Water

See problem here: [Container With Most Water](https://leetcode.com/problems/container-with-most-water/)

As you may noticed, the constraint of this problem that requires two boundaries falls in pattern of two pointers. So a quick idea is using two pointers as two boundaries, and to find two position than could hold most water.

Now let's go through three steps in two pointers problem.

First, where to begin? The naive way is go from one side to another side and search all possible combinations. This will result in O(N^2) time complexity. It sucks. A commonly used trick is we one pointer go from begin and another from end, then two pointers go face to face towards middle. The key idea is a little bit similar to dynamic programming, i.e. if I could know what happen given boundary [i, j], then I could calculate the condition given boundary [i + 1, j - 1] really fast.

Second, how to move on? To solve the problem with O(N) algorithm, each iteration must give one choice. What I mean is, given condition of [i, j], we could then check [i + 1, j] and [i, j - 1] and to iteratively find the result, but in this way it still is a O(N^2) algorithm. If we could eliminate one of the possibility, then the algorithm would be O(N)! Actually we could do that. If height at i is smaller than height at j, then there's no reason for us to check [i, j - 1]! So we move the one with smaller height toward the larger one.

Third, when to stop? Obviously, when two pointers meet, we stop.

The time complexity of this algorithm is O(N) and space complexity is O(1).

## Example 4 -- Trapping Rain Water

See problem here: [Trapping Rain Water](https://leetcode.com/problems/trapping-rain-water/)

Although this is a hard level problem in leetcode, given the solution to above problem, it should be easy to think of the solution to this one.

To briefly explain the solution to this problem, we use two pointer start from begin and end, and go toward each other. Each time we move the pointer with shorter block to the one with bigger, and calculate the water trapping within the range. We stop when two pointers meet.
