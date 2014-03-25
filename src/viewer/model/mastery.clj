(ns viewer.model.mastery
  (:require [viewer.model.certificate :as certificate]
            [viewer.model.ship :as ship]))

(defn roman-to-masterylevel [r]
  ({"I" 0 "II" 1 "III" 2 "IV" 3 "V" 4} r))

(defn new-skill [id name level]
  {:skilllevel level :typename name :typeid id})

(defn new-skill-list []
  {:s #{} :l []})

(defn add-skill [list skill]
  (if (contains? (:s list) skill)
    list
    {:s (conj (:s list) skill)
     :l (conj (:l list) skill)}))

(defn add-skills [list skills]
  (reduce (fn [l s] (add-skill l s))
          list
          skills))

(defn list-skills [list]
  (:l list))

;; <entry skillID=\"3330\" skill=\"Caldari Frigate\" level=\"1\"></entry>
(defn skills-as-xml [ss]
  (map (fn [s] (format "            <entry skillID=\"%d\" skill=\"%s\" level=\"%d\"></entry>\n"
                      (:typeid s)
                      (:typename s)
                      (:skilllevel s)))
       ss))

(defn masteries-as-xml [ms]
  (let [skills (reduce (fn [ss [l m]]
                         (reduce (fn [sss c] (add-skills sss (:skills c))) ss m))
                       (new-skill-list)
                       ms)]
    (skills-as-xml (list-skills skills))))

; <plan xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" name=\"Condor - Expert\" revision=\"4016\">
(defn xml-head [name]
  (let [xsi "http://www.w3.org/2001/XMLSchema-instance"
        xsd "http://www.w3.org/2001/XMLSchema"
        revision "4016"]
    ["<?xml version=\"1.0\"?>"
     (format "<plan xmlns:xsi=\"%s\" xmlns:xsd=\"%s\" name=\"%s\" revision=\"%s\">\n"
             xsi xsd name revision)]))

;; <plan xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" name="Condor - Medium" revision="4016">
(defn as-xml [shipname level]
  (let [masteries (-> (ship/get-ship shipname) :data :masteries)
        head (xml-head (format "%s - %s" shipname level))
        tail ["</plan>\n"]]
    (apply str (concat head
                       (masteries-as-xml
                        (filter (fn [[k v]] (<= k (roman-to-masterylevel level)))
                                masteries))
                       tail))))



