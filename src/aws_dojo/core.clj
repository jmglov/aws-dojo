(ns aws-dojo.core
  (:require [amazonica.core :as aws]
            [aws-dojo.drawing :as drawing]
            [aws-dojo.s3 :as s3]
            [aws-dojo.sqs :as sqs]
            [environ.core :refer [env]]))

(defn authenticate! []
  (aws/defcredential
    (env :aws-access-key-id) (env :aws-secret-access-key) (env :aws-region)))

(defn run [queue-name bucket-name]
  (authenticate!))
;(sqs/create-queue! "Johan")
(def queue (sqs/find-queue "Johan"))


(def drawing {:command "createDrawing"
              :title "Hello, world!",
              :name "hello-world",
              :color "#808080",
              :width 640,
              :height 480,
              :shapes 2})
 
(def rectangle {:command "drawRectangle",  
                :color "white",
                :point [20, 40],
                :width 600,
                :height 400,})
(def circle {:command "drawCircle",  
             :color "red",
             :center [320, 240],
             :radius 150,
             :z 1})
(sqs/send-message queue drawing)
(sqs/send-message queue rectangle)
(sqs/send-message queue circle)

(sqs/receive-message queue)

;(filter (fn [command] (not= "createDrawing" (:command command))) commands)
;(first (filter (fn [command] (= "createDrawing" (:command command))) commands))
