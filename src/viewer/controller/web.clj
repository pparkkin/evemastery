(ns viewer.controller.web
  (:require [compojure.core :refer [defroutes GET]]
            [clojure.data.json :as json]
            [viewer.model.mastery :as mastery]
            [viewer.view.mastery :as mastery-view]))

;;; ["/user/:id", :id #"[0-9]+"]
(defroutes routes
  (GET ["/masteries/:typeid", :typeid #"[0-9]+"] [typeid]
       (mastery-view/render (mastery/all (read-string typeid)))))

