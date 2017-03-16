(ns aws-dojo.drawing
  (:require [hiccup.core :as hiccup]))

(defn concatv [& xs]
  (->> (apply concat xs)
       (into [])))

(defn render [commands])
