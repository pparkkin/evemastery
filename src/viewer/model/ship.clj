(ns viewer.model.ship
  (:require [clojure.java.jdbc :as sql]))

(def spec (or (System/getenv "DATABASE_URL")
              "postgresql://localhost:5432/evedata"))

(def ships-market-group-id 4)

(defn market-groups
  ([]
     (market-groups ships-market-group-id))
  ([parent]
     (mapcat
      (fn [mg] (conj (market-groups (:marketgroupid mg)) mg))
      (into
       []
       (sql/query
        spec
        [(format "select * from \"invMarketGroups\" where \"parentGroupID\" = %d" parent)])))))

(defn all []
  (map (fn [mg]
         {:name
          (:marketgroupname mg)
          :ships
          (into []
                (sql/query
                 spec
                 [(format "select * from \"invTypes\" where \"marketGroupID\" = %d"
                          (:marketgroupid mg))]))})
       (market-groups)))
