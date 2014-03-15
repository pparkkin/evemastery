(ns viewer.model.ship
  (:require [clojure.java.jdbc :as sql]))

(def spec (or (System/getenv "DATABASE_URL")
              "postgresql://localhost:5432/evedata"))

(defn query-ship-info [typeid]
  (first (sql/query spec
                    [(format "select * from \"invTypes\" where \"typeID\" = %d" typeid)])))

(defn ship-typeid [name]
  (:typeid
   (first (sql/query spec
                     [(format "select * from \"invTypes\" where \"typeName\" = '%s'" name)]))))

(defn ship-info [typeid]
  (cond (instance? Number typeid) (query-ship-info typeid)
        (instance? String typeid) (query-ship-info (ship-typeid typeid))))
