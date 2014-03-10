(ns viewer.model.race
  (:require [clojure.java.jdbc :as sql]))

(def spec (or (System/getenv "DATABASE_URL")
              "postgresql://localhost:5432/evedata"))

(defn all []
  (into
   []
   (sql/query
    spec
    ["select * from \"chrRaces\""])))
