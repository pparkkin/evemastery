(ns viewer.view.mastery
  (:require [hiccup.page :as page]))

(defn render-mastery [[level certs]]
  [:div.mastery
   [:h2.mastery-level level]
   [:ul.certificates
    (map (fn [m] [:li.certificate (:name m)])
         certs)]])

(defn render-ship [ship masteries]
  [:div.ship
   [:div.ship-info
    [:h1.ship-name ship]]
   [:div.masteries
    (map render-mastery masteries)]])

(defn render [ms]
  (let [ship-name (first ms)
        masteries (second ms)]
    (page/html5
     [:head
      [:title (format "Masteries - %s" ship-name)]
      (page/include-css "/stylesheets/mastery.css")]
     [:body
      (render-ship ship-name masteries)])))
