(ns aws-dojo.core
  (:require [amazonica.core :as aws]
            [aws-dojo.drawing :as drawing]
            [aws-dojo.s3 :as s3]
            [aws-dojo.sqs :as sqs]
            [environ.core :refer [env]]))

(defn authenticate! []
  (aws/defcredential
    (env :aws-access-key-id) (env :aws-secret-access-key) (env :aws-region)))

(defn read-commands [queue]
  (loop [commands []]
    (if-let [{command :sqs/message-body} (sqs/receive-message queue)]
      (let [commands (conj commands command)]
        (if (drawing/render? commands)
          commands
          (recur commands)))
      (recur commands))))

(defn create-drawing! [queue bucket]
  (when-let [commands (seq (read-commands queue))]
    (let [{:keys [:drawing/name :drawing/html]} (drawing/render commands)]
      (s3/put! bucket name html))))

(defn setup! [queue-name bucket-name]
  (authenticate!)
  [(sqs/create-queue! queue-name)
   (s3/create-bucket! bucket-name)])

(defn run [queue bucket]
  (while true
    (when-let [url (create-drawing! queue bucket)]
      (println url))
    (Thread/sleep 100)))
