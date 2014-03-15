(ns viewer.model.certificate
  (:require [clojure.java.jdbc :as sql]))

(def spec (or (System/getenv "DATABASE_URL")
              "postgresql://localhost:5432/evedata"))

;;; select t.\"typeName\", c.\"skillLevel\" from \"certSkills\" c join \"invTypes\" t on c.\"skillID\" = t.\"typeID\" where c.\"certID\" = 50 and \"certLevelInt\" = 3
(defn query-list-skills [certid level]
  (into
   []
   (sql/query
    spec
    [(format "select t.\"typeID\", t.\"typeName\", c.\"skillLevel\" from \"certSkills\" c join \"invTypes\" t on c.\"skillID\" = t.\"typeID\" where c.\"certID\" = %d and \"certLevelInt\" = %d" certid level)])))

(defn list-skills [certid level]
  (filter (fn [s] (> (:skilllevel s) 0)) (query-list-skills certid level)))
