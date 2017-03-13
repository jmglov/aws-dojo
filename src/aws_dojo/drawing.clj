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

(defn- create-command? [{:keys [command]}]
  (= "createDrawing" command))

(defn- ->shape [{:keys [command] :as cmd}]
  (case command
    "drawRectangle"
    (let [{:keys [color point width height]} cmd
          [x y] point]
      [:rect {:style {:stroke-width 0}
              :x x, :y y
              :width width, :height height
              :fill color}])

    "drawCircle"
    (let [{:keys [color center radius]} cmd
          [x y] center]
      [:circle {:style {:stroke-width 0}
                :cx x
                :cy y
                :r radius
                :fill color}])))

(s/fdef ->hiccup
        :args (s/cat :commands (s/coll-of :drawing/command))
        :ret vector?)

(defn ->hiccup [commands]
  (let [create (first (filter create-command? commands))
        cmds (remove create-command? commands)]
    [:html
     [:head
      [:title (:title create)]]
     [:body
      (concatv [:svg {:width (:width create)
                      :height (:height create)}
                [:rect {:style {:stroke-width 0}
                        :x 0, :y 0
                        :width (:width create), :height (:height create)
                        :fill (:color create)}]]
               (map ->shape cmds))]]))

(s/fdef render
        :args (s/cat :hiccup vector?)
        :ret string?)

(defn render [hiccup]
  (hiccup/html hiccup))
