(ns viewer.model.mastery
  (:require [clojure.java.jdbc :as sql]
            [viewer.model.certificate :as certificate]))

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

(defn all-bak [typeid]
  (first ; There should only be one, but anyway, just take one
   (into [] (for [[k v] (group-by :typename (query typeid))]
              [k (group-by :masterylevel v)]))))
