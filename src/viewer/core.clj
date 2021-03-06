(ns viewer.core
  (:require [compojure.core :refer [defroutes context]]
            [compojure.route :as route]
            [viewer.controller.api :as api]
            [viewer.controller.web :as web]
            [viewer.controller.admin :as admin]
            [viewer.view.common :as common]
            [appengine-magic.core :as ae]))

(defroutes routes
  (context "/api" [] api/routes)
  (context "/admin" [] admin/routes)
  web/routes
  (route/resources "/")
  (route/not-found (common/not-found)))

(ae/def-appengine-app viewer-app #'routes)
