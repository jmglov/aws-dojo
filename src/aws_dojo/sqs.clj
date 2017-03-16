(ns aws-dojo.sqs
  (:require [amazonica.aws.sqs :as sqs]
            [cheshire.core :as json]))

(defn create-queue! [name])

(defn send-message [queue msg])

(defn receive-message [queue])
