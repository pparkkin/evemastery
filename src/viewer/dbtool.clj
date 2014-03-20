(ns viewer.dbtool
  (:require [clojure.java.jdbc :as sql]))

(def spec (or (System/getenv "DATABASE_URL")
              "postgresql://localhost:5432/evedata"))

(defn ships []
   (sql/query spec
                ["select * from \"invTypes\" t
                   join \"invGroups\" g on t.\"groupID\" = g.\"groupID\"
                   join \"invCategories\" c on g.\"categoryID\" = c.\"categoryID\"
                    where c.\"categoryID\" = 6 and t.\"published\" = true"]))

(defn skills-for-certificate [certid level]
  (filter (fn [s] (> (:skilllevel s) 0))
          (into
           []
           (sql/query
            spec
            [(format "select t.\"typeID\", t.\"typeName\", c.\"skillLevel\"
                       from \"certSkills\" c
                        join \"invTypes\" t on c.\"skillID\" = t.\"typeID\"
                       where c.\"certID\" = %d and \"certLevelInt\" = %d" certid level)]))))

(defn masteries [typeid]
  (group-by :masterylevel
            (map (fn [m] (assoc m :skills (skills-for-certificate (:certid m) (:masterylevel m))))
                 (into
                  []
                  (sql/query
                   spec
                   [(format "select m.\"masteryLevel\", m.\"certID\", c.\"name\"
                              from \"certMasteries\" m
                               join \"invTypes\" t on m.\"typeID\" = t.\"typeID\"
                               join \"certCerts\" c on m.\"certID\" = c.\"certID\"
                              where m.\"typeID\" = %d" typeid)])))))

(defn dump-csv [f]
  (spit f (pr-str (map (fn [s] {:ship s :masteries (masteries (:typeid s))})
                       (ships)))))

(defn -main [& args]
  (dump-csv (first args)))
