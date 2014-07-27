(defproject mechanics "0.1.0-SNAPSHOT"
  :description "Scmutils in Clojure"
  :url "http://github.com/aamedina/mechanics"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [criterium "0.4.3"]]
  :main ^:skip-aot mechanics.core
  :profiles {:uberjar {:aot :all}})
