(defproject viewer "0.1.0-SNAPSHOT"
  :description "EVE Online Data Dump Viewer"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0-RC1"]
                 [ring "1.2.2"]
                 [compojure "1.1.6"]
                 [org.clojure/data.json "0.2.4"]
                 [hiccup "1.0.4"]
                 [appengine-magic "0.5.1-SNAPSHOT"]]
  :plugins [[appengine-magic "0.5.1-SNAPSHOT"]])
