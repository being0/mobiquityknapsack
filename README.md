## Table of Contents

- [Introduction](#Introduction)
- [Components](#components)
    * [Packer](#packer)
    * [PackerLogic](#packerlogic)    
    * [KnapsackSolver](#knapsacksolver)    
    * [KnapsackRepository](#knapsackrepository)    
    * [ProblemParser](#problemparser)    
    
- [Build](#build)
- [Wish List](#wishlist)



## Introduction

The challenge is a 0-1 knapsack problem that has been explained [here](https://en.wikipedia.org/wiki/Knapsack_problem)

There are various approaches to solve this problem like Brute Force, Dynamic Programming,... Because weights are not Integer in this problem we can't use Dynamic Programming. To solve this problem a **Least Cost Branch and Bound** algorithm has been used.

Below I describe the components that has been used in this project from a top down view. 

to improve maintainability and Readability of the code, best practices, SOLID principals, Design patterns(DI, IOC, Service, Repository(DDD)) have been applied.


## Components

* Packer

    Packer is the starting class. it configs and injects dependencies. 
    
    If this project wants to be used as a library then it is recommended to use the **PackerLogic** as start point and config it by an IOC framework like Spring. 
  
* PackerLogic

    PackerLogic provide abstraction for packer logic implementations. 
    DefaultPackerLogic implementation is responsible to read problems from Repository and solve them using KnapsackSolver 

* KnapsackSolver

* KnapsackRepository

    KnapsackRepository provide abstraction for repository implementations.
    
    KnapsackProblemFileRepository provides access to file problem repository. executeOnAllEntries can work on files with large number of lines.

* ProblemParser
    
    ProblemParser provides abstraction for problem parsers.
    
    RegexProblemParser uses Regex and uses caching group feature to parse and extract a problem.
    
## Build 

Gradle is on charge for this project.
To build this project on the root of the project run this command:

    $ ./gradlew run
    
## Wish List
* Volume test

This project is designed to work on files with heavy lines. 
However if the number of entries on each problem could be huge as well, 
we need to address this on ProblemParser(It is now handled in PackerLogic)
