(ns viewer.core
  (:require [ring.adapter.jetty :as ring]
            [compojure.core :refer [defroutes context]]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [viewer.controller.api :as api]
            [viewer.controller.web :as web]))

(defroutes routes
  (context "/api" [] api/routes)
  web/routes
  (route/resources "/"))

(def app
  (handler/site routes))

(defn -main []
  (ring/run-jetty #'routes {:port 8080 :join? false}))
