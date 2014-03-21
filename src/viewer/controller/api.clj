(ns viewer.controller.api
  (:require [compojure.core :refer [defroutes GET]]
            [clojure.data.json :as json]
            [viewer.model.mastery :as mastery]
            [viewer.model.ship :as ship]
            [viewer.model.certificate :as certificate]))

(defn json-response [data]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (json/write-str data)})

;;; ["/user/:id", :id #"[0-9]+"]
(defroutes routes
  (GET ["/masteries/:shipname", :shipname #"[a-zA-Z ]+"] [shipname]
       (json-response (-> (ship/get-ship shipname) :data :masteries))))

