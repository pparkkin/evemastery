(ns viewer.core
  (:require [ring.adapter.jetty :as ring]
            [compojure.core :refer [defroutes context]]
            [viewer.controller.api :as api]
            [viewer.controller.web :as web]))

(defroutes routes
  (context "/api" [] api/routes)
  web/routes)

(defn -main []
  (ring/run-jetty #'routes {:port 8080 :join? false}))
