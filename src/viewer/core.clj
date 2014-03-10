(ns viewer.core
  (:require [ring.adapter.jetty :as ring]
            [viewer.controller.web :as web]))

(defn -main []
  (ring/run-jetty #'web/routes {:port 8080 :join? false}))
