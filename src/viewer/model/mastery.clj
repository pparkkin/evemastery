(ns viewer.model.mastery
  (:require [clojure.java.jdbc :as sql]
            [viewer.model.certificate :as certificate]
            [viewer.model.ship :as ship]))

(def spec (or (System/getenv "DATABASE_URL")
              "postgresql://localhost:5432/evedata"))

;;; certMasteries
;;;      typeID -> invTypes (e.g. 582)
;;;      masteryLevel
;;;      certID -> certCerts

;;; invTypes
;;;      typeID
;;;      typeName

;;; certCerts
;;;      certID
;;;      name


(defn query [typeid]
  (into
   []
   (sql/query
    spec
    [(format "select m.\"masteryLevel\", m.\"certID\", c.\"name\"
               from \"certMasteries\" m
               join \"invTypes\" t on m.\"typeID\" = t.\"typeID\"
               join \"certCerts\" c on m.\"certID\" = c.\"certID\"
               where m.\"typeID\" = %d" typeid)])))

(defn all [typeid]
  (group-by :masterylevel
            (map (fn [m] (assoc m :skills (certificate/list-skills (:certid m) (:masterylevel m))))
                 (query typeid))))

(defn roman-to-masterylevel [r]
  ({"I" 0 "II" 1 "III" 2 "IV" 3 "V" 4} r))

;; <entry skillID="3330" skill="Caldari Frigate" level="1"/>
(defn skills-as-xml [ss]
  (map (fn [s] {:tag :entry
               :attrs {:skillID (:typeid s)
                       :skill (:typename s)
                       :level (:skilllevel s)}})
       ss))

(defn masteries-as-xml [ms]
  ;; Certs inside masteries, skills inside certs
  (mapcat (fn [[l m]]
            (mapcat (fn [c] (skills-as-xml (:skills c))) m))
          ms))

;; <plan xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" name="Condor - Medium" revision="4016">
(defn as-xml [shipname level]
  (let [typeid (ship/ship-typeid shipname)]
    {:tag :plan
     :attrs {:xmlns:xsi "http://www.w3.org/2001/XMLSchema-instance"
             :xmlns:xsd "http://www.w3.org/2001/XMLSchema"
             :name (format "%s - %s" shipname level)}
     :content (masteries-as-xml
               (filter (fn [[k v]] (<= k (roman-to-masterylevel level)))
                       (all typeid)))}))

(defn all-bak [typeid]
  (first ; There should only be one, but anyway, just take one
   (into [] (for [[k v] (group-by :typename (query typeid))]
              [k (group-by :masterylevel v)]))))
