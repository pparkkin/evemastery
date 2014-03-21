(ns viewer.controller.admin
  (:require [compojure.core :refer [defroutes GET POST]]
            [appengine-magic.multipart-params :refer [wrap-multipart-params]]
            [viewer.view.admin :as view]
            [viewer.model.ship :as ship]))

(defn upload-datafile [file]
  (let [ships (read-string (apply str (map #(char (bit-and % 255)) (:bytes file))))]
    (ship/update-db ships)))

(defroutes routes
  (GET "/" []
       "Hello, Wold!")
  (GET "/datafile/upload" []
       (view/datafile-upload-form))
  (POST "/datafile/upload" []
        (wrap-multipart-params
         (fn [req]
           (view/datafile-upload (upload-datafile ((req :params) "file")))))))

