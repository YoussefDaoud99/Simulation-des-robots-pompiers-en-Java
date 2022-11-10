
Ensimag 2A POO - TP 2020
============================

### Context

This project is done as part of my training at Grenoble Inp - Ensimag in a team of 3 people.

The Java project involves developing an application to simulate a team of firefighter robots operating autonomously in a natural environment. To do so, we must define the different elements for our simulation, namely:

- **A map** represented by a matrix of boxes which are characterized by their coordinates and the nature of the terrain which varies between forest, habitat, rock, open or water.

- **A fire** that is defined by the space on which it is located and the number of liters of water needed to extinguish it. On the other hand, in our simulation, we do not take into account the spread of fires.

- **The robots** are "elementary firefighters" who can move around, pour water on a fire and fill their tank. In addition, the extinction of fires is not done in a random way but it is necessary to have a strategy which makes it possible to optimize the time necessary to extinguish all the fires. To do so, we used the optimization methods that we used in the operational research course, namely the calculation of the shortest path. So, me and our team chose to implement **Dijkstra's algorithm** since we have no negative cost.

### Guide

- src: contains the classes developed for our simulation.

  -> LecteurDonnees.java         : reads all elements of a data description file (tiles, fires and robots) and displays them.
  -> TestLecteurDonnees.java     : reads a data file and displays its contents.
  -> TestInvader                 : creates a "mini Invaders" simulator in a graphics window


- cartes: some examples of data files

- bin/gui.jar: Java archive containing the classes of the graphical interface. See example usage in TestInvader.java

- doc: the documentation (API) of the GUI classes contained in gui.jar. Entry point: index.html

- Makefile: some explanations on online compilation, in particular the concept of classpath and the use of gui.jar

-> Jdoc: contains the Javadoc generated documentation of the project.

To Test the simulation go to src/Simulation/TestGraphique , you will find 2 strategies, elementary and advanced, just uncomment the strategy of your choice and execute :)
