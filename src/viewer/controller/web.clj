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

(defn xml-response [filename content]
  {:status 200
   :headers {"Content-Type" "application/xml"
             "Content-Disposition" (format "attachment; filename=\"%s\"" filename)}
   :body content})

;;; ["/user/:id", :id #"[0-9]+"]
(defroutes routes
  (GET "/masteries/:shipname" [shipname]
       (let [ship (:data (ship/get-ship shipname))]
         (when ship
           (mastery-view/render (ship :ship)
                                (ship :masteries)))))
  (GET "/masteries/xml/:filename" [filename]
       (let [[ship level] (parse-xml-filename filename)]
         (xml-response filename (with-out-str (xml/emit-element (mastery/as-xml ship level)))))))

