(ns aws-dojo.drawing
  (:require [hiccup.core :as hiccup]
            [clojure.spec :as s]))

(defn concatv [& xs]
  (->> (apply concat xs)
       (into [])))

(s/def :command/command #{"createDrawing"
                          "drawRectangle"
                          "drawCircle"})

(s/def :drawing/command (s/keys :req-un [:command/command]))

(s/def :drawing/name string?)
(s/def :drawing/html string?)

(s/def :drawing/drawing (s/keys :req [:drawing/name
                                      :drawing/html]))

(s/fdef render
        :args (s/cat :commands (s/coll-of :drawing/command))
        :ret :drawing/drawing)

(defn render [commands])
