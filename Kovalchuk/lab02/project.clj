(defproject lab02 "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "MIT License"
            :url "http://www.opensource.org/licenses/mit-license.php"}
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :main ^:skip-aot lab02.main
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})