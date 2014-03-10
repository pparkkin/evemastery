(ns viewer.model.mastery
  (:require [clojure.java.jdbc :as sql]))

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
    [(format "select t.\"typeName\", m.\"masteryLevel\", c.\"name\"
               from \"certMasteries\" m
               join \"invTypes\" t on m.\"typeID\" = t.\"typeID\"
               join \"certCerts\" c on m.\"certID\" = c.\"certID\"
               where m.\"typeID\" = %d" typeid)])))

(defn all [typeid]
  (into {} (for [[k v] (group-by :typename (query typeid))]
             [k (group-by :masterylevel v)])))

(defn all-r []
  (reduce #(assoc %1 (:typename %2) (conj (%1 (:typename %2)) (dissoc %2 :typename)))
          {}
          (query)))
