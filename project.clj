(defproject viewer "0.1.0-SNAPSHOT"
  :description "EVE Online Data Dump Viewer"
  :url "http://evemastery.appspot.com/"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0-RC1"]
                 [org.clojure/java.jdbc "0.3.2"]
                 [postgresql "9.1-901.jdbc4"]
                 [ring "1.2.2"]
                 [compojure "1.1.6"]
                 [org.clojure/data.json "0.2.4"]
                 [hiccup "1.0.4"]
                 [appengine-magic "0.5.1-SNAPSHOT"]]
  :plugins [[appengine-magic "0.5.1-SNAPSHOT"]])
