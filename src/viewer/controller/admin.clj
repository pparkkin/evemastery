(ns viewer.controller.admin
  (:require [compojure.core :refer [defroutes GET POST]]
            [appengine-magic.multipart-params :refer [wrap-multipart-params]]
            [viewer.view.admin :as view]))

(defn upload-datafile [params]
  )

(defroutes routes
  (GET "/" []
       "Hello, Wold!")
  (GET "/datafile/upload" []
       (view/datafile-upload-form))
  (wrap-multipart-params
   (POST "/datafile/upload" {params :params}
         (upload-datafile params)
         (view/datafile-upload))))

