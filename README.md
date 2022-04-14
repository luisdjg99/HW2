# HW2

Problem 1

General problem approach:

Since we are only working with 4 servants(threads in this case) ,  concurrency levels are not high, so a Coarse Grained Synchronization should most of the time sufice. In the case the more servants are needed, then a non-blocking synchronization or Fine-Grained apporach must be considered. In my implementation, I'm using a reentrant lock to regulate access to the linked list. 

