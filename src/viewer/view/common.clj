(ns viewer.view.common
  (:require [hiccup.page :as page]
            [hiccup.util :as util]))

(defn not-found []
  (page/html5
   [:head
    (page/include-js "/javascript/analytics.js")]
   [:body
    [:h1 "Could not find the page you were looking for. Sorry."]]))
