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
  ;; (GET ["/masteries/:typeid", :typeid #"[0-9]+"] [typeid]
  ;;      (json-response (mastery/all (read-string typeid))))
  ;; (GET ["/ship/:typeid", :typeid #"[0-9]+"] [typeid]
  ;;      (json-response (ship/ship-info (read-string typeid))))
  ;; (GET ["/ship/:shipname", :shipname #"[a-zA-Z ]+"] [shipname]
  ;;      (json-response (ship/ship-info shipname)))
  ;; (GET ["/skills/:certid/:level", :certid #"[0-9]+", :level #"[0-9]+"] [certid level]
  ;;      (json-response (certificate/list-skills (read-string certid) (read-string level))))
  )

