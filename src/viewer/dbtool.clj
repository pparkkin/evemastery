(ns viewer.dbtool
  (:require [viewer.model.ship :as ship]
            [viewer.model.mastery :as mastery]))

(defn dump-csv [f]
  (spit f (pr-str (map (fn [s] {:ship s :masteries (mastery/all (:typeid s))}) (ship/all)))))

(defn -main [& args]
  (dump-csv (first args)))
