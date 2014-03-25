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

;; <entry skillID="3330" skill="Caldari Frigate" level="1"/>
(defn skills-as-xml [ss]
  (map (fn [s] {:tag :entry
               :attrs {:skillID (:typeid s)
                       :skill (:typename s)
                       :level (:skilllevel s)}})
       ss))

(defn masteries-as-xml [ms]
  (let [skills (reduce (fn [ss [l m]]
                         (reduce (fn [sss c] (add-skills sss (:skills c))) ss m))
                       (new-skill-list)
                       ms)]
    (skills-as-xml (list-skills skills))))

;; <plan xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" name="Condor - Medium" revision="4016">
(defn as-xml [shipname level]
  (let [masteries (-> (ship/get-ship shipname) :data :masteries)]
    {:tag :plan
     :attrs {:xmlns:xsi "http://www.w3.org/2001/XMLSchema-instance"
             :xmlns:xsd "http://www.w3.org/2001/XMLSchema"
             :name (format "%s - %s" shipname level)}
     :content (masteries-as-xml
               (filter (fn [[k v]] (<= k (roman-to-masterylevel level)))
                       masteries))}))



