(ns viewer.model.ship
  (:require [appengine-magic.services.datastore :as ds]))

(ds/defentity Ship [^{:tag :key} name, data])

(defn put-ship [name data]
  (let [s (Ship. name (ds/as-text (pr-str data)))]
    (ds/save! s)
    name))

(defn get-ship [name]
  (let [ship (ds/retrieve Ship name)]
    ship))

(defn query-ship-info [typeid]
  (cond (instance? Number typeid)

        (instance? String typeid)
        ))

(defn ship-typeid [name]
  (:typeid (query-ship-info name)))

(defn ship-info [typeid]
  (query-ship-info typeid))

