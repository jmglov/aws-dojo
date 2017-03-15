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

(defn- shape? [{:keys [command] :as cmd}]
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

(defn- get-create
  [commands]
  (first (filter create-command? commands)))

(defn- ->hiccup [commands]
  (let [{:keys [title width height color]} (get-create commands)
        cmds (->> commands
                  (filter shape?)
                  (sort-by #(or (:z %) 0)))]
    [:html
     [:head
      [:title title]]
     [:body
      (concatv [:svg {:width width
                      :height height}
                [:rect {:style {:stroke-width 0}
                        :x 0, :y 0
                        :width width, :height height
                        :fill color}]]
               (map ->shape cmds))]]))

(defn render? [commands]
  (= (:shapes (get-create commands))
     (count (filter shape? commands))))

(s/fdef render
        :args (s/cat :commands (s/coll-of :drawing/command))
        :ret :drawing/drawing)

(defn render [commands]
  {:drawing/name (:name (get-create commands))
   :drawing/html (-> commands ->hiccup hiccup/html)})
