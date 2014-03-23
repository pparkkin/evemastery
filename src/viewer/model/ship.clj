(ns viewer.model.ship
  (:require [appengine-magic.services.datastore :as ds]
            [clojure.string :refer [upper-case]]))

(ds/defentity Ship [^{:tag :key} name, ^{:tag :clj} data])

(defn delete-all []
  (ds/delete!
   (ds/query :kind Ship)))

(defn put-ship [name data]
  (let [s (Ship. (upper-case name) (ds/as-text (pr-str data)))]
    (ds/save! s)
    name))

(defn update-db [ships]
  (delete-all)
  (map (fn [s] (put-ship (-> s :ship :typename) s))
       ships))

(defn get-ship [name]
  (let [ship (first (ds/query :kind Ship :filter (= :name (upper-case name))))]
    (if (not (nil? ship))
      {:name (:name ship) :data (read-string (.getValue (:data ship)))})))


