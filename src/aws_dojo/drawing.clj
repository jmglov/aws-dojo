(ns aws-dojo.drawing
  (:require [hiccup.core :as hiccup]))

(defn concatv [& xs]
  (->> (apply concat xs)
       (into [])))

(defn ->rect [shape]
  [:rect {:fill (:color shape)
          :width (:width shape)
          :height (:height shape)
          :x (first (:point shape))
          :y (second (:point shape))}])

(defn ->circle [shape]
  [:circle {:fill (:color shape)
            :r (:radius shape)
            :z (:z shape)
            :cx (first (:center shape))
            :cy (second (:center shape))}])

(defn ->shape [command]
  (if (= (:command command) "drawCircle" ) (->circle command) (->rect command)))

(defn render [commands]
  (let [drawing (first (filter (fn [command] (= "createDrawing" (:command command))) commands)),
        shapes (filter (fn [command] (not= "createDrawing" (:command command))) commands)]
    (hiccup/html [:html [:head [:title (:title drawing)]] [:body [:svg 
                                                                  {:width (:width drawing) 
                                                                   :height (:height drawing) 
                                                                   :style (str "background-color:"  (:color drawing))}
                                                                  (map ->shape shapes)]]])))
                                                                    

                                                             

;(filter (fn [command] (not= "createDrawing" (:command command))) commands)
;(first (filter (fn [command] (= "createDrawing" (:command command))) commands))



