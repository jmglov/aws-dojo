(ns aws-dojo.core
  (:require [amazonica.core :as aws]
            [aws-dojo.drawing :as drawing]
            [aws-dojo.s3 :as s3]
            [aws-dojo.sqs :as sqs]
            [environ.core :refer [env]])
  (:import (java.time Instant)))

(defn authenticate! []
  (aws/defcredential
    (env :aws-access-key-id) (env :aws-secret-access-key) (env :aws-region)))

(defn read-commands [queue]
  (loop [commands []]
    (if-let [command (sqs/receive-message queue)]
      (recur (conj commands command))
      commands)))

(defn drawing-key []
  (str "drawing-" (.toEpochMilli (Instant/now)) ".html"))

(defn create-drawing! [queue bucket]
  (when-let [commands (seq (read-commands queue))]
    (->> commands
         drawing/->hiccup
         drawing/render
         (s3/put! bucket (drawing-key)))))

(defn run [queue-name bucket-name]
  (authenticate!)
  (let [queue (sqs/create-queue! queue-name)
        bucket (s3/create-bucket! bucket-name)]
    (while true
      (create-drawing! queue bucket)
      (Thread/sleep 100))))
