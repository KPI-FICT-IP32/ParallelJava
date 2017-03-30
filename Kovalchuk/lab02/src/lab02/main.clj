(ns lab02.main
  (:require [lab02.sync.semaphore :as sem]
            [lab02.sync.blocking-queue :as bq]
            [lab02.sync.futures :as fut]
            [lab02.sync.delays :as del]
            [lab02.sync.executor :as exc]
            [lab02.generators :as gen])
  (:gen-class))

(defn -main
  [& args]
  (let [task (first args)]
    (case task
      "gen" (gen/run)
      "sem" (sem/run)
      "bq" (bq/run)
      "fut" (fut/run)
      "del" (del/run)
      "exc" (exc/run)
      (prn (str "Unknown task: " task)))))
