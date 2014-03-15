(ns viewer.controller.web
  (:require [compojure.core :refer [defroutes GET]]
            [clojure.data.json :as json]
            [clojure.xml :as xml]
            [viewer.model.mastery :as mastery]
            [viewer.model.ship :as ship]
            [viewer.view.mastery :as mastery-view]))

(def xml-filename-pattern #"(\S+) - (\S+).xml")

(defn parse-xml-filename [fn]
  (rest (re-find xml-filename-pattern fn)))

(defn xml-response [content]
  {:status 200
   :headers {"Content-Type" "application/xml"}
   :body content})

;;; ["/user/:id", :id #"[0-9]+"]
(defroutes routes
  (GET ["/masteries/:typeid", :typeid #"[0-9]+"] [typeid]
       (mastery-view/render (ship/ship-info (read-string typeid))
                            (mastery/all (read-string typeid))))
  (GET ["/masteries/:shipname", :shipname #"[a-zA-Z ]+"] [shipname]
       (let [ship-info (ship/ship-info shipname)]
         (mastery-view/render ship-info
                              (mastery/all (:typeid ship-info)))))
  (GET "/masteries/xml/:filename" [filename]
       (let [[ship level] (parse-xml-filename filename)]
         (xml-response (with-out-str (xml/emit-element (mastery/as-xml ship level)))))))

