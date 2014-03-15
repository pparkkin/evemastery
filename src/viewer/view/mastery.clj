(ns viewer.view.mastery
  (:require [hiccup.page :as page]
            [hiccup.util :as util]))

(def roman-numerals
  {1 "I", 2 "II", 3 "III", 4 "IV", 5 "V"})

(defn mastery-level-heading [level]
  (roman-numerals (inc level)))

(defn render-mastery [ship [level certs]]
  [:div.mastery
   [:a.mastery-xml {"href" (format "xml/%s - %s.xml" ship (mastery-level-heading level))} "XML"]
   [:h2.mastery-level (mastery-level-heading level)]
   [:ul.certificates
    (map (fn [m] [:li.certificate
                 [:h3.certificate (:name m)]
                 [:ul.skills {"style" "display: none;"}
                  (map (fn [s] [:li.skill (format "%s - %d" (:typename s) (:skilllevel s))])
                       (:skills m))]])
         certs)]])

(defn remove-html [str]
  (clojure.string/replace str #"<[^>]+>" ""))

(defn render-ship [ship masteries]
  [:div.ship
   [:div.ship-info
    [:h1.ship-name (:typename ship)]
    [:div.ship-desc (clojure.string/replace (remove-html (:description ship)) #"\n" "<br>")]]
   [:div.masteries
    (map (partial render-mastery (:typename ship)) masteries)]])

(defn render [si ms]
  (let [ship-name (:typename si)
        masteries ms]
    (page/html5
     [:head
      [:title (format "Masteries - %s" ship-name)]
      (page/include-css "/stylesheets/mastery.css")
      (page/include-js "//ajax.googleapis.com/ajax/libs/jquery/2.1.0/jquery.min.js")
      (page/include-js "/javascript/mastery.js")]
     [:body
      (render-ship si masteries)])))
