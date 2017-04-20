(ns aws-dojo.sqs
  (:require [amazonica.aws.sqs :as sqs]
            [cheshire.core :as json]))

(defn create-queue! [name]
  (sqs/create-queue name))

(defn send-message [queue msg]
  (sqs/send-message queue msg))

(defn receive-message [queue]
  (sqs/receive-message queue))

(defn find-queue [name]
  (sqs/find-queue name))




