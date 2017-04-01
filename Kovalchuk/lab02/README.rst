=============
Parallel Java
=============

Overview
========

These are my assignments for Parallel Programing in Java university course.

As the course focused on classes and methods JVM provided for multi-threading
programming, assignments could be implemented in any JVM-supported language.

For these assignments a Clojure_ language has been chosen. I have used 
the following techniques:

- simple threads with semaphores for synchronization
- clojure futures
- clojure delays
- thread pool executor
- blocking queue


Running
=======

This projects uses Leiningen_ as a build system. To compile a project just execute

.. code-block:: 
   bash

   lein uberjar


This will create an executable jar in target/uberjar/lab02-0.1.0-SNAPSHOT-standalone.jar

Then you should manually create output directories for every version you want to run, otherwise the application will fail with exception:

.. code-block::
   bash

   mkdir -p out/{bq,del,exc,fut,sem}

Then you can run an executable jar with the following command:

.. code-block::
   bash

   java -jar target/uberjar/lab02-0.1.0-SNAPSHOT-standalone.jar "command"

where ``command`` is one of the following:

:gen:
  generates input data in the in/ directory
:sem:
  runs computations using simple threads and semaphores
:bq:
  runs computations using blocking queues
:fut:
  runs computations using clojure's futures
:del:
  runs computations using clojure's delays
:exc:
  runs computations using thread pool executor

.. _Clojure: https://clojure.org
.. _Leiningen: https://leiningen.org/
