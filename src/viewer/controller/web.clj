(ns viewer.controller.web
  (:require [compojure.core :refer [defroutes GET]]
            [clojure.data.json :as json]
            [viewer.model.mastery :as mastery]
            [viewer.model.ship :as ship]
            [viewer.view.mastery :as mastery-view]))

;;; ["/user/:id", :id #"[0-9]+"]
(defroutes routes
  (GET ["/masteries/:typeid", :typeid #"[0-9]+"] [typeid]
       (mastery-view/render (ship/ship-info (read-string typeid))
                            (mastery/all (read-string typeid))))
  (GET ["/masteries/:shipname", :shipname #"[a-zA-Z ]+"] [shipname]
       (let [ship-info (ship/ship-info shipname)]
         (mastery-view/render ship-info
                              (mastery/all (:typeid ship-info))))))

