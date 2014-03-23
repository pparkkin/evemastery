(ns viewer.view.search
  (:require [hiccup.page :as page]
            [hiccup.util :as util]))

(defn render []
  (page/html5
   [:head
    (page/include-css "/stylesheets/search.css")
    (page/include-js "/javascript/analytics.js")
    [:title "Search for it."]]
   [:body
    [:div#content
     [:h1 "Search by ship name"]
     [:form
      [:input {:type "text" :name "q"}]
      [:input {:type "submit" :value "Search"}]]]]))
