(ns viewer.core
  (:require [ring.adapter.jetty :as ring]
            [compojure.core :refer [defroutes context]]
            [compojure.route :as route]
            [viewer.controller.api :as api]
            [viewer.controller.web :as web]
            [appengine-magic.core :as ae]))

(defroutes routes
  (context "/api" [] api/routes)
  web/routes
  (route/resources "/"))

(ae/def-appengine-app viewer-app #'routes)
