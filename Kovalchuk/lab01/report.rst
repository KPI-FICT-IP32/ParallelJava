====================================
Багатопотокове програмування на Java
====================================
---------------------------------------
Лабораторна робота №1 (Thread/Runnable) 
---------------------------------------

:Виконав:
  студент групи ІП-32
  *Ковальчук О. М.*
:Перевірив:
  доцент кафедри ОТ
  *Долголенко О.М.*

Завдання
========
Написати програму мовою Java для обчислення чотирьох математичних функцій

Варіант 12
    #. А=В*МС+D*MZ+E*MM;
    #. D=В*МZ-E*MM*a;
    #. MА=MD*(MT+MZ)-ME*MM;
    #. MG=min(D+C)*MD*MT-MZ*ME.

Виконання
=========

Лістинг
-------

Запуск задач на виконання:

.. code-block:: java

      @SneakyThrows(InterruptedException.class)
      static void runThreads() {
          Flow fl = new Flow(new NormalFunctions());

          Thread[] ts = new Thread[4];

          ts[0] = new Thread(fl::flow1);
          ts[1] = new Thread(fl::flow2);
          ts[2] = new Thread(fl::flow3);
          ts[3] = new Thread(fl::flow4);

          System.out.println("Ready to start");
          for (Thread t : ts) t.start();
          for (Thread t : ts) t.join();
          System.out.println("Finished");
      }

Опис сценарію виконання потоку (на прикладі сценарію обчислення першої функції):

.. code-block:: java

    public void flow1() {
        System.out.printf("%d [Flow 1] started\n", System.currentTimeMillis());
        double[] vb = readVector("data/vb.in");
        double[][] mc = readMatrix("data/mc.in");
        double[] vd = readVector("data/vd.in");
        double[][] mz = readMatrix("data/mz.in");
        double[] ve = readVector("data/ve.in");
        double[][] mm = readMatrix("data/mm.in");

        double[] va = funcs.func1(vb, mc, vd, mz, ve, mm);
        write(va, "out/va.out");
        System.out.printf("%d [Flow 1] finished\n", System.currentTimeMillis());
    }


Опис функцій:

.. code-block:: java

  public class NormalFunctions implements Functions {
      public double[] func1(
          double[] vb, double[][] mc,
          double[] vd, double[][] mz,
          double[] ve, double[][] mm) {
          return add(
              add(multiply(vb, mc),
                  multiply(vd, mz)),
              multiply(ve, mm));
      }

      public double[] func2(
          double[] vb, double[][] mz,
          double[] ve, double[][] mm, double a) {
          return subtract(multiply(vb, mz),
                          multiply(multiply(ve, mm), a));
      }

      public double[][] func3(
          double[][] md, double[][] mt, double[][] mz,
          double[][] me, double[][] mm) {
          return subtract(
              multiply(md, add(mt, mz)),
              multiply(me, mm));
      }

      public double[][] func4(
          double[] vd, double[] vc, double[][] md, double[][] mt,
          double[][] mz, double[][] me) {
          return subtract(
              multiply(
                  multiply(min(add(vd, vc)), md),
                  mt),
              multiply(mz, me));
      }
  }

Лог виконання програми
----------------------

.. code-block::

  Ready to start
  1488364667156 [Flow 1] started
  1488364667157 [Flow 4] started
  1488364667157 [Flow 3] started
  1488364667157 [Flow 2] started
  1488364672228 [Writer] out/vd.out written
  1488364672232 [Flow 2] finished
  1488364674078 [Writer] out/va.out written
  1488364674078 [Flow 1] finished
  1488364709282 [Writer] out/mg.out written
  1488364709283 [Flow 4] finished
  1488364711374 [Writer] out/ma.out written
  1488364711374 [Flow 3] finished
  Finished

Засікаємо час виконання:

.. code-block:: sh

  #!/bin/sh

  die() { echo "$@" 1>&2 ; exit 1; }

  APP="lab01-1.0.0-jar-with-dependencies.jar"

  START=$(date +"%s%N")

  java -jar $APP

  END=$(date +"%s%N")

  RES=$(echo "$END - $START" | bc)
  echo "$RES ns"

У результаті отримаємо час виконання :code:`44459467551 ns`
