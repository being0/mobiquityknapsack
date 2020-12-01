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

        Packer is the starting class. it configs and injects dependencies. It would be an enhancement to use IOC framework like Spring to do the configurations and setup there.
  