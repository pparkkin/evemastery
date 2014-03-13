(ns viewer.view.mastery
  (:require [hiccup.page :as page]))

(defn render-mastery [[level certs]]
  [:div.mastery
   [:h2.mastery-level level]
   [:ul.certificates
    (map (fn [m] [:li.certificate
                 (:name m)
                 [:ul.skills
                  (map (fn [s] [:li.skill (format "%s - %d" (:typename s) (inc (:skilllevel s)))])
                       (:skills m))]])
         certs)]])

(defn render-ship [ship masteries]
  [:div.ship
   [:div.ship-info
    [:h1.ship-name (:typename ship)]
    [:div.ship-desc (clojure.string/replace (:description ship) #"\n" "<br>")]]
   [:div.masteries
    (map render-mastery masteries)]])

(defn render [si ms]
  (let [ship-name (:typename si)
        masteries ms]
    (page/html5
     [:head
      [:title (format "Masteries - %s" ship-name)]
      (page/include-css "/stylesheets/mastery.css")]
     [:body
      (render-ship si masteries)])))
