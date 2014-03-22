(ns viewer.controller.admin
  (:require [compojure.core :refer [defroutes GET POST]]
            [ring.util.request :as request]
            [appengine-magic.multipart-params :refer [wrap-multipart-params]]
            [appengine-magic.services.user :as user]
            [viewer.view.admin :as view]
            [viewer.model.ship :as ship]))

(defn upload-datafile [file]
  (let [ships (read-string (apply str (map #(char (bit-and % 255)) (:bytes file))))]
    (ship/update-db ships)))

(defn require-admin [stuff redir]
  (if (or (nil? (user/current-user)) (not (user/user-admin?)))
    (view/unauthorized (user/login-url :destination redir))
    (stuff)))

(defroutes routes
  (GET "/datafile/upload" [:as req]
       (require-admin #(view/datafile-upload-form)
                     (request/request-url req)))
  (POST "/datafile/upload" []
        (wrap-multipart-params
         (fn [req]
           (require-admin #(view/datafile-upload (upload-datafile ((req :params) "file")))
                          (request/request-url req))))))

