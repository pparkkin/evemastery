(ns viewer.view.admin
  (:require [hiccup.page :as page]
            [hiccup.util :as util]))

(defn unauthorized [login]
  (page/html5
   [:head
    [:title "Unauthorized"]]
   [:body
    [:h1 "You are not authorized to access this. Please "
     [:a {:href login} "login"]
     " first."]]))

(defn datafile-upload-form []
  (page/html5
   [:head
    [:title "Upload new data file"]]
   [:body
    [:h1 "Upload new data file"]
    [:form {:method "post" :enctype "multipart/form-data"}
       [:input {:name "file" :type "file"}]
       [:input {:type "submit" :name "submit" :value "Upload file"}]]]))

(defn datafile-upload [result]
  (page/html5
   [:head
    [:title "Thank you"]]
   [:body
    [:h1 "Thanks!"]
    [:pre (pr-str result)]]))
