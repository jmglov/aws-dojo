(ns aws-dojo.sqs
  (:require [clojure.spec :as s]))

(s/def :sqs/name string?)
(s/def :sqs/url string?)

(s/def :sqs/queue (s/keys :req [:sqs/name
                                :sqs/url]))

(s/fdef create-queue
        :args (s/cat :name :sqs/queue-name)
        :ret :sqs/queue)

(defn create-queue [name])
