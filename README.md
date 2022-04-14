# HW2

Problem 1

General problem approach:

Since we are only working with 4 servants(threads in this case) ,  concurrency levels are not high, so a Coarse Grained Synchronization should most of the time sufice. In the case the more servants are needed, then a non-blocking synchronization or Fine-Grained apporach must be considered. In my implementation, I'm using a reentrant lock to regulate access to the linked list. 

My algorithm is designed as follows:

First, I create a random array of size 500,000 , which is the amount of presents that the Minotaur recieved. 

Then this array is filled with random values from 0 to 700,000 ( I chose 700,000 at random, if another format of id is required, this can be changed) representing the id or the corresponding gift.

This array is later sorted and ready to be inserted into the Concurrent Linked List to write the "Thank you" notes.

4 threads or "servants" are instantiated and ready to start selecting items from the sorted array to insert intop the Concurrent Linked List.

Now,  my implementation of a Concurrent Linked List, as mentioned above uses a Coarse_Grained Approach, where a single reentrant lock is used to restric read and write operations to the Linked List.

The sorted array is split by 4 intervals ( one interval per thread) and each thread will create its own instance of a concurrent linked list. After inserting all values, then all 4 mini linked lists are joined. Forming the final sorted list of "presents".

This situation is starvation free, as threads will not interfere with each other, and the shared resource (in this case the sorted array) will not be infinately lock at any point. 
