(ns viewer.view.search
  (:require [hiccup.page :as page]
            [hiccup.util :as util]))

(defn render []
  (page/html5
   [:head
    [:title "Search for it."]]
   [:body
    [:h1 "Search by ship name"]
    [:form
     [:input {:type "text" :name "q"}]
     [:input {:type "submit" :value "Search"}]]]))
