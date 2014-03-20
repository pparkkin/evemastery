(ns viewer.model.ship
  (:require [clojure.java.jdbc :as sql]))

(def spec (or (System/getenv "DATABASE_URL")
              "postgresql://localhost:5432/evedata"))



(defn query-ship-info
  ([typeid]
     (cond (instance? Number typeid)
           (first (sql/query
                   spec
                   [(format "select * from \"invTypes\"
                              where \"typeID\" = %d" typeid)]))
           (instance? String typeid)
           (first (sql/query
                   spec
                   [(format "select * from \"invTypes\"
                              where \"typeName\" = '%s'" typeid)]))))
  ([]
  ;; select * from \"invTypes\" t
  ;;  join \"invGroups\" g on t.\"groupID\" = g.\"groupID\"
  ;;  join \"invCategories\" c on g.\"categoryID\" = c.\"categoryID\"
  ;;   where c.\"categoryID\" = 6
     (sql/query spec
                ["select * from \"invTypes\" t
                   join \"invGroups\" g on t.\"groupID\" = g.\"groupID\"
                   join \"invCategories\" c on g.\"categoryID\" = c.\"categoryID\"
                    where c.\"categoryID\" = 6 and t.\"published\" = true"])))

(defn ship-typeid [name]
  (:typeid (query-ship-info name)))

(defn ship-info [typeid]
  (cond (instance? Number typeid) (query-ship-info typeid)
        (instance? String typeid) (query-ship-info typeid)))

(defn all []
  (query-ship-info))
