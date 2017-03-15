(ns aws-dojo.drawing
  (:require [hiccup.core :as hiccup]
            [clojure.spec :as s]
            [clojure.string :as string]))

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

(defn- create-command? [{:keys [command]}]
  (= "createDrawing" command))

(defn- shape? [{:keys [command]}]
  (string/starts-with? command "draw"))

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

(defn- ->name [commands]
  (->> commands
       (filter #(= "renderDrawing" (:command %)))
       first
       :name))

(defn- ->hiccup [commands]
  (let [create (first (filter create-command? commands))
        cmds (filter shape? commands)]
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

(defn render? [{:keys [command]}]
  (= "renderDrawing" command))

(s/fdef render
        :args (s/cat :commands (s/coll-of :drawing/command))
        :ret :drawing/drawing)

(defn render [commands]
  {:drawing/name (->name commands)
   :drawing/html (-> commands ->hiccup hiccup/html)})
