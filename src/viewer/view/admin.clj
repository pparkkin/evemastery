(ns viewer.view.admin
  (:require [hiccup.page :as page]
            [hiccup.util :as util]))


(defn datafile-upload-form []
  (page/html5
   [:head
    [:title "Upload new data file"]]
   [:body
    [:h1 "Upload new data file"]
    [:form {:method "post"}
     [:input {:name "stuff" :type "text"}]]]))

(defn datafile-upload []
  (page/html5
   [:head
    [:title "Thank you"]]
   [:body
    [:h1 "Thanks!"]]))
