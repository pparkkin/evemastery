(ns viewer.view.mastery
  (:require [hiccup.core :as h]))

(defn render-masteries [[k v]]
  [:div
   [:h2 k]
   (map (fn [m] [:div (:name m)]) v)])

(defn render-ship [[k v]]
  [:div
   [:h1 k]
   (map render-masteries v)])

(defn render [ms]
  (h/html (map render-ship ms)))
