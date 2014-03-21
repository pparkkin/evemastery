(ns viewer.model.ship
  (:require [appengine-magic.services.datastore :as ds]))

(ds/defentity Ship [^{:tag :key} name, ^{:tag :clj} data])

(defn delete-all []
  (ds/delete!
   (ds/query :kind Ship)))

(defn put-ship [name data]
  (let [s (Ship. name data)]
    (ds/save! s)
    name))

(defn update-db [ships]
  (delete-all)
  (map (fn [s] (put-ship (-> s :ship :typename) s))
       ships))

(defn get-ship [name]
  (first (ds/query :kind Ship :filter (= :name name))))


