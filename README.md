## Table of Contents

- [Introduction](#Introduction)
- [Components](#components)
    * [Packer](#packer)
    * [PackerLogic](#packerlogic)    
    * [KnapsackSolver](#knapsacksolver)    
    * [KnapsackRepository](#knapsackrepository)    
    * [ProblemParser](#problemparser)    
    
- [Build](#build)
- [WishList](#wishlist)
    * [Volume test] (#Volume Test)
    * [Publisher/Subscriber] (#Publisher/Subscriber)



## Introduction

The challenge is a 0/1 knapsack problem that has been explained [here](https://en.wikipedia.org/wiki/Knapsack_problem)

There are various approaches to solve this problem like Brute Force, Dynamic Programming,... Because weights are not Integer in this problem we can't use Dynamic Programming. To solve this problem a **Least Cost Branch and Bound** algorithm has been used.

Below I describe the components that has been used in this project from a top down view. 

to improve maintainability and Readability of the code, best practices, SOLID principals, Design patterns(DI, IOC, Service, Repository(DDD)) have been applied.


## Components

* Packer

    Packer is the starting class. it configs and injects dependencies. 
    
    If this project wants to be used as a library then it is recommended to use the **PackerLogic** as start point and config it by an IOC framework like Spring. 
  
* PackerLogic

    PackerLogic provides abstraction for packer logic implementations. 
    DefaultPackerLogic implementation is responsible to read problems from Repository and solve them using KnapsackSolver 

* KnapsackSolver

    KnapsackSolver provides abstraction for knapsack algorithm.
    
    LcBAndBKnapsackSolver uses **Least Cost Branch And Bound** algorithm to solve this problem in most efficient approach(However this problem is polynomial and may end to exponential time complexity).
    Instead of normal brute force approach that checks all possible solutions, **Branch and Bound** Algorithm tries 
    to eliminate candidates that their **Upper Bound** is not better than the current best solution(So in this case the subtree is not continued).
    Here in Knapsack to calculate Upper Bound we sort items by ratio of value/weight and we take fraction of last item(fraction is not used in solution but it is used for upper bound).
    Also We can traverse the tree using FIFO and LIFO data structures. Here to optimize more we use **PriorityQueue**, 
    using PriorityQueue allows us to apply **Least Cost** strategy. The priority queue let us to peek the node with the lowest bound at the first(**Lower Bound** is the value of actual solution at the node)
    
    Since 0/1 Knapsack is about maximizing the total value, we cannot directly use the LC Branch and Bound technique to solve this. Instead, we convert this into a minimization problem by taking negative of the given values.      
        

* KnapsackRepository

    KnapsackRepository provides abstraction for repository implementations.
    
    KnapsackProblemFileRepository provides access to file problem repository. executeOnAllEntries can work on files with large number of lines.

* ProblemParser
    
    ProblemParser provides abstraction for problem parsers.
    
    RegexProblemParser uses Regex and uses caching group feature to parse and extract a problem.
    
## Build 

Gradle is on charge for this project.
To build this project on the root of the project run this command:

    $ ./gradlew run
    
## WishList
* Volume test

    This project is designed to work on files with large number of lines. 
    However if the number of entries on each line/problem could be huge as well, 
    we need to address this on ProblemParser(It is now handled in PackerLogic)

* Publisher/Subscriber
     We can implement PackerLogic as a publisher. This has two benefits, it works better on heavy files and also 
     subscriber doesn't need to wait for all results(it can stop between or decides in case exception occurs).
     So I wish to extend this logic to be a publisher and Packer as subscriber.